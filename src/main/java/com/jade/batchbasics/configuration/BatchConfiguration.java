package com.jade.batchbasics.configuration;

import com.jade.batchbasics.model.City;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.function.Function;

/**
 * The annotation EnableBatchProcessing enables Spring Batch features and provides a base configuration
 * for setting up batch jobs in an annotated Configuration class
 * <p>
 * EnableBatchProcessing uses a memory-based database, which means that when it is gone, the data is gone
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;


    /**
     * reads data from the csv file
     */
    @Bean
    public FlatFileItemReader<City> reader() {
        System.out.println("-----------Inside reader() method--------");
        FlatFileItemReader<City> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("BackEnd.csv"));
        reader.setLineMapper(new DefaultLineMapper<City>() {{
                                 setLineTokenizer(new DelimitedLineTokenizer() {{
                                                      setNames("ibge_id","uf", "name", "capital", "lon", "lat", "no_accents", "alternative_names", "microregion", "mesoregion");
                                                  }}
                                 );
                                 setFieldSetMapper(new BeanWrapperFieldSetMapper<City>() {{
                                                       setTargetType(City.class);
                                                   }}
                                 );
                             }}
        );
        return reader;
    }


    /**
     * performs the processing defined in the class CityProcessor before inserting the data into the database
     */
    @Bean
    public CityProcessor processor() {
        System.out.println("-----------Inside  processor() method--------");
        return  new CityProcessor();
    }


    /**
     * writes data into the database
     */
    @Bean
    public JdbcBatchItemWriter<City> writer() {
        System.out.println("-----------Inside writer() method--------");
        JdbcBatchItemWriter<City> writer = new JdbcBatchItemWriter<City>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<City>()
        );
        writer.setSql("INSERT INTO city (ibge_id, uf,name,capital,lon,lat,no_accents,alternative_names,microregion,mesoregion) VALUES (:ibge_id, :uf,:name,:capital,:lon,:lat,:no_accents,:alternative_names,:microregion,:mesoregion)");
        writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public Job importCityJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importCityJob")
                .listener(listener).flow(step1()).end().build();
    }


    /**
     * chunk size was determined as the amount of rows
     */
    @Bean Step step1() {
        return stepBuilderFactory.get("step1").<City, City>chunk(5566).reader(reader())
               .processor(processor()).writer(writer()).build();
    }

}

package com.jade.batchbasics.configuration;

import com.jade.batchbasics.model.City;
import org.springframework.batch.item.ItemProcessor;

public class CityProcessor implements ItemProcessor<City, City> {

    @Override
    public City process(City city) throws Exception {
        System.out.println("Processing... " + city);
        System.out.println(city.getName());
        return city;
    }
}

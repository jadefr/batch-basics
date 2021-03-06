# batch-basics

O __Spring Batch__ foi utilizado para ler um arquivo csv e armazenar os registros em uma tabela SQL.

O arquivo utilizado contém informações sobre todos os municípios do Brasil. Os dados foram mapeados em dez colunas.

A documentação do Spring Batch pode ser encontrada em https://docs.spring.io/spring-batch/docs/current/reference/html/index.html

Projeto baseado em https://bitbucket.org/ramram43210/java_spring_2019/src/master/JavaEE_2020/SpringBoot_BatchService/

## Tech Stack

* Java 8
* SQL

## Dependências Utilizadas

* Spring Web
* Spring Batch

## Estrutura Base do Projeto

    src
      ├ java
      |  ├ configuration
      |  |  └ BatchConfiguration.java 
      |  |  └ CityProcessor.java
      |  |  └ JobCompletionNotificationListener.java 
      |  ├ controller
      |  |  └ JobInvokerController.java
      |  ├ model
      |  |  └ City.java
      |  └ App.java
      └ resources 
        └ application.properties
        └ arquivo.csv
        

## Classe BatchConfiguration.java 

A seguir são explicados os métodos da classe.

###  FlatFileItemReader<City> reader()

Um objeto reader, pertencente à classe FlatFileItemReader<>, lê o arquivo.csv e transforma os campos a serem mapeados em colunas, através do método _DelimitedLineTokenizer()_, definido no método _setLineMapper()_ do reader, ambos nativos do Spring Batch.

### CityProcessor processor()

Executa o processamento, definido na classe CityProcessor.java, antes de inserir os registros no banco de dados.

### JdbcBatchItemWriter<City> writer()
    
Um objeto writer, da classe JdbcBatchItemWriter<> do Spring Batch, é instanciado para inserir os registros no banco. A inserção é feita através dos métodos _setSql()_ e _setDataSource_.

### Step step1()

Intancia o step, que é a unidade fundamental do job, utilizada para configurar as informações necessárias para definir e controlar o processamento em batch. O step envolve o reader, processor e writer.

### Job importCityJob(JobCompletionNotificationListener listener)

Instancia o job, construído através do step.

        
## Conexão com o Banco de Dados
  
  Arquivo _application.properties_:
  
    ## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
    spring.datasource.url=jdbc:mysql://localhost:3306/regions?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    spring.datasource.username=
    spring.datasource.password=
    spring.batch.initialize-schema=ALWAYS
    spring.batch.job.enabled=false

    # Dialeto SQL para melhorar o SQL gerado pelo Hibernate
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect


Código para gerar a tabela, em SQL:

    CREATE DATABASE regions;
    
    USE regions;
    
    CREATE TABLE city
    (
        ibge_id           varchar(7)   NOT NULL,
        uf                varchar(100) NULL,
        name              varchar(100) NULL,
        capital           varchar(100) NULL,
        lon               varchar(100) NULL,
        lat               varchar(100) NULL,
        no_accents        varchar(100) NULL,
        alternative_names varchar(100) NULL,
        microregion       varchar(100) NULL,
        mesoregion        varchar(100) NULL,
        PRIMARY KEY (ibge_id)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8;
    

## JobInvoker
  
  Para que o job seja inicializado, é necessário chamar o controller:
  
    http://localhost:8080/run-batch-job
    
  
 Alternativamente, para que o job seja inicializado automaticamente (sem a necessidade de se chamar o controller), deve-se omitir a seguinte linha do _application.properties_:
 
    spring.batch.job.enabled=false
  
## Rerun

Para reexecutar o programa, é necessário dar um DROP DATABASE e criá-lo novamente, assim como a tabela.

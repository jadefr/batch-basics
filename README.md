# batch-basics

O Spring Batch foi utilizado para ler um arquivo csv e armazenar os registros em uma tabela SQL.

O arquivo utilizado contém informações sobre todos os municípios do Brasil. Os dados foram mapeados em dez colunas.

Projeto baseado em https://bitbucket.org/ramram43210/java_spring_2019/src/master/JavaEE_2020/SpringBoot_BatchService/

# Tech Stack

* Java 8
* SQL

# Dependências Utilizadas

* Spring Web
* Spring Batch

# Estrutura Base do Projeto

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

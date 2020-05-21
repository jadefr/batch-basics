package com.jade.batchbasics;

import com.jade.batchbasics.configuration.BatchConfiguration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchBasicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchBasicsApplication.class, args);
    }

}

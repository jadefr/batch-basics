package com.jade.batchbasics.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

public class JobInvokerController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importCityJob")
    Job importCityJob;

    @RequestMapping("/run-batch-job")
    public String handle() throws Exception
    {

        JobParameters jobParameters = new JobParametersBuilder().addString("source", "Spring Boot")
                .toJobParameters();
        jobLauncher.run(importCityJob, jobParameters);

        return "Batch job has been invoked";
    }
}

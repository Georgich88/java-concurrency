package com.georgeisaev.fillthegaps.executorspringdefaulttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ExecutorSpringDefaultTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExecutorSpringDefaultTestApplication.class, args);
    }

}

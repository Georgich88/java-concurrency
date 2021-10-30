package com.georgeisaev.fillthegaps.executorspringdefaulttest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class SampleService {

    private final AsyncSampleService asyncSampleService;

    public SampleService(AsyncSampleService asyncSampleService) {
        this.asyncSampleService = asyncSampleService;
    }

    @PostConstruct
    void postConstruct() {
        asyncSampleService.doSomething();
    }

}

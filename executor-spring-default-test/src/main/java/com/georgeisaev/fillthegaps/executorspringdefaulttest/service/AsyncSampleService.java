package com.georgeisaev.fillthegaps.executorspringdefaulttest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncSampleService {

    private final BeanFactory beanFactory;

    public AsyncSampleService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Async
    public void doSomething() {
        log.info("Do something asynchronously");
        log.info("Default executor {}", beanFactory.getBean("applicationTaskExecutor"));
    }

}

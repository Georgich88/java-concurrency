package com.georgeisaev.fillthegaps.sharedvariables.starvation;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class StarvationExample {

    volatile boolean isDone;

    public static void main(String[] args) {
        new StarvationExample().doUntilDone();
    }

    void doUntilDone() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (!isDone) {
                log.info("Wait until done");
                doSleep();
            }
        });

        executorService.submit(() -> {
            isDone = true;
        });
    }

    @SneakyThrows
    private void doSleep() {
        Thread.sleep(100);
    }

}

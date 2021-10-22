package com.georgeisaev.fillthegaps.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongTask implements Runnable {


    private static final int DEFAULT_WAIT_MILLIS = 300_000;
    private final int waitMillis;

    LongTask() {
        this(DEFAULT_WAIT_MILLIS);
    }

    LongTask(int waitMillis) {
        this.waitMillis = waitMillis;
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            log.info("Start long task {}", Thread.currentThread());
            Thread.sleep(waitMillis);
            log.info("Finish long task {}", Thread.currentThread());
        } catch (InterruptedException e) {
            log.error("Error during thread execution", e);
            throw e;
        }
    }


    /**
     * Returns an instance of the task that runs for 30 seconds
     *
     * @return the long task
     */
    public static Runnable longTask() {
        return new LongTask();
    }

    /**
     * Returns an instance of the task that runs specific amount of time {@code waitMillis}
     *
     * @param waitMillis wait time for long task
     * @return the long task
     */
    public static Runnable longTask(int waitMillis) {
        return new LongTask(waitMillis);
    }

}

package com.georgeisaev.fillthegaps.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Task implements Runnable {

    private static ThreadLocal<Integer> value = ThreadLocal.withInitial(() -> 0);

    @Override
    public void run() {
        int currentValue = value.get();
        value.set(currentValue + 1);
        log.info(value.get() + " ");
    }


    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) {
            executor.submit(new Task());
        }
    }

}

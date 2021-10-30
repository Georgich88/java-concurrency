package com.georgeisaev.fillthegaps.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ExecutorTypesSample {

    public static void main(String[] args) {

        log.info("\nINSTANCE OF ThreadPoolExecutor:");

        log.info("Executors.newWorkStealingPool(): {}", instanceOfThreadPoolExecutor(Executors.newWorkStealingPool()));
        log.info("Executors.newSingleThreadExecutor(): {}", instanceOfThreadPoolExecutor(Executors.newSingleThreadExecutor()));
        log.info("Executors.newFixedThreadPool(16): {}", instanceOfThreadPoolExecutor(Executors.newFixedThreadPool(16)));
        log.info("Executors.newCachedThreadPool(): {}", instanceOfThreadPoolExecutor(Executors.newCachedThreadPool()));
        log.info("Executors.newScheduledThreadPool(8): {}", instanceOfThreadPoolExecutor(Executors.newScheduledThreadPool(8)));
        log.info("new ForkJoinPool(): {}", instanceOfThreadPoolExecutor(new ForkJoinPool()));
        log.info("new new ThreadPoolExecutor(...): {}",
                instanceOfThreadPoolExecutor(new ThreadPoolExecutor(1, 1, 1L, TimeUnit.DAYS, new LinkedBlockingDeque<>())));

        log.info("\nCLASS IS ThreadPoolExecutor:");

        log.info("Executors.newWorkStealingPool(): {}", isThreadPoolExecutor(Executors.newWorkStealingPool()));
        log.info("Executors.newSingleThreadExecutor(): {}", isThreadPoolExecutor(Executors.newSingleThreadExecutor()));
        log.info("Executors.newFixedThreadPool(16): {}", isThreadPoolExecutor(Executors.newFixedThreadPool(16)));
        log.info("Executors.newCachedThreadPool(): {}", isThreadPoolExecutor(Executors.newCachedThreadPool()));
        log.info("Executors.newScheduledThreadPool(8): {}", isThreadPoolExecutor(Executors.newScheduledThreadPool(8)));
        log.info("new ForkJoinPool(): {}", isThreadPoolExecutor(new ForkJoinPool()));
        log.info("new new ThreadPoolExecutor(...): {}",
                isThreadPoolExecutor(new ThreadPoolExecutor(1, 1, 1L, TimeUnit.DAYS, new LinkedBlockingDeque<>())));

        log.info("\nTYPE OF:");

        log.info("Executors.newWorkStealingPool(): {}", typeOf(Executors.newWorkStealingPool()));
        log.info("Executors.newSingleThreadExecutor(): {}", typeOf(Executors.newSingleThreadExecutor()));
        log.info("Executors.newFixedThreadPool(16): {}", typeOf(Executors.newFixedThreadPool(16)));
        log.info("Executors.newCachedThreadPool(): {}", typeOf(Executors.newCachedThreadPool()));
        log.info("Executors.newScheduledThreadPool(8): {}", typeOf(Executors.newScheduledThreadPool(8)));
        log.info("new ForkJoinPool(): {}", typeOf(new ForkJoinPool()));
        log.info("new new ThreadPoolExecutor(...): {}",
                typeOf(new ThreadPoolExecutor(1, 1, 1L, TimeUnit.DAYS, new LinkedBlockingDeque<>())));

    }

    private static boolean instanceOfThreadPoolExecutor(Object object) {
        return object instanceof ThreadPoolExecutor;
    }

    private static boolean isThreadPoolExecutor(Object object) {
        return ThreadPoolExecutor.class.equals(object.getClass());
    }

    private static String typeOf(Object object) {
        return object.getClass().getName();
    }

}

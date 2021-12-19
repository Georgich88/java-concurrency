package com.georgeisaev.fillthegaps.forkjoinpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class ForkJoinSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        averageExecutorService();
    }

    private static void averageForkJoinPool() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.invoke(new ForkJoinTask<Object>() {
            @Override
            public Object getRawResult() {
                return null;
            }
            @Override
            protected void setRawResult(Object value) {

            }
            @Override
            protected boolean exec() {
                return false;
            }
        });
    }

    private static void averageExecutorService() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<?> result = executor.submit(() -> System.out.println(
                IntStream.range(0, 10000000).average().getAsDouble()
        ));
        result.get();
        executor.shutdown();
    }

}

package com.georgeisaev.fillthegaps.volatiles;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The variable {@code latestValue} is available for many threads.
 * One of them calls the following code:
 * <pre>
 *     latestValue = latestValue + 4;
 *     System.out.println(latestValue);
 * </pre>
 * Other threads can also change {@code latestValue} variable.
 * What can be printed to console?
 */
@Slf4j
public class VolatileSample {

    volatile int latestValue = 0;

    public static void main(String[] args) throws InterruptedException {
        VolatileSample volatileSample = new VolatileSample();
        Set<Callable<Void>> tasks = volatileSample.generateTasks();
        tasks.add(volatileSample::generateMainTask);
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.invokeAll(tasks);
    }

    Void generateMainTask() {
        latestValue = latestValue + 4;
        log.info("Main task result {}", latestValue);
        return null;
    }

    Set<Callable<Void>> generateTasks() {
        return IntStream.range(0, 100).mapToObj(v -> (Callable<Void>) this::addRandom).collect(Collectors.toSet());
    }

    Void addRandom() {
        latestValue = latestValue + ThreadLocalRandom.current().nextInt(2);
        log.info("Subtle task {}", latestValue);
        return null;
    }

}
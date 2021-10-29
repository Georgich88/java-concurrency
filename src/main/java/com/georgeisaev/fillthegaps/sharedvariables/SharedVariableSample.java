package com.georgeisaev.fillthegaps.sharedvariables;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Slf4j
public class SharedVariableSample {

    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        Executor executor = Executors.newFixedThreadPool(5);
        IntStream.range(0, 100).forEach(i -> executor.execute(() -> {
            byte[] bytes = new byte[2];
            ThreadLocalRandom.current().nextBytes(bytes);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add("X");
        }));
        Executor executor2 = Executors.newFixedThreadPool(5);
        IntStream.range(0, 100).forEach(i -> executor2.execute(() -> {
            int size = list.size();
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(10));
            } catch (InterruptedException e) {
                log.error("Error occurred during sleep", e);
            }
            log.info("Current array {}", Arrays.asList(list.toArray(new String[size])));
            if (list.size() != size) {
                log.info("Data inconsistency {} {}", size, list.size());
            }
        }));
    }

}

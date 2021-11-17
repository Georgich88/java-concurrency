package com.georgeisaev.fillthegaps.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class GridThreadSerialNumberSample {

    public static void main(String[] args) {
        final GridThreadSerialNumberSimple sNum = new GridThreadSerialNumberSimple();
        Executor executor = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(i -> executor.execute(() -> log.info("Thread number {}", sNum.get())));
    }

}

class GridThreadSerialNumber {

    /**
     * The next serial number to be assigned.
     */
    private int nextSerialNum = 0;

    /**
     *
     */
    private ThreadLocal<Integer> serialNum = new ThreadLocal<Integer>() {
        @Override
        protected synchronized Integer initialValue() {
            return nextSerialNum++;
        }
    };

    /**
     * @return Serial number value.
     */
    public int get() {
        return serialNum.get();
    }

}

class GridThreadSerialNumberSimple {

    /**
     * The next serial number to be assigned.
     */
    private AtomicInteger nextSerialNum = new AtomicInteger();

    /**
     *
     */
    private final ThreadLocal<Integer> serialNum = ThreadLocal.withInitial(nextSerialNum::incrementAndGet);

    /**
     * @return Serial number value.
     */
    public int get() {
        return serialNum.get();
    }

    public void unload() {
        serialNum.remove(); // Compliant }

    }

}
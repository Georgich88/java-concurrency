package com.georgeisaev.fillthegaps.sharedvariables.synchronizedblock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

@Slf4j
public class SynchronizedBlockSample {

    private static final AtomicInteger STATIC_COUNTER = new AtomicInteger();
    private final AtomicInteger counter;

    public SynchronizedBlockSample() {
        this.counter = new AtomicInteger();
        new SynchronizedBlockSample().lockingMethod();
        Object monitor = new Object();
        Runnable taskWithSynchronized = () -> {
            synchronized (monitor) {
                log.info("Into synchronized lambda block");
            }
        };
        ForkJoinPool.commonPool().execute(taskWithSynchronized);
        new NestedClass().nestedClassMethod();
    }

    public static void main(String[] args) {
        lockingStaticMethod();
    }

    static synchronized void lockingStaticMethod() {
        log.info("Into lockingStaticMethod() block");
        STATIC_COUNTER.incrementAndGet();
        log.info("Static counter {}", STATIC_COUNTER.get());
    }

    synchronized void lockingMethod() {
        log.info("Into lockingMethod() block");
        counter.incrementAndGet();
        log.info("Counter {}", STATIC_COUNTER.get());
    }

    class NestedClass {

        synchronized void nestedClassMethod() {
            log.info("Into nestedClassMethod() block");
            counter.incrementAndGet();
            log.info("Counter {}", counter.get());
        }

    }

}

class ReadUpdateTest {

    int value = 0;
    private final Object readLock = new Object();
    private final Object writeLock = new Object();

    public int read() {
        synchronized (readLock) {
            return value;
        }
    }

    public void update(int value) {
        synchronized (writeLock) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

    }

}

@Slf4j
class UpdateClearTest {

    Set<Integer> set;

    public UpdateClearTest() {
        set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
    }

    @SneakyThrows
    public void update() {
        synchronized (set) {
            // T1 stop
            log.info("start update()");
            Thread.sleep(25_000);
            log.info("end update()");
        }
    }

    public void clear() {
        log.info("start clear()");
        set.clear();
        log.info("{}", set);
        log.info("end clear()");
    }

    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main()");
        UpdateClearTest updateClearTest = new UpdateClearTest();
        Runnable r1 = updateClearTest::update;
        Runnable r2 = updateClearTest::clear;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(r1);
        executor.submit(r2);
        log.info("end main()");
    }

}
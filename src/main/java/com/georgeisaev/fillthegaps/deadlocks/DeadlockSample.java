package com.georgeisaev.fillthegaps.deadlocks;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class DeadlockSample {

    private final List<Monitor> monitors;

    public DeadlockSample(int mutexNumber) {
        this.monitors = new CopyOnWriteArrayList<>();
        for (int i = 0; i < mutexNumber; i++) {
            monitors.add(new Monitor(i));
        }
    }

    public static void main(String[] args) {
        new DeadlockSample(4).tryLock(4);
    }

    public void tryLock(int threadNumber) {
        List<AsyncTask> tasks = IntStream.range(0, threadNumber)
                .mapToObj(index -> new AsyncTask(index,
                        monitors.get(index),
                        monitors.get(index + 2 >= monitors.size() ? (index + 2) % monitors.size() : index + 2),
                        monitors.get(index + 4 >= monitors.size() ? (index + 4) % monitors.size() : index + 4)))
                .collect(Collectors.toList());
        Executor executor = Executors.newFixedThreadPool(threadNumber);
        tasks.forEach(executor::execute);
    }

    @ToString
    class AsyncTask implements Runnable {

        private final int index;
        private final Monitor firstResource;
        private final Monitor secondResource;
        private final Monitor thirdResource;

        AsyncTask(int index, Monitor firstResource, Monitor secondResource, Monitor thirdResource) {
            this.index = index;
            this.firstResource = firstResource;
            this.secondResource = secondResource;
            this.thirdResource = thirdResource;
        }

        @Override
        public void run() {
            try {
                synchronized (firstResource) {
                    log.info("Try lock {} by {}, attempt {}", firstResource, this, 1);
                    Thread.sleep(1000);
                    synchronized (secondResource) {
                        log.info("Try lock {} by {}, attempt {}", secondResource, this, 2);
                        Thread.sleep(1000);
                        synchronized (thirdResource) {
                            log.info("Try lock {} by {}, attempt {}", thirdResource, this, 2);
                            Thread.sleep(1000);
                        }
                    }
                }
            } catch (InterruptedException e) {
                log.error("Exception occurred {}", e);
            }
        }


    }

    static class Pair<V> extends AtomicMarkableReference<V> {

        /**
         * Creates a new {@code AtomicMarkableReference} with the given
         * initial values.
         *
         * @param initialRef  the initial reference
         * @param initialMark the initial mark
         */
        public Pair(V initialRef, boolean initialMark) {
            super(initialRef, initialMark);
        }

        @Override
        public String toString() {
            return "Pair{" + this.getReference() + "=" + this.isMarked() + "}";
        }

    }

    @ToString
    static class Monitor {

        private final int index;

        Monitor(int index) {
            this.index = index;
        }

    }

}

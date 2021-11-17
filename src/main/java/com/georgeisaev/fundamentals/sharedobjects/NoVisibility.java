package com.georgeisaev.fundamentals.sharedobjects;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public class NoVisibility {

    private static boolean ready;
    private static long number;

    public static class ReaderThread extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            log.info("number {}", number);
            new WriterThread().start();
        }

    }

    public static class WriterThread extends Thread {

        @Override
        public void run() {
            while (!ready) {
                number++;
            }
            log.info("number {}", number);
        }

    }

    public static void main(String[] args) {
        IntStream.range(0, 5).forEach(i -> new ReaderThread().start());
        Thread thread = new Thread(() -> {
            number = 42;
            ready = true;
            Thread.yield();
        });
        thread.start();

    }

}

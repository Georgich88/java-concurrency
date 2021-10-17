package com.georgeisaev.fillthegaps.jmm.publication;

import java.util.concurrent.atomic.AtomicInteger;

public class PublicationDemo {

    static class UnsafeState {

        int a;

    }

    public static UnsafeState usafeState;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < 1_000; i++) {
            Thread t1 = new Thread(() -> {
                UnsafeState s = new UnsafeState();
                s.a = 1;
                usafeState = s;
            });
            Thread t2 = new Thread(() -> {
                int value = usafeState != null ? usafeState.a : 0;
                if (value != 1) { // fileds are not published yet
                    count.incrementAndGet();
                }
                usafeState = null;
            });
            t1.start();
            t2.start();
            t2.join();
            t1.join();
        }
        System.out.println(count);
        System.out.println("done");
    }

}

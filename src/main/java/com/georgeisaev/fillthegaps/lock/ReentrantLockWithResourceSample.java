package com.georgeisaev.fillthegaps.lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWithResourceSample {

    public static final String PATH = "C:\\dev\\edu\\java-concurrency\\src\\main\\resources\\lock.file";
    private final Lock lock = new ReentrantLock();

    public void updateRegistry(File file) {
        try (InputStream in = new FileInputStream(file)) {
            lock.lock();
            // ...
        } catch (IOException fnf) { /*...*/ } finally {
            lock.unlock();
            /*...*/
        }
    }

    public static void main(String[] args) {
        ReentrantLockWithResourceSample updater = new ReentrantLockWithResourceSample();
        var pool = Executors.newFixedThreadPool(5);
        pool.execute(updater.lock::lock);
        pool.execute(() -> updater.updateRegistry(new File(PATH + "wrong")));

    }

}

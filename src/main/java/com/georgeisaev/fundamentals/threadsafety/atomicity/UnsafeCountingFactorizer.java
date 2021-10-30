package com.georgeisaev.fundamentals.threadsafety.atomicity;

import com.georgeisaev.annotation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * Illustration of a weak concurrent implementation.
 * <p>
 * Lost update, because incrementing {@code count++} is not atomic.
 */
@Slf4j
@NotThreadSafe
public class UnsafeCountingFactorizer {

    private long count;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UnsafeCountingFactorizer factorizer = new UnsafeCountingFactorizer();
        @SuppressWarnings("unchecked")
        CompletableFuture<Void>[] tasks = IntStream.range(0, 100)
                .mapToObj(id -> CompletableFuture.runAsync(factorizer::service))
                .toArray(CompletableFuture[]::new);
        // Combining tasks and retrieve a min price
        CompletableFuture.allOf(tasks).join();
        log.info("Count: {}", factorizer.getCount());
    }

    public void service() {
        count++;
    }

    // Getters

    public long getCount() {
        return count;
    }

}

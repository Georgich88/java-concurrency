package com.georgeisaev.fundamentals.threadsafety.atomicity;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.NotThreadSafe;

import java.math.BigInteger;
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
public class UnsafeCountingFactorizer extends GenericServlet implements FactorizerServlet {

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

    @Override
    public void service() {
        service(null, null);
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        BigInteger i = extractFromRequest(request);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(response, factors);
    }


    // Getters

    public long getCount() {
        return count;
    }


}

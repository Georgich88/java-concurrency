package com.georgeisaev.fillthegaps.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> log.info("Do something"));
        CompletableFuture.supplyAsync(() -> {
            log.info("1");
            return new Object();
        }).thenApply(o -> {
            log.info("2");
            return new Object();
        }).thenApplyAsync(o -> {
            log.info("3");
            return new Object();
        }).thenRun(() -> log.info("4"));
        Thread.sleep(55_000);
    }

}

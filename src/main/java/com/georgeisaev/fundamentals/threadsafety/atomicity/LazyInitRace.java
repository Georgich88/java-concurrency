package com.georgeisaev.fundamentals.threadsafety.atomicity;

import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Illustrates race conditions in lazy initialization
 */
@Slf4j
public class LazyInitRace {

    public static final int THREADS = 10;
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) {
        LazyInitRace lazyInitRace = new LazyInitRace();
        Executor executor = Executors.newFixedThreadPool(THREADS);
        IntStream.range(0, THREADS).forEach(ignored -> executor.execute(
                () -> log.info("{}", lazyInitRace.getInstance())));
    }

}

@ToString
class ExpensiveObject {

    private final UUID id;

    @SneakyThrows
    public ExpensiveObject() {
        id = UUID.randomUUID();
        Thread.sleep(6);
    }

}

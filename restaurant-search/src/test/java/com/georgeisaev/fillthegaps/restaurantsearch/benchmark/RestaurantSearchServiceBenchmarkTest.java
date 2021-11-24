package com.georgeisaev.fillthegaps.restaurantsearch.benchmark;

import com.georgeisaev.fillthegaps.restaurantsearch.service.impl.RestaurantSearchServiceConcurrentHashMapImpl;
import com.georgeisaev.fillthegaps.restaurantsearch.service.impl.RestaurantSearchServiceConcurrentHashMapLongAdderImpl;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@ContextConfiguration(classes = RestaurantSearchServiceBenchmarkTestConfig.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RestaurantSearchServiceBenchmarkTest {

    volatile AnnotationConfigApplicationContext context;
    private RestaurantSearchServiceConcurrentHashMapImpl restaurantSearchServiceConcurrentHashMap;
    private RestaurantSearchServiceConcurrentHashMapLongAdderImpl restaurantSearchServiceConcurrentHashMapLongAdder;

    @Setup(Level.Trial)
    public void setupContext() {
        this.context = new AnnotationConfigApplicationContext();
        context.register(RestaurantSearchServiceConcurrentHashMapImpl.class);
        context.register(RestaurantSearchServiceConcurrentHashMapLongAdderImpl.class);
        context.refresh();
        restaurantSearchServiceConcurrentHashMap =
                this.context.getBean(RestaurantSearchServiceConcurrentHashMapImpl.class);
        restaurantSearchServiceConcurrentHashMapLongAdder =
                this.context.getBean(RestaurantSearchServiceConcurrentHashMapLongAdderImpl.class);
    }

    @TearDown(Level.Trial)
    public void closeContext() {
        this.context.close();
    }

    @Test
    void executeJmhRunner() throws RunnerException {
        Options jmhRunnerOptions = new OptionsBuilder()
                // set the class name regex for benchmarks to search for to the current class
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(3)
                .measurementIterations(10)
                // do not use forking or the benchmark methods will not see references stored within its class
                .forks(0)
                // do not use multiple threads
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result("/dev/null") // set this to a valid filename if you want reports
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(jmhRunnerOptions).run();
    }

    @Benchmark
    public void addToStatLongAdder() {
        IntStream.range(0, 100).parallel().forEach(i ->
                restaurantSearchServiceConcurrentHashMapLongAdder.addToStat(randomRestaurant()));
    }

    @Benchmark
    public void addToStatLong() {
        IntStream.range(0, 100).parallel().forEach(i ->
                restaurantSearchServiceConcurrentHashMap.addToStat(randomRestaurant()));
    }

    private static String randomRestaurant() {
        return RESTAURANTS[ThreadLocalRandom.current().nextInt(RESTAURANTS.length)];
    }


    private static final String[] RESTAURANTS = {
            "Twins Garden",
            "ARTEST - Chef’s Table",
            "Selfie", "Beluga",
            "Grand Cru",
            "Biologie",
            "Sakhalin",
            "Savva",
            "Bjorn",
            "Graspoort",
            "Restaurant La Vista"
    };

}


@Configuration
@Import({RestaurantSearchServiceConcurrentHashMapImpl.class,
        RestaurantSearchServiceConcurrentHashMapLongAdderImpl.class})
class RestaurantSearchServiceBenchmarkTestConfig {

}
package com.georgeisaev.fillthegaps.completablefuture.benchmark;

import com.georgeisaev.fillthegaps.completablefuture.service.PriceAggregatorService;
import com.georgeisaev.fillthegaps.completablefuture.service.PriceRetrieverService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@ContextConfiguration(classes = PriceAggregatorServiceBenchmarkTestConfig.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PriceAggregatorServiceBenchmarkTest {

    volatile AnnotationConfigApplicationContext context;
    private PriceRetrieverService priceRetrieverService;
    private PriceAggregatorService priceAggregatorService;

    @Setup(Level.Trial)
    public void setupContext() {
        this.context = new AnnotationConfigApplicationContext();
        context.register(PriceRetrieverService.class);
        context.register(PriceAggregatorService.class);
        context.refresh();
        priceRetrieverService = this.context.getBean(PriceRetrieverService.class);
        priceAggregatorService = this.context.getBean(PriceAggregatorService.class);
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
                .measurementIterations(3)
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
    public void shouldFindPrice() {
        priceRetrieverService.findPrice(1L, 1L);
    }

    @Benchmark
    public void shouldFindMinPrice() {
        priceAggregatorService.findMinPrice(1L);
    }

}


@Configuration
@Import({PriceAggregatorService.class, PriceRetrieverService.class})
class PriceAggregatorServiceBenchmarkTestConfig {

}

//@SpringBootApplication
//class PriceAggregatorServiceBenchmarkTestApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(PriceAggregatorServiceBenchmarkTestApplication.class, args);
//    }
//
//}
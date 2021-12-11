package com.georgeisaev.fillthegaps.immutableobjectorder.service;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;
import com.georgeisaev.fillthegaps.immutableobjectorder.service.impl.ImmutableOrderServiceImpl;
import com.georgeisaev.fillthegaps.immutableobjectorder.service.impl.MutableOrderServiceImpl;
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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@ContextConfiguration(classes = OrderServiceBenchmarkTestConfig.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class OrderServiceBenchmarkTest {

    private static final int ITERATION_SIZE = 100;
    private static final PaymentInfo PAYMENT_INFO = new PaymentInfo("");
    volatile AnnotationConfigApplicationContext context;
    private ImmutableOrderServiceImpl immutableOrderService;
    private MutableOrderServiceImpl mutableOrderService;

    @Setup(Level.Trial)
    public void setupContext() {
        this.context = new AnnotationConfigApplicationContext();
        context.register(ImmutableOrderServiceImpl.class);
        context.register(MutableOrderServiceImpl.class);
        context.refresh();
        immutableOrderService = this.context.getBean(ImmutableOrderServiceImpl.class);
        mutableOrderService = this.context.getBean(MutableOrderServiceImpl.class);
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
    public void shouldProceedImmutable() {
        IntStream.range(0, ITERATION_SIZE)
                .parallel()
                .forEach(i -> {
                    long orderId = immutableOrderService.createOrder(generateItems(10));
                    if (i % 2 == 0) {
                        immutableOrderService.updatePaymentInfo(orderId, PAYMENT_INFO);
                    } else {
                        immutableOrderService.setPacked(orderId);
                    }
                });
    }

    @Benchmark
    public void shouldProceedMutable() {
        IntStream.range(0, ITERATION_SIZE)
                .parallel().
                forEach(i -> {
                    long orderId = mutableOrderService.createOrder(generateItems(10));
                    if (i % 2 == 0) {
                        mutableOrderService.updatePaymentInfo(orderId, PAYMENT_INFO);
                    } else {
                        mutableOrderService.setPacked(orderId);
                    }
                });
    }

    static List<Item> generateItems(int size) {
        return IntStream.range(0, size).mapToObj(i -> new Item(String.valueOf(i))).collect(Collectors.toList());
    }

}


@Configuration
@Import({ImmutableOrderServiceImpl.class,
        MutableOrderServiceImpl.class})
class OrderServiceBenchmarkTestConfig {

}
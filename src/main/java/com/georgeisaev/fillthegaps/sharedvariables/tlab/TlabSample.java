package com.georgeisaev.fillthegaps.sharedvariables.tlab;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Slf4j
@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TlabSample {

    public static void main(String[] args) throws RunnerException {
        Options jmhRunnerOptions = new OptionsBuilder()
                // set the class name regex for benchmarks to search for to the current class
                .include("\\." + TlabSample.class.getSimpleName() + "\\.")
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(3)
                // do not use multiple threads
                .threads(1)
                .resultFormat(ResultFormatType.JSON)
                .result("/dev/null") // set this to a valid filename if you want reports
                .shouldFailOnError(true)
                .jvmArgs("-XX:-UseTLAB")
                .build();

        new Runner(jmhRunnerOptions).run();
    }

    @Benchmark
    public void fillObjectArray() {
        final int size = 50_000_000;
        Object[] objects = new Object[size];
        for (int i = 0; i < size; ++i) {
            objects[i] = new Object();
        }
    }

}

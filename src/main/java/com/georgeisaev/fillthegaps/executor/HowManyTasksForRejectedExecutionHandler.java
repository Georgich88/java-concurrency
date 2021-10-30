package com.georgeisaev.fillthegaps.executor;

import com.georgeisaev.annotation.InterviewQuestion;
import com.georgeisaev.fillthegaps.common.LongTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

@Slf4j
@InterviewQuestion(description = "Сколько задач нужно отправить в single, fixed и cached экзекьюторы, " +
        "чтобы для них вызвался RejectedExecutionHandler")
public class HowManyTasksForRejectedExecutionHandler {

    private static final long MAX_TASKS = 2147483647L * 2;

    public static void main(String[] args) {

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        examineExecutorService(singleThreadExecutor, "");

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        examineExecutorService(fixedThreadPool, "");

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        examineExecutorService(cachedThreadPool, "");
    }

    @SneakyThrows
    private static void examineExecutorService(ExecutorService executorService, String name) {
        LongStream.range(0, MAX_TASKS).forEach(i -> {
            var percent = ((int) ((double) i / MAX_TASKS * 100));
            if (percent != 0 && percent % 10 == 0) {
                log.info("{} {} / {}", name, i, MAX_TASKS);
            }
            executorService.execute(LongTask.longTask());
        });
        System.gc();
        Thread.sleep(3_000);
    }

}

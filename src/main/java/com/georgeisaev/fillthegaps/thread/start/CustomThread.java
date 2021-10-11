package com.georgeisaev.fillthegaps.thread.start;

import com.georgeisaev.annotation.InterviewQuestion;

/**
 * Вопрос на знание методов {@link Thread#run()} и {@link Thread#start()}
 */
@InterviewQuestion(description = "Что будет напечатано в консоли")
public class CustomThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new CustomThread();
        thread.run();
        thread.run();
        thread.run();
        thread.start();
        Thread.sleep(1000);
        thread.run();
        thread.run();
        thread.start();
    }

}

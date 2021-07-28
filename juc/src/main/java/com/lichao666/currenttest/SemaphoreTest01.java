package com.lichao666.currenttest;

import java.util.concurrent.Semaphore;

public class SemaphoreTest01 {

    //限流，同时允许多少个线程工作
    static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "开始执行任务");
                    sleep();
                    System.out.println(Thread.currentThread().getName() + "开始执行任务结束");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }

    static void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

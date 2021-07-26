package com.lichao666.threadpool;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FixThreadPoolTest {
    public static void main(String[] args) {
        FixThreadPoolTest f = new FixThreadPoolTest();
        f.init();
    }
    public void init(){
        ScheduledExecutorService respScheduler = new ScheduledThreadPoolExecutor(2);
        System.out.println("task begin:" + System.currentTimeMillis() / 1000);
        respScheduler.scheduleAtFixedRate(this::exe, 2, 3, TimeUnit.SECONDS);
    }
    public void exe(){
        try {
            Thread.sleep(5000);//2000
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "--task run:" + System.currentTimeMillis() / 1000);
    }
}

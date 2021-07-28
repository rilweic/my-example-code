package com.lichao666.currenttest;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest01 implements Runnable{

    CountDownLatch countDownLatch = new CountDownLatch(5);


    public static void main(String[] args) throws InterruptedException {
        CountDownLatchTest01 c = new CountDownLatchTest01();
        for (int i = 0; i < 5; i++) {

            Thread t = new Thread(c,"t"+i);
            t.start();

        }
        c.countDownLatch.await();

        System.out.println("全部线程执行完成");
    }

    @Override
    public void run() {
        sleep();
        System.out.println(Thread.currentThread().getName()+"执行完成");
        countDownLatch.countDown();
    }

    static void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

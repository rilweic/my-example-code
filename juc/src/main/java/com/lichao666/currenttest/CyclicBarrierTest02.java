package com.lichao666.currenttest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest02 {

    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("全部到齐了，开始后续执行");
            }
        });
        for (int i = 0; i < 4*N; i++) {
            new Thread(new MyThread(cyclicBarrier),"t"+i).start();
        }

        System.out.println("主线程结束");


    }
}

class MyThread implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public MyThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + " 执行了，等待其他线程到来");
            this.cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
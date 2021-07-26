package com.lichao666.locktest.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantlockTest03 {

    static long sum = 0L;

    public static void main(String[] args) {

        Thread[] threads = new Thread[1000];
        ReentrantLock lock = new ReentrantLock();
        Object o = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    synchronized (o) {
                        sum++;
                    }

                }
            });
        }

        long startTime = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("sync "+ (System.currentTimeMillis() - startTime));

        System.out.println("--------------------------------");


        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    lock.lock();
                    sum++;
                    lock.unlock();
                }

            });
        }

        startTime = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("lock "+(System.currentTimeMillis() - startTime));


    }
}

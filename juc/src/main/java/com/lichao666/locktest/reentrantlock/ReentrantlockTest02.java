package com.lichao666.locktest.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantlockTest02 {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{
            for (int i = 0; i < 3; i++) {
                lock.lock();
                System.out.println("t1-lock-" + i);
            }

            for (int i = 0; i < 3; i++) {
                lock.unlock();
                System.out.println("t1-unlock-" + i);
            }
        });

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 3; i++) {
                lock.lock();
                System.out.println("t2-lock-" + i);
            }

            for (int i = 0; i < 3; i++) {
                lock.unlock();
                System.out.println("t2-unlock-" + i);
            }
        });

        t1.start();
        t2.start();

    }
}

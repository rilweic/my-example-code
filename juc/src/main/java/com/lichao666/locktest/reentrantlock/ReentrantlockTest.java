package com.lichao666.locktest.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantlockTest {
    Lock lock = new ReentrantLock();

    private int sum = 0;

    public void add(){
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            sum ++;
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantlockTest r = new ReentrantlockTest();

        for (int i = 0; i < 5; i++) {
            new Thread(r::add,"thread"+i).start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(r.sum);

    }

}



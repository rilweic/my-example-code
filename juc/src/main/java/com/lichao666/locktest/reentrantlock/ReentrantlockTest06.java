package com.lichao666.locktest.reentrantlock;

// 公平锁演示

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantlockTest06 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new FairReentrantlock(),"t1").start();
        new Thread(new FairReentrantlock(),"t2").start();
    }

}

class FairReentrantlock implements Runnable{

    static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"获得锁");
            lock.unlock();
        }
    }
}
package com.lichao666.locktest.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// tryLock演示

public class ReentrantlockTest05 {

    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            System.out.println("m1 start sleep forever");
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        try {
//            lock.lock();
            lock.lockInterruptibly(); // 加上此句表示可以对中断做出响应，否则不会做出任何反应
            System.out.println("lockInterruptibly test");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantlockTest05 r = new ReentrantlockTest05();

        Thread t1 = new Thread(r::m1);
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(r::m2);
        t2.start();
        t2.interrupt();

    }

}

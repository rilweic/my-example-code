package com.lichao666.locktest.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// tryLock演示

public class ReentrantlockTest04 {

    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            System.out.println("m1 start");
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("m1 end ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        boolean islocked = false;
        try {

            islocked = lock.tryLock(5, TimeUnit.SECONDS);
            // 不管是否成功，都会执行
            System.out.println("m2 " + islocked);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (islocked) lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantlockTest04 r = new ReentrantlockTest04();

        new Thread(r::m1).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(r::m2).start();
    }

}

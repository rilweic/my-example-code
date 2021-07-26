package com.lichao666.volatiletest;

public class Volatiletest implements Runnable {

    // volatile保证了变量修改的可见性
    volatile boolean alwaysrun = true;

    public static void main(String[] args) {
        Volatiletest t = new Volatiletest();
        new Thread(t, "t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.alwaysrun = false;
        System.out.println("main thread is over");
    }

    @Override
    public void run() {
        System.out.println("thread started");
            while (alwaysrun) {

            }
        System.out.println("thread end");
    }
}



package com.lichao666.createthread;

import java.util.concurrent.Executors;

public class CreateThread01 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread t = new ThreadByExtendsThread();
            t.setName("线程"+i);
            t.start();
        }
        Thread t1 = Executors.defaultThreadFactory().newThread(() -> System.out.println("hello !"));

    }
}

class ThreadByExtendsThread extends Thread{

    @Override
    public void run() {
        long t = 0;
        try {
            t= (long) (Math.random()*2000);
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程名称为："+ Thread.currentThread().getName() + " 耗时："+t);

    }
}

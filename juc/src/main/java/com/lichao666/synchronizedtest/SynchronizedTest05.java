package com.lichao666.synchronizedtest;


import java.util.concurrent.TimeUnit;

public class SynchronizedTest05 {

    public static void main(String[] args) {
        ReentrantSynchronized r = new ReentrantSynchronized();
        r.m1();
    }
}

class ReentrantSynchronized{
    synchronized  void m1(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1 方法 加锁了");
        m2();

    }

    synchronized void m2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 方法 加锁了");
    }
}
package com.lichao666.synchronizedtest;

/**
 * synchronized 关键字加锁
 */
public class SynchronizedTest02 {
    public static void main(String[] args) {
        MyT2 t = new MyT2();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    t.reduce();
                }
            }).start();
        }
    }
}

class MyT2{
    public int number = 1000;

    public synchronized void reduce(){
            try {
                Thread.sleep(1);
                number--;
                System.out.println(Thread.currentThread().getName() +":"+ number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}

class MyT3 {
    public int number = 1000;

    public void reduce() {
        synchronized (this) {


            try {
                Thread.sleep(1);
                number--;
                System.out.println(Thread.currentThread().getName() + ":" + number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyT4 {
    public int number = 1000;
    Object o = new Object();
    public void reduce() {

        synchronized (o) {
            try {
                Thread.sleep(1);
                number--;
                System.out.println(Thread.currentThread().getName() + ":" + number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

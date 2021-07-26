package com.lichao666.synchronizedtest;


public class SynchronizedTest03 {
    public static void main(String[] args) {
        MyT5 t = new MyT5();

        new Thread(t::m1,"t1").start();
        new Thread(t::m2,"t2").start();
    }
}

class MyT5{

    /**
     * 一个加锁
     */
    public synchronized void m1(){

        try {
            System.out.println("m1 start ");
            Thread.sleep(10000);
            System.out.println("m1 end  ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一个不加锁
     */
    public void m2(){

        try {
            System.out.println("m2 start ");
            Thread.sleep(5000);
            System.out.println("m2 end  ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




package com.lichao666.synchronizedtest;

/**
 * 这个类有线程安全问题，因为减去数字的时候会出现竞争
 */
public class SynchronizedTest01 {
    public static void main(String[] args) {
        MyT t = new MyT();
        for (int i = 0; i < 100; i++) {
            new Thread(t::reduce).start();
        }
    }
}

class MyT{
    public int number = 100;


    public void reduce(){
        try {
            Thread.sleep(1);
            number--;
            System.out.println(Thread.currentThread().getName() +":"+ number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}



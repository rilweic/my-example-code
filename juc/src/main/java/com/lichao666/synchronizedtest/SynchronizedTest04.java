package com.lichao666.synchronizedtest;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest04 {

    public static void main(String[] args) {
        Account a = new Account(0.0);
        Thread t1 = new Thread(() -> {
            a.setBalance(100.0);
        });

        t1.start();

        // 先睡一秒，此时 setBalance已经进入了，但是上锁了
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("1111:"+a.getBalance());
        // 再睡一秒
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("2222:"+a.getBalance());


    }
}

class Account{
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    // 此处不加锁会出现脏读现象
    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void setBalance(double balance) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }


}
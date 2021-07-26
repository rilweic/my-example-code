package com.lichao666.locktest.deadlock;

public class DeadLockTest01 implements Runnable{

    // 这里必须为静态类
    static Object a = new Object();
    static Object b = new Object();
    boolean flag;

    public DeadLockTest01(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        if(flag){
            synchronized (a){
                System.out.println("锁定a对象，并睡眠1000毫秒");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    System.out.println("在锁定的a对象中再锁定b");
                }
            }
        }else{
            synchronized (b){
                System.out.println("锁定b对象，并睡眠1000毫秒");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (a){
                    System.out.println("在锁定的b对象中锁定a对象");
                }


            }
        }

    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLockTest01(true));
        t1.start();
        Thread t2 = new Thread(new DeadLockTest01(false));
        t2.start();
    }
}





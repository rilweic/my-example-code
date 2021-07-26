package com.lichao666.createthread;

public class CreateThread02 {
    public static void main(String[] args) {
        for (int i = 0; i <100; i++) {
           Thread t = new Thread(new ThreadByImplementsRunnable());
           t.setName("线程-"+i);
           t.start();
        }
    }
}

class ThreadByImplementsRunnable implements Runnable{

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
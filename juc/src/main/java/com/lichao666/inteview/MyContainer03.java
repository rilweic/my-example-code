package com.lichao666.inteview;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 使用notify 和 wait来实现
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * @param <T>
 */
public class MyContainer03<T> {

    final private LinkedList<T> list = new LinkedList();
    final int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        // 只要容器满了，就等待
        while (list.size() == MAX) {
            try {
                this.wait(); // 等待直到可以继续放
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        this.notifyAll();
    }


    public synchronized T get() {
        while (list.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        --count;
        T t = list.removeFirst();
        this.notifyAll();
        return t;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        MyContainer03<String> c03 = new MyContainer03<>();

        // 先启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        System.out.println(Thread.currentThread().getName() + "消费了:" + c03.get());
                    }
                }
            }, "c-" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {

                    String name = Thread.currentThread().getName();
                    String product = UUID.randomUUID().toString();
                    System.out.println(name + " 生产了 " + name+"_"+product);
                    c03.put(name + "_" + product);
                }
            }, "p-" + i).start();
        }

        System.out.println("主线程结束");
    }
}

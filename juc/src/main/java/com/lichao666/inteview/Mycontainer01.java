package com.lichao666.inteview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
// countdown 需要时间，这个写法有问题
public class Mycontainer01 {
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        Mycontainer01 c = new Mycontainer01();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("add " + i);

                if (c.size() == 5) {
                    countDownLatch.countDown();
                }
                c.add(i);
            }
        });

        Thread query = new Thread(() -> {
            try {
                if (c.size() != 5) {
                    countDownLatch.await();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("query 线程停止");
        });

        add.start();
        query.start();
    }
}

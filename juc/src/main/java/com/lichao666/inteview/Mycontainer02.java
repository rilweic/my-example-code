package com.lichao666.inteview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Mycontainer02 {
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        Mycontainer02 c = new Mycontainer02();
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        Thread add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("add " + i);
                c.add(i);
                if (c.size() == 5) {
                    countDownLatch1.countDown();

                    try {
                        countDownLatch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        Thread query = new Thread(() -> {
            try {
                if (c.size() != 5) {
                    countDownLatch1.await();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch2.countDown();
            System.out.println("query 线程停止");
        });

        add.start();
        query.start();
    }
}

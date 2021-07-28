package com.lichao666.currenttest;

import java.util.concurrent.*;

public class CyclicBarrierTest01 {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {

            @Override
            public void run() {
                System.out.println("好了，大家可以去吃饭了……"  );
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

        System.out.println("要吃饭，必须所有人都到终点，oK?");
        System.out.println("不放弃不抛弃！");

        for (int i = 0; i < 8; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":Go");
                    try {
                        TimeUnit.MICROSECONDS.sleep(10);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+ ":我到终点了");
                    try {
                        barrier.await(); // 可多次调用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+ ":终于可以吃饭啦！");

                }
            });

        }
        exec.shutdown();

    }

}

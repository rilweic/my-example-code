package com.lichao666.atomicxxx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicxxxTest {
    int count1 = 0;
    AtomicInteger count2 = new AtomicInteger(0);

    void m(){
        for (int i = 0; i < 10000; i++) {
            count1++;
            count2.addAndGet(1);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        AtomicxxxTest atomicxxxTest = new AtomicxxxTest();
        List<Thread> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(atomicxxxTest::m,i+""));
        }

        list.forEach((t) -> t.start());

        list.forEach((t) -> {
            try {
                t.join();
                // 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程将可能早于子线程结束。
                // 如果主线程需要知道子线程的执行结果时，就需要等待子线程执行结束了。
                // 主线程可以sleep(xx),但这样的xx时间不好确定，因为子线程的执行时间不确定，join()方法比较合适这个场景。
                // 否则就需要用 Thread.sleep来暂停主线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(atomicxxxTest.count1);
        System.out.println(atomicxxxTest.count2);

    }
}

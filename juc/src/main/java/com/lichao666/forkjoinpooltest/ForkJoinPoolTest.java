package com.lichao666.forkjoinpooltest;

import java.util.concurrent.*;

public class ForkJoinPoolTest {
    private static int NUMBER = 10000000;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPoolTest forkJoinPoolTest = new ForkJoinPoolTest();
        System.out.println(Integer.MAX_VALUE);
        System.out.println("单线程相加的结果："+forkJoinPoolTest.circulateAdd());

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> result = pool.submit(new CalculateTask(0, NUMBER));
        System.out.println("并行执行的结果："+result.get());
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();

    }

    public long circulateAdd() {
        long total = 0l;
        for (int i = 0; i < NUMBER; i++) {
            total += i;
        }
        return total;

    }

}


class CalculateTask extends RecursiveTask<Long> {

    private int start;
    private int end;
    private static final int THRESHOLD = 49;

    public CalculateTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0l;
        if ((end - start) <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            CalculateTask a = new CalculateTask(start, middle);
            CalculateTask b = new CalculateTask(middle, end);
            a.fork();
            b.fork();

            sum = a.join() + b.join();

        }
        return sum;
    }
}
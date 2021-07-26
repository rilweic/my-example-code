package com.lichao666.createthread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CreateThread03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (int i = 0; i < 100; i++) {
            FutureTask<Double> futureTask = new FutureTask<>(() -> Math.random()* 4000);
            Thread t = new Thread(futureTask,"线程-"+i);
            t.start();
            System.out.println(t.getName()+"\t"+futureTask.get());
        }
    }

}



package com.lichao666.shutdownhook;

import java.util.concurrent.TimeUnit;

public class ShutdownHookDemo {
    public static void main(String[] args) throws InterruptedException {
        //将hook线程添加到运行时环境中去
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("hook");
            }
        });
        TimeUnit.SECONDS.sleep(3);
        System.out.println("main done.");

    }
}

package com.lichao666.inteviewA1B2C3;

import java.util.concurrent.locks.LockSupport;

public class A_01_LockSupport {

    static int start = 65;
    static int end = 90;

    static Thread charThread = null;
    static Thread numThread = null;
    public static void main(String[] args) {


        charThread = new Thread(() -> {
            for (int i = start; i <= end; i++) {
                System.out.print((char) i);
                /* 打印完成,将别的线程开启 然后停住自己*/
                LockSupport.unpark(numThread);
                LockSupport.park();

            }
        });

        numThread = new Thread(() -> {
            for (int j = start; j <= end; j++) {
                LockSupport.park();
                System.out.print(j-64);
                LockSupport.unpark(charThread);
            }
        });

        charThread.start();
        numThread.start();

        System.out.println();
    }


}

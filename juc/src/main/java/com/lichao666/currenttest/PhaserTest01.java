package com.lichao666.currenttest;

import java.util.concurrent.Phaser;

/**
 * Phaser 分阶段执行任务，可以将任务成各个阶段来执行，可以替代Countdownlatch 和 cyclicbarrier
 */
public class PhaserTest01 implements Runnable {

    static int number = 5;
    Phaser phaser;

    public PhaserTest01(Phaser phaser) {
        this.phaser = phaser;
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser(number);// 直接指定 parties

        for (int i = 0; i < number; i++) {
            PhaserTest01 p = new PhaserTest01(phaser);
            new Thread(p, "t" + i).start();
        }
    }

    @Override
    public void run() {
        sleep();
        System.out.println(Thread.currentThread().getName() + "已到达...");
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + "继续执行任务...");

    }

    static void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


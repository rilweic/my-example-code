package com.lichao666.currenttest;

import java.util.concurrent.Phaser;

/**
 * Phaser 分阶段执行任务，可以将任务成各个阶段来执行，可以替代Countdownlatch 和 cyclicbarrier
 */
public class PhaserTest02 implements Runnable {

    static int number = 50;
    Phaser phaser;

    public PhaserTest02(Phaser phaser) {
        this.phaser = phaser;
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser();

        for (int i = 0; i < number; i++) {
            phaser.register();
            new Thread(new PhaserTest02(phaser), "t" + i).start();
        }
    }

    @Override
    public void run() {
        int i = phaser.arriveAndAwaitAdvance();
        sleep();
        System.out.println(Thread.currentThread().getName() + "执行完任务，当前阶段为："+i);

    }

    static void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


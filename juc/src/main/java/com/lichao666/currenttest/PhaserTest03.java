package com.lichao666.currenttest;

import java.util.concurrent.Phaser;

/**
 * Phaser 分阶段执行任务，可以将任务成各个阶段来执行
 */
public class PhaserTest03 {


    public static void main(String[] args) {
        Gaokao p = new Gaokao();
        p.bulkRegister(8);
        for (int i = 0; i < 8; i++) {
           Student s = new Student("学生:"+i);
           new Thread(()->{
                s.chifan();
                p.arriveAndAwaitAdvance();

                s.shangche();
                p.arriveAndAwaitAdvance();

                s.kaoshi();
                p.arriveAndAwaitAdvance();
           }).start();
        }

    }


}

class Gaokao extends Phaser{

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase){
            case 0:
                System.out.println("考语文");
                return false;
            case 1:
                System.out.println("考数学");
                return false;
            case 2:
                System.out.println("考理综");
                return false;
            case 3:
                System.out.println("考英语");
                return true;
            default:
                return true;
        }
    }
}

class Student{
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public void chifan(){
        sleep();
        System.out.println(this.name+" 到达吃饭地");
    }

    public void shangche(){
        sleep();
        System.out.println(this.name+" 到达上车地");
    }
    public void kaoshi(){
        sleep();
        System.out.println(this.name+" 考完了");
    }


    static void sleep() {
        try {
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

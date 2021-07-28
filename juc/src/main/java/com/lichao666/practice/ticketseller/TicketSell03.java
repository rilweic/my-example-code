package com.lichao666.practice.ticketseller;

import java.util.ArrayList;
import java.util.List;

/**
 * 将01的案例修改为加锁的方案，但是这种方案的效率并不高
 */
public class TicketSell03 {
    static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("编号-" + i);
        }
    }

    public static void main(String[] args) {
        TicketSell03 sell01 = new TicketSell03();

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                while(true){
                    synchronized (tickets){
                        if(tickets.size() <=0){
                            break;
                        }
                        System.out.println(Thread.currentThread().getName() + "销售了" + tickets.remove(0) + "的票");
                    }
                }

            }).start();
        }
    }


}

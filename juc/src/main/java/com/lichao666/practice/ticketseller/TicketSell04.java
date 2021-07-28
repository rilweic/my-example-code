package com.lichao666.practice.ticketseller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 此类线程安全.
 */
public class TicketSell04 {
    // 并发队列
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000000; i++) {
            tickets.add("编号-" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                while (true) {
                    String ticket = tickets.poll();
                    if(ticket == null){
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + "销售了" + tickets.poll() + "的票");
                }
            }).start();
        }
    }


}

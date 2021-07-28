package com.lichao666.practice.ticketseller;

import java.util.List;
import java.util.Vector;

/**
 * 此类线程安全.
 */
public class TicketSell02 {
    // 将arraylist换成Vector就好了
    static List<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("编号-" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                //这里判断会不安全
                while (tickets.size() > 0) {
                    // 这里 remove不安全
                    System.out.println(Thread.currentThread().getName() + "销售了" + tickets.remove(0) + "的票");
                }
            }).start();
        }
    }


}

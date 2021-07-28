package com.lichao666.practice.ticketseller;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类线程不安全. 1.程序判断余量线程不安全。2.程序移除元素线程不安全。
 */
public class TicketSell01 {
    static List<String> tickets = new ArrayList<>();

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

package com.lichao666.avg;

public class AvgTest {

    public static double avg(int a, int b) {
        return ((a&b) + ((a^b) >> 1));
    }

    public static void main(String[] args) {
        double res = avg(4, 8);
        System.out.println(res);
    }
}

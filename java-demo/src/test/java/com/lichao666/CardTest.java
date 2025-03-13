package com.lichao666;

import com.game.doudizhu.Card;

public class CardTest {
    public static void main(String[] args) {
        // 创建几张牌
        Card card1 = new Card("♠", "A", 12);
        Card card2 = new Card("♥", "2", 13);
        Card card3 = new Card("♠", "A", 12);

        // 测试toString方法
        System.out.println("Card1: " + card1);
        System.out.println("Card2: " + card2);

        // 测试比较方法
        System.out.println("Card1 比 Card2 小: " + (card1.compareTo(card2) < 0));

        // 测试equals方法
        System.out.println("Card1 equals Card3: " + card1.equals(card3));

        // 测试getter方法
        System.out.println("Card1 花色: " + card1.getSuit());
        System.out.println("Card1 点数: " + card1.getRank());
        System.out.println("Card1 大小值: " + card1.getValue());
    }
}
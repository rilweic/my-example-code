package com.lichao666;

import com.game.doudizhu.Card;
import com.game.doudizhu.Deck;

import java.util.List;

public class DeckTest {
    public static void main(String[] args) {
        Deck deck = new Deck();

        // 测试初始牌组
        System.out.println("1. 测试初始牌组:");
        System.out.println("初始牌数: " + deck.remainingCards());
        System.out.println("所有牌: ");
        printCards(deck.getAllCards());

        // 测试洗牌
        System.out.println("\n2. 测试洗牌:");
        deck.shuffle();
        System.out.println("洗牌后的牌组: ");
        printCards(deck.getAllCards());

        // 测试发牌
        System.out.println("\n3. 测试发牌:");
        List<Card> hand1 = deck.dealCards(17);
        System.out.println("发出17张牌: ");
        printCards(hand1);
        System.out.println("剩余牌数: " + deck.remainingCards());

        // 测试重置
        System.out.println("\n4. 测试重置:");
        deck.reset();
        System.out.println("重置后牌数: " + deck.remainingCards());
    }

    private static void printCards(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i) + " ");
            if ((i + 1) % 13 == 0) {
                System.out.println();  // 每13张牌换行
            }
        }
        System.out.println();
    }
}
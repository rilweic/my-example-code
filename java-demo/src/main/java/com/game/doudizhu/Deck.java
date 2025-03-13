package com.game.doudizhu;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;
    private static final String[] SUITS = {"♠", "♥", "♣", "♦"};
    private static final String[] RANKS = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        // 生成52张普通牌
        for (String suit : SUITS) {
            for (int i = 0; i < RANKS.length; i++) {
                cards.add(new Card(suit, RANKS[i], i));
            }
        }
        // 添加大小王
        cards.add(new Card("", "小王", RANKS.length));
        cards.add(new Card("", "大王", RANKS.length + 1));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public ArrayList<Card> dealCards(int count) {
        ArrayList<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < count && !cards.isEmpty(); i++) {
            dealtCards.add(cards.remove(0));
        }
        return dealtCards;
    }

    public int remainingCards() {
        return cards.size();
    }

    public void reset() {
        cards.clear();
        initializeDeck();
    }

    public ArrayList<Card> getAllCards() {
        return new ArrayList<>(cards);
    }
}
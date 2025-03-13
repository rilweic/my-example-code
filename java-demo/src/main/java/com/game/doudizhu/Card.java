package com.game.doudizhu;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final String suit;  // 花色
    private final String rank;  // 点数
    private final int value;    // 牌面大小值

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() { return suit; }
    public String getRank() { return rank; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        return suit + rank;
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                suit.equals(card.suit) &&
                rank.equals(card.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank, value);
    }
}
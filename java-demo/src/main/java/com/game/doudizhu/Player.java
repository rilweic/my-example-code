package com.game.doudizhu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Player {
    protected String name;
    protected ArrayList<Card> handCards;
    protected boolean isLandlord;
    protected boolean isHuman;

    protected Game game;

    public Player(String name) {
        this.name = name;
        this.handCards = new ArrayList<>();
        this.isLandlord = false;
        this.isHuman = true;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void addCards(ArrayList<Card> cards) {
        handCards.addAll(cards);
        Collections.sort(handCards);
    }

    public ArrayList<Card> playCards(ArrayList<Card> cards) {
        if (hasCards(cards)) {
            ArrayList<Card> playedCards = new ArrayList<>(cards);
            for (Card card : cards) {
                removeMatchingCard(card);
            }
            return playedCards;
        }
        return null;
    }

    private void removeMatchingCard(Card cardToRemove) {
        for (Iterator<Card> it = handCards.iterator(); it.hasNext();) {
            Card card = it.next();
            if (isSameCard(card, cardToRemove)) {
                it.remove();
                break;
            }
        }
    }

    protected boolean hasCards(ArrayList<Card> cards) {
        ArrayList<Card> tempHand = new ArrayList<>(handCards);
        for (Card card : cards) {
            boolean found = false;
            for (Iterator<Card> it = tempHand.iterator(); it.hasNext();) {
                Card handCard = it.next();
                if (isSameCard(handCard, card)) {
                    it.remove();
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    protected boolean isSameCard(Card card1, Card card2) {
        return card1.getSuit().equals(card2.getSuit()) &&
                card1.getRank().equals(card2.getRank()) &&
                card1.getValue() == card2.getValue();
    }

    public void clearCards() {
        handCards.clear();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHandCards() {
        return new ArrayList<>(handCards);
    }

    public boolean isLandlord() {
        return isLandlord;
    }

    public void setLandlord(boolean landlord) {
        isLandlord = landlord;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
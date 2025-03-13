package com.game.doudizhu;

import java.util.*;
import java.util.stream.Collectors;

public class AIPlayer extends Player {

    public AIPlayer(String name) {
        super(name);
        this.isHuman = false;
    }

    public ArrayList<Card> decideCards(ArrayList<Card> lastPlayedCards) {
        // 如果是第一手或者上家是自己，必须出牌
        if (lastPlayedCards == null || lastPlayedCards.isEmpty()) {
            return decideBestCards();
        }

        // 获取上家牌型并尝试打过上家
        CardCombo.Type lastType = CardCombo.getType(lastPlayedCards);
        ArrayList<Card> cards = findSameTypeCards(lastType, lastPlayedCards);
        if (cards != null) {
            return cards;
        }

        // 如果打不过，尝试用炸弹
        cards = findBomb();
        if (cards != null) {
            return cards;
        }

        // 最后尝试用王炸
        cards = findRocket();
        if (cards != null) {
            return cards;
        }

        // 打不过就要不起
        return null;
    }

    private ArrayList<Card> decideBestCards() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();

        // 优先出炸弹（手牌少时）
        ArrayList<Card> bomb = findBomb();
        if (bomb != null && handCards.size() <= 5) {
            return bomb;
        }

        // 按优先级顺序查找牌型
        ArrayList<Card> cards;

        cards = findTripleWithPairFirst();
        if (cards != null) return cards;

        cards = findTripleWithOneFirst();
        if (cards != null) return cards;

        cards = findStraight();
        if (cards != null) return cards;

        cards = findStraightPair();
        if (cards != null) return cards;

        cards = findTriple();
        if (cards != null) return cards;

        cards = findPair();
        if (cards != null) return cards;

        // 最后出最小的单牌
        return new ArrayList<>(Collections.singletonList(
                handCards.stream()
                        .min(Comparator.comparingInt(Card::getValue))
                        .orElse(handCards.get(0))
        ));
    }

    private ArrayList<Card> findSameTypeCards(CardCombo.Type type, ArrayList<Card> lastCards) {
        int lastMaxValue = Collections.max(lastCards, Comparator.comparingInt(Card::getValue)).getValue();

        switch (type) {
            case SINGLE:
                return findSingleCard(lastMaxValue);
            case PAIR:
                return findPairCards(lastMaxValue);
            case TRIPLE:
                return findTripleCards(lastMaxValue);
            case TRIPLE_WITH_ONE:
                return findTripleWithOne(lastMaxValue);
            case TRIPLE_WITH_PAIR:
                return findTripleWithPair(lastMaxValue);
            default:
                return null;
        }
    }

    private ArrayList<Card> findSingleCard(int value) {
        Optional<Card> card = handCards.stream()
                .filter(c -> c.getValue() > value)
                .min(Comparator.comparingInt(Card::getValue));

        return card.map(c -> new ArrayList<>(Collections.singletonList(c))).orElse(null);
    }

    private ArrayList<Card> findPair() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<List<Card>> pair = valueGroups.values().stream()
                .filter(list -> list.size() >= 2)
                .min(Comparator.comparingInt(cards -> cards.get(0).getValue()));

        if (pair.isPresent()) {
            return new ArrayList<>(pair.get().subList(0, 2));
        }
        return null;
    }

    private ArrayList<Card> findPairCards(int value) {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<Map.Entry<Integer, List<Card>>> pair = valueGroups.entrySet().stream()
                .filter(e -> e.getKey() > value && e.getValue().size() >= 2)
                .min(Map.Entry.comparingByKey());

        if (pair.isPresent()) {
            List<Card> cards = pair.get().getValue();
            return new ArrayList<>(cards.subList(0, 2));
        }
        return null;
    }

    private ArrayList<Card> findTriple() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<List<Card>> triple = valueGroups.values().stream()
                .filter(list -> list.size() >= 3)
                .min(Comparator.comparingInt(cards -> cards.get(0).getValue()));

        if (triple.isPresent()) {
            return new ArrayList<>(triple.get().subList(0, 3));
        }
        return null;
    }

    private ArrayList<Card> findTripleCards(int value) {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<Map.Entry<Integer, List<Card>>> triple = valueGroups.entrySet().stream()
                .filter(e -> e.getKey() > value && e.getValue().size() >= 3)
                .min(Map.Entry.comparingByKey());

        if (triple.isPresent()) {
            List<Card> cards = triple.get().getValue();
            return new ArrayList<>(cards.subList(0, 3));
        }
        return null;
    }

    private ArrayList<Card> findTripleWithOneFirst() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<Map.Entry<Integer, List<Card>>> triple = valueGroups.entrySet().stream()
                .filter(e -> e.getValue().size() >= 3)
                .min(Map.Entry.comparingByKey());

        if (triple.isPresent()) {
            ArrayList<Card> result = new ArrayList<>(triple.get().getValue().subList(0, 3));
            Optional<Card> single = handCards.stream()
                    .filter(card -> card.getValue() != triple.get().getKey())
                    .findFirst();

            if (single.isPresent()) {
                result.add(single.get());
                return result;
            }
        }
        return null;
    }

    private ArrayList<Card> findTripleWithOne(int minValue) {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<Map.Entry<Integer, List<Card>>> triple = valueGroups.entrySet().stream()
                .filter(e -> e.getKey() > minValue && e.getValue().size() >= 3)
                .min(Map.Entry.comparingByKey());

        if (triple.isPresent()) {
            ArrayList<Card> result = new ArrayList<>(triple.get().getValue().subList(0, 3));
            Optional<Card> single = handCards.stream()
                    .filter(card -> card.getValue() != triple.get().getKey())
                    .findFirst();

            if (single.isPresent()) {
                result.add(single.get());
                return result;
            }
        }
        return null;
    }

    private ArrayList<Card> findTripleWithPairFirst() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<Map.Entry<Integer, List<Card>>> triple = valueGroups.entrySet().stream()
                .filter(e -> e.getValue().size() >= 3)
                .min(Map.Entry.comparingByKey());

        if (triple.isPresent()) {
            ArrayList<Card> result = new ArrayList<>(triple.get().getValue().subList(0, 3));
            Optional<Map.Entry<Integer, List<Card>>> pair = valueGroups.entrySet().stream()
                    .filter(e -> e.getKey() != triple.get().getKey() && e.getValue().size() >= 2)
                    .findFirst();

            if (pair.isPresent()) {
                result.addAll(pair.get().getValue().subList(0, 2));
                return result;
            }
        }
        return null;
    }

    private ArrayList<Card> findTripleWithPair(int minValue) {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<Map.Entry<Integer, List<Card>>> triple = valueGroups.entrySet().stream()
                .filter(e -> e.getKey() > minValue && e.getValue().size() >= 3)
                .min(Map.Entry.comparingByKey());

        if (triple.isPresent()) {
            ArrayList<Card> result = new ArrayList<>(triple.get().getValue().subList(0, 3));
            Optional<Map.Entry<Integer, List<Card>>> pair = valueGroups.entrySet().stream()
                    .filter(e -> e.getKey() != triple.get().getKey() && e.getValue().size() >= 2)
                    .findFirst();

            if (pair.isPresent()) {
                result.addAll(pair.get().getValue().subList(0, 2));
                return result;
            }
        }
        return null;
    }

    private ArrayList<Card> findBomb() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        Optional<List<Card>> bomb = valueGroups.values().stream()
                .filter(list -> list.size() == 4)
                .findFirst();

        return bomb.map(ArrayList::new).orElse(null);
    }

    private ArrayList<Card> findRocket() {
        ArrayList<Card> jokers = new ArrayList<>(handCards.stream()
                .filter(card -> card.getRank().contains("王"))
                .collect(Collectors.toList()));

        return jokers.size() == 2 ? jokers : null;
    }

    private Map<Integer, List<Card>> groupCardsByValue() {
        return handCards.stream().collect(Collectors.groupingBy(Card::getValue));
    }

    private ArrayList<Card> findStraight() {
        ArrayList<Card> sortedCards = new ArrayList<>(handCards);
        Collections.sort(sortedCards);

        int maxLen = 0;
        int start = 0;

        for (int i = 0; i < sortedCards.size() - 4; i++) {
            int len = 1;
            for (int j = i + 1; j < sortedCards.size(); j++) {
                if (sortedCards.get(j).getValue() == sortedCards.get(j-1).getValue() + 1) {
                    len++;
                } else {
                    break;
                }
            }
            if (len >= 5 && len > maxLen) {
                maxLen = len;
                start = i;
            }
        }

        if (maxLen >= 5) {
            return new ArrayList<>(sortedCards.subList(start, start + maxLen));
        }
        return null;
    }

    private ArrayList<Card> findStraightPair() {
        Map<Integer, List<Card>> valueGroups = groupCardsByValue();
        ArrayList<Integer> pairValues = new ArrayList<>(valueGroups.entrySet().stream()
                .filter(e -> e.getValue().size() >= 2)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList()));

        if (pairValues.size() >= 3) {
            for (int i = 0; i < pairValues.size() - 2; i++) {
                if (pairValues.get(i+1) == pairValues.get(i) + 1 &&
                        pairValues.get(i+2) == pairValues.get(i) + 2) {
                    ArrayList<Card> result = new ArrayList<>();
                    for (int j = i; j < i + 3; j++) {
                        List<Card> pair = valueGroups.get(pairValues.get(j));
                        result.addAll(pair.subList(0, 2));
                    }
                    return result;
                }
            }
        }
        return null;
    }
}
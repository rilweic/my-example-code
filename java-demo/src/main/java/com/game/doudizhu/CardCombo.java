package com.game.doudizhu;

import java.util.*;
import java.util.stream.Collectors;

public class CardCombo {
    public enum Type {
        SINGLE,          // 单张
        PAIR,           // 对子
        TRIPLE,         // 三张
        TRIPLE_WITH_ONE, // 三带一
        TRIPLE_WITH_PAIR,// 三带二
        STRAIGHT,       // 顺子
        STRAIGHT_PAIR,  // 连对
        AIRPLANE,       // 飞机不带
        AIRPLANE_WITH_WINGS, // 飞机带翅膀
        FOUR_WITH_TWO,  // 四带二
        BOMB,           // 炸弹
        ROCKET,         // 王炸
        INVALID        // 无效牌型
    }

    public static Type getType(ArrayList<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            return Type.INVALID;
        }

        // 先对牌进行排序
        ArrayList<Card> sortedCards = new ArrayList<>(cards);
        Collections.sort(sortedCards);

        // 统计每个点数的数量
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : sortedCards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }

        int size = cards.size();

        // 判断王炸
        if (size == 2 && isRocket(sortedCards)) {
            return Type.ROCKET;
        }

        // 判断炸弹
        if (size == 4 && valueCount.containsValue(4)) {
            return Type.BOMB;
        }

        // 判断三带牌型
        if (valueCount.containsValue(3)) {
            final int tripleValue = valueCount.entrySet().stream()
                    .filter(e -> e.getValue() == 3)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(-1);

            if (size == 4) { // 三带一
                boolean hasExtra = valueCount.entrySet().stream()
                        .anyMatch(e -> e.getKey() != tripleValue && e.getValue() == 1);
                if (hasExtra) {
                    return Type.TRIPLE_WITH_ONE;
                }
            } else if (size == 5) { // 三带二
                boolean hasExtraPair = valueCount.entrySet().stream()
                        .anyMatch(e -> e.getKey() != tripleValue && e.getValue() == 2);
                if (hasExtraPair) {
                    return Type.TRIPLE_WITH_PAIR;
                }
            }
        }

        if (valueCount.containsValue(4)) {
            if (size == 6) { // 四带二张单牌
                return Type.FOUR_WITH_TWO;
            }
            if (size == 8 && valueCount.containsValue(2)) { // 四带二对
                return Type.FOUR_WITH_TWO;
            }
        }

        if (isAirplane(cards)) {
            return Type.AIRPLANE;
        }

        if (isAirplaneWithWings(cards)) {
            return Type.AIRPLANE_WITH_WINGS;
        }


        // 判断其他牌型
        switch (size) {
            case 1:
                return Type.SINGLE;
            case 2:
                if (valueCount.containsValue(2)) {
                    return Type.PAIR;
                }
                break;
            case 3:
                if (valueCount.containsValue(3)) {
                    return Type.TRIPLE;
                }
                break;
            default:
                if (isStraight(sortedCards)) {
                    return Type.STRAIGHT;
                }
                if (isStraightPair(sortedCards)) {
                    return Type.STRAIGHT_PAIR;
                }
                if (isAirplane(sortedCards)) {
                    return Type.AIRPLANE;
                }
        }

        return Type.INVALID;
    }

    public static boolean canBeat(ArrayList<Card> cards1, ArrayList<Card> cards2) {
        Type type1 = getType(cards1);
        Type type2 = getType(cards2);

        // 处理特殊牌型
        if (type1 == Type.ROCKET) {
            return true;
        }
        if (type2 == Type.ROCKET) {
            return false;
        }
        if (type1 == Type.BOMB && type2 != Type.BOMB) {
            return true;
        }
        if (type2 == Type.BOMB && type1 != Type.BOMB) {
            return false;
        }

        // 普通牌型必须相同
        if (type1 != type2) {
            return false;
        }

        // 根据不同牌型比较大小
        switch (type1) {
            case SINGLE:
            case PAIR:
            case TRIPLE:
            case BOMB:
                return getMaxValue(cards1) > getMaxValue(cards2);

            case TRIPLE_WITH_ONE:
            case TRIPLE_WITH_PAIR:
                return getTripleValue(cards1) > getTripleValue(cards2);

            case STRAIGHT:
            case STRAIGHT_PAIR:
            case FOUR_WITH_TWO:
                return getFourValue(cards1) > getFourValue(cards2);
            case AIRPLANE_WITH_WINGS:
                return getMinTripleValue(cards1) > getMinTripleValue(cards2);
            case AIRPLANE:
                if (cards1.size() != cards2.size()) {
                    return false;
                }
                return getMaxValue(cards1) > getMaxValue(cards2);

            default:
                return false;
        }
    }

    // 获取四带二中四张牌的值
    private static int getFourValue(ArrayList<Card> cards) {
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }
        return valueCount.entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    // 获取飞机中最小的三张的值
    private static int getMinTripleValue(ArrayList<Card> cards) {
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }
        return valueCount.entrySet().stream()
                .filter(e -> e.getValue() >= 3)
                .map(Map.Entry::getKey)
                .min(Integer::compareTo)
                .orElse(-1);
    }

    private static boolean isRocket(ArrayList<Card> cards) {
        if (cards.size() != 2) return false;
        return cards.get(0).getRank().equals("小王") &&
                cards.get(1).getRank().equals("大王");
    }

    private static boolean isStraight(ArrayList<Card> cards) {
        if (cards.size() < 5) return false;

        // 不能包含2和王
        for (Card card : cards) {
            if (card.getRank().equals("2") ||
                    card.getRank().equals("小王") ||
                    card.getRank().equals("大王")) {
                return false;
            }
        }

        // 检查是否连续
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getValue() != cards.get(i - 1).getValue() + 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isStraightPair(ArrayList<Card> cards) {
        if (cards.size() < 6 || cards.size() % 2 != 0) return false;

        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }

        if (!valueCount.values().stream().allMatch(count -> count == 2)) {
            return false;
        }

        ArrayList<Integer> values = new ArrayList<>(valueCount.keySet());
        Collections.sort(values);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAirplane(ArrayList<Card> cards) {
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }

        // 找出所有三张的点数
        ArrayList<Integer> tripleValues = valueCount.entrySet().stream()
                .filter(e -> e.getValue() == 3)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        // 至少需要两个连续的三张
        if (tripleValues.size() < 2) {
            return false;
        }

        // 检查三张是否连续
        for (int i = 1; i < tripleValues.size(); i++) {
            if (tripleValues.get(i) != tripleValues.get(i - 1) + 1) {
                return false;
            }
        }

        // 纯飞机：牌的数量应该是三张的倍数
        return cards.size() == tripleValues.size() * 3;
    }

    private static boolean isAirplaneWithWings(ArrayList<Card> cards) {
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }

        // 找出所有三张的点数
        ArrayList<Integer> tripleValues = valueCount.entrySet().stream()
                .filter(e -> e.getValue() == 3)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        if (tripleValues.size() < 2) {
            return false;
        }

        // 检查连续性
        for (int i = 1; i < tripleValues.size(); i++) {
            if (tripleValues.get(i) != tripleValues.get(i - 1) + 1) {
                return false;
            }
        }

        // 计算三张的数量和总牌数
        int tripleCount = tripleValues.size() * 3;
        int totalCards = cards.size();

        // 带单张：每个三张带一张单牌
        if (totalCards == tripleCount + tripleValues.size()) {
            // 验证剩余牌是否都是单张
            boolean hasSingles = valueCount.values().stream()
                    .filter(count -> count == 1)
                    .count() == tripleValues.size();
            return hasSingles;
        }

        // 带对子：每个三张带一对
        if (totalCards == tripleCount + tripleValues.size() * 2) {
            // 验证剩余牌是否都是对子
            long pairCount = valueCount.values().stream()
                    .filter(count -> count == 2)
                    .count();
            return pairCount == tripleValues.size();
        }

        return false;
    }

    private static int getMaxValue(ArrayList<Card> cards) {
        return Collections.max(cards, Comparator.comparingInt(Card::getValue)).getValue();
    }

    private static int getTripleValue(ArrayList<Card> cards) {
        Map<Integer, Integer> valueCount = new HashMap<>();
        for (Card card : cards) {
            valueCount.merge(card.getValue(), 1, Integer::sum);
        }

        for (Map.Entry<Integer, Integer> entry : valueCount.entrySet()) {
            if (entry.getValue() == 3) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
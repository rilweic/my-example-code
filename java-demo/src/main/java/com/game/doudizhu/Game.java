package com.game.doudizhu;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Card> landlordCards;  // 地主牌
    private Player currentPlayer;       // 当前出牌的玩家
    private ArrayList<Card> lastPlayedCards; // 最后一手出的牌
    private Player lastPlayedPlayer;    // 最后一手出牌的玩家
    private boolean gameOver;

    public Game() {
        // 初始化玩家（一个人类玩家，两个AI玩家）
        players = new ArrayList<>();
        players.add(new Player("玩家"));
        players.add(new AIPlayer("左边AI"));
        players.add(new AIPlayer("右边AI"));
        landlordCards = new ArrayList<>();
        gameOver = false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void startGame() {
        gameOver = false;
        lastPlayedCards = null;
        lastPlayedPlayer = null;

        // 清空所有玩家的手牌
        for (Player player : players) {
            player.setGame(this);
            player.clearCards();
            player.setLandlord(false);
        }

        // 创建并洗牌
        Deck deck = new Deck();
        deck.shuffle();

        // 发牌
        for (Player player : players) {
            ArrayList<Card> dealtCards = new ArrayList<>(deck.dealCards(17));
            player.addCards(dealtCards);
        }

        // 留出地主牌
        landlordCards = new ArrayList<>(deck.dealCards(3));

        // 选择地主（这里简化为随机选择）
        int landlordIndex = new Random().nextInt(3);
        Player landlord = players.get(landlordIndex);
        landlord.setLandlord(true);
        landlord.addCards(new ArrayList<>(landlordCards));

        // 设置第一个出牌的玩家为地主
        currentPlayer = landlord;
    }

    public boolean playCards(Player player, ArrayList<Card> cards) {
        // 检查是否轮到该玩家出牌
        if (player != currentPlayer || gameOver) {
            return false;
        }

        // 检查出牌是否有效
        CardCombo.Type comboType = CardCombo.getType(cards);
        if (comboType == CardCombo.Type.INVALID) {
            return false;
        }

        // 检查是否能大过上家的牌
        if (lastPlayedCards != null && lastPlayedPlayer != currentPlayer) {
            if (!CardCombo.canBeat(cards, lastPlayedCards)) {
                return false;
            }
        }

        // 尝试出牌
        ArrayList<Card> playedCards = player.playCards(cards);
        if (playedCards != null) {
            lastPlayedCards = new ArrayList<>(playedCards);
            lastPlayedPlayer = player;

            // 检查是否游戏结束
            if (player.getHandCards().isEmpty()) {
                gameOver = true;
            }

            // 移动到下一个玩家
            currentPlayer = getNextPlayer();
            return true;
        }

        return false;
    }

    public boolean pass() {
        // 第一手不能不要
        if (lastPlayedCards == null) {
            return false;
        }

        // 如果是该玩家最后出的牌，不能不要
        if (lastPlayedPlayer == currentPlayer) {
            return false;
        }

        // 移动到下一个玩家
        currentPlayer = getNextPlayer();

        // 如果转回到出牌的玩家，清空上一手牌
        if (currentPlayer == lastPlayedPlayer) {
            lastPlayedCards = null;
            lastPlayedPlayer = null;
        }

        return true;
    }

    private Player getNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        return players.get((currentIndex + 1) % players.size());
    }

    // Getter方法
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Card> getLastPlayedCards() {
        return lastPlayedCards != null ? new ArrayList<>(lastPlayedCards) : null;
    }

    public Player getLastPlayedPlayer() {
        return lastPlayedPlayer;
    }

    public ArrayList<Card> getLandlordCards() {
        return new ArrayList<>(landlordCards);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getHumanPlayer() {
        return players.get(0);
    }

    public Player getLeftAIPlayer() {
        return players.get(2);
    }

    public Player getRightAIPlayer() {
        return players.get(1);
    }

    public String getPlayerPosition(Player player) {
        if (player == players.get(0)) {
            return "玩家";
        } else if (player == players.get(1)) {
            return "右家";
        } else if (player == players.get(2)) {
            return "左家";
        }
        return "未知";
    }
}
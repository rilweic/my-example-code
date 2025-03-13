package com.game.doudizhu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameWindow extends JFrame {
    private Game game;
    private JPanel mainPanel;
    private CardPanel playerPanel;
    private CardPanel leftAIPanel;
    private CardPanel rightAIPanel;
    private JPanel centerPanel;
    private JPanel controlPanel;
    private JLabel statusLabel;
    private JLabel lastPlayerLabel;
    private JButton playButton;
    private JButton passButton;
    private JLabel leftPlayerLabel;
    private JLabel rightPlayerLabel;
    private JLabel humanPlayerLabel;
    private CardPanel lastPlayedPanel;

    // 添加警告标签
    private JLabel leftWarningLabel;
    private JLabel rightWarningLabel;
    private JLabel humanWarningLabel;

    private AudioPlayer audioPlayer;

    public GameWindow() {
        game = new Game();
        initializeUI();
        startNewGame();
        audioPlayer = new AudioPlayer();
        audioPlayer.playAudio("/audio/bgm.wav");
    }

    private void initializeUI() {
        setTitle("斗地主");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // 初始化所有面板和组件
        initializePlayerPanels();
        initializeControlPanel();
        initializeCenterPanel();

        // 将所有组件添加到主面板
        assembleMainPanel();

        add(mainPanel);
    }

    private void assembleMainPanel() {
        // 创建左侧AI的容器
        JPanel leftAIContainer = new JPanel(new BorderLayout());
        leftAIContainer.setOpaque(false);

        // 创建左侧标签容器（包含警告和玩家标签）
        JPanel leftLabelContainer = new JPanel();
        leftLabelContainer.setLayout(new BoxLayout(leftLabelContainer, BoxLayout.Y_AXIS));
        leftLabelContainer.setOpaque(false);
        leftLabelContainer.add(leftWarningLabel);
        leftLabelContainer.add(leftPlayerLabel);

        leftAIContainer.add(leftLabelContainer, BorderLayout.NORTH);
        leftAIContainer.add(leftAIPanel, BorderLayout.CENTER);

        // 创建右侧AI的容器
        JPanel rightAIContainer = new JPanel(new BorderLayout());
        rightAIContainer.setOpaque(false);

        // 创建右侧标签容器
        JPanel rightLabelContainer = new JPanel();
        rightLabelContainer.setLayout(new BoxLayout(rightLabelContainer, BoxLayout.Y_AXIS));
        rightLabelContainer.setOpaque(false);
        rightLabelContainer.add(rightWarningLabel);
        rightLabelContainer.add(rightPlayerLabel);

        rightAIContainer.add(rightLabelContainer, BorderLayout.NORTH);
        rightAIContainer.add(rightAIPanel, BorderLayout.CENTER);

        // 创建玩家区域的容器
        JPanel humanContainer = new JPanel(new BorderLayout());
        humanContainer.setOpaque(false);

        // 创建玩家标签和按钮的容器
        JPanel humanControlPanel = new JPanel(new BorderLayout());
        humanControlPanel.setOpaque(false);

        // 创建玩家标签容器
        JPanel humanLabelContainer = new JPanel();
        humanLabelContainer.setLayout(new BoxLayout(humanLabelContainer, BoxLayout.Y_AXIS));
        humanLabelContainer.setOpaque(false);
        humanLabelContainer.add(humanWarningLabel);
        humanLabelContainer.add(humanPlayerLabel);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(playButton);
        buttonPanel.add(passButton);

        humanControlPanel.add(humanLabelContainer, BorderLayout.NORTH);
        humanControlPanel.add(buttonPanel, BorderLayout.CENTER);

        humanContainer.add(humanControlPanel, BorderLayout.NORTH);
        humanContainer.add(playerPanel, BorderLayout.CENTER);

        // 将所有容器添加到主面板
        mainPanel.add(statusLabel, BorderLayout.NORTH);
        mainPanel.add(leftAIContainer, BorderLayout.WEST);
        mainPanel.add(rightAIContainer, BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(humanContainer, BorderLayout.SOUTH);
    }

    private void initializePlayerPanels() {
        // 创建玩家面板
        playerPanel = new CardPanel(true);
        leftAIPanel = new CardPanel(false);
        rightAIPanel = new CardPanel(false);

        // 设置AI面板显示背面和方向
        leftAIPanel.setShowBack(true);
        rightAIPanel.setShowBack(true);
        leftAIPanel.setRotation(-90);
        rightAIPanel.setRotation(90);

        // 创建玩家标签
        leftPlayerLabel = createPlayerLabel();
        rightPlayerLabel = createPlayerLabel();
        humanPlayerLabel = createPlayerLabel();

        // 创建警告标签
        leftWarningLabel = createWarningLabel();
        rightWarningLabel = createWarningLabel();
        humanWarningLabel = createWarningLabel();

        // 设置面板大小
        playerPanel.setPreferredSize(new Dimension(800, 180));
        leftAIPanel.setPreferredSize(new Dimension(140, 500));
        rightAIPanel.setPreferredSize(new Dimension(140, 500));
    }


    private void updateWarningLabels() {
        // 更新左边AI的警告
        int leftCards = game.getLeftAIPlayer().getHandCards().size();
        leftWarningLabel.setVisible(leftCards <= 2);

        // 更新右边AI的警告
        int rightCards = game.getRightAIPlayer().getHandCards().size();
        rightWarningLabel.setVisible(rightCards <= 2);

        // 更新玩家的警告
        int humanCards = game.getHumanPlayer().getHandCards().size();
        humanWarningLabel.setVisible(humanCards <= 2);
    }

    // 创建警告标签
    private JLabel createWarningLabel() {
        JLabel label = new JLabel("警告：剩牌数少于2张！");
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        label.setForeground(Color.RED);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);  // 居中对齐
        label.setVisible(false);  // 初始时不可见
        return label;
    }

    private void initializeControlPanel() {
        controlPanel = new JPanel(new BorderLayout());
        controlPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setOpaque(false);

        playButton = new JButton("出牌");
        passButton = new JButton("不要");

        Dimension buttonSize = new Dimension(100, 30);
        playButton.setPreferredSize(buttonSize);
        passButton.setPreferredSize(buttonSize);

        playButton.addActionListener(e -> handlePlay());
        passButton.addActionListener(e -> handlePass());

        buttonPanel.add(playButton);
        buttonPanel.add(passButton);

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));

        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(statusLabel, BorderLayout.SOUTH);
    }

    private void initializeCenterPanel() {
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        lastPlayerLabel = new JLabel("等待出牌", JLabel.CENTER);
        lastPlayerLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));

        lastPlayedPanel = new CardPanel(false);
        lastPlayedPanel.setPreferredSize(new Dimension(400, 150));

        centerPanel.add(lastPlayerLabel, BorderLayout.NORTH);
        centerPanel.add(lastPlayedPanel, BorderLayout.CENTER);
    }

    private JLabel createPlayerLabel() {
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void startNewGame() {
        game.startGame();
        updateUI();

        // 如果地主是AI，则自动开始AI回合
        Player currentPlayer = game.getCurrentPlayer();
        if (!currentPlayer.isHuman()) {
            handleAITurn();
        }
    }

    private void handlePlay() {
        ArrayList<Card> selectedCards = playerPanel.getSelectedCards();
        if (selectedCards.isEmpty()) {
            updateStatus("请选择要出的牌");
            return;
        }

        if (game.playCards(game.getHumanPlayer(), selectedCards)) {
            updateUI();
            playerPanel.clearSelection();

            if (game.isGameOver()) {
                handleGameOver();
            } else {
                handleAITurn();
            }
        } else {
            updateStatus("出牌不符合规则");
        }
    }

    private void handlePass() {
        if (game.getLastPlayedCards() == null) {
            updateStatus("第一手不能不要");
            return;
        }

        if (game.pass()) {
            updateUI();
            updateStatus("玩家不要");
            handleAITurn();
        }
    }



    private void handleAITurn() {
        Timer timer = new Timer(1000, e -> {  // 第一个AI开始思考前的延时
            if (!game.getCurrentPlayer().isHuman() && !game.isGameOver()) {
                Player currentPlayer = game.getCurrentPlayer();
                if (currentPlayer instanceof AIPlayer) {
                    AIPlayer ai = (AIPlayer) currentPlayer;

                    // 显示当前是谁在思考
                    updateStatus(game.getPlayerPosition(ai) + " 正在思考...");

                    // 延时1秒后再做出决策
                    Timer decisionTimer = new Timer(1000, ev -> {
                        ArrayList<Card> aiCards = ai.decideCards(game.getLastPlayedCards());

                        if (aiCards != null) {
                            game.playCards(ai, aiCards);
                            String playerPos = game.getPlayerPosition(ai);
                            updateStatus(playerPos + " 出牌: " + formatCards(aiCards));
                            updateUI();
                        } else {
                            game.pass();
                            String playerPos = game.getPlayerPosition(ai);
                            updateStatus(playerPos + " 不要");
                            updateUI();
                        }

                        if (game.isGameOver()) {
                            handleGameOver();
                            return;
                        }

                        // 在这个AI行动完后，等待1.5秒再轮到下一个玩家
                        Timer nextPlayerTimer = new Timer(1500, ex -> {
                            if (!game.getCurrentPlayer().isHuman() && !game.isGameOver()) {
                                handleAITurn();  // 如果下一个还是AI，继续AI回合
                            }
                        });
                        nextPlayerTimer.setRepeats(false);
                        nextPlayerTimer.start();
                    });

                    decisionTimer.setRepeats(false);
                    decisionTimer.start();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private String formatCards(ArrayList<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            return "";
        }
        CardCombo.Type type = CardCombo.getType(cards);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(" ")));
        sb.append("]");
        // 添加牌型说明
        sb.append(" - ").append(getTypeDescription(type));
        return sb.toString();
    }

    private String getTypeDescription(CardCombo.Type type) {
        switch (type) {
            case SINGLE: return "单张";
            case PAIR: return "对子";
            case TRIPLE: return "三张";
            case TRIPLE_WITH_ONE: return "三带一";
            case TRIPLE_WITH_PAIR: return "三带二";
            case STRAIGHT: return "顺子";
            case STRAIGHT_PAIR: return "连对";
            case AIRPLANE: return "飞机";
            case BOMB: return "炸弹";
            case ROCKET: return "王炸";
            default: return "";
        }
    }


    private void handleGameOver() {
        Player winner = null;
        for (Player player : new Player[]{game.getHumanPlayer(),
                game.getLeftAIPlayer(),
                game.getRightAIPlayer()}) {
            if (player.getHandCards().isEmpty()) {
                winner = player;
                break;
            }
        }

        String message;
        if (winner.isLandlord()) {
            message = game.getPlayerPosition(winner) + "(地主)赢得了游戏！";
        } else {
            message = game.getPlayerPosition(winner) + "(农民)赢得了游戏！";
        }

        int choice = JOptionPane.showConfirmDialog(this,
                message + "\n是否开始新游戏？",
                "游戏结束",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
            audioPlayer.stopAudio();
        }
        audioPlayer.stopAudio();
    }

    private void updateUI() {
        // 更新玩家手牌
        playerPanel.setCards(new ArrayList<>(game.getHumanPlayer().getHandCards()));
        leftAIPanel.setCards(new ArrayList<>(game.getLeftAIPlayer().getHandCards()));
        rightAIPanel.setCards(new ArrayList<>(game.getRightAIPlayer().getHandCards()));

        // 更新玩家标签
        updatePlayerLabels();

        // 更新最后出的牌
        ArrayList<Card> lastPlayed = game.getLastPlayedCards();
        Player lastPlayer = game.getLastPlayedPlayer();
        if (lastPlayed != null && lastPlayer != null) {
            lastPlayedPanel.setCards(new ArrayList<>(lastPlayed));
            String playerPos = game.getPlayerPosition(lastPlayer);
            CardCombo.Type type = CardCombo.getType(lastPlayed);
            lastPlayerLabel.setText("<html><center>" +
                    playerPos + " 出牌:<br>" +
                    getTypeDescription(type) + "</center></html>");
        } else {
            lastPlayedPanel.setCards(new ArrayList<>());
            lastPlayerLabel.setText("等待出牌");
        }

        // 更新按钮状态
        boolean isPlayerTurn = game.getCurrentPlayer() == game.getHumanPlayer();
        playButton.setEnabled(isPlayerTurn);  // 只有在玩家回合时才能出牌
        passButton.setEnabled(isPlayerTurn && game.getLastPlayedCards() != null); // 有上家出牌时才能不要

        // 可选：改变按钮的外观，使禁用状态更明显
        if (!isPlayerTurn) {
            playButton.setBackground(Color.LIGHT_GRAY);
            passButton.setBackground(Color.LIGHT_GRAY);
        } else {
            playButton.setBackground(null);  // 恢复默认颜色
            passButton.setBackground(null);
        }

        updateWarningLabels();

        repaint();
    }

    private void updatePlayerLabels() {
        Player human = game.getHumanPlayer();
        Player left = game.getLeftAIPlayer();
        Player right = game.getRightAIPlayer();

        humanPlayerLabel.setText(getPlayerLabelText("玩家", human));
        leftPlayerLabel.setText(getPlayerLabelText("左家", left));
        rightPlayerLabel.setText(getPlayerLabelText("右家", right));

        highlightCurrentPlayer();
    }

    private String getPlayerLabelText(String name, Player player) {
        String identity = player.isLandlord() ? "地主" : "农民";
        return String.format("%s (%s) - 剩余 %d 张",
                name, identity, player.getHandCards().size());
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void highlightCurrentPlayer() {
        humanPlayerLabel.setForeground(Color.BLACK);
        rightPlayerLabel.setForeground(Color.BLACK);
        leftPlayerLabel.setForeground(Color.BLACK);

        Player currentPlayer = game.getCurrentPlayer();
        Color highlightColor = new Color(0, 100, 0);  // 深绿色

        if (currentPlayer == game.getHumanPlayer()) {
            humanPlayerLabel.setForeground(highlightColor);
        } else if (currentPlayer == game.getRightAIPlayer()) {
            rightPlayerLabel.setForeground(highlightColor);
        } else if (currentPlayer == game.getLeftAIPlayer()) {
            leftPlayerLabel.setForeground(highlightColor);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
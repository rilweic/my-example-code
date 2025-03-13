package com.game.cubev2;

import javax.swing.*;
import java.awt.*;

/**
 * 显示游戏分数的面板
 */
public class ScorePanel extends JPanel {
    private JLabel scoreLabel;
    private GameManager gameManager;

    /**
     * 创建分数面板
     * @param gameManager 游戏管理器
     */
    public ScorePanel(GameManager gameManager) {
        this.gameManager = gameManager;

        // 使用绝对布局
        setLayout(null);

        // 创建分数标签
        scoreLabel = new JLabel("分数: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBounds(10, 5, 100, 30);
        add(scoreLabel);

        // 创建新游戏按钮
        JButton newGameButton = new JButton("新游戏");
        newGameButton.addActionListener(e -> startNewGame());

        // 创建退出按钮
        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> exitGame());

        // 设置按钮位置 - 计算使按钮位于右侧
        int boardWidth = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE;
        exitButton.setBounds(boardWidth - 100, 5, 80, 30);
        newGameButton.setBounds(boardWidth - 190, 5, 80, 30);

        add(newGameButton);
        add(exitButton);

        // 设置面板大小
        setPreferredSize(new Dimension(boardWidth, 40));
        setSize(boardWidth, 40);
    }
    /**
     * 更新分数显示
     * @param score 新分数
     */
    public void updateScore(int score) {
        scoreLabel.setText("分数: " + score);
    }

    /**
     * 开始新游戏
     */
    private void startNewGame() {
        // 确认是否开始新游戏
        int option = JOptionPane.showConfirmDialog(
                this,
                "确定要开始新游戏吗？当前游戏进度将丢失。",
                "新游戏确认",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            gameManager.resetGame(); // 调用新的resetGame方法
        }
    }

    /**
     * 退出游戏
     */
    private void exitGame() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "确定要退出游戏吗？",
                "退出确认",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}


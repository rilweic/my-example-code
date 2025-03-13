package com.game.cubev2;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;

/**
 * Cube Block Game的主类，负责初始化游戏并启动界面
 */
public class CubeBlockGame extends JFrame {
    private GameBoard gameBoard;
    private PreviewPanel previewPanel;
    private ScorePanel scorePanel;
    private GameManager gameManager;

    public CubeBlockGame() {
        super("Cube Block Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 禁止调整窗口大小

        // 使用绝对布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // 初始化游戏管理器
        gameManager = new GameManager();

        // 初始化游戏界面组件
        gameBoard = new GameBoard(gameManager);
        previewPanel = new PreviewPanel(gameManager, gameBoard);
        scorePanel = new ScorePanel(gameManager);

        // 启用拖放支持
        gameBoard.setDropTarget(new DropTarget(gameBoard, DnDConstants.ACTION_COPY, null));

        // 设置游戏管理器的UI组件引用
        gameManager.setUIComponents(gameBoard, previewPanel, scorePanel);

        // 计算各组件的位置和大小
        int boardSize = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE;
        int previewHeight = 150; // 预览区域高度
        int headerHeight = 40;   // 顶部区域高度
        int padding = 10;        // 内边距

        // 设置各组件位置
        scorePanel.setBounds(padding, padding, boardSize, headerHeight);
        gameBoard.setBounds(padding, headerHeight + padding * 2, boardSize, boardSize);
        previewPanel.setBounds(padding, headerHeight + boardSize + padding * 3, boardSize, previewHeight);

        // 添加组件到面板
        mainPanel.add(scorePanel);
        mainPanel.add(gameBoard);
        mainPanel.add(previewPanel);

        // 设置面板大小
        int totalWidth = boardSize + padding * 2;
        int totalHeight = headerHeight + boardSize + previewHeight + padding * 5;
        mainPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));

        // 设置内容面板
        setContentPane(mainPanel);

        // 启动游戏
        gameManager.initializeGame();

        // 调整窗口大小并显示
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // 在Mac上设置系统外观和感觉
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Cube Block Game");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new CubeBlockGame());
    }
}
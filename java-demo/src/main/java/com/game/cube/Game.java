package com.game.cube;

import javax.swing.*;

public class Game {
    public Grid grid;
    public BlockPool pool;
    public int score;

    public Game() {
        grid = new Grid();
        pool = new BlockPool();
        score = 0;
    }

    // 检查游戏是否能继续
    public boolean canContinue() {
        for (Block block : pool.getPool()) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (grid.canPlaceBlock(block, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Game game = new Game();
        CubeBlockGUI gui = new CubeBlockGUI(game);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cube Block Game");
            frame.add(gui);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
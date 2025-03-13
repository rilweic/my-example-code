package com.game.cubev2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 行/列消除动画效果
 */
public class LineClearAnimation {
    private GameBoard gameBoard;
    private Timer timer;
    private int animationStep = 0;
    private final int ANIMATION_DURATION = 10; // 动画持续步数
    private List<Integer> clearRows = new ArrayList<>();
    private List<Integer> clearCols = new ArrayList<>();
    private Random random = new Random();
    private ArrayList<Particle> particles = new ArrayList<>();

    /**
     * 创建消除动画
     * @param gameBoard 游戏棋盘
     * @param clearRows 要消除的行
     * @param clearCols 要消除的列
     */
    public LineClearAnimation(GameBoard gameBoard, List<Integer> clearRows, List<Integer> clearCols) {
        this.gameBoard = gameBoard;
        this.clearRows = new ArrayList<>(clearRows);
        this.clearCols = new ArrayList<>(clearCols);

        // 创建粒子效果
        createParticles();

        SoundUtils.defaultPlay();

        // 创建动画计时器
        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;

                // 更新粒子
                updateParticles();

                // 重绘游戏板
                gameBoard.repaint();


                // 动画结束
                if (animationStep >= ANIMATION_DURATION) {
                    timer.stop();
                    gameBoard.clearAnimationComplete();
                }
            }
        });

    }

    /**
     * 开始动画
     */
    public void start() {
        animationStep = 0;
        timer.start();
    }

    /**
     * 判断动画是否正在运行
     * @return 是否正在运行
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    /**
     * 创建粒子效果
     */
    private void createParticles() {
        // 为每个要消除的行创建粒子
        for (int row : clearRows) {
            for (int i = 0; i < GameConstants.BOARD_SIZE * 3; i++) {
                int x = random.nextInt(GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);
                int y = row * GameConstants.CELL_SIZE + GameConstants.CELL_SIZE / 2;

                double angle = random.nextDouble() * 2 * Math.PI;
                double speed = 1 + random.nextDouble() * 3;

                particles.add(new Particle(
                        x, y,
                        Math.cos(angle) * speed, Math.sin(angle) * speed,
                        getRandomColor(),
                        15 + random.nextInt(15)  // 生命周期
                ));
            }
        }

        // 为每个要消除的列创建粒子
        for (int col : clearCols) {
            for (int i = 0; i < GameConstants.BOARD_SIZE * 3; i++) {
                int x = col * GameConstants.CELL_SIZE + GameConstants.CELL_SIZE / 2;
                int y = random.nextInt(GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);

                double angle = random.nextDouble() * 2 * Math.PI;
                double speed = 1 + random.nextDouble() * 3;

                particles.add(new Particle(
                        x, y,
                        Math.cos(angle) * speed, Math.sin(angle) * speed,
                        getRandomColor(),
                        15 + random.nextInt(15)  // 生命周期
                ));
            }
        }
    }

    /**
     * 获取随机颜色
     * @return 随机颜色
     */
    private Color getRandomColor() {
        Color[] colors = {
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                Color.CYAN, Color.BLUE, Color.MAGENTA, Color.PINK
        };
        return colors[random.nextInt(colors.length)];
    }

    /**
     * 更新粒子位置
     */
    private void updateParticles() {
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update();

            // 移除生命周期结束的粒子
            if (p.life <= 0) {
                particles.remove(i);
            }
        }
    }

    /**
     * 绘制动画
     * @param g Graphics对象
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // 绘制被消除的行
        for (int row : clearRows) {
            float alpha = 0.7f - (animationStep / (float)ANIMATION_DURATION) * 0.7f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // 根据动画步骤计算颜色
            int brightness = 255 - animationStep * 25;
            if (brightness < 0) brightness = 0;

            g2d.setColor(new Color(brightness, brightness, 255));
            g2d.fillRect(0, row * GameConstants.CELL_SIZE,
                    GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE,
                    GameConstants.CELL_SIZE);
        }

        // 绘制被消除的列
        for (int col : clearCols) {
            float alpha = 0.7f - (animationStep / (float)ANIMATION_DURATION) * 0.7f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // 根据动画步骤计算颜色
            int brightness = 255 - animationStep * 25;
            if (brightness < 0) brightness = 0;

            g2d.setColor(new Color(brightness, brightness, 255));
            g2d.fillRect(col * GameConstants.CELL_SIZE, 0,
                    GameConstants.CELL_SIZE,
                    GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);
        }

        // 重置合成模式
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // 绘制粒子
        for (Particle p : particles) {
            p.draw(g2d);
        }

        g2d.dispose();
    }

    /**
     * 粒子类
     */
    private class Particle {
        double x, y;        // 位置
        double vx, vy;      // 速度
        double gravity = 0.1; // 重力
        Color color;        // 颜色
        int size;           // 大小
        int life;           // 生命周期

        public Particle(double x, double y, double vx, double vy, Color color, int life) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
            this.size = 2 + random.nextInt(4);
            this.life = life;
        }

        public void update() {
            x += vx;
            y += vy;
            vy += gravity;
            life--;

            // 尺寸随生命周期减小
            if (life < 5) {
                size = Math.max(1, size - 1);
            }
        }

        public void draw(Graphics2D g) {
            int alpha = Math.min(255, life * 17);
            g.setColor(new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    alpha
            ));
            g.fillOval((int)x, (int)y, size, size);
        }
    }
}
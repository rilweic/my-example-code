package com.game.cubev2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * 游戏结束时的动画效果
 */
public class GameEndAnimation {
    private GameBoard gameBoard;
    private final int score;
    private final Timer timer;
    private int animationStep = 0;
    private final Random random = new Random();
    private final ArrayList<Particle> particles = new ArrayList<>();
    private final ArrayList<FallingBlock> fallingBlocks = new ArrayList<>();
    private final Color[] particleColors = {
            Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE,
            Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK
    };

    /**
     * 创建游戏结束动画
     * @param gameBoard 游戏棋盘
     * @param score 最终分数
     */
    public GameEndAnimation(GameBoard gameBoard, int score) {
        this.gameBoard = gameBoard;
        this.score = score;

        // 初始化粒子
        initParticles();

        // 初始化方块下落效果
        initFallingBlocks();

        // 创建动画计时器
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;

                // 更新粒子
                updateParticles();

                // 更新下落方块
                updateFallingBlocks();

                // 重绘游戏板
                gameBoard.repaint();

                // 动画结束条件
                if (animationStep > 150) {
                    timer.stop();
                    showFinalScoreDialog();
                }
            }
        });
    }

    /**
     * 开始动画
     */
    public void start() {
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
     * 初始化粒子效果
     */
    private void initParticles() {
        // 在棋盘中心创建一些爆炸粒子
        int centerX = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE / 2;
        int centerY = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE / 2;

        for (int i = 0; i < 150; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = 1 + random.nextDouble() * 5;

            particles.add(new Particle(
                    centerX, centerY,
                    Math.cos(angle) * speed, Math.sin(angle) * speed,
                    particleColors[random.nextInt(particleColors.length)],
                    20 + random.nextInt(40)  // 生命周期
            ));
        }
    }

    /**
     * 初始化方块下落效果
     */
    private void initFallingBlocks() {
        // 获取当前棋盘状态
        boolean[][] board = gameBoard.getBoardState();
        if (board == null) return;

        // 为每个现有方块创建一个下落效果
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                if (board[y][x]) {
                    // 添加一个延迟随机的下落方块
                    fallingBlocks.add(new FallingBlock(
                            x * GameConstants.CELL_SIZE,
                            y * GameConstants.CELL_SIZE,
                            random.nextInt(20)  // 随机延迟
                    ));
                }
            }
        }
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
     * 更新下落方块
     */
    private void updateFallingBlocks() {
        for (FallingBlock block : fallingBlocks) {
            block.update();
        }
    }

    /**
     * 修改GameEndAnimation类的showFinalScoreDialog方法，确保对话框正确关闭
     */

    /**
     * 显示最终分数对话框
     */
    private void showFinalScoreDialog() {
        String message = "游戏结束!\n你的最终分数是: " + score;
        if (score >= 1000) {
            message += "\n太棒了! 你是游戏高手!";
        } else if (score >= 500) {
            message += "\n很好! 继续努力!";
        } else {
            message += "\n再接再厉!";
        }

        // 创建自定义对话框，以便能够控制其关闭
        final JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(gameBoard),
                "游戏结束", true);
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 创建消息标签，支持多行显示
        JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setFont(new Font("Dialog", Font.PLAIN, 14));

        // 创建按钮
        JButton restartButton = new JButton("再玩一次");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();

                // 延迟一小段时间后重置游戏，确保对话框已完全关闭
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (gameBoard instanceof GameBoard) {
                            ((GameBoard)gameBoard).resetGame();
                        }
                    }
                });
            }
        });

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);

        // 将组件添加到面板
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 设置对话框内容和属性
        dialog.setContentPane(panel);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(gameBoard);
        dialog.setResizable(false);

        // 添加窗口关闭监听器，在对话框关闭时也重置游戏
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (gameBoard instanceof GameBoard) {
                    ((GameBoard)gameBoard).resetGame();
                }
            }
        });

        // 显示对话框
        dialog.setVisible(true);
    }

    /**
     * 绘制动画
     * @param g Graphics对象
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // 绘制半透明黑色背景
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRect(0, 0, GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE,
                GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);

        // 绘制粒子
        for (Particle p : particles) {
            p.draw(g2d);
        }

        // 绘制下落方块
        for (FallingBlock block : fallingBlocks) {
            block.draw(g2d);
        }

        // 绘制"游戏结束"文字
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics fm = g2d.getFontMetrics();
        String gameOver = "游戏结束";
        int textX = (GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE - fm.stringWidth(gameOver)) / 2;
        int textY = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE / 2 - 50;

        // 文字阴影效果
        g2d.setColor(Color.BLACK);
        g2d.drawString(gameOver, textX + 2, textY + 2);
        g2d.setColor(Color.WHITE);
        g2d.drawString(gameOver, textX, textY);

        // 绘制分数文字
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        fm = g2d.getFontMetrics();
        String scoreText = "分数: " + score;
        textX = (GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE - fm.stringWidth(scoreText)) / 2;
        textY = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE / 2;

        // 分数文字阴影
        g2d.setColor(Color.BLACK);
        g2d.drawString(scoreText, textX + 2, textY + 2);
        g2d.setColor(Color.YELLOW);
        g2d.drawString(scoreText, textX, textY);
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
            if (life < 10) {
                size = Math.max(1, size - 1);
            }
        }

        public void draw(Graphics2D g) {
            int alpha = Math.min(255, life * 10);
            g.setColor(new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    alpha
            ));
            g.fillOval((int)x, (int)y, size, size);
        }


    }

    /**
     * 下落方块类
     */
    private class FallingBlock {
        int x, y;        // 初始位置
        double currentY; // 当前Y位置
        double speed;    // 下落速度
        int delay;       // 开始下落前的延迟
        int rotation;    // 旋转角度
        double rotationSpeed; // 旋转速度

        public FallingBlock(int x, int y, int delay) {
            this.x = x;
            this.y = y;
            this.currentY = y;
            this.delay = delay;
            this.speed = 1 + random.nextDouble() * 3;
            this.rotation = 0;
            this.rotationSpeed = -2 + random.nextDouble() * 4;
        }

        public void update() {
            if (delay > 0) {
                delay--;
                return;
            }

            currentY += speed;
            speed *= 1.05; // 加速下落
            rotation += rotationSpeed;
        }

        public void draw(Graphics2D g) {
            if (delay > 0) {
                // 延迟中不旋转，正常绘制
                drawCubeBlock(g, x, y);
                return;
            }

            // 绘制旋转下落的方块
            Graphics2D g2 = (Graphics2D) g.create();

            // 设置旋转
            g2.translate(x + GameConstants.CELL_SIZE / 2, currentY + GameConstants.CELL_SIZE / 2);
            g2.rotate(Math.toRadians(rotation));
            g2.translate(-(GameConstants.CELL_SIZE / 2), -(GameConstants.CELL_SIZE / 2));

            // 绘制方块
            drawCubeBlock(g2, 0, 0);

            g2.dispose();
        }

        private void drawCubeBlock(Graphics2D g, int x, int y) {
            int size = GameConstants.CELL_SIZE;
            int bevelSize = 6;

            // 绘制主体
            g.setColor(GameConstants.BLOCK_COLOR);
            g.fillRect(x, y, size, size);

            // 绘制边缘效果
            g.setColor(GameConstants.BLOCK_HIGHLIGHT);
            g.fillPolygon(
                    new int[] {x, x + size, x + size - bevelSize, x + bevelSize},
                    new int[] {y, y, y + bevelSize, y + bevelSize},
                    4
            );
            g.fillPolygon(
                    new int[] {x, x + bevelSize, x + bevelSize, x},
                    new int[] {y, y + bevelSize, y + size - bevelSize, y + size},
                    4
            );

            g.setColor(GameConstants.BLOCK_SHADOW);
            g.fillPolygon(
                    new int[] {x + size, x + size, x + size - bevelSize, x + size - bevelSize},
                    new int[] {y, y + size, y + size - bevelSize, y + bevelSize},
                    4
            );
            g.fillPolygon(
                    new int[] {x, x + size, x + size - bevelSize, x + bevelSize},
                    new int[] {y + size, y + size, y + size - bevelSize, y + size - bevelSize},
                    4
            );

            g.setColor(Color.BLACK);
            g.drawRect(x, y, size, size);
        }


    }
}
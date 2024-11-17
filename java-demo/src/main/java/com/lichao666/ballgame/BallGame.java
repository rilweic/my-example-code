package com.lichao666.ballgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class BallGame extends JFrame {
    private final double EPSILON = 0.02; // 用于处理浮点数精度问题的小容忍范围
    private final double VELOCITY_THRESHOLD = 0.5; // 速度阈值，小于此值时认为速度为0

    private final double SPEED_THRESHOLD = 5.0; // 定义足够大的速度阈值

    private static final double DAMPING = 0.95; // 阻尼系数，降低来提高稳定性

    private static final double MIN_ENERGY = 0.05; // 最小能量阈值

    public BallGame() {
        // 设置窗口标题
        setTitle("Ball Game");
        // 设置窗口关闭行为
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小
        setSize(800, 800);
        // 设置窗口居中
        setLocationRelativeTo(null);
        // 设置窗口不可改变大小
        setResizable(false);

        // 创建并添加画板
        add(new GamePanel());
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        // 在事件分派线程中创建和显示这个框架
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BallGame().setVisible(true);
            }
        });
    }

    // 内部类，用于绘图面板
    private class GamePanel extends JPanel {

        private ArrayList<Ball> balls = new ArrayList<>();
        private Timer timer;
        private final int circleRadius = 350; // 大圆半径
        private final Point2D.Double circleCenter = new Point2D.Double(400, 400); // 大圆中心坐标

        private final int interval = 20;

        public GamePanel() {
            initBalls(); // 初始化小球

            // 设置计时器，每40毫秒更新一次画面
            timer = new Timer(interval, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    update();
                    repaint();
                }
            });
            timer.start();
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    pointBallsTowards(mouseX, mouseY);
                }
            });
        }

        private void pointBallsTowards(int mouseX, int mouseY) {
            for (Ball ball : balls) {
                double dx = mouseX - ball.getX();
                double dy = mouseY - ball.getY();
                // 计算方向向量并规范化
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double directionX = dx / magnitude;
                double directionY = dy / magnitude;

                // 设置小球速度为单位方向向量乘以一个常数速率
                double speed = 15; // 速率可以根据需要调整
                ball.setVelocity(directionX * speed, directionY * speed);
            }
        }

        private void initBalls() {
            Random random = new Random();
            for (int i = 0; i < 200; i++) {
                double angle = random.nextDouble() * 2 * Math.PI;
                double r = random.nextDouble() * (circleRadius - 15);
                double x = circleCenter.x + r * Math.cos(angle);
                double y = circleCenter.y + r * Math.sin(angle);
                balls.add(new Ball(x, y, 0, 2)); // 初始速度向下
            }
        }

        private void checkCollisionWithCircle(Ball ball) {
            double dx = ball.getX() - circleCenter.x;
            double dy = ball.getY() - circleCenter.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            double overlap = distance + ball.getRadius() - circleRadius;


            if (overlap >= 0) {
                double nx = dx / distance;
                double ny = dy / distance;
                double backDistance = overlap + 0.1; // 确保球不会粘在边界上
                ball.setX(circleCenter.x + nx * (circleRadius - ball.getRadius() - backDistance));
                ball.setY(circleCenter.y + ny * (circleRadius - ball.getRadius() - backDistance));

                // 反射速度并考虑阻尼和能量阈值
                double dot = ball.getDx() * nx + ball.getDy() * ny;
                double reflectedDx = ball.getDx() - 2 * dot * nx;
                double reflectedDy = ball.getDy() - 2 * dot * ny;

                if (reflectedDy * reflectedDy < MIN_ENERGY) {  // 特别关注垂直方向的能量
                    reflectedDy = 0;
                }

                ball.setVelocity(reflectedDx * DAMPING, reflectedDy * DAMPING);
            }
        }





        private void update() {
            for (Ball ball : balls) {
                ball.move();  // 更新小球的位置和速度
                checkCollisionWithCircle(ball);  // 检查并处理与大圆的碰撞

                // 检查小球之间的碰撞
                for (Ball other : balls) {
                    if (ball != other) {
                        checkCollisionBetweenBalls(ball, other);
                    }
                }

                // 如果小球几乎不移动，则彻底停止
                if (Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy()) < MIN_ENERGY) {
                    ball.setVelocity(0, 0);
                }
            }
        }

        private void checkCollisionBetweenBalls(Ball ballA, Ball ballB) {
            double dx = ballA.getX() - ballB.getX();
            double dy = ballA.getY() - ballB.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double minDistance = ballA.getRadius() + ballB.getRadius();

            if (distance <= minDistance + EPSILON) {
                // 计算碰撞响应
                double nx = dx / distance;
                double ny = dy / distance;

                // 计算沿碰撞轴的速度分量
                double p = 2 * (ballA.getDx() * nx + ballA.getDy() * ny - ballB.getDx() * nx - ballB.getDy() * ny) / 2;

                // 计算新的速度向量，假设小球质量相同
                double vAx = ballA.getDx() - p * nx;
                double vAy = ballA.getDy() - p * ny;
                double vBx = ballB.getDx() + p * nx;
                double vBy = ballB.getDy() + p * ny;

                ballA.setVelocity(Math.abs(vAx) < VELOCITY_THRESHOLD ? 0 : vAx, Math.abs(vAy) < VELOCITY_THRESHOLD ? 0 : vAy);
                ballB.setVelocity(Math.abs(vBx) < VELOCITY_THRESHOLD ? 0 : vBx, Math.abs(vBy) < VELOCITY_THRESHOLD ? 0 : vBy);


                // 调整位置以避免小球相互重叠
                double overlap = 0.5 * (minDistance - distance + 1);
                ballA.setX(ballA.getX() + overlap * nx);
                ballA.setY(ballA.getY() + overlap * ny);
                ballB.setX(ballB.getX() - overlap * nx);
                ballB.setY(ballB.getY() - overlap * ny);

                // 计算速度差
                double diffDx = ballA.getDx() - ballB.getDx();
                double diffDy = ballA.getDy() - ballB.getDy();
                // 根据速度阈值判断是否生成新球
                // 使用速度差作为生成新球的条件
                double speedDiff = Math.sqrt(diffDx * diffDx + diffDy * diffDy);

                if (speedDiff > SPEED_THRESHOLD) {
                    if (balls.size() < 1000) {
//                        generateNewBall();
                    }

                }
            }
        }


        private void generateNewBall() {
            Random random = new Random();
            if (balls.isEmpty()) return; // 如果没有小球，直接返回，避免错误
            // 获取列表中第一个小球的半径
            int newBallRadius = balls.get(0).getRadius();
            // 大圆的顶部位置
            double x = circleCenter.x; // 圆心的X坐标
            double y = circleCenter.y - circleRadius + newBallRadius; // 圆心的Y坐标减去半径，再加上新球的半径
            // 随机速度向量，范围从 -3 到 3
            double dx = -3 + random.nextDouble() * 10; // -3 到 3
            double dy = 0; // -3 到 3
            Ball newBall = new Ball(x, y, dx, dy);

            balls.add(newBall);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // 设置背景颜色
            setBackground(Color.WHITE);

            Graphics2D g2d = (Graphics2D) g;

            // 开启抗锯齿渲染
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置画笔颜色
            g2d.setColor(Color.RED);

            // 计算圆的起始坐标
            int x = (getWidth() - 700) / 2;
            int y = (getHeight() - 700) / 2;

            // 绘制圆形
            g2d.drawOval(x, y, 700, 700);

            for (Ball ball : balls) {
                ball.draw(g2d);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            // 设置绘图区域的期望大小
            return new Dimension(800, 800);
        }
    }
}


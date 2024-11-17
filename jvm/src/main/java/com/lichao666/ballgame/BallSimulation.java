package com.lichao666.ballgame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BallSimulation extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int CIRCLE_DIAMETER = 700;
    private static final int CIRCLE_STROKE = 5;
    private static final int DELAY = 20;
    private static final int MAX_BALLS = 20; // 限制最大球数
    private DrawingPanel drawingPanel;
    private ArrayList<Ball> balls = new ArrayList<>();
    private Random random = new Random();
    private Timer timer;

    public BallSimulation() {
        setTitle("Ball Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        drawingPanel = new DrawingPanel();
        add(drawingPanel);

        setResizable(false);
        pack();
        setSize(WIDTH, HEIGHT);
        setVisible(true);

        initializeBalls();
        startAnimation();
    }

    private void initializeBalls() {
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        int radius = CIRCLE_DIAMETER / 2 - CIRCLE_STROKE;

        for (int i = 0; i < 2; i++) {
            Ball ball;
            do {
                double angle = random.nextDouble() * 2 * Math.PI;
                double distance = random.nextDouble() * (radius - Ball.getRadius());
                double x = centerX + distance * Math.cos(angle);
                double y = centerY + distance * Math.sin(angle);
                ball = new Ball(x, y);
            } while (checkCollisions(ball));
            balls.add(ball);
        }
    }

    private boolean checkCollisions(Ball newBall) {
        for (Ball ball : balls) {
            if (newBall.checkCollision(ball)) {
                return true;
            }
        }
        return false;
    }

    private void startAnimation() {
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBalls();
                drawingPanel.repaint();
            }
        });
        timer.start();
    }

    private void updateBalls() {
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        int radius = CIRCLE_DIAMETER / 2 - CIRCLE_STROKE;

        for (Ball ball : balls) {
            ball.move();
            ball.checkBoundaryCollision(centerX, centerY, radius);
        }

        boolean collisionOccurred = false;
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                if (balls.get(i).checkCollision(balls.get(j))) {
                    collisionOccurred = true;
                    break;
                }
            }
            if (collisionOccurred) break;
        }

        if (collisionOccurred && balls.size() < MAX_BALLS) {
            addNewBall();
        }
    }

    private void addNewBall() {
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        int radius = CIRCLE_DIAMETER / 2 - CIRCLE_STROKE;

        Ball newBall = new Ball(centerX, centerY - radius + Ball.getRadius());
        newBall.setVelocity(random.nextDouble() * 4 - 2, 0);
        balls.add(newBall);
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(CIRCLE_STROKE));
            int x = (panelWidth - CIRCLE_DIAMETER) / 2;
            int y = (panelHeight - CIRCLE_DIAMETER) / 2;
            g2d.drawOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);

            for (Ball ball : balls) {
                ball.draw(g2d);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BallSimulation());
    }
}

class Ball {
    private double x, y;
    private double vx, vy;
    private static final double RADIUS = 10;
    private static final double GRAVITY = 0.5;
    private static final double DAMPING = 0.8;

    public Ball(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
    }

    public void move() {
        vy += GRAVITY;
        x += vx;
        y += vy;
    }

    public void checkBoundaryCollision(int centerX, int centerY, int radius) {
        double dx = x - centerX;
        double dy = y - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > radius - RADIUS) {
            double angle = Math.atan2(dy, dx);
            x = centerX + (radius - RADIUS) * Math.cos(angle);
            y = centerY + (radius - RADIUS) * Math.sin(angle);

            double normalX = dx / distance;
            double normalY = dy / distance;
            double tangentX = -normalY;
            double tangentY = normalX;

            double dotProductNormal = vx * normalX + vy * normalY;
            double dotProductTangent = vx * tangentX + vy * tangentY;

            vx = tangentX * dotProductTangent - normalX * dotProductNormal * DAMPING;
            vy = tangentY * dotProductTangent - normalY * dotProductNormal * DAMPING;
        }
    }

    public boolean checkCollision(Ball other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 2 * RADIUS;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) (x - RADIUS), (int) (y - RADIUS), (int) (2 * RADIUS), (int) (2 * RADIUS));
    }

    public static double getRadius() {
        return RADIUS;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }
}
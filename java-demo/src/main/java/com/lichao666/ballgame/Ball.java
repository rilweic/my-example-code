package com.lichao666.ballgame;

import lombok.Data;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

@Data
public class Ball {
    private double x, y; // 小球的当前位置
    private double dx, dy; // 小球的速度向量
    private int radius = 15; // 小球的半径
    private Color color; // 小球的颜色
    private static final double GRAVITY = 0.2;

    private static final double THRESHOLD = 0.1;  // 速度阈值
    private static final double DAMPING = 0.98; // 阻尼系数
    private static final double MIN_ENERGY = 0.01; // 最小能量阈值

    public Ball(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = randomColor();
    }

    public Ball(double x, double y, double dx, double dy,int radius) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = randomColor();
        this.radius = radius;
    }

//    public void move() {
//
//        double speedSquared = dx * dx + dy * dy;
//
//        if (speedSquared > MIN_ENERGY || Math.abs(dy) > THRESHOLD) {
//            dy += GRAVITY;  // 应用重力
//            dx *= DAMPING;
//            dy *= DAMPING;
//
//            x += dx;
//            y += dy;
//        } else {
//            dx = 0;
//            dy = 0;
//        }
//    }


    public void move() {
        dy += GRAVITY;  // 永远应用重力
        dx *= DAMPING;  // 应用阻尼
        dy *= DAMPING;

        x += dx;
        y += dy;

        // 增加阻尼，特别是在低速时
        if (Math.abs(dx) < THRESHOLD) dx *= 0.5;
        if (Math.abs(dy) < THRESHOLD) dy *= 0.5;

        if (Math.abs(dx) < MIN_ENERGY && Math.abs(dy) < MIN_ENERGY) {
            dx = 0;
            dy = 0;
        }
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    private Color randomColor() {
        Random rand = new Random();
        // 随机生成RGB值
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

        // 应用速度阈值检查
        if (Math.abs(this.dx) < THRESHOLD) this.dx = 0;
        if (Math.abs(this.dy) < THRESHOLD) this.dy = 0;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
    }
}


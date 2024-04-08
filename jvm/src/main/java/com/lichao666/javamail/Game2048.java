package com.lichao666.javamail;

// Create a 480x512 interface
// Import necessary libraries
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game2048 extends JPanel {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 512;
    private static final int TRIANGLE_SIZE = 50;

    public Game2048() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);
        int startX = (WIDTH / 2) - (TRIANGLE_SIZE / 2);
        int startY = (HEIGHT / 2) - (TRIANGLE_SIZE / 2);
        int[] xPoints = {startX, startX + TRIANGLE_SIZE, startX + (TRIANGLE_SIZE / 2)};
        int[] yPoints = {startY, startY, startY + TRIANGLE_SIZE};
        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        g2d.fill(triangle);
        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangle GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Game2048());
        frame.pack();
        frame.setVisible(true);
    }



}

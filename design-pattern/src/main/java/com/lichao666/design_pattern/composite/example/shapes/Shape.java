package com.lichao666.design_pattern.composite.example.shapes;


import java.awt.Graphics;

public interface Shape {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    public void move(int x, int y);
    public boolean isInsideBounds(int x, int y);
    public void select();
    public void unSelect();
    public boolean isSelected();
    public void paint(Graphics graphics);
}

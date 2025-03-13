package com.game.cubev2;

import java.awt.Color;

/**
 * 表示游戏中的方块
 */
public class Block {
    private boolean[][] shape;
    private Color color;
    private int width;
    private int height;

    /**
     * 创建一个新的方块
     * @param shape 方块形状的二维数组表示
     * @param color 方块的颜色
     */
    public Block(boolean[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.height = shape.length;
        this.width = shape[0].length;
    }

    /**
     * 获取方块形状
     * @return 表示方块形状的二维布尔数组
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * 获取方块颜色
     * @return 方块的颜色
     */
    public Color getColor() {
        return color;
    }

    /**
     * 获取方块宽度
     * @return 方块的宽度（单位：单元格）
     */
    public int getWidth() {
        return width;
    }

    /**
     * 获取方块高度
     * @return 方块的高度（单位：单元格）
     */
    public int getHeight() {
        return height;
    }

    /**
     * 创建方块的旋转副本
     * @return 旋转90度后的新方块
     */
    public Block rotate() {
        boolean[][] rotated = new boolean[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotated[x][height - 1 - y] = shape[y][x];
            }
        }
        return new Block(rotated, color);
    }

    /**
     * 计算方块包含的子方块数量
     * @return 子方块数量
     */
    public int countCells() {
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (shape[y][x]) {
                    count++;
                }
            }
        }
        return count;
    }
}
package com.game.cube;

import java.util.List;

public class Block {
    private List<int[]> shape; // 方块的形状，存储单元格的相对坐标
    private String name;       // 方块的名称，用于标识

    public Block(String name, List<int[]> shape) {
        this.name = name;
        this.shape = shape;
    }

    public List<int[]> getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }
}
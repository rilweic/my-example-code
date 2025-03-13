package com.game.cube;

import java.util.List;

public class Grid {
    private boolean[][] cells; // 10x10网格，true表示占用，false表示空
    private static final int SIZE = 10;

    public Grid() {
        cells = new boolean[SIZE][SIZE];
    }

    // 检查是否可以在指定位置放置方块
    public boolean canPlaceBlock(Block block, int row, int col) {
        List<int[]> shape = block.getShape();
        for (int[] offset : shape) {
            int r = row + offset[0];
            int c = col + offset[1];
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE || cells[r][c]) {
                return false;
            }
        }
        return true;
    }

    // 放置方块到指定位置
    public void placeBlock(Block block, int row, int col) {
        List<int[]> shape = block.getShape();
        for (int[] offset : shape) {
            int r = row + offset[0];
            int c = col + offset[1];
            cells[r][c] = true;
        }
    }

    // 清除填满的行和列，返回清除的总数
    public int clearCompleteRowsAndColumns() {
        int totalClearances = 0;
        boolean changed;
        do {
            changed = false;
            for (int i = 0; i < SIZE; i++) {
                if (isRowComplete(i)) {
                    clearRow(i);
                    totalClearances++;
                    changed = true;
                }
            }
            for (int j = 0; j < SIZE; j++) {
                if (isColumnComplete(j)) {
                    clearColumn(j);
                    totalClearances++;
                    changed = true;
                }
            }
        } while (changed);
        return totalClearances;
    }

    private boolean isRowComplete(int row) {
        for (int j = 0; j < SIZE; j++) {
            if (!cells[row][j]) return false;
        }
        return true;
    }

    private void clearRow(int row) {
        for (int j = 0; j < SIZE; j++) {
            cells[row][j] = false;
        }
    }

    private boolean isColumnComplete(int col) {
        for (int i = 0; i < SIZE; i++) {
            if (!cells[i][col]) return false;
        }
        return true;
    }

    private void clearColumn(int col) {
        for (int i = 0; i < SIZE; i++) {
            cells[i][col] = false;
        }
    }

    public boolean[][] getCells() {
        return cells;
    }
}
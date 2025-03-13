package com.game.cubev2;

import java.awt.Color;

/**
 * 游戏中使用的常量定义
 */
public class GameConstants {
    // 游戏棋盘大小
    public static final int BOARD_SIZE = 8;

    // 单元格大小（像素）
    public static final int CELL_SIZE = 50;

    // 预览区块数量
    public static final int PREVIEW_PIECES = 3;

    // 方块颜色 - 统一使用棕色
    public static final Color BLOCK_COLOR = new Color(139, 69, 19); // 棕色

    // 方块立体效果的颜色
    public static final Color BLOCK_HIGHLIGHT = new Color(160, 82, 45); // 亮棕色
    public static final Color BLOCK_SHADOW = new Color(101, 67, 33);    // 暗棕色

}
package com.game.cubev2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.min;

/**
 * 预览面板，显示下一个可用的方块
 */
public class PreviewPanel extends JPanel {
    private GameManager gameManager;
    private JPanel[] previewPanels;
    private GameBoard gameBoard;

    /**
     * 创建预览面板
     * @param gameManager 游戏管理器
     * @param gameBoard 游戏棋盘（用于重绘）
     */
    public PreviewPanel(GameManager gameManager, GameBoard gameBoard) {
        this.gameManager = gameManager;
        this.gameBoard = gameBoard;

        // 使用绝对布局
        setLayout(null);

        // 计算预览面板尺寸 - 增加尺寸以确保大型方块能够显示
        int panelWidth = (GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE) / GameConstants.PREVIEW_PIECES - 10;
        // 增加高度，确保高的方块也能完整显示
        int panelHeight = (GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE) / 4;

        previewPanels = new JPanel[GameConstants.PREVIEW_PIECES];

        // 创建预览子面板
        for (int i = 0; i < GameConstants.PREVIEW_PIECES; i++) {
            final int index = i;
            previewPanels[i] = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawPreviewBlock(g, index);
                }
            };

            // 设置固定大小
            previewPanels[i].setPreferredSize(new Dimension(panelWidth, panelHeight));
            previewPanels[i].setSize(panelWidth, panelHeight);
            previewPanels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            previewPanels[i].addMouseListener(new PreviewMouseListener(i));

            // 设置位置
            int x = i * (panelWidth + 5);
            previewPanels[i].setBounds(x, 0, panelWidth, panelHeight);

            add(previewPanels[i]);
        }

        // 设置整个预览面板的大小
        setPreferredSize(new Dimension(GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE, panelHeight));
        setSize(GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE, panelHeight);
    }

    /**
     * 修复PreviewPanel类的drawPreviewBlock方法，确保大尺寸方块能完整显示
     */

    /**
     * 绘制预览方块
     * @param g Graphics对象
     * @param index 预览面板索引
     */

    private void drawPreviewBlock(Graphics g, int index) {
        ArrayList<Block> nextBlocks = gameManager.getNextBlocks();

        if (index >= nextBlocks.size()) return;

        Block block = nextBlocks.get(index);

        // 如果该位置没有方块（已使用），则什么都不画
        if (block == null) return;

        boolean isSelected = (index == gameManager.getSelectedPreviewIndex());

        int panelWidth = previewPanels[index].getWidth();
        int panelHeight = previewPanels[index].getHeight();

        // 获取方块的尺寸
        int blockWidth = block.getWidth();
        int blockHeight = block.getHeight();

        // 计算单元格大小
        int cellSize;

        // 对于1x1的单个方块，使用较小的尺寸以避免看起来过大
        if (blockWidth == 1 && blockHeight == 1) {
            cellSize = Math.min(panelWidth, panelHeight) / 3; // 使其大约为面板尺寸的1/3
        } else {
            // 对于其他方块，计算合适的尺寸使其填满预览面板但留有边距
            cellSize = Math.min(
                    (panelWidth - 20) / Math.max(1, blockWidth),
                    (panelHeight - 20) / Math.max(1, blockHeight)
            );
        }

        // 确保方块最小有一定尺寸
        cellSize = Math.max(cellSize, 10);

        // 居中显示
        int offsetX = (panelWidth - blockWidth * cellSize) / 2;
        int offsetY = (panelHeight - blockHeight * cellSize) / 2;

        // 如果被选中，绘制选中边框
        if (isSelected) {
            g.setColor(Color.RED);
            g.drawRect(
                    offsetX - 5,
                    offsetY - 5,
                    blockWidth * cellSize + 10,
                    blockHeight * cellSize + 10
            );
        }

        // 绘制方块
        boolean[][] shape = block.getShape();
        for (int y = 0; y < blockHeight; y++) {
            for (int x = 0; x < blockWidth; x++) {
                if (shape[y][x]) {
                    // 绘制立体效果的方块
                    drawCubeBlockSmall(g, offsetX + x * cellSize, offsetY + y * cellSize, cellSize);
                }
            }
        }
    }

    /**
     * 绘制小型立体方块（用于预览区）
     * @param g Graphics对象
     * @param x 绘制位置的x坐标
     * @param y 绘制位置的y坐标
     * @param size 方块大小
     */
    private void drawCubeBlockSmall(Graphics g, int x, int y, int size) {
        int bevelSize = min(3, size / 5); // 立体效果的边缘宽度，确保不超过方块大小的1/5

        // 绘制主体
        g.setColor(GameConstants.BLOCK_COLOR);
        g.fillRect(x, y, size, size);

        // 绘制左边和上边的高光（亮边）
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

        // 绘制右边和下边的阴影（暗边）
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

        // 绘制黑色边框
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
    }

    @Override
    public void repaint() {
        super.repaint();
        if (previewPanels != null) {
            for (JPanel panel : previewPanels) {
                if (panel != null) {
                    panel.repaint();
                }
            }
        }
    }

    /**
     * 预览面板鼠标监听器
     */
    private class PreviewMouseListener extends MouseAdapter {
        private int index;

        public PreviewMouseListener(int index) {
            this.index = index;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // 只允许选择非空的方块
            ArrayList<Block> nextBlocks = gameManager.getNextBlocks();
            if (index < nextBlocks.size() && nextBlocks.get(index) != null) {
                // 选择该方块
                gameManager.selectPreviewBlock(index);

                // 创建自定义游标（使用透明图像可以暂时隐藏游标，因为我们将在游戏板上显示幽灵方块）
                try {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Image cursorImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                    Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");
                    gameBoard.setCursor(invisibleCursor);
                } catch (Exception ex) {
                    // 如果创建自定义光标失败，就继续使用默认光标
                    System.err.println("无法创建自定义光标: " + ex.getMessage());
                }

                // 模拟开始拖拽
                gameBoard.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 恢复默认游标
            gameBoard.setCursor(Cursor.getDefaultCursor());
        }
    }
}
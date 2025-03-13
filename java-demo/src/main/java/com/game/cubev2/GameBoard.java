package com.game.cubev2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.AlphaComposite;
import java.util.ArrayList;

/**
 * 游戏棋盘面板，负责显示和处理游戏主界面
 */
public class GameBoard extends JPanel {
    private GameManager gameManager;
    private static final int BOARD_PIXEL_SIZE = GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE;
    private GameEndAnimation endAnimation; // 游戏结束动画
    private LineClearAnimation clearAnimation; // 消除动画
    /**
     * 创建游戏棋盘
     * @param gameManager 游戏管理器
     */
    public GameBoard(GameManager gameManager) {
        this.gameManager = gameManager;

        // 不使用布局管理器
        setLayout(null);

        // 确保游戏棋盘为固定的正方形尺寸
        setPreferredSize(new Dimension(BOARD_PIXEL_SIZE, BOARD_PIXEL_SIZE));
        setMinimumSize(new Dimension(BOARD_PIXEL_SIZE, BOARD_PIXEL_SIZE));
        setMaximumSize(new Dimension(BOARD_PIXEL_SIZE, BOARD_PIXEL_SIZE));
        setSize(BOARD_PIXEL_SIZE, BOARD_PIXEL_SIZE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 添加鼠标监听器
        addMouseListener(new BoardMouseListener());
        addMouseMotionListener(new BoardMouseMotionListener());
    }

    /**
     * 启动消除动画
     * @param clearRows 要消除的行列表
     * @param clearCols 要消除的列列表
     */
    public void startClearAnimation(java.util.List<Integer> clearRows, java.util.List<Integer> clearCols) {
        clearAnimation = new LineClearAnimation(this, clearRows, clearCols);
        clearAnimation.start();
    }

    /**
     * 消除动画完成的回调
     * 这个方法将被LineClearAnimation在动画结束时调用
     */
    public void clearAnimationComplete() {
        clearAnimation = null;
        // 通知GameManager消除动画已完成，可以继续游戏逻辑
        gameManager.continueClearingLines();
    }

    /**
     * 启动游戏结束动画
     * @param score 最终分数
     */
    public void startGameEndAnimation(int score) {
        endAnimation = new GameEndAnimation(this, score);
        endAnimation.start();
        repaint();
    }

    /**
     * 获取当前游戏板状态，用于游戏结束动画
     * @return 棋盘状态的二维数组
     */
    public boolean[][] getBoardState() {
        return gameManager.getBoard();
    }


    public void resetGame() {
        // 清除游戏结束动画
        endAnimation = null;

        // 重置游戏管理器
        gameManager.resetGame();

        // 重绘界面
        repaint();

        // 恢复光标
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制网格
        drawGrid(g);

        // 绘制已放置的方块
        drawPlacedBlocks(g);


        // 如果有选中的方块，绘制"幽灵"方块和可能的消除提示
        if (gameManager.getSelectedBlock() != null) {
            Point mousePos = getMousePosition();
            if (mousePos != null) {
                int gridX = mousePos.x / GameConstants.CELL_SIZE;
                int gridY = mousePos.y / GameConstants.CELL_SIZE;

                Block selectedBlock = gameManager.getSelectedBlock();

                // 调整位置以考虑方块中心
                gridX -= selectedBlock.getWidth() / 2;
                gridY -= selectedBlock.getHeight() / 2;

                // 检查是否可以在当前位置放置方块
                boolean canPlace = gameManager.canPlaceBlock(selectedBlock, gridX, gridY);

                if (canPlace) {
                    // 预测并高亮显示可能会被消除的行和列
                    highlightPotentialClears(g, selectedBlock, gridX, gridY);
                }

                // 绘制"幽灵"方块
                drawGhostBlock(g, mousePos);
            }
        }

        // 如果有消除动画正在运行，绘制它
        if (clearAnimation != null && clearAnimation.isRunning()) {
            clearAnimation.draw(g);
        }

        // 如果游戏结束动画存在并正在运行，绘制它
        if (endAnimation != null && endAnimation.isRunning()) {
            endAnimation.draw(g);
        }
    }

    /**
     * 高亮显示放置方块后可能会被消除的行和列
     * @param g Graphics对象
     * @param block 要放置的方块
     * @param gridX 网格X坐标
     * @param gridY 网格Y坐标
     */
    private void highlightPotentialClears(Graphics g, Block block, int gridX, int gridY) {
        // 创建棋盘的临时副本
        boolean[][] tempBoard = new boolean[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                tempBoard[y][x] = gameManager.getBoard()[y][x];
            }
        }

        // 在临时棋盘上"放置"方块
        boolean[][] shape = block.getShape();
        for (int y = 0; y < block.getHeight(); y++) {
            for (int x = 0; x < block.getWidth(); x++) {
                if (shape[y][x]) {
                    int boardX = gridX + x;
                    int boardY = gridY + y;

                    // 确保在边界内
                    if (boardX >= 0 && boardX < GameConstants.BOARD_SIZE &&
                            boardY >= 0 && boardY < GameConstants.BOARD_SIZE) {
                        tempBoard[boardY][boardX] = true;
                    }
                }
            }
        }

        // 检查哪些行会被填满
        ArrayList<Integer> fullRows = new ArrayList<>();
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            boolean fullRow = true;
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                if (!tempBoard[y][x]) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                fullRows.add(y);
            }
        }

        // 检查哪些列会被填满
        ArrayList<Integer> fullCols = new ArrayList<>();
        for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
            boolean fullColumn = true;
            for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
                if (!tempBoard[y][x]) {
                    fullColumn = false;
                    break;
                }
            }

            if (fullColumn) {
                fullCols.add(x);
            }
        }

        // 高亮显示将被消除的行
        if (!fullRows.isEmpty()) {
            g.setColor(new Color(255, 255, 0, 100)); // 半透明黄色
            for (int row : fullRows) {
                g.fillRect(0, row * GameConstants.CELL_SIZE,
                        GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE,
                        GameConstants.CELL_SIZE);
            }
        }

        // 高亮显示将被消除的列
        if (!fullCols.isEmpty()) {
            g.setColor(new Color(255, 255, 0, 100)); // 半透明黄色
            for (int col : fullCols) {
                g.fillRect(col * GameConstants.CELL_SIZE, 0,
                        GameConstants.CELL_SIZE,
                        GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);
            }
        }

        // 在消除线条上方绘制放大符号或消除动画
        if (!fullRows.isEmpty() || !fullCols.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(255, 165, 0)); // 橙色
            g2d.setStroke(new BasicStroke(2.0f));

            // 在每个将被消除的行和列的边缘绘制闪烁边框
            for (int row : fullRows) {
                g2d.drawRect(0, row * GameConstants.CELL_SIZE,
                        GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE,
                        GameConstants.CELL_SIZE);
            }

            for (int col : fullCols) {
                g2d.drawRect(col * GameConstants.CELL_SIZE, 0,
                        GameConstants.CELL_SIZE,
                        GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);
            }

            g2d.dispose();
        }
    }

    /**
     * 绘制网格
     * @param g Graphics对象
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);

        // 绘制垂直线
        for (int i = 0; i <= GameConstants.BOARD_SIZE; i++) {
            g.drawLine(
                    i * GameConstants.CELL_SIZE, 0,
                    i * GameConstants.CELL_SIZE, GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE
            );
        }

        // 绘制水平线
        for (int i = 0; i <= GameConstants.BOARD_SIZE; i++) {
            g.drawLine(
                    0, i * GameConstants.CELL_SIZE,
                    GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE, i * GameConstants.CELL_SIZE
            );
        }
    }

    /**
     * 绘制已放置的方块
     * @param g Graphics对象
     */
    private void drawPlacedBlocks(Graphics g) {
        boolean[][] board = gameManager.getBoard();

        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                if (board[y][x]) {
                    drawCubeBlock(g, x * GameConstants.CELL_SIZE, y * GameConstants.CELL_SIZE);
                }
            }
        }
    }

    /**
     * 绘制立体效果的方块
     * @param g Graphics对象
     * @param x 绘制位置的x坐标
     * @param y 绘制位置的y坐标
     */
    private void drawCubeBlock(Graphics g, int x, int y) {
        int size = GameConstants.CELL_SIZE;
        int bevelSize = 6; // 立体效果的边缘宽度

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

    /**
     * 绘制"幽灵"方块（显示方块即将放置的位置）
     * @param g Graphics对象
     * @param mousePos 鼠标位置
     */
    private void drawGhostBlock(Graphics g, Point mousePos) {
        Block selectedBlock = gameManager.getSelectedBlock();
        if (selectedBlock == null) return;

        int gridX = mousePos.x / GameConstants.CELL_SIZE;
        int gridY = mousePos.y / GameConstants.CELL_SIZE;

        // 调整位置以考虑方块中心
        gridX -= selectedBlock.getWidth() / 2;
        gridY -= selectedBlock.getHeight() / 2;

        // 检查是否可以在当前位置放置方块
        boolean canPlace = gameManager.canPlaceBlock(selectedBlock, gridX, gridY);

        boolean[][] shape = selectedBlock.getShape();

        // 绘制"幽灵"方块
        for (int y = 0; y < selectedBlock.getHeight(); y++) {
            for (int x = 0; x < selectedBlock.getWidth(); x++) {
                if (shape[y][x]) {
                    int posX = (gridX + x) * GameConstants.CELL_SIZE;
                    int posY = (gridY + y) * GameConstants.CELL_SIZE;

                    // 根据是否可放置选择不同的显示效果
                    if (canPlace) {
                        // 可放置 - 绘制半透明的立体方块
                        drawTransparentCubeBlock(g, posX, posY);
                    } else {
                        // 不可放置 - 绘制红色边框提示
                        g.setColor(new Color(255, 0, 0, 128));
                        g.fillRect(posX, posY, GameConstants.CELL_SIZE, GameConstants.CELL_SIZE);
                        g.setColor(Color.RED);
                        g.drawRect(posX, posY, GameConstants.CELL_SIZE, GameConstants.CELL_SIZE);
                    }
                }
            }
        }
    }

    /**
     * 绘制半透明立体效果的方块（用于预览）
     * @param g Graphics对象
     * @param x 绘制位置的x坐标
     * @param y 绘制位置的y坐标
     */
    private void drawTransparentCubeBlock(Graphics g, int x, int y) {
        int size = GameConstants.CELL_SIZE;
        int bevelSize = 6; // 立体效果的边缘宽度

        // 使用半透明效果
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        // 绘制主体
        g2d.setColor(GameConstants.BLOCK_COLOR);
        g2d.fillRect(x, y, size, size);

        // 绘制左边和上边的高光（亮边）
        g2d.setColor(GameConstants.BLOCK_HIGHLIGHT);
        g2d.fillPolygon(
                new int[] {x, x + size, x + size - bevelSize, x + bevelSize},
                new int[] {y, y, y + bevelSize, y + bevelSize},
                4
        );
        g2d.fillPolygon(
                new int[] {x, x + bevelSize, x + bevelSize, x},
                new int[] {y, y + bevelSize, y + size - bevelSize, y + size},
                4
        );

        // 绘制右边和下边的阴影（暗边）
        g2d.setColor(GameConstants.BLOCK_SHADOW);
        g2d.fillPolygon(
                new int[] {x + size, x + size, x + size - bevelSize, x + size - bevelSize},
                new int[] {y, y + size, y + size - bevelSize, y + bevelSize},
                4
        );
        g2d.fillPolygon(
                new int[] {x, x + size, x + size - bevelSize, x + bevelSize},
                new int[] {y + size, y + size, y + size - bevelSize, y + size - bevelSize},
                4
        );

        // 绘制黑色边框
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, size, size);

        g2d.dispose();
    }

    private class BoardMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            // 拖拽模式下，mousePressed不做任何操作
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (gameManager.isGameOver() || gameManager.getSelectedBlock() == null) return;

            int gridX = e.getX() / GameConstants.CELL_SIZE;
            int gridY = e.getY() / GameConstants.CELL_SIZE;

            gameManager.tryPlaceBlock(gridX, gridY);
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (gameManager.getSelectedBlock() != null) {
                repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // 确保鼠标拖动时重绘，以更新"幽灵"方块位置和消除提示
            if (gameManager.getSelectedBlock() != null) {
                repaint();
            }
        }

    }
}
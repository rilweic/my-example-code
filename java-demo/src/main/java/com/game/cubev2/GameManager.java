package com.game.cubev2;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 管理游戏逻辑和状态的类
 */
public class GameManager {
    // 游戏状态
    private boolean[][] board = new boolean[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
    // boardColors 数组不再需要，因为所有方块都是同一颜色
    private ArrayList<Block> nextBlocks = new ArrayList<>();
    private Block selectedBlock;
    private int selectedPreviewIndex = -1;
    private int score = 0;
    private boolean gameOver = false;

    private List<Integer> pendingClearRows = new ArrayList<>(); // 等待消除的行
    private List<Integer> pendingClearCols = new ArrayList<>(); // 等待消除的列
    private boolean animatingClear = false; // 是否正在播放消除动画

    // 随机数生成器
    private Random random = new Random();

    // UI组件引用
    private GameBoard gameBoard;
    private PreviewPanel previewPanel;
    private ScorePanel scorePanel;

    /**
     * 设置UI组件引用
     */
    public void setUIComponents(GameBoard gameBoard, PreviewPanel previewPanel, ScorePanel scorePanel) {
        this.gameBoard = gameBoard;
        this.previewPanel = previewPanel;
        this.scorePanel = scorePanel;
    }

    /**
     * 重置游戏状态，开始新游戏
     */
    public void resetGame() {
        // 直接调用初始化方法，确保行为一致
        initializeGame();
    }



    public void initializeGame() {
        // 清空棋盘
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                board[y][x] = false;
            }
        }


        initializeRandomBlocks();
        // 重置分数
        score = 0;

        // 重置游戏结束标志
        gameOver = false;

        // 清空选择状态
        selectedBlock = null;
        selectedPreviewIndex = -1;

        // 生成初始的下一组方块
        generateNextBlocks();

        // 刷新UI
        refreshUI();
    }

    private void initializeRandomBlocks() {
        // 增加初始方块数量
        int initialBlocks = 8;

        // 定义中心区域，使方块集中在棋盘中央部分
        int minX = 1;
        int maxX = GameConstants.BOARD_SIZE - 2;
        int minY = 1;
        int maxY = GameConstants.BOARD_SIZE - 2;

        // 先在中心区域随机放置一些方块
        for (int i = 0; i < initialBlocks * 2/3; i++) { // 2/3的方块放在中心区域
            int x = minX + random.nextInt(maxX - minX);
            int y = minY + random.nextInt(maxY - minY);

            // 避免在同一位置重复放置
            if (!board[y][x]) {
                board[y][x] = true;
            } else {
                // 如果位置已被占用，重试
                i--;
            }
        }

        // 剩余方块可以放在整个棋盘上
        for (int i = 0; i < initialBlocks * 1/3; i++) { // 1/3的方块放在整个棋盘范围
            int x = random.nextInt(GameConstants.BOARD_SIZE);
            int y = random.nextInt(GameConstants.BOARD_SIZE);

            // 避免在同一位置重复放置
            if (!board[y][x]) {
                board[y][x] = true;
            } else {
                // 如果位置已被占用，重试
                i--;
            }
        }

        // 添加临近方块，使方块分布更紧凑
        addAdjacentBlocks();
    }

    /**
     * 添加临近现有方块的新方块，使分布更紧凑
     */
    private void addAdjacentBlocks() {
        // 复制当前棋盘状态
        boolean[][] currentBoard = new boolean[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                currentBoard[y][x] = board[y][x];
            }
        }

        // 定义可能的相邻位置（上、下、左、右、对角线）
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        // 对于每个现有方块，尝试在其周围添加新方块
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                if (currentBoard[y][x]) {
                    // 对于每个方向
                    for (int[] dir : directions) {
                        int newY = y + dir[0];
                        int newX = x + dir[1];

                        // 检查新位置是否有效且为空
                        if (newY >= 0 && newY < GameConstants.BOARD_SIZE &&
                                newX >= 0 && newX < GameConstants.BOARD_SIZE &&
                                !board[newY][newX]) {

                            // 有30%的概率在此位置添加新方块
                            if (random.nextDouble() < 0.3) {
                                board[newY][newX] = true;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 刷新所有UI组件
     */
    public void refreshUI() {
        if (gameBoard != null) gameBoard.repaint();
        if (previewPanel != null) previewPanel.repaint();
        if (scorePanel != null) scorePanel.updateScore(score);
    }

    /**
     * 生成下一组方块
     */
    public void generateNextBlocks() {
        nextBlocks.clear();
        for (int i = 0; i < GameConstants.PREVIEW_PIECES; i++) {
            generateNewBlock();
        }
    }

    /**
     * 生成一个新方块
     */
    public void generateNewBlock() {
        Block block = generateRandomBlock();
        nextBlocks.add(block);
    }

    /**
     * 随机生成一个方块
     * @return 随机生成的方块
     */
    private Block generateRandomBlock() {
        // 使用BlockGenerator生成随机方块
        return BlockGenerator.generateRandomBlock();
    }

    /**
     * 选择预览区的方块
     * @param index 预览区索引
     */
    public void selectPreviewBlock(int index) {
        if (!gameOver && index < nextBlocks.size() && nextBlocks.get(index) != null) {
            selectedBlock = nextBlocks.get(index);
            selectedPreviewIndex = index;
            refreshUI();
        }
    }

    /**
     * 尝试在指定位置放置当前选中的方块
     * @param gridX 网格X坐标
     * @param gridY 网格Y坐标
     * @return 是否成功放置
     */
    public boolean tryPlaceBlock(int gridX, int gridY) {
        if (gameOver || selectedBlock == null) return false;

        // 调整位置以考虑方块中心
        gridX -= selectedBlock.getWidth() / 2;
        gridY -= selectedBlock.getHeight() / 2;

        if (canPlaceBlock(selectedBlock, gridX, gridY)) {
            placeBlock(selectedBlock, gridX, gridY);
            return true;
        }

        return false;
    }

    /**
     * 检查是否可以在指定位置放置方块
     * @param block 要放置的方块
     * @param gridX 网格X坐标
     * @param gridY 网格Y坐标
     * @return 是否可以放置
     */
    public boolean canPlaceBlock(Block block, int gridX, int gridY) {
        boolean[][] shape = block.getShape();

        // 检查方块是否适合在棋盘上
        for (int y = 0; y < block.getHeight(); y++) {
            for (int x = 0; x < block.getWidth(); x++) {
                if (shape[y][x]) {
                    int boardX = gridX + x;
                    int boardY = gridY + y;

                    // 检查是否超出边界
                    if (boardX < 0 || boardX >= GameConstants.BOARD_SIZE ||
                            boardY < 0 || boardY >= GameConstants.BOARD_SIZE) {
                        return false;
                    }

                    // 检查位置是否已被占用
                    if (board[boardY][boardX]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 在指定位置放置方块
     * @param block 要放置的方块
     * @param gridX 网格X坐标
     * @param gridY 网格Y坐标
     */
    private void placeBlock(Block block, int gridX, int gridY) {
        boolean[][] shape = block.getShape();

        // 在棋盘上放置方块
        for (int y = 0; y < block.getHeight(); y++) {
            for (int x = 0; x < block.getWidth(); x++) {
                if (shape[y][x]) {
                    int boardX = gridX + x;
                    int boardY = gridY + y;
                    board[boardY][boardX] = true;
                }
            }
        }

        // 增加分数 - 根据方块包含的子方块数量
        score += block.countCells();

        if (scorePanel != null) {
            scorePanel.updateScore(score);
        }
        // 检查是否可以消除行/列
        checkLineClear();

        // 只将已使用的方块标记为null，但暂不移除和生成新的
        nextBlocks.set(selectedPreviewIndex, null);
        selectedBlock = null;
        selectedPreviewIndex = -1;

        // 检查是否所有预览方块都已用完
        boolean allUsed = true;
        for (Block bb : nextBlocks) {
            if (bb != null) {
                allUsed = false;
                break;
            }
        }

        // 如果所有方块都已使用，则重新生成一组新方块
        if (allUsed) {
            generateNextBlocks();
        }

        // 检查游戏是否结束
        checkGameOver();

        // 刷新UI
        refreshUI();
    }

    /**
     * 检查并清除完整的行和列
     */
    private void checkLineClear() {
        // 重置待消除行列列表
        pendingClearRows.clear();
        pendingClearCols.clear();

        // 检查哪些行会被填满
        for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
            boolean fullRow = true;
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                if (!board[y][x]) {
                    fullRow = false;
                    break;
                }
            }

            if (fullRow) {
                pendingClearRows.add(y);
            }
        }

        // 检查哪些列会被填满
        for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
            boolean fullColumn = true;
            for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
                if (!board[y][x]) {
                    fullColumn = false;
                    break;
                }
            }

            if (fullColumn) {
                pendingClearCols.add(x);
            }
        }

        // 有行或列需要消除
        if (!pendingClearRows.isEmpty() || !pendingClearCols.isEmpty()) {
            // 记录有消除发生
            animatingClear = true;

            // 计算将被消除的方块总数
            int clearedCells = 0;
            // 计算行消除的方块数
            for (int row : pendingClearRows) {
                for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                    if (board[row][x]) {
                        clearedCells++;
                    }
                }
            }

            // 计算列消除的方块数（注意避免重复计算行列交叉点）
            for (int col : pendingClearCols) {
                for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
                    // 如果这一行已经被计算过（在待消除行列表中），则跳过以避免重复计算
                    if (pendingClearRows.contains(y)) continue;

                    if (board[y][col]) {
                        clearedCells++;
                    }
                }
            }

//            // 更新分数 - 每行/列100分
//            int linesCleared = pendingClearRows.size() + pendingClearCols.size();
//            score += linesCleared * GameConstants.LINE_CLEAR_SCORE;

            // 增加分数 - 每消除一个方块加1分
            score += clearedCells;

            // 刷新分数显示
            if (scorePanel != null) {
                scorePanel.updateScore(score);
            }

            // 启动消除动画
            if (gameBoard != null) {
                gameBoard.startClearAnimation(pendingClearRows, pendingClearCols);
            } else {
                // 如果没有游戏面板（可能在测试模式下），直接完成消除
                continueClearingLines();
            }
        } else {
            // 没有需要消除的行列，可以继续游戏
            finishBlockPlacement();
        }
    }

    public void continueClearingLines() {
        // 实际执行消除操作

        // 清除被标记的行
        for (int row : pendingClearRows) {
            // 清除该行
            for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                board[row][x] = false;
            }
        }

        // 清除被标记的列
        for (int col : pendingClearCols) {
            // 清除该列
            for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
                board[y][col] = false;
            }
        }

        // 重置状态
        animatingClear = false;

        // 完成当前方块的放置流程
        finishBlockPlacement();
    }

    private void finishBlockPlacement() {
        // 检查游戏是否结束
        checkGameOver();

        // 刷新UI
        refreshUI();
    }

    /**
     * 检查游戏是否结束
     */
    private void checkGameOver() {
        // 检查是否可以放置任何下一个方块
        boolean canPlaceAny = false;

        for (Block block : nextBlocks) {
            // 跳过已经使用的（null）方块
            if (block == null) continue;

            // 尝试棋盘上的每个位置
            for (int y = 0; y < GameConstants.BOARD_SIZE; y++) {
                for (int x = 0; x < GameConstants.BOARD_SIZE; x++) {
                    if (canPlaceBlock(block, x, y)) {
                        canPlaceAny = true;
                        break;
                    }
                }
                if (canPlaceAny) break;
            }
            if (canPlaceAny) break;
        }

        if (!canPlaceAny) {
            gameOver = true;
            // 启动游戏结束动画
            if (gameBoard != null) {
                gameBoard.startGameEndAnimation(score);
            } else {
                // 如果没有游戏面板（可能在测试模式下），显示常规对话框
                JOptionPane.showMessageDialog(null,
                        "游戏结束！您的得分: " + score,
                        "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Getter方法

    public boolean[][] getBoard() {
        return board;
    }

    public ArrayList<Block> getNextBlocks() {
        return nextBlocks;
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public int getSelectedPreviewIndex() {
        return selectedPreviewIndex;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
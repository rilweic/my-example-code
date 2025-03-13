package com.game.cubev2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 优化的方块生成器，提供俄罗斯方块风格的各种方块形状
 */
public class BlockGenerator {
    // 随机数生成器
    private static final Random random = new Random();

    // 记录最近生成的形状，用于避免重复
    private static List<TetrominoType> recentTypes = new ArrayList<>();
    private static final int MAX_RECENT_MEMORY = 5;

    /**
     * 俄罗斯方块形状类型枚举
     */
    public enum TetrominoType {
        I(10, new boolean[][] {{true, true, true, true}}),

        J(10, new boolean[][] {
                {true, false, false},
                {true, true, true}
        }),

        L(10, new boolean[][] {
                {false, false, true},
                {true, true, true}
        }),

        O(8, new boolean[][] {
                {true, true},
                {true, true}
        }),

        S(8, new boolean[][] {
                {false, true, true},
                {true, true, false}
        }),

        T(10, new boolean[][] {
                {false, true, false},
                {true, true, true}
        }),

        Z(8, new boolean[][] {
                {true, true, false},
                {false, true, true}
        }),

        SMALL_L(6, new boolean[][] {
                {true, false},
                {true, true}
        }),

        SMALL_J(6, new boolean[][] {
                {false, true},
                {true, true}
        }),

//        PLUS(7, new boolean[][] {
//                {false, true, false},
//                {true, true, true},
//                {false, true, false}
//        }),

//        U(6, new boolean[][] {
//                {true, false, true},
//                {true, true, true}
//        }),

//        LONG_S(5, new boolean[][] {
//                {true, false, false},
//                {true, true, false},
//                {false, true, true}
//        }),

        LONG_Z(5, new boolean[][] {
                {true, true, true},
                {false, false, true},
                {false, false, true}
        }),

        SINGLE(2, new boolean[][] {{true}}),

        DOUBLE_H(4, new boolean[][] {{true, true}}),

        DOUBLE_V(4, new boolean[][] {
                {true},
                {true}
        }),

        TRIPLE_H(5, new boolean[][] {{true, true, true}}),

        TRIPLE_V(5, new boolean[][] {
                {true},
                {true},
                {true}
        });

        private final int weight;         // 出现概率权重
        private final boolean[][] shape;  // 默认形状

        TetrominoType(int weight, boolean[][] shape) {
            this.weight = weight;
            this.shape = shape;
        }

        public int getWeight() {
            return weight;
        }

        public boolean[][] getShape() {
            return shape;
        }
    }

    /**
     * 生成随机方块
     * @return 随机生成的方块
     */
    public static Block generateRandomBlock() {
        TetrominoType type = selectRandomType();
        return createBlock(type);
    }

    /**
     * 生成一组多样化的随机方块
     * @param count 要生成的方块数量
     * @return 随机方块列表
     */
    public static List<Block> generateRandomBlocks(int count) {
        List<Block> blocks = new ArrayList<>();
        List<TetrominoType> selectedTypes = new ArrayList<>();

        // 为每个位置选择一个形状
        for (int i = 0; i < count; i++) {
            TetrominoType type;

            // 尝试生成与已选择形状不同的类型
            int attempts = 0;
            do {
                type = selectRandomType();
                attempts++;
                // 最多尝试5次，避免可能的无限循环
            } while (selectedTypes.contains(type) && attempts < 5);

            selectedTypes.add(type);
            blocks.add(createBlock(type));
        }

        // 确保不会出现全是单一方块的情况
        boolean allSingles = true;
        for (TetrominoType type : selectedTypes) {
            if (type != TetrominoType.SINGLE) {
                allSingles = false;
                break;
            }
        }

        // 如果全是单个方块，替换其中一个
        if (allSingles && !blocks.isEmpty()) {
            TetrominoType newType;
            do {
                newType = selectRandomType();
            } while (newType == TetrominoType.SINGLE);

            blocks.set(0, createBlock(newType));
        }

        return blocks;
    }

    /**
     * 根据权重随机选择一个方块类型
     * @return 选择的方块类型
     */
    private static TetrominoType selectRandomType() {
        // 计算总权重
        int totalWeight = 0;
        for (TetrominoType type : TetrominoType.values()) {
            totalWeight += type.getWeight();
        }

        // 随机选择点
        int randomPoint = random.nextInt(totalWeight);

        // 根据权重选择类型
        int currentWeight = 0;
        TetrominoType selectedType = TetrominoType.values()[0];

        for (TetrominoType type : TetrominoType.values()) {
            currentWeight += type.getWeight();
            if (randomPoint < currentWeight) {
                selectedType = type;
                break;
            }
        }

        // 避免连续出现相同类型
        if (!recentTypes.isEmpty() && recentTypes.get(recentTypes.size() - 1) == selectedType) {
            // 50%的概率重新选择
            if (random.nextBoolean()) {
                return selectRandomType();
            }
        }

        // 更新最近使用的类型
        recentTypes.add(selectedType);
        if (recentTypes.size() > MAX_RECENT_MEMORY) {
            recentTypes.remove(0);
        }

        return selectedType;
    }

    /**
     * 根据指定类型创建方块
     * @param type 方块类型
     * @return 创建的方块
     */
    private static Block createBlock(TetrominoType type) {
        // 复制形状数组，避免修改原始数据
        boolean[][] shape = deepCopyShape(type.getShape());

        // 随机决定是否旋转方块（O形方块除外）
        if (type != TetrominoType.O) {
            int rotations = random.nextInt(4);
            for (int i = 0; i < rotations; i++) {
                shape = rotateShape(shape);
            }
        }

        return new Block(shape, GameConstants.BLOCK_COLOR);
    }

    /**
     * 深度复制形状数组
     * @param original 原始形状数组
     * @return 复制的数组
     */
    private static boolean[][] deepCopyShape(boolean[][] original) {
        boolean[][] copy = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    /**
     * 将形状顺时针旋转90度
     * @param shape 原始形状
     * @return 旋转后的形状
     */
    private static boolean[][] rotateShape(boolean[][] shape) {
        int height = shape.length;
        int width = shape[0].length;

        boolean[][] rotated = new boolean[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotated[x][height - 1 - y] = shape[y][x];
            }
        }

        return rotated;
    }
}
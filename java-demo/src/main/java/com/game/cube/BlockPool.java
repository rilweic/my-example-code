package com.game.cube;

import java.util.*;

public class BlockPool {
    private List<Block> pool;      // 当前方块池
    private List<Block> allBlocks; // 所有方块类型
    private int blocksPlaced;      // 已放置方块数

    public BlockPool() {
        pool = new ArrayList<>();
        allBlocks = initializeAllBlocks();
        blocksPlaced = 0;
        initializePool();
    }

    private List<Block> initializeAllBlocks() {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block("I-h", Arrays.asList(new int[]{0,0}, new int[]{0,1}, new int[]{0,2}, new int[]{0,3})));
        blocks.add(new Block("I-v", Arrays.asList(new int[]{0,0}, new int[]{1,0}, new int[]{2,0}, new int[]{3,0})));
        blocks.add(new Block("O", Arrays.asList(new int[]{0,0}, new int[]{0,1}, new int[]{1,0}, new int[]{1,1})));
        blocks.add(new Block("T", Arrays.asList(new int[]{0,1}, new int[]{1,0}, new int[]{1,1}, new int[]{1,2})));
        blocks.add(new Block("L", Arrays.asList(new int[]{0,1}, new int[]{1,1}, new int[]{2,0}, new int[]{2,1})));
        return blocks;
    }

    private void initializePool() {
        Random rand = new Random();
        pool.clear();
        for (int i = 0; i < 3; i++) {
            pool.add(allBlocks.get(rand.nextInt(allBlocks.size())));
        }
        blocksPlaced = 0;
    }

    public Block getBlock(int index) {
        return (index >= 0 && index < pool.size()) ? pool.get(index) : null;
    }

    public void removeBlock(int index) {
        if (index >= 0 && index < pool.size()) {
            pool.remove(index);
            blocksPlaced++;
            if (blocksPlaced == 3) {
                initializePool();
            }
        }
    }

    public List<Block> getPool() {
        return pool;
    }
}
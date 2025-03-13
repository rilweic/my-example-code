package com.game.cube;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class CubeBlockGUI extends JPanel {
    private Game game;
    private JLabel scoreLabel;
    private JLabel[][] gridLabels;
    private List<JPanel> blockPanels;
    private JPanel glassPane;
    private JLabel previewLabel;
    private JFrame frame; // 添加对 JFrame 的引用
    private Block selectedBlock; // 当前选中的方块

    public CubeBlockGUI(Game game) {
        this.game = game;
        this.gridLabels = new JLabel[10][10];
        this.blockPanels = new ArrayList<>();

        setLayout(new BorderLayout());

        // 上部：网格和得分
        JPanel topPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: 0");
        topPanel.add(scoreLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridLabels[i][j] = new JLabel();
                gridLabels[i][j].setOpaque(true);
                gridLabels[i][j].setBackground(Color.WHITE);
                gridLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                gridLabels[i][j].setPreferredSize(new Dimension(40, 40));
                gridPanel.add(gridLabels[i][j]);
            }
        }
        new DropTarget(gridPanel, new GridDropTargetListener());
        topPanel.add(gridPanel, BorderLayout.CENTER);

        // 下部：方块池
        JPanel blockPoolPanel = new JPanel(new FlowLayout());
        updateBlockPool(blockPoolPanel);

        add(topPanel, BorderLayout.CENTER);
        add(blockPoolPanel, BorderLayout.SOUTH);

        // GlassPane 用于预览
        glassPane = new JPanel();
        glassPane.setOpaque(false);
        glassPane.setLayout(null);
        previewLabel = new JLabel();
        previewLabel.setOpaque(true);
        glassPane.add(previewLabel);

        // 获取 JFrame 并设置 GlassPane
        frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setGlassPane(glassPane);
            glassPane.setVisible(true);
        }

        updateGrid();
    }

    private void updateGrid() {
        boolean[][] cells = game.grid.getCells();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridLabels[i][j].setBackground(cells[i][j] ? Color.GRAY : Color.WHITE);
            }
        }
    }

    private void updateBlockPool(JPanel blockPoolPanel) {
        blockPoolPanel.removeAll();
        blockPanels.clear();
        for (int i = 0; i < game.pool.getPool().size(); i++) {
            Block block = game.pool.getBlock(i);
            JPanel blockPanel = createBlockPanel(block);
            final int index = i;
            blockPanel.setTransferHandler(new TransferHandler("name") {
                @Override
                public int getSourceActions(JComponent c) {
                    return TransferHandler.COPY;
                }

                @Override
                protected Transferable createTransferable(JComponent c) {
                    selectedBlock = game.pool.getBlock(index); // 设置当前选中的方块
                    return new StringSelection(String.valueOf(index));
                }
            });
            blockPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JComponent comp = (JComponent) e.getSource();
                    TransferHandler handler = comp.getTransferHandler();
                    handler.exportAsDrag(comp, e, TransferHandler.COPY);
                }
            });
            blockPanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point p = SwingUtilities.convertPoint(blockPanel, e.getPoint(), glassPane);
                    updatePreview(p);
                }
            });
            blockPanels.add(blockPanel);
            blockPoolPanel.add(blockPanel);
        }
        blockPoolPanel.revalidate();
        blockPoolPanel.repaint();
    }

    private JPanel createBlockPanel(Block block) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // 绘制阴影
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
                // 绘制渐变方块
                GradientPaint gp = new GradientPaint(0, 0, Color.LIGHT_GRAY, getWidth(), getHeight(), Color.DARK_GRAY);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(80, 80));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return panel;
    }

    private void updatePreview(Point mousePoint) {
        if (selectedBlock == null) return;
        int row = mousePoint.y / 40;
        int col = mousePoint.x / 40;
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            if (game.grid.canPlaceBlock(selectedBlock, row, col)) {
                previewLabel.setBackground(new Color(0, 255, 0, 100)); // 绿色半透明
            } else {
                previewLabel.setBackground(new Color(255, 0, 0, 100)); // 红色半透明
            }
            previewLabel.setBounds(col * 40, row * 40, 80, 80);
        } else {
            previewLabel.setBounds(-100, -100, 0, 0);
        }
    }

    private class GridDropTargetListener extends DropTargetAdapter {
        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                Transferable tr = dtde.getTransferable();
                String indexStr = (String) tr.getTransferData(DataFlavor.stringFlavor);
                int index = Integer.parseInt(indexStr);
                Block block = game.pool.getBlock(index);
                Point dropPoint = dtde.getLocation();
                JPanel gridPanel = (JPanel) dtde.getDropTargetContext().getComponent();
                int row = dropPoint.y / 40;
                int col = dropPoint.x / 40;
                if (game.grid.canPlaceBlock(block, row, col)) {
                    game.grid.placeBlock(block, row, col);
                    int clearances = game.grid.clearCompleteRowsAndColumns();
                    game.score += clearances * 10;
                    game.pool.removeBlock(index);
                    updateGrid();
                    updateBlockPool((JPanel) getComponent(1));
                    scoreLabel.setText("Score: " + game.score);
                    if (!game.canContinue()) {
                        JOptionPane.showMessageDialog(CubeBlockGUI.this, "游戏结束！最终得分: " + game.score, "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                dtde.dropComplete(true);
            } catch (Exception e) {
                dtde.rejectDrop();
            }
        }
    }
}
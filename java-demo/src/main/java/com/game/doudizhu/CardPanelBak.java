package com.game.doudizhu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardPanelBak extends JPanel {
    private List<Card> cards;
    private Set<Card> selectedCards;
    private boolean selectable;
    private int rotation;  // 用于旋转显示（左右AI的牌需要横着显示）

    private boolean showBack = false;  // 是否显示牌的背面

    private boolean isVertical;  // 是否垂直显示

    // 卡牌绘制的常量
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 140;
    private static final int CARD_ARC = 15;
    private static final int OVERLAY_GAP = 30;
    private static final int SELECTION_LIFT = 20;
    private static final int VERTICAL_PADDING = 10;  // 垂直边距
    private boolean isDragging = false;
    private Point dragStart = null;
    private Point dragEnd = null;
    private int layoutDirection; // 0: 水平, 1: 竖直


    public CardPanelBak(boolean selectable) {
        this.cards = new ArrayList<>();
        this.selectedCards = new HashSet<>();
        this.selectable = selectable;
        this.showBack = false;
        this.rotation = 0;
        this.layoutDirection = 0;  // 默认水平布局

        setOpaque(false);

        if (selectable) {
            CardMouseHandler mouseHandler = new CardMouseHandler();
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }
    }

    public void setShowBack(boolean showBack) {
        this.showBack = showBack;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 设置渲染提示
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (layoutDirection == 0) {  // 水平布局
            drawCardsHorizontally(g2d);
        } else {  // 竖直布局
            drawCardsVertically(g2d);
        }
    }


    private void drawCardsHorizontally(Graphics2D g2d) {
        if (cards.isEmpty()) return;

        int totalWidth = (cards.size() - 1) * OVERLAY_GAP + CARD_WIDTH;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - CARD_HEIGHT) / 2;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            boolean isSelected = selectedCards.contains(card);

            int x = startX + i * OVERLAY_GAP;
            int y = startY - (isSelected ? SELECTION_LIFT : 0);

            drawCard(g2d, card, x, y, isSelected, 0, showBack);
        }
    }

    private void drawCardsVertically(Graphics2D g2d) {
        if (cards.isEmpty()) return;

        // 对于竖直布局，交换牌的宽度和高度
        int actualCardWidth = CARD_HEIGHT;  // 旋转后的牌宽度等于原来的高度
        int actualCardHeight = CARD_WIDTH;  // 旋转后的牌高度等于原来的宽度

        // 计算整体高度（原来的宽度）和开始位置
        int totalHeight = (cards.size() - 1) * OVERLAY_GAP + actualCardWidth;
        int startX = (getWidth() - actualCardHeight) / 2;  // 居中显示
        int startY = (getHeight() - totalHeight) / 2;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            boolean isSelected = selectedCards.contains(card);

            // 计算每张牌的位置
            int x = startX;
            int y = startY + i * OVERLAY_GAP;

            if (rotation == 90) {  // 右边玩家
                x = startX;
                y = startY + i * OVERLAY_GAP;
                drawCard(g2d, card, x, y, isSelected, 90, showBack);
            } else {  // 左边玩家
                x = startX;
                y = startY + i * OVERLAY_GAP;
                drawCard(g2d, card, x, y, isSelected, -90, showBack);
            }
        }
    }


    private void drawCard(Graphics2D g2d, Card card, int x, int y, boolean isSelected, int cardRotation, boolean showBack) {
        // 保存原始变换
        AffineTransform originalTransform = g2d.getTransform();

        // 如果需要旋转，设置旋转中心点和角度
        if (cardRotation != 0) {
            g2d.rotate(Math.toRadians(cardRotation),
                    x + CARD_WIDTH / 2.0,
                    y + CARD_HEIGHT / 2.0);
        }

        // 绘制卡牌背景
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);

        // 绘制卡牌边框
        g2d.setColor(isSelected ? Color.BLUE : Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);

        if (showBack) {
            drawCardBack(g2d, x, y);
        } else {
            drawCardFront(g2d, card, x, y);
        }

        // 如果被选中，添加半透明的蓝色遮罩
        if (isSelected) {
            g2d.setColor(new Color(0, 0, 255, 30));
            g2d.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);
        }

        // 恢复原始变换
        g2d.setTransform(originalTransform);
    }




    // 新增绘制牌背面的方法
    private void drawCardBack(Graphics2D g2d, int x, int y) {
        // 设置背景色（深蓝色）
        g2d.setColor(new Color(0, 0, 139));
        g2d.fillRoundRect(x + 4, y + 4, CARD_WIDTH - 8, CARD_HEIGHT - 8, CARD_ARC - 2, CARD_ARC - 2);

        // 绘制花纹
        g2d.setColor(new Color(200, 200, 255));
        int patternWidth = CARD_WIDTH - 20;
        int patternHeight = CARD_HEIGHT - 20;
        int px = x + 10;
        int py = y + 10;

        // 绘制网格花纹
        for (int i = 0; i < patternWidth; i += 8) {
            for (int j = 0; j < patternHeight; j += 8) {
                g2d.drawRect(px + i, py + j, 4, 4);
            }
        }

        // 绘制中心图案
        int centerX = x + CARD_WIDTH / 2;
        int centerY = y + CARD_HEIGHT / 2;
        g2d.setColor(new Color(255, 215, 0));  // 金色
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "斗";
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, centerX - textWidth / 2, centerY + fm.getAscent() / 2);
    }

    private void drawCardFront(Graphics2D g2d, Card card, int x, int y) {
        // 设置文字颜色
        if (card.getSuit().equals("♥") || card.getSuit().equals("♦") ||
                card.getRank().equals("大王") || card.getRank().equals("小王")) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.BLACK);
        }

        // 设置字体
        Font rankFont = new Font("Microsoft YaHei", Font.BOLD, 20);
        Font suitFont = new Font("Microsoft YaHei", Font.BOLD, 20);
        int padding = 8;

        if (card.getRank().equals("大王") || card.getRank().equals("小王")) {
            // 大小王显示处理
            String displayText = card.getRank().substring(0, 1);
            String jokerText = "王";

            g2d.setFont(rankFont);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(displayText, x + padding, y + padding + fm.getAscent());
            g2d.drawString(jokerText, x + padding, y + padding + fm.getHeight() + fm.getAscent());

            // 右下角
            int textWidth = fm.stringWidth(displayText);
            g2d.drawString(displayText,
                    x + CARD_WIDTH - textWidth - padding,
                    y + CARD_HEIGHT - padding - fm.getHeight());

            int jokerWidth = fm.stringWidth(jokerText);
            g2d.drawString(jokerText,
                    x + CARD_WIDTH - jokerWidth - padding,
                    y + CARD_HEIGHT - padding);
        } else {
            // 普通牌显示处理
            g2d.setFont(rankFont);
            g2d.drawString(card.getRank(), x + padding, y + padding + g2d.getFontMetrics().getAscent());
            g2d.setFont(suitFont);
            g2d.drawString(card.getSuit(), x + padding, y + padding + rankFont.getSize() + suitFont.getSize());

            // 右下角
            g2d.setFont(rankFont);
            FontMetrics fmRank = g2d.getFontMetrics();
            String rank = card.getRank();
            int rankWidth = fmRank.stringWidth(rank);
            g2d.drawString(rank,
                    x + CARD_WIDTH - rankWidth - padding,
                    y + CARD_HEIGHT - padding - suitFont.getSize());

            g2d.setFont(suitFont);
            FontMetrics fmSuit = g2d.getFontMetrics();
            String suit = card.getSuit();
            int suitWidth = fmSuit.stringWidth(suit);
            g2d.drawString(suit,
                    x + CARD_WIDTH - suitWidth - padding,
                    y + CARD_HEIGHT - padding);
        }
    }


    private class CardMouseHandler extends MouseAdapter {
        private Point startPoint = null;
        private HashSet<Card> originalSelection = new HashSet<>();

        @Override
        public void mousePressed(MouseEvent e) {
            if (!selectable) return;
            startPoint = e.getPoint();
            dragStart = startPoint;
            dragEnd = startPoint;
            isDragging = false;

            // 记录原始选择状态
            originalSelection = new HashSet<>(selectedCards);

            // 检查是否点击到了某张牌
            Card clickedCard = getCardAtPoint(e.getPoint());
            if (clickedCard != null) {
                // 切换选中状态：如果已选中则取消，如果未选中则添加
                if (selectedCards.contains(clickedCard)) {
                    selectedCards.remove(clickedCard);
                } else {
                    selectedCards.add(clickedCard);
                }
                repaint();
            } else {
                // 点击空白处时清除选择
                selectedCards.clear();
                repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!selectable) return;
            isDragging = true;
            dragEnd = e.getPoint();

            // 如果开始拖拽，清除初始选择
            if (Math.abs(e.getX() - startPoint.x) > 5 ||
                    Math.abs(e.getY() - startPoint.y) > 5) {
                selectedCards.clear();
            }

            // 更新选择区域内的牌
            updateSelectionForDrag();
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!selectable) return;

            // 如果没有拖拽，那么就是单击，已经在mousePressed中处理了选择逻辑
            isDragging = false;
            dragStart = null;
            dragEnd = null;
            repaint();
        }
    }

    // 添加新的辅助方法
    private void updateSelectionForDrag() {
        if (dragStart == null || dragEnd == null) return;

        // 计算选择区域
        int x1 = Math.min(dragStart.x, dragEnd.x);
        int y1 = Math.min(dragStart.y, dragEnd.y);
        int x2 = Math.max(dragStart.x, dragEnd.x);
        int y2 = Math.max(dragStart.y, dragEnd.y);

        // 计算可视区域
        int totalWidth = (cards.size() - 1) * OVERLAY_GAP + CARD_WIDTH;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = SELECTION_LIFT + VERTICAL_PADDING;

        // 检查每张牌是否在选择区域内
        for (int i = 0; i < cards.size(); i++) {
            int cardX = startX + i * OVERLAY_GAP;
            int cardY = startY;

            Rectangle cardRect = new Rectangle(cardX, cardY, CARD_WIDTH, CARD_HEIGHT);
            Rectangle selectionRect = new Rectangle(x1, y1, x2 - x1, y2 - y1);

            if (cardRect.intersects(selectionRect)) {
                selectedCards.add(cards.get(i));
            }
        }
    }

    private Card getCardAtPoint(Point p) {
        int totalWidth = (cards.size() - 1) * OVERLAY_GAP + CARD_WIDTH;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = SELECTION_LIFT + VERTICAL_PADDING;

        // 从后往前检查（使重叠的卡牌也能被点击）
        for (int i = cards.size() - 1; i >= 0; i--) {
            int cardX = startX + i * OVERLAY_GAP;
            int cardY = startY - (selectedCards.contains(cards.get(i)) ? SELECTION_LIFT : 0);

            if (p.x >= cardX && p.x <= cardX + CARD_WIDTH &&
                    p.y >= cardY && p.y <= cardY + CARD_HEIGHT) {
                return cards.get(i);
            }
        }
        return null;
    }

    // 公共方法
    public void setCards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
        this.selectedCards.clear();
        repaint();
    }

    public List<Card> getSelectedCards() {
        return new ArrayList<>(selectedCards);
    }

    public void clearSelection() {
        selectedCards.clear();
        repaint();
    }

    public void setRotation(int degrees) {
        this.rotation = degrees;
        // 根据旋转角度设置布局方向
        if (degrees == 90 || degrees == -90) {
            this.layoutDirection = 1;  // 竖直布局
        } else {
            this.layoutDirection = 0;  // 水平布局
        }
        repaint();
    }
}
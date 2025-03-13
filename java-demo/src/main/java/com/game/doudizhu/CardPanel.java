package com.game.doudizhu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CardPanel extends JPanel {
    private ArrayList<Card> cards;
    private Set<Card> selectedCards;
    private boolean selectable;
    private boolean showBack;
    private int rotation;
    private boolean isVertical;

    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 140;
    private static final int CARD_ARC = 15;
    private static final int OVERLAY_GAP = 30;
    private static final int SELECTION_LIFT = 20;

    public CardPanel(boolean selectable) {
        this.cards = new ArrayList<>();
        this.selectedCards = new HashSet<>();
        this.selectable = selectable;
        this.showBack = false;
        this.rotation = 0;
        this.isVertical = false;

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

    public void setRotation(int degrees) {
        this.rotation = degrees;
        this.isVertical = (degrees == 90 || degrees == -90);
        updatePanelSize();
        repaint();
    }

    private void updatePanelSize() {
        if (isVertical) {
            setPreferredSize(new Dimension(CARD_HEIGHT + 20, 600));
        } else {
            setPreferredSize(new Dimension(800, CARD_HEIGHT + 40));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cards.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (isVertical) {
            drawCardsVertically(g2d);
        } else {
            drawCardsHorizontally(g2d);
        }
    }

    private void drawCardsHorizontally(Graphics2D g2d) {
        int totalWidth = (cards.size() - 1) * OVERLAY_GAP + CARD_WIDTH;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - CARD_HEIGHT) / 2;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            boolean isSelected = selectedCards.contains(card);

            int x = startX + i * OVERLAY_GAP;
            int y = startY - (isSelected ? SELECTION_LIFT : 0);

            drawCard(g2d, card, x, y, isSelected);
        }
    }

    private void drawCardsVertically(Graphics2D g2d) {
        int totalHeight = (cards.size() - 1) * OVERLAY_GAP + CARD_HEIGHT;
        int startX = (getWidth() - CARD_WIDTH) / 2;
        int startY = (getHeight() - totalHeight) / 2;

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            boolean isSelected = selectedCards.contains(card);

            int x = startX;
            int y = startY + i * OVERLAY_GAP;

            // Save current transform
            AffineTransform originalTransform = g2d.getTransform();

            // Set rotation center and angle
            g2d.rotate(Math.toRadians(rotation), x + CARD_WIDTH/2, y + CARD_HEIGHT/2);

            // Draw card
            drawCard(g2d, card, x, y, isSelected);

            // Restore original transform
            g2d.setTransform(originalTransform);
        }
    }

    private void drawCard(Graphics2D g2d, Card card, int x, int y, boolean isSelected) {
        // Draw card background
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);

        // Draw card border
        g2d.setColor(isSelected ? Color.BLUE : Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);

        if (showBack) {
            drawCardBack(g2d, x, y);
        } else {
            drawCardFront(g2d, card, x, y);
        }

        // Draw selection effect
        if (isSelected) {
            g2d.setColor(new Color(0, 0, 255, 30));
            g2d.fillRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);
        }
    }

    private void drawCardBack(Graphics2D g2d, int x, int y) {
        // Set background color (dark blue)
        g2d.setColor(new Color(0, 0, 139));
        g2d.fillRoundRect(x + 4, y + 4, CARD_WIDTH - 8, CARD_HEIGHT - 8, CARD_ARC - 2, CARD_ARC - 2);

        // Draw pattern
        g2d.setColor(new Color(200, 200, 255));
        int patternWidth = CARD_WIDTH - 20;
        int patternHeight = CARD_HEIGHT - 20;
        int px = x + 10;
        int py = y + 10;

        // Draw grid pattern
        for (int i = 0; i < patternWidth; i += 8) {
            for (int j = 0; j < patternHeight; j += 8) {
                g2d.drawRect(px + i, py + j, 4, 4);
            }
        }

        // Draw center text
        g2d.setColor(new Color(255, 215, 0));  // Gold color
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "斗";
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, x + (CARD_WIDTH - textWidth)/2, y + CARD_HEIGHT/2 + fm.getAscent()/2);
    }

    private void drawCardFront(Graphics2D g2d, Card card, int x, int y) {
        // Set text color
        if (card.getSuit().equals("♥") || card.getSuit().equals("♦") ||
                card.getRank().equals("大王") || card.getRank().equals("小王")) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.BLACK);
        }

        // Set fonts
        Font rankFont = new Font("Microsoft YaHei", Font.BOLD, 20);
        Font suitFont = new Font("Microsoft YaHei", Font.BOLD, 20);
        int padding = 8;

        if (card.getRank().equals("大王") || card.getRank().equals("小王")) {
            String displayText = card.getRank().substring(0, 1);
            String jokerText = "王";

            g2d.setFont(rankFont);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(displayText, x + padding, y + padding + fm.getAscent());
            g2d.drawString(jokerText, x + padding, y + padding + fm.getHeight() + fm.getAscent());

            int textWidth = fm.stringWidth(displayText);
            g2d.drawString(displayText,
                    x + CARD_WIDTH - textWidth - padding,
                    y + CARD_HEIGHT - padding - fm.getHeight());

            int jokerWidth = fm.stringWidth(jokerText);
            g2d.drawString(jokerText,
                    x + CARD_WIDTH - jokerWidth - padding,
                    y + CARD_HEIGHT - padding);
        } else {
            g2d.setFont(rankFont);
            g2d.drawString(card.getRank(), x + padding, y + padding + g2d.getFontMetrics().getAscent());
            g2d.setFont(suitFont);
            g2d.drawString(card.getSuit(), x + padding, y + padding + rankFont.getSize() + suitFont.getSize());

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

    public void setCards(ArrayList<Card> cards) {
        this.cards = new ArrayList<>(cards);
        this.selectedCards.clear();
        repaint();
    }

    public ArrayList<Card> getSelectedCards() {
        return new ArrayList<>(selectedCards);
    }

    public void clearSelection() {
        selectedCards.clear();
        repaint();
    }

    private class CardMouseHandler extends MouseAdapter {
        private Point startPoint = null;
        private boolean isDragging = false;

        @Override
        public void mousePressed(MouseEvent e) {
            if (!selectable) return;
            startPoint = e.getPoint();
            isDragging = false;

            Card clickedCard = getCardAt(e.getPoint());
            if (clickedCard != null) {
                if (selectedCards.contains(clickedCard)) {
                    selectedCards.remove(clickedCard);
                } else {
                    selectedCards.add(clickedCard);
                }
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            startPoint = null;
            isDragging = false;
        }
    }

    private Card getCardAt(Point p) {
        if (isVertical) {
            return getCardAtVertical(p);
        } else {
            return getCardAtHorizontal(p);
        }
    }

    private Card getCardAtHorizontal(Point p) {
        int totalWidth = (cards.size() - 1) * OVERLAY_GAP + CARD_WIDTH;
        int startX = (getWidth() - totalWidth) / 2;
        int startY = (getHeight() - CARD_HEIGHT) / 2;

        for (int i = cards.size() - 1; i >= 0; i--) {
            int x = startX + i * OVERLAY_GAP;
            int y = startY - (selectedCards.contains(cards.get(i)) ? SELECTION_LIFT : 0);

            if (p.x >= x && p.x <= x + CARD_WIDTH &&
                    p.y >= y && p.y <= y + CARD_HEIGHT) {
                return cards.get(i);
            }
        }
        return null;
    }

    private Card getCardAtVertical(Point p) {
        int totalHeight = (cards.size() - 1) * OVERLAY_GAP + CARD_HEIGHT;
        int startX = (getWidth() - CARD_WIDTH) / 2;
        int startY = (getHeight() - totalHeight) / 2;

        for (int i = cards.size() - 1; i >= 0; i--) {
            int x = startX;
            int y = startY + i * OVERLAY_GAP;

            Point rotatedPoint = rotatePoint(p, new Point(x + CARD_WIDTH/2, y + CARD_HEIGHT/2), -rotation);

            if (rotatedPoint.x >= x && rotatedPoint.x <= x + CARD_WIDTH &&
                    rotatedPoint.y >= y && rotatedPoint.y <= y + CARD_HEIGHT) {
                return cards.get(i);
            }
        }
        return null;
    }

    private Point rotatePoint(Point p, Point center, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);

        double dx = p.x - center.x;
        double dy = p.y - center.y;

        int newX = (int) (center.x + dx * cos - dy * sin);
        int newY = (int) (center.y + dx * sin + dy * cos);

        return new Point(newX, newY);
    }
}
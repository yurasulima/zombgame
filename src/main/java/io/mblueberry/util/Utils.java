package io.mblueberry.util;

import io.mblueberry.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {


    public static double calculateDistance(int blockX, int blockY, int playerX, int playerY) {
        return Math.sqrt(Math.pow(blockX - playerX, 2) + Math.pow(blockY - playerY, 2));
    }

    public static BufferedImage scaleIMage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }

    public static int getCenterTextX(String text, Graphics2D g2, Game game) {
        int textLen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return game.getWidth() / 2 - textLen / 2;
    }
    public static int getAlignRight(String text, int x, Graphics2D g2) {
        int textLen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return x - textLen;
    }
    public static int getLocalCenterTextX(String text, Graphics2D g2) {
        Font font = new Font("Arial", Font.PLAIN, 24);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(text, g2);
        int textWidth = (int) rect.getWidth();
        return textWidth / 2;
    }
    public static String str(String text) {
        return new String(text.getBytes(), UTF_8);
    }
}

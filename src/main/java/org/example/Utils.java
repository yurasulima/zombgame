package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utils {


    public static BufferedImage scaleIMage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }


    public static String str(String text) {
        return new String(text.getBytes(), UTF_8);
    }
}

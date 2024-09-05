package io.mblueberry.ui;


import io.mblueberry.core.BlockLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class NinePatch {
    private BufferedImage image;

    public NinePatch(String imagePath) {
        try {
            // Load the 9-patch image
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawNinePatch(Graphics2D g, int x, int y, int width, int height) {
        int patchWidth = image.getWidth();
        int patchHeight = image.getHeight();

        // Corners of the 9-patch
        BufferedImage topLeft = image.getSubimage(1, 1, 1, 1);
        BufferedImage topRight = image.getSubimage(patchWidth - 2, 1, 1, 1);
        BufferedImage bottomLeft = image.getSubimage(1, patchHeight - 2, 1, 1);
        BufferedImage bottomRight = image.getSubimage(patchWidth - 2, patchHeight - 2, 1, 1);

        // Fill areas of the 9-patch
        BufferedImage top = image.getSubimage(2, 1, patchWidth - 4, 1);
        BufferedImage left = image.getSubimage(1, 2, 1, patchHeight - 4);
        BufferedImage right = image.getSubimage(patchWidth - 2, 2, 1, patchHeight - 4);
        BufferedImage bottom = image.getSubimage(2, patchHeight - 2, patchWidth - 4, 1);
        BufferedImage center = image.getSubimage(2, 2, patchWidth - 4, patchHeight - 4);

        // Draw corners
        g.drawImage(topLeft, x, y, null);
        g.drawImage(topRight, x + width - 1, y, null);
        g.drawImage(bottomLeft, x, y + height - 1, null);
        g.drawImage(bottomRight, x + width - 1, y + height - 1, null);

        // Draw edges
        g.drawImage(top, x + 1, y, width - 2, 1, null);
        g.drawImage(left, x, y + 1, 1, height - 2, null);
        g.drawImage(right, x + width - 1, y + 1, 1, height - 2, null);
        g.drawImage(bottom, x + 1, y + height - 1, width - 2, 1, null);

        // Draw the center
        g.drawImage(center, x + 1, y + 1, width - 2, height - 2, null);
    }
}

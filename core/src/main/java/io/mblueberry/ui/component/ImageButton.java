package io.mblueberry.ui.component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ImageButton {
    private int x, y, width, height;
    private Image image;
    private Rectangle bounds;
    private Runnable action;

    public ImageButton(int x, int y, int width, int height, String imagePath, Runnable action) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.action = action;
        this.bounds = new Rectangle(x, y, width, height);
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public void update(MouseEvent e) {
        if (bounds.contains(e.getPoint())) {
            // Optional: Change appearance on hover
        }
    }

    public void onClick(MouseEvent e) {
        if (bounds.contains(e.getPoint())) {
            action.run();
        }
    }
}

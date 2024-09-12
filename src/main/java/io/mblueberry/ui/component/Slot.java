package io.mblueberry.ui.component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Slot {
    public int x, y, width, height;
    private boolean selected = false;
    private Color normalColor, hoverColor, clickColor, textColor, selectedColor;
    private boolean hovering = false, clicking = false;
    private Runnable onClick;
    private BufferedImage selector;
    private BufferedImage slot;
    public Slot(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalColor = Color.GRAY;
        this.normalColor = Color.GRAY;
        this.selectedColor = Color.ORANGE;
        this.hoverColor = Color.LIGHT_GRAY;
        this.clickColor = Color.DARK_GRAY;
        this.textColor = Color.WHITE;
        try {
            selector = ImageIO.read(getClass().getResourceAsStream("/textures/ui/selector.png"));
            slot = ImageIO.read(getClass().getResourceAsStream("/textures/ui/slot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public void setColors(Color normalColor, Color hoverColor, Color clickColor, Color textColor) {
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
        this.textColor = textColor;
    }

    public void draw(Graphics2D g) {
        if (clicking) {
            g.setColor(clickColor);
        } else if (hovering) {
            g.setColor(hoverColor);
        } else  {
            g.setColor(normalColor);
        }
        if (selected) {
          //  g.setColor(selectedColor);
        }
        g.drawImage(slot, x, y, width, height, null);
        if (selected) {
            g.drawImage(selector, x, y, width, height, null);
        }


    }

    public void handleMouseEvent(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx >= x && mx <= x + width && my >= y && my <= y + height) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                clicking = true;
            } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                if (clicking && onClick != null) {
                    onClick.run();
                }
                clicking = false;
            } else {
                hovering = true;
            }
        } else {
            hovering = false;
            clicking = false;
        }
    }

    public void setSelected(boolean b) {
        selected = b;
    }
}

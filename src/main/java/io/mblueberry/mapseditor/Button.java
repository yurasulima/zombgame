package io.mblueberry.mapseditor;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button {
    public int x, y, width, height;
    private String label;
    private Color normalColor, hoverColor, clickColor, textColor;
    private boolean hovering = false, clicking = false;
    private Runnable onClick;

    public Button(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.normalColor = Color.GRAY;
        this.hoverColor = Color.LIGHT_GRAY;
        this.clickColor = Color.DARK_GRAY;
        this.textColor = Color.WHITE;
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

    public void draw(Graphics g) {
        if (clicking) {
            g.setColor(clickColor);
        } else if (hovering) {
            g.setColor(hoverColor);
        } else {
            g.setColor(normalColor);
        }
        g.fillRect(x, y, width, height);

        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (width - fm.stringWidth(label)) / 2;
        int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(label, textX, textY);
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
}



//public class Button implements MouseListener {
//    private int x, y, width, height;
//    private String label;
//    private boolean hovered;
//    private Color baseColor, hoverColor, textColor;
//    private Font font;
//    private boolean clicked;
//
//    public Button(int x, int y, int width, int height, String label) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        this.label = label;
//        this.hovered = false;
//        this.baseColor = Color.GRAY;
//        this.hoverColor = Color.LIGHT_GRAY;
//        this.textColor = Color.BLACK;
//        this.font = new Font("Arial", Font.BOLD, 20);
//        this.clicked = false;
//    }
//
//    public void setBaseColor(Color baseColor) {
//        this.baseColor = baseColor;
//    }
//
//    public void setHoverColor(Color hoverColor) {
//        this.hoverColor = hoverColor;
//    }
//
//    public void setTextColor(Color textColor) {
//        this.textColor = textColor;
//    }
//
//    public void setFont(Font font) {
//        this.font = font;
//    }
//
//    public boolean isClicked() {
//        return clicked;
//    }
//
//    public void resetClicked() {
//        this.clicked = false;
//    }
//
//    public void draw(Graphics g) {
//        g.setColor(hovered ? hoverColor : baseColor);
//        g.fillRect(x, y, width, height);
//
//        g.setColor(textColor);
//        g.setFont(font);
//        int stringWidth = g.getFontMetrics().stringWidth(label);
//        int stringHeight = g.getFontMetrics().getHeight();
//        g.drawString(label, x + (width - stringWidth) / 2, y + (height + stringHeight) / 2 - 5);
//    }
//
//    public void checkHover(int mouseX, int mouseY) {
//        hovered = new Rectangle(x, y, width, height).contains(mouseX, mouseY);
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//       // if (hovered) {
//      //  System.out.println("e.toString() = " + e.toString());
//            clicked = true;
//       // }
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) { }
//
//    @Override
//    public void mouseReleased(MouseEvent e) { }
//
//    @Override
//    public void mouseEntered(MouseEvent e) { }
//
//    @Override
//    public void mouseExited(MouseEvent e) { }
//
//    public static void main(String[] args) {
//        // This is a placeholder for testing the Button class.
//        // You would typically integrate this Button class within your game's main loop and rendering system.
//    }
//}

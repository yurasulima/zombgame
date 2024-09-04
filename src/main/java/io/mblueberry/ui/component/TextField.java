package io.mblueberry.ui.component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TextField {
    private int x, y, width, height;
    private String text;
    private boolean focused;
    private Font font;
    private Rectangle bounds;

    public TextField(int x, int y, int width, int height, Font font) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.text = "";
        this.focused = false;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.setFont(font);
        g.drawString(text, x + 5, y + (height / 2) + 5);
    }

    public void update(MouseEvent e) {
        focused = bounds.contains(e.getPoint());
    }

    public void keyTyped(KeyEvent e) {
        if (focused) {
            char keyChar = e.getKeyChar();
            if (keyChar == KeyEvent.VK_BACK_SPACE && text.length() > 0) {
                text = text.substring(0, text.length() - 1);
            } else if (Character.isLetterOrDigit(keyChar) || Character.isSpaceChar(keyChar)) {
                text += keyChar;
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

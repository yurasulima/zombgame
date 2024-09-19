package io.mblueberry.ui.component;

import io.mblueberry.ui.IBaseUi;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class ListButton implements IBaseUi {

    public List<Button> buttons = new ArrayList<>();

    int x;
    int y;
    int lastX;
    int lastY;
    int padding = 10;
    public ListButton(int x, int y) {
        this.x       = x;
        this.y       = y;
        this.lastX   = x;
        this.lastY   = y;
    }

    public void add(Button button) {
        buttons.add(button);
        button.setPosition(lastX, lastY);
        lastY += button.height;
        lastY += padding;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        for (Button button : buttons) {
            button.draw(g2);
        }
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

    }

    public void handleMouse(MouseEvent e){
        for (Button button : buttons) {
            button.handleMouseEvent(e);
        }
    }
}

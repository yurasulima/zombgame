package io.mblueberry.ui;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public interface IBaseUi {
    void update();
    void draw(Graphics2D g2);

    void handleMouseWheel(MouseWheelEvent e);
}

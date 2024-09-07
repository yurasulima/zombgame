package io.mblueberry.mapseditor;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static io.mblueberry.mapseditor.Map.TILE_SIZE;

public class MouseWheelHandler implements MouseWheelListener {
    public MouseWheelHandler(MapsEditor mapsEditor) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            TILE_SIZE += 8;
        } else {
            TILE_SIZE -= 8;
        }
    }
}

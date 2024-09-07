package io.mblueberry.mapseditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveHandler implements MouseMotionListener {
    public MapsEditor mapsEditor;
    public MouseMoveHandler(MapsEditor mapsEditor) {
        this.mapsEditor = mapsEditor;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mapsEditor.map != null) {
            mapsEditor.map.handleMouseMove(e);
            mapsEditor.editorUi.mouseMove(e);
        }
    }
}

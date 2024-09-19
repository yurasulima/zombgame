package io.mblueberry.input;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheelHandler implements MouseWheelListener {
    private Game game;
    public MouseWheelHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (game.gameState == GameState.PLAYING) {
           game.guiManager.handleMouseWheel(e);
        }
    }
}

package io.mblueberry.input;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveHandler implements MouseMotionListener {

    private Game game;

    public MouseMoveHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (game.gameState == GameState.START_SCREEN) {
         //   game.guiManager.handleMouseUI(e);
        }
        game.world.handleMouseMove(e);
    }
}

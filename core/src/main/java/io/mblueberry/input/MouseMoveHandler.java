package io.mblueberry.input;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;
import io.mblueberry.ui.UiState;

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
            game.guiManager.startMenuUi.handleMouse(e);
        }
        if (game.gameState == GameState.PLAYING) {
            if (game.uiState == UiState.HUD) {
                game.guiManager.gameUi.handleMouseMove(e);
                game.guiManager.gameUi.handleMouse(e);
            }
            if (game.uiState == UiState.INVENTORY) {
                game.guiManager.inventoryUi.handleMoveMouse(e);
            }
            if (game.uiState == UiState.CHEST_INVENTORY) {
                game.guiManager.chestInventoryUi.handleMoveMouse(e);
            }
        }
    }
}

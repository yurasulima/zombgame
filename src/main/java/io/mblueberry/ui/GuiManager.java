package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class GuiManager implements IBaseUi {
    private Game game;
    public GameUi gameUi;
    private InventoryUi inventoryUi;
    private StartMenuUi startMenuUi;

    public GuiManager(Game game) {
        this.game = game;
        gameUi = new GameUi(game);
        inventoryUi = new InventoryUi(game);
        startMenuUi = new StartMenuUi(game);
    }


    @Override
    public void update() {
        if (game.gameState == GameState.PLAYING) {
            gameUi.update();
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        if (game.gameState == GameState.PLAYING) {
            gameUi.draw(g2);
        }
        if (game.gameState == GameState.INVENTORY) {
            inventoryUi.draw(g2);
        }
        if (game.gameState == GameState.START_SCREEN) {
          //  gameUi.draw(g2);
        }
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

        if (game.gameState == GameState.PLAYING) {
            gameUi.handleMouseWheel(e);
        }
        if (game.gameState == GameState.INVENTORY) {
            inventoryUi.handleMouseWheel(e);
        }
        if (game.gameState == GameState.START_SCREEN) {
            startMenuUi.handleMouseWheel(e);
        }
    }
}

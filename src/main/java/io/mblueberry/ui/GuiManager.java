package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public class GuiManager implements IBaseUi {
    private Game game;
    public GameUi gameUi;
    public DebugUi debugUi;
    public InventoryUi inventoryUi;
    public ChestInventoryUi chestInventoryUi;
    public ChatUi chatUi;
    private StartMenuUi startMenuUi;

    public GuiManager(Game game) {
        this.game = game;
        gameUi = new GameUi(game);
        inventoryUi = new InventoryUi(game);
        chestInventoryUi = new ChestInventoryUi(game);
        startMenuUi = new StartMenuUi(game);
        debugUi = new DebugUi(game);
        chatUi = new ChatUi(game);
    }


    @Override
    public void update() {
        if (game.gameState == GameState.PLAYING) {
            gameUi.update();
            if (game.keyHandler.showInventory) {
                inventoryUi.update();
            }

            if (game.uiState == UiState.CHAT) {
                chatUi.update();
            }

        }
    }

    public void openChestUi() {
        game.uiState = UiState.CHEST_INVENTORY;
    }



    @Override
    public void draw(Graphics2D g2) {
        if (game.gameState == GameState.PLAYING) {
            handleGameStateUi(g2);

        }
        if (game.gameState == GameState.INVENTORY) {
            inventoryUi.draw(g2);
        }
        if (game.gameState == GameState.START_SCREEN) {
          //  gameUi.draw(g2);
        }


        if (Game.DEBUG) {
            debugUi.draw(g2);
        }
    }

    private void handleGameStateUi(Graphics2D g2) {
        if (game.uiState == UiState.HUD) {
            gameUi.draw(g2);
        }
        if (game.uiState == UiState.INVENTORY) {
            inventoryUi.draw(g2);
        }
        if (game.uiState == UiState.CHEST_INVENTORY) {
            chestInventoryUi.draw(g2);
        }
        if (game.uiState == UiState.CHAT) {
            chatUi.draw(g2);
        }
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

        if (game.gameState == GameState.PLAYING) {
            gameUi.handleMouseWheel(e);
            chatUi.handleMouseWheel(e);
            chestInventoryUi.handleMouseWheel(e);
        }
        if (game.gameState == GameState.INVENTORY) {
            inventoryUi.handleMouseWheel(e);
        }
        if (game.gameState == GameState.START_SCREEN) {
            startMenuUi.handleMouseWheel(e);
        }
    }
}

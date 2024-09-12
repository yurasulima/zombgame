package io.mblueberry.input;

import io.mblueberry.Game;
import io.mblueberry.ui.UiState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static io.mblueberry.Game.DEBUG;

public class KeyHandler implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    public boolean showDebug;
    public boolean paused;
    public boolean spacePressed;
    public boolean showStata = false;
    public boolean showInventory = false;

    public Game game;

    public KeyHandler(Game game) {
        this.game = game;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if (game.uiState == UiState.CHAT) {
            game.guiManager.chatUi.handleKeyBoard(e);
        } else 
        if (game.uiState == UiState.HUD) {

            //TODO move key handler to gameUi class
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;

            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;

            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;

            }
            if (code == KeyEvent.VK_F5) {
                DEBUG = !DEBUG;

            }
            if (code == KeyEvent.VK_R) {
                showStata = !showStata;
            }


            if (code == KeyEvent.VK_T) {
                game.uiState = UiState.CHAT;
            }

            if (code == KeyEvent.VK_E) {
                    game.uiState = UiState.INVENTORY;

            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = !spacePressed;

            }
        }
         else if (game.uiState == UiState.INVENTORY) {
            game.uiState = UiState.HUD;
        } else if (game.uiState == UiState.CHEST_INVENTORY) {
            game.guiManager.chestInventoryUi.chestBlock.close();
            game.uiState = UiState.HUD;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;

        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
//        if (code == KeyEvent.VK_R) {
//            showStata = false;
//        }

    }
}

package org.example.input;

import org.example.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    public boolean showDebug;
    public boolean paused;

    public Game gamePanel;

    public KeyHandler(Game gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //PLAY STATE
     //   if (gamePanel.gameState == gamePanel.playState) {
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
                showDebug = !showDebug;

            }

//            if (code == KeyEvent.VK_ESCAPE) {
//                gamePanel.gameState = gamePanel.pauseState;
//            }
       // }

//        //PAUSE STATE
//        else if (gamePanel.gameState == gamePanel.pauseState) {
//            if (code == KeyEvent.VK_ESCAPE) {
//                gamePanel.gameState = gamePanel.playState;
//            }
//
//        }
//
//        //DIALOGUE STATE
//        else if (gamePanel.gameState == gamePanel.dialogueState) {
//
//            if (code == KeyEvent.VK_ESCAPE) {
//                gamePanel.gameState = gamePanel.playState;
//            }
//        }


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

    }
}

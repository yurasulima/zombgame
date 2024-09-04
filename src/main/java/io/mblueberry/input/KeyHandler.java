package io.mblueberry.input;

import io.mblueberry.Game;

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
    public boolean spacePressed;
    public boolean showStata = false;

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

        //PLAY STATE
     //   if (game.gameState == game.playState) {
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
            if (code == KeyEvent.VK_R) {
                showStata = !showStata;
            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = !spacePressed;

            }

//            if (code == KeyEvent.VK_ESCAPE) {
//                game.gameState = game.pauseState;
//            }
       // }

//        //PAUSE STATE
//        else if (game.gameState == game.pauseState) {
//            if (code == KeyEvent.VK_ESCAPE) {
//                game.gameState = game.playState;
//            }
//
//        }
//
//        //DIALOGUE STATE
//        else if (game.gameState == game.dialogueState) {
//
//            if (code == KeyEvent.VK_ESCAPE) {
//                game.gameState = game.playState;
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
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
//        if (code == KeyEvent.VK_R) {
//            showStata = false;
//        }

    }
}

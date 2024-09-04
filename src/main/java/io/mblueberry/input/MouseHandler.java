package io.mblueberry.input;


import io.mblueberry.Game;
import io.mblueberry.core.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {


    public Game game;

    public MouseHandler(Game game) {
        this.game = game;
    }

    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse mouseClicked");
    }

    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse mouseEntered");
    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse mouseExited");
    }

    public void mouseReleased(MouseEvent e) {

        if (game.gameState == GameState.START_SCREEN) {
            game.ui.handleMouseClick(e);
        }
        if (game.gameState == GameState.PLAYING) {
            game.world.handleMouseClickWorld(e);
        }

    }

    public void mousePressed(MouseEvent e) {

        if (game.gameState == GameState.START_SCREEN) {
            game.ui.handleMouseClick(e);
        }
        //  game.player.shoot(e.getX(), e.getY());
//TODO        player.shoot();
    }



}

package org.example.input;


import org.example.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {


    public Game gamePanel;

    public MouseHandler(Game gamePanel) {
        this.gamePanel = gamePanel;
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
        //System.out.println("Mouse mouseReleased");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse mousePressed: x="+ e.getX() +" y="+e.getY());
        gamePanel.player.shoot(e.getX(), e.getY());
//        player.shoot();
//        repaint();

    }



}

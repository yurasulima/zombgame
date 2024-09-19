package io.mblueberry.ui;

import io.mblueberry.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static io.mblueberry.Game.TILE_SIZE;

public class DebugUi implements IBaseUi{
    private final Game game;

    public DebugUi(Game game) {
        this.game = game;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(20F));
        g2.drawString("player:", 10, 310);
        g2.drawString(" x: " + (game.player.x - game.cameraX) / TILE_SIZE, 10, 330);
        g2.drawString(" y: " + (game.player.y - game.cameraY) / TILE_SIZE, 10, 350);
        g2.drawString(" screen x: "+game.player.x, 10, 370);
        g2.drawString(" screen y: "+game.player.y, 10, 390);
        g2.drawString(" camera x: "+game.cameraX, 10, 410);
        g2.drawString(" camera y: "+game.cameraY, 10, 430);
        g2.drawString(" absolute x: " + (game.player.x - game.cameraX), 10, 450);
        g2.drawString(" absolute y: " + (game.player.y - game.cameraY), 10, 470);
        g2.drawString(" block: " + game.world.getTileScreen(game.player.x - game.cameraX, game.player.y - game.cameraY).getType(), 10, 490);

        if (game.guiManager.gameUi.currentPickBlock != null) {
            g2.drawString(" picker: " + game.guiManager.gameUi.currentPickBlock.getType(), 10, 510);
        }

        g2.drawString(" MouseX: " + game.guiManager.gameUi.mouseX, 10, 530);
        g2.drawString(" MouseY: " + game.guiManager.gameUi.mouseY, 10, 550);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

    }

    @Override
    public void handleMouse(MouseEvent e) {

    }
}

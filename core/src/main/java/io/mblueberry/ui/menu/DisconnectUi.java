package io.mblueberry.ui.menu;

import io.mblueberry.Game;
import io.mblueberry.ui.IBaseUi;
import io.mblueberry.ui.component.Button;
import io.mblueberry.util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DisconnectUi implements IBaseUi {
    private final int buttonSizeX = 200;
    private final Game game;
    public String message;
    public Button okButton;

    public DisconnectUi(Game game) {
        this.game = game;
        int panelHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int panelWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        okButton = new Button((panelWidth / 2) - (buttonSizeX / 2), panelHeight / 2, buttonSizeX, 50, "Ok");
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawString(message, Utils.getCenterTextX(message, g2, game), okButton.x - 50);
        okButton.draw(g2);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

    }

    @Override
    public void handleMouse(MouseEvent e) {
        okButton.handleMouseEvent(e);
    }

}

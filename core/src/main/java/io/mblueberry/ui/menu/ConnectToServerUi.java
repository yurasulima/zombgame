package io.mblueberry.ui.menu;

import io.mblueberry.Game;
import io.mblueberry.ui.IBaseUi;
import io.mblueberry.ui.component.Button;
import io.mblueberry.util.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ConnectToServerUi implements IBaseUi {
    private final int buttonSizeX = 200;
    private final Game game;
    public String status;
    public Button cancelButton;


    public ConnectToServerUi(Game game) {
        this.game = game;
        int panelHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int panelWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        cancelButton = new Button((panelWidth / 2) - (buttonSizeX / 2), panelHeight / 2, buttonSizeX, 50, "Cancel");
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawString(status, Utils.getCenterTextX(status, g2, game), cancelButton.x - 50);
        cancelButton.draw(g2);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

    }

    @Override
    public void handleMouse(MouseEvent e) {
        cancelButton.handleMouseEvent(e);
    }

}
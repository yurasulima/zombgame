package io.mblueberry.ui.menu;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;
import io.mblueberry.ui.IBaseUi;
import io.mblueberry.ui.component.Button;
import io.mblueberry.ui.component.ListButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class StartMenuUi implements IBaseUi {
    private final Game game;
    public ListButton listButton;
    public Button startButton;
    public Button serverButton;
    private int buttonSizeX = 200;
    private int buttonSizeY = 50;
    public StartMenuUi(Game game) {
        this.game = game;

        int panelHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int panelWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        listButton = new ListButton((panelWidth / 2) - (buttonSizeX / 2), panelHeight / 2);
        startButton = new Button(0, 0, buttonSizeX, buttonSizeY, "Start");
        serverButton = new Button(0, 0, buttonSizeX, buttonSizeY, "Server");
        listButton.add(startButton);
        listButton.add(serverButton);
        setupButton();
    }

    private void setupButton() {
        startButton.setOnClick(() -> {
            game.gameState = GameState.PLAYING;
            System.out.println("start click");
        });
        serverButton.setOnClick(() -> {
            System.out.println("server click");
            game.gameState = GameState.CONNECT_TO_SERVER;
            game.startClient();
        });
    }

    @Override
    public void update() {

    }
    @Override
    public void draw(Graphics2D g2) {
        drawButtons(g2);
    }

    private void drawButtons(Graphics2D g2) {
        listButton.draw(g2);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

    }

    public void handleMouse(MouseEvent e) {
        listButton.handleMouse(e);
    }
}

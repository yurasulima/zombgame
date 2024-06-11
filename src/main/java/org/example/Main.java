package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");
        Game gamePanel = new Game();
        window.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        window.add(gamePanel);
        gamePanel.setupGame();
        gamePanel.startGameThread();
        window.pack();
        window.setVisible(true);
    }
}
package io.mblueberry;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = new Game();

        window.setLayout(new BorderLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        window.add(game);
        game.setupGame();
        game.startGameThread();
        window.pack();
        window.setVisible(true);

    }
}
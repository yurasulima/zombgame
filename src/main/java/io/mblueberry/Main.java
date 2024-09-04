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
        if (args.length > 0 && args[0].equals("--server")) {
            game.gameType = "server";
            window.setTitle("Server");
            game.startServer();
        } else if (args.length > 0 && args[0].equals("--client")) {
            game.gameType = "client";
            window.setTitle("Client");
            game.startClient();
        } else {

            game.gameType = "offline";
        }

        window.pack();
        window.setVisible(true);

    }
}
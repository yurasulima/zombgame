package org.example;

import org.example.Entity.Player;
import org.example.input.KeyHandler;
import org.example.input.MouseHandler;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    public int tileSize = 64;
    public int cameraX = 0;
    public int cameraY = 0;
    public KeyHandler keyHandler;
    public EventHandler eventHandler;
    MouseHandler mouseHandler;
    Thread gameThread;
    public World world;
    public CollisionChecker collisionChecker;
    public Player player;

    public void setupGame() {
        mouseHandler = new MouseHandler(this);
        keyHandler = new KeyHandler(this);
        collisionChecker = new CollisionChecker(this);
        eventHandler = new EventHandler(this);
        this.addMouseListener(mouseHandler);
        player = new Player(this, keyHandler);
        world = new World(this, player);
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        world.update();
    }

    @Override
    public void run() {
        double frameInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + frameInterval;
        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += frameInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        world.draw(g2);
        g2.dispose();
    }
}

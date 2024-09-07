package io.mblueberry.mapseditor;

import javax.swing.*;
import java.awt.*;

public class MapsEditor extends JPanel implements Runnable {

    public int cameraX = 200;
    public int cameraY = 0;
    public Thread editorThread;
    public EditorUi editorUi;
    public KeyHandler keyHandler;
    public MouseMoveHandler mouseMoveHandler;
    public MouseWheelHandler mouseWheelHandler;
    public Map map;

    public void setup() {
        editorUi = new EditorUi(this);
        keyHandler = new KeyHandler();
        mouseMoveHandler = new MouseMoveHandler(this);
        mouseWheelHandler = new MouseWheelHandler(this);
        setFocusable(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(editorUi);
        this.addMouseMotionListener(mouseMoveHandler);
        this.addMouseWheelListener(mouseWheelHandler);
    }

    public void start() {
        editorThread = new Thread(this);
        editorThread.start();
    }
    public void update() {
        if (map != null) {
            map.update();
        }
        editorUi.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (map != null) {
            map.draw(g2);
        }
        editorUi.draw(g2);
        g2.dispose();
    }

    @Override
    public void run() {
        double frameInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + frameInterval;
        while (editorThread != null) {
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
}

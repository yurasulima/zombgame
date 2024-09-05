package io.mblueberry;

import io.mblueberry.core.CollisionChecker;
import io.mblueberry.core.EventHandler;
import io.mblueberry.core.GameState;
import io.mblueberry.core.World;
import io.mblueberry.object.block.Chest;
import io.mblueberry.object.block.Tent;
import io.mblueberry.object.entity.Player;
import io.mblueberry.object.items.AxeItem;
import io.mblueberry.object.items.ShieldItem;
import io.mblueberry.object.items.SwordItem;
import io.mblueberry.object.block.Block;
import io.mblueberry.input.KeyHandler;
import io.mblueberry.input.MouseHandler;
import io.mblueberry.input.MouseMoveHandler;
import io.mblueberry.input.MouseWheelHandler;
import io.mblueberry.server.Client;
import io.mblueberry.server.Server;
import io.mblueberry.ui.GuiManager;
import io.mblueberry.ui.UI;
import io.mblueberry.ui.UiState;
import io.mblueberry.ui.component.Button;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    public static int tileSize = 64;
    public int cameraX = 0;
    public int cameraY = 0;
    public KeyHandler keyHandler;
    public EventHandler eventHandler;
    public MouseHandler mouseHandler;
    public MouseWheelHandler mouseWheelHandler;
    public MouseMoveHandler mouseMoveHandler;
    public Thread gameThread;
    public World world;
    public CollisionChecker collisionChecker;
    public Player player;
    public String gameType;
    public GameState gameState;
    public UiState uiState;
    public UI ui;
    public GuiManager guiManager;
    public Button button;
    public Button buttonServer;
    public Button buttonClient;
    public Server server;
    public Client client;

    public boolean isServer = false;
    public boolean isServerStarted = false;

    public boolean isClient = false;
    public boolean isClientStarted = false;

    public void setupGame() {
        guiManager = new GuiManager(this);
        mouseHandler = new MouseHandler(this);
        mouseMoveHandler = new MouseMoveHandler(this);
        uiState = UiState.HUD;
        mouseWheelHandler = new MouseWheelHandler(this);
        keyHandler = new KeyHandler(this);
        collisionChecker = new CollisionChecker(this);
        eventHandler = new EventHandler(this);
        player = new Player(this, keyHandler, 10, 10, "player");
        player.inventory.add(new AxeItem());
        player.inventory.add(new SwordItem());
        player.inventory.add(new ShieldItem());
        Block tree = new Block("tree");
        tree.setupImage();
        player.inventory.add(tree);
        Block road00 = new Block("road00");
        road00.setupImage();
        player.inventory.add(road00);

        Block wall = new Block("wall");
        wall.setupImage();
        player.inventory.add(wall);

        Chest chest1 = new Chest();
        chest1.update();
        player.inventory.add(chest1);

        world = new World(this, player);
        ui = new UI(this);
        //button = new Button(100, 100, 200, 50, "Start", this::onButtonClick);
        button = new Button(100, 100, 200, 50, "Start");
        buttonClient = new Button(100, 200, 200, 50, "Start Client");
        buttonServer = new Button(100, 400, 200, 50, "Start Server");
        gameState = GameState.PLAYING;
        server = new Server(this);
        client = new Client(this);
        setFocusable(true);
        addKeyListener(keyHandler);
        addMouseMotionListener(mouseMoveHandler);
        addMouseListener(mouseHandler);
        addMouseWheelListener(mouseWheelHandler);
      //  addMouseListener(button);
      //  addMouseListener(buttonServer);
      //  addMouseListener(buttonClient);
    }

//    private void onButtonClick() {
//        gameState = GameState.PLAYING;
//        System.out.println("pressed");
//    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();


    }
    public void startServer() {
        server.start();
    }
    public void startClient() {

        client.start();
    }
    public void update() {



        if (gameState == GameState.START_SCREEN) {
//            if (button.isClicked()) {
//                gameState = GameState.PLAYING;
//                button.resetClicked();
//            }
//            if (buttonServer.isClicked()) {
//
//                gameState = GameState.PLAYING;
//                buttonServer.resetClicked();
//            }
//            if (buttonClient.isClicked()) {
//
//                gameState = GameState.PLAYING;
//                buttonClient.resetClicked();
//            }
//            button.update();
//            Button button = new Button(100, 100, 100, 200, "Start", () -> gameState = GameState.PLAYING);
        } else if (gameState == GameState.PLAYING) {
            world.update();
        } else if (gameState == GameState.GAME_OVER) {

        }
        ui.update();
        guiManager.update();
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

      //  if (gameState == GameState.START_SCREEN) {
//            button.draw(g2);
//            buttonClient.draw(g2);
//    /        buttonServer.draw(g2);
      //  } else if (gameState == GameState.PLAYING) {
      //      world.draw(g2);


     //   } else if (gameState == GameState.GAME_OVER) {

     //   }
      //  ui.draw(g2);
        world.draw(g2);
        guiManager.draw(g2);
        g2.dispose();
    }


}

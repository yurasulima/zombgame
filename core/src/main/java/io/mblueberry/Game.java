package io.mblueberry;

import io.mblueberry.core.CollisionChecker;
import io.mblueberry.core.EventHandler;
import io.mblueberry.core.GameState;
import io.mblueberry.core.World;
import io.mblueberry.core.ai.PathFinder;
import io.mblueberry.core.object.entity.Entity;
import io.mblueberry.core.object.item.*;
import io.mblueberry.core.particle.ParticleSystem;
import io.mblueberry.core.object.block.ChestBlock;
import io.mblueberry.core.object.entity.Player;
import io.mblueberry.core.object.entity.npc.HitNPC;
import io.mblueberry.core.object.entity.npc.OldManNPC;
import io.mblueberry.core.object.block.Block;
import io.mblueberry.input.KeyHandler;
import io.mblueberry.input.MouseHandler;
import io.mblueberry.input.MouseMoveHandler;
import io.mblueberry.input.MouseWheelHandler;
import io.mblueberry.server.Client;
import io.mblueberry.server.GameClient;
import io.mblueberry.server.GameServer;
import io.mblueberry.server.Server;
import io.mblueberry.server.packet.PlayerUpdate;
import io.mblueberry.ui.GuiManager;
import io.mblueberry.ui.UiState;
import io.mblueberry.ui.component.Button;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    public static int TILE_SIZE = 64;
    public static boolean DEBUG = true;
    public int cameraX = 0;
    public int cameraY = 0;
    public String username = "mblueberry";
    public KeyHandler keyHandler;
    public EventHandler eventHandler;
    public MouseHandler mouseHandler;
    public MouseWheelHandler mouseWheelHandler;
    public MouseMoveHandler mouseMoveHandler;
    public Thread gameThread;
    public World world;
    public CollisionChecker collisionChecker;
    public Player player;
    public Entity hitNpc;
    public String gameType;
    public GameState gameState;
    public UiState uiState;
    public PathFinder pathFinder;
    public ParticleSystem particleSystem;
    public GuiManager guiManager;
    public Button button;
    public Button buttonServer;
    public Button buttonClient;
    public GameServer gameServer;
    public GameClient gameClient;
    public boolean isOnline;


    public void setupGame() {
        // Ініціалізація гри, яка підходить як для клієнта, так і для сервера
        mouseHandler = new MouseHandler(this);
        mouseMoveHandler = new MouseMoveHandler(this);
        particleSystem = new ParticleSystem();
        uiState = UiState.HUD;
        mouseWheelHandler = new MouseWheelHandler(this);
        keyHandler = new KeyHandler(this);
        collisionChecker = new CollisionChecker(this);
        eventHandler = new EventHandler(this);
        player = new Player(this, 4, 6);
        hitNpc = new HitNPC(this, 6, 6);
        world = new World(this, player);
        pathFinder = new PathFinder(this);
        guiManager = new GuiManager(this);

        // Налаштування інвентаря
        setupPlayerInventory();

        // Додавання NPC та об'єктів
        setupWorldObjects();

        // Налаштування елементів інтерфейсу
        button = new Button(100, 100, 200, 50, "Start");
        buttonClient = new Button(100, 200, 200, 50, "Start Client");
        buttonServer = new Button(100, 400, 200, 50, "Start Server");

        gameState = GameState.START_SCREEN;
        setFocusable(true);

        addKeyListener(keyHandler);
        addMouseMotionListener(mouseMoveHandler);
        addMouseListener(mouseHandler);
        addMouseWheelListener(mouseWheelHandler);
        addKeyListener(player);
    }

    public void sendPlayerUpdate() {
        if (gameClient != null) {
            PlayerUpdate update = new PlayerUpdate(player.x - cameraX, player.y - cameraY, player.username, player.currentState);
            gameClient.sendUpdate(update);
        }
    }



    // Метод для налаштування інвентаря гравця
    private void setupPlayerInventory() {
        player.inventory.add(new AxeItem());
        player.inventory.add(new BowItem());
        player.inventory.add(new PikeItem());
        player.inventory.add(new SwordItem());
        player.inventory.add(new ShieldItem());
        player.inventory.add(new BoomerangItem());
        ChestBlock chestBlock1 = new ChestBlock(this);
        chestBlock1.stackCount = 5;
        player.inventory.add(chestBlock1);

        Block grass00 = new Block("grass00");
        player.inventory.add(grass00);

        Block grass01 = new Block("grass01");
        player.inventory.add(grass01);

        Block tree = new Block("tree");
        tree.stackCount = 33;
        player.inventory.add(tree);

        Block tree1 = new Block("tree");
        tree1.stackCount = 31;
        player.inventory.add(tree1);

        Block tree2 = new Block("tree");
        player.inventory.add(tree2);

        Block road00 = new Block("road00");
        player.inventory.add(road00);

        Block wall = new Block("wall");
        player.inventory.add(wall);
    }

    // Метод для додавання об'єктів у світ
    private void setupWorldObjects() {
        OldManNPC e = new OldManNPC(this, 23, 23);
        world.entities.add(e);
        e.speak();
        world.entities.add(hitNpc);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Метод для запуску сервера
    public void startServer() {
        gameServer = new GameServer(this);
        gameServer.start();
    }

    // Метод для запуску клієнта
    public void startClient() {
        gameClient = new GameClient(this);
        gameClient.start();
    }

    public void update() {
        if (gameState == GameState.START_SCREEN) {
            // Логіка стартового екрану
        } else if (gameState == GameState.PLAYING) {
            world.update();
        } else if (gameState == GameState.GAME_OVER) {
            // Логіка екрану завершення гри
        }
        guiManager.update();
        particleSystem.update();
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Малювання гри
        if (gameState == GameState.PLAYING) {
            world.draw(g2);
        }
        guiManager.draw(g2);
        g2.dispose();
    }
}

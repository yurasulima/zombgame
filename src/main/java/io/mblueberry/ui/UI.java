package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.core.GameState;
import io.mblueberry.core.Tile;
import io.mblueberry.object.items.HeartItem;
import io.mblueberry.ui.component.Button;
import io.mblueberry.ui.component.Slot;
import io.mblueberry.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static io.mblueberry.util.Utils.getAlignRight;

public class UI {


    public BufferedImage heartFull, heartHalf, heartBlank;
    Graphics2D g2;
    Game game;
    int panelWidth = 0;
    int panelHeight = 0;
    private Button button;
    private Button settings;
    private Slot slot1;
    private Slot slot2;
    private Slot slot3;
    private Slot slot4;
    boolean drawed = false;

    String currentDialogue;

    public UI(Game game) {
        this.game = game;
        HeartItem heart = new HeartItem(game);
        heartFull = heart.heartFull;
        heartHalf = heart.heartHalf;
        heartBlank = heart.heartBlank;

        button = new Button(0, 0, 200, 50, "Start");
        settings = new Button(0, 0, 200, 50, "Settings");
        button.setOnClick(() -> {
            game.gameState = GameState.PLAYING;
            System.out.println("Start clicked!");
        });
        settings.setOnClick(() -> {
            game.gameState = GameState.PAUSED;
            System.out.println("settings clicked!");
        });
    }

    public void setupUI() {


//        int buttonX = (panelWidth - button.height ) / 2;
        int buttonX = (panelWidth / 2) - (button.width / 2);
        int buttonY = (panelHeight - button.height) / 2 - 30;
        button.setPosition(buttonX, buttonY);
        int settingsX = (panelWidth - settings.width) / 2;
        int settingsY = buttonY + button.height + 10;
        settings.setPosition(settingsX, settingsY);
    }

    public void update() {
        panelHeight = game.getHeight();
        panelWidth = game.getWidth();
        setupUI();
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        if (game.gameState == GameState.PLAYING) {
            drawPlayerLife();
            drawToolbar();
            if (game.keyHandler.showStata) {
                drawPlayerStata();
            }
        }

        if (game.gameState == GameState.START_SCREEN) {
            drawBackground();
            button.draw(g2);
            settings.draw(g2);


        }


    }

    public void handleMouseMove(MouseEvent e){

    }
    public void handleMouseClick(MouseEvent e){
        button.handleMouseEvent(e);
        settings.handleMouseEvent(e);
    }
    public void drawToolbar(){


//        int y = game.getHeight() - 140;
//        int x = 10;


     //   for (int i = 0; i < 9; i++) {

//            slot1 = new Slot(x, y, 64, 64);
//
//            slot1.setOnClick(() -> {
//                System.out.println("slot clicked!");
//            });
//            slot1.draw(g2);
//           // g2.drawRect(x, y, 64, 64);
//            x += 74;
      //  }


//        y = game.getHeight() - 140;
//        x = 10;
//
//        for (int i = 0; i < game.player.inventory.size(); i++) {
//            Entity item = game.player.inventory.get(i);
//            if (item != null) {
//                g2.drawImage(item.hold, x, y, 64, 64, null);
//            }
//            x += 74;
//        }
    }

    public void drawDialogueScreen() {

        //WINDOWS
        int x = game.tileSize / 2;
        int y = game.tileSize / 2;
        int width = game.getWidth() - (game.tileSize * 4);
        int height = game.tileSize * 4;
        drawSubWindow(x, y, width, height);


        g2.setFont(g2.getFont().deriveFont(15F));
        x += game.tileSize;
        y += game.tileSize;
        for (String s : currentDialogue.split("\n")) {
            g2.drawString(s, x, y);
            y += 30;
        }
    }

    private void drawPlayerStata() {
        int frameX = game.tileSize * 2;
        int frameY = game.tileSize * 2;
        int frameW = game.tileSize * 5;
        int frameH = game.tileSize * 10;
        drawSubWindow(frameX, frameY, frameW, frameH);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + game.tileSize;
        final int textH = 32;

        g2.drawString("Level", textX, textY);
        textY += textH;
        g2.drawString("Life", textX, textY);
        textY += textH;
        g2.drawString("Strength", textX, textY);
        textY += textH;
        g2.drawString("Dexterity", textX, textY);
        textY += textH;
        g2.drawString("Attack", textX, textY);
        textY += textH;
        g2.drawString("Defense", textX, textY);
        textY += textH;
        g2.drawString("Exp", textX, textY);
        textY += textH;
        g2.drawString("Next Level", textX, textY);
        textY += textH;
        g2.drawString("Coin", textX, textY);
        textY += textH;
        g2.drawString("Weapon", textX, textY);
        textY += textH;
        g2.drawString("Shield", textX, textY);

        int tailX = (frameX + frameW) - 20;
        textY = frameY + game.tileSize;
        String value;

        value = String.valueOf(game.player.level);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = game.player.life + "/" + game.player.maxLife;
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.strength);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.dexterity);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.attack);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.defense);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.exp);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.nextLevelExp);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.coin);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.currentWeapon.displayName);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += textH;

        value = String.valueOf(game.player.currentShield.displayName);
        textX = getAlignRight(value, tailX, g2);
        g2.drawString(value, textX, textY);


      
    }

    private void drawBackground() {
//        if (!drawed) {
//
//
//            int maxWorldColumn = game.getWidth() + 128 / game.tileSize;
//            int maxWorldRow = game.getHeight() + 128 / game.tileSize;
//            int tileSize = game.tileSize;
//            Tile[] tile = setup();
//            Random random = new Random();
//            for (int row = 0; row < maxWorldRow; row++) {
//                for (int col = 0; col < maxWorldColumn; col++) {
//                    int worldX = col * tileSize;
//                    int worldY = row * tileSize;
//
//                    int i = random.nextInt(2);
//                    g2.drawImage(tile[i].image, worldX, worldY, null);
//                }
//            }
//            drawed = true;
//        }

//        Dimension mapSize = game.world.getMapSize("/maps/world2.txt");
//
//        int maxWorldColumn = mapSize.width;
//        int maxWorldRow = mapSize.height;
//        int tileSize = game.tileSize;
//
//
//        for (int row = 0; row < maxWorldRow; row++) {
//            for (int col = 0; col < maxWorldColumn; col++) {
//                int tileNum = game.world.mapTileNum[col][row];
//                int worldX = col * tileSize;
//                int worldY = row * tileSize;
//                int screenX = worldX;
//                int screenY = worldY;
//
//                g2.drawImage(game.world.tile[tileNum].getImage(), screenX, screenY, null);
//            }
//        }

    }

    public Tile[] setup() {
        Tile[] tile = new Tile[2];
        try {
            String imagePath = "grass00";
            tile[0] = new Tile();
            tile[1] = new Tile();


            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[0].image = Utils.scaleIMage(tile[0].image, game.tileSize, game.tileSize);
            tile[0].collision = false;
            tile[0].name = imagePath;

            imagePath = "grass01";
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[1].image = Utils.scaleIMage(tile[1].image, game.tileSize, game.tileSize);
            tile[1].collision = false;
            tile[1].name = imagePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tile;
    }



    private void drawPlayerLife() {
//        game.player.maxLife = 10;
//        game.player.life = 9;
        int x = game.tileSize / 2;
        int y = game.tileSize / 2;

        for (int i = 0; i < game.player.maxLife / 2; i++) {
            g2.drawImage(heartBlank, x, y, null);
            x += game.tileSize / 2;
        }

        x = game.tileSize / 2;
        for (int i = 0; i < game.player.life / 2; i++) {
            g2.drawImage(heartFull, x, y, null);
            x += game.tileSize / 2;
        }
        if (game.player.life % 2 != 0) {
            g2.drawImage(heartHalf, x, y, null);
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 200);
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);


        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 35, 35);

    }

}

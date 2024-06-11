package org.example.Entity;

import org.example.*;
import org.example.Bullets.Bullet;
import org.example.input.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Player extends Entity {

    private Game game;
    private KeyHandler keyHandler;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    BufferedImage img;


    public Player(Game game, KeyHandler keyHandler) {
        super(game);
        this.game = game;
        this.keyHandler = keyHandler;
        isPlayer = true;
        solidArea = new Rectangle(16, 32, 32, 32);
        direction = "down";
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            Utils utils = new Utils();
            img = utils.scaleIMage(img, 64, 64);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues() {
        screenX = game.tileSize * 10;
        screenY = game.tileSize * 10;
        game.cameraX  -= game.tileSize * 10;
        game.cameraY  -= game.tileSize * 10;
        move = 9;
        direction = "down";
    }

    public void getPlayerImage() {
        hold = setup("/player/boy_up_1");
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");

    }

    public void draw(Graphics2D g2){


        BufferedImage image = null;
//        System.out.println("solidArea.x = " + solidArea.x);
//        System.out.println("screenX = " + screenX);
//        int areaStartX = this.screenX + this.solidArea.x;
//        int areaEndX = this.screenX + this.solidArea.x + this.solidArea.width;
//        int areaStartY = this.screenY + this.solidArea.y;
//        int areaEndY = this.screenY  + this.solidArea.y + this.solidArea.height;

//        int entityLeftColumn = entityLeftWorldX / game.tileSize;
//        int entityRightColumn = entityRightWorldX / game.tileSize;
//        int entityTopRow = entityTopWorldY / game.tileSize;
//        int entityBottomRow = entityBottomWorldY / game.tileSize;

        g2.setColor(Color.BLACK);
//        g2.drawRect(entityLeftWorldX,  entityTopWorldY, 5 , 5);
//        g2.drawRect(entityRightWorldX,  entityBottomWorldY, 5 , 5);
//
//        g2.drawRect(entityRightWorldX,  entityBottomWorldY, 5 , 5);
//        g2.drawRect(entityRightWorldX,  entityBottomWorldY, 5 , 5);




        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
        }




        g2.drawImage(image, screenX, screenY, null);



        int tileX = (screenX - game.cameraX) / game.tileSize;
        int tileY = (screenY - game.cameraY) / game.tileSize;

        byte[] a = (
                "player: " +

                "screenX: " + screenX + ", " +
                "screenY: " + screenY + " " +
                "x + offset: " + (screenX - game.cameraX) + ", " +
                "y + offset: " + (screenY - game.cameraY) + " " +
                "tile (" + tileX + ", " + tileY + "): " + (game.world.getTileScreen(screenX - game.cameraX + game.tileSize / 2, screenY - game.cameraY + game.tileSize / 2).name)

        ).getBytes();
        String text = new String(a, UTF_8);

        int x = getCenterTextX(text, g2);

        int y = game.getHeight() / 2 - (game.tileSize * 2);

        g2.drawString(text, x, y);


        g2.setColor(Color.BLACK);
//        System.out.println("solidArea.x = " + solidArea.x);
//        System.out.println("solidArea.x = " + solidArea.y);
//        System.out.println("solidArea.x = " + solidArea.width);
//        System.out.println("solidArea.x = " + solidArea.height);
       g2.drawRect(screenX + solidArea.x,  screenY + solidArea.y, solidArea.width , solidArea.height);


    }

    public int getCenterTextX(String text, Graphics2D g2) {
        int textLen = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return game.getWidth() / 2 - textLen / 2;
    }
    public void shoot(double mouseX, double mouseY) {
        double dx = mouseX - screenX;
        double dy = mouseY - screenY; // Corrected to screenY
        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        System.out.println("dx = " + dx);
        System.out.println("dy = " + dy);
        Bullet bullet = new Bullet(screenX - game.cameraX, screenY - game.cameraY, dx, dy, 15.0, 10,  game);
        game.world.bullets.add(bullet);
    }

    public void update(){

        if (keyHandler.downPressed) {
            direction = "down";
        }
        if (keyHandler.upPressed) {
            direction = "up";
        }
        if (keyHandler.rightPressed) {
            direction = "right";
        }
        if (keyHandler.leftPressed) {
            direction = "left";
        }

        int move = game.tileSize / 16;
        if ((keyHandler.upPressed || keyHandler.downPressed) && (keyHandler.rightPressed || keyHandler.leftPressed)) {

            move = game.tileSize / 18;
        }
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        game.collisionChecker.checkTile(this);


        if (!collisionOn) {
            int displayCenterX = game.getWidth() / 2;
            int displayCenterY = game.getHeight() / 2;

            if (keyHandler.downPressed) {
                direction = "down";
                if (game.player.screenY > (displayCenterY + 200)) {

                    int tileY = (screenY - game.cameraY);
                    int screenRealX = game.getWidth();
                  //  System.out.println("tileY = " + tileY + ", screenY = "+screenY +", realX = "+ screenRealX);
                    if (tileY - screenY > screenRealX) {
                        System.out.println("detect");
                        screenY += move;
                    } else {
                        game.cameraY -= move;
                    }

                } else {
                    screenY += move;
                }
            }
            if (keyHandler.upPressed) {
                direction = "up";
                if (game.player.screenY < (displayCenterY - 200)) {

                    int tileY = (screenY - game.cameraY);
                    if (tileY > screenY) {
                        game.cameraY += move;
                    } else {
                        screenY -= move;
                    }
                } else {
                    screenY -= move;
                }
            }
            if (keyHandler.rightPressed) {
                direction = "right";
                if (game.player.screenX > (displayCenterX + 200)) {



                    int tileX = (screenX - game.cameraX);
                    int screenRealX = game.getHeight();
                 //   System.out.println("tileX = " + tileX + ", screenX = "+screenX +", realX = "+ screenRealX);
                    if (tileX - screenX > screenRealX) {
                        System.out.println("detect");
                        screenX += move;
                    } else {
                        game.cameraX -= move;
                    }
                } else {
                    screenX += move;
                }
            }
            if (keyHandler.leftPressed) {
                //  game.cameraX += move;
                direction = "left";
                if (game.player.screenX < (displayCenterX - 200)) {

                    int tileX = (screenX - game.cameraX);
                    int tileY = (screenY - game.cameraY);
                    if (tileX > screenX) {
                        game.cameraX += move;
                    } else {
                        screenX -= move;
                    }

                } else {
                    screenX -= move;
                }
            }
        }

    }
}

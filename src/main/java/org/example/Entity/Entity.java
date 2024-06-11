package org.example.Entity;

import org.example.Game;
import org.example.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Entity {

    public int move;
    public boolean isPlayer = false;
    public BufferedImage hold, up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Game game;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String[] dialogues = new String[20];
    public int dialogueIndex = 0;
    public int screenX = 0;
    public int screenY = 0;



    public Entity(Game game) {
        this.game = game;
        solidArea = new Rectangle(16, 32, 32, 32);
    }

    public BufferedImage setup(String imageName){
        Utils utils = new Utils();
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = utils.scaleIMage(scaleImage, game.tileSize, game.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }


    public void setAction(){}
    public void update(){
        setAction();

        collisionOn = false;
        game.collisionChecker.checkTileEntity(this);
//        game.collisionChecker.checkObject(this, false);
//        game.collisionChecker.checkPlayer(this);

        if (!collisionOn) {
            if (direction.equals("up")) {
                screenY -= move;
            }
            if (direction.equals("down")) {
                screenY += move;
            }
            if (direction.equals("left")) {
                screenX -= move;
            }
            if (direction.equals("right")) {
                screenX += move;
            }
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void speak(){
//        if (dialogues[dialogueIndex] == null) {
//            dialogueIndex = 0;
//        }
//        game.ui.currentDialogue = dialogues[dialogueIndex];
//        dialogueIndex++;

        switch (game.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
//    public void update(){
//        setAction();
//        collisionOn = false;
////        game.collisionChecker.checkTile(this);
////        game.collisionChecker.checkObject(this, false);
////        game.collisionChecker.checkPlayer(this);
//
//        if (!collisionOn) {
//            if (direction.equals("up")) {
//                screenY -= speed;
//            }
//            if (direction.equals("down")) {
//                screenY += speed;
//            }
//            if (direction.equals("left")) {
//                screenX -= speed;
//            }
//            if (direction.equals("right")) {
//                screenX += speed;
//            }
//        }
//        spriteCounter++;
//        if (spriteCounter > 12) {
//            if (spriteNum == 1) {
//                spriteNum = 2;
//            } else if (spriteNum == 2) {
//                spriteNum = 1;
//            }
//            spriteCounter = 0;
//        }
//    }

    public void draw(Graphics2D g2) {


           BufferedImage image = null;
//        int screenX = worldX - game.player.screenX + game.player.screenX;
//
//        int screenY = worldY - game.player.screenY + game.player.screenY;
//
//        if (worldX + game.tileSize > game.player.screenX - game.player.screenX &&
//            worldX - game.tileSize < game.player.screenX + game.player.screenX &&
//            worldY + game.tileSize > game.player.screenY - game.player.screenY &&
//            worldY - game.tileSize < game.player.screenY + game.player.screenY) {
//            g2.drawImage (image, screenX, screenY, game.tileSize, game.tileSize, null);
//        }


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

            default:
                image = hold;
        }

        g2.drawImage(image, screenX + game.cameraX, screenY + game.cameraY, null);
    }


    @Override
    public String toString() {
        return "Entity{" +
               "screenX=" + screenX +
               ", screenY=" + screenY +
               ", move=" + move +
               ", up1=" + up1 +
               ", up2=" + up2 +
               ", down1=" + down1 +
               ", down2=" + down2 +
               ", left1=" + left1 +
               ", left2=" + left2 +
               ", right1=" + right1 +
               ", right2=" + right2 +
               ", direction='" + direction + '\'' +
               ", spriteCounter=" + spriteCounter +
               ", spriteNum=" + spriteNum +
               ", game=" + game +
               ", solidArea=" + solidArea +
               ", solidAreaDefaultX=" + solidAreaDefaultX +
               ", solidAreaDefaultY=" + solidAreaDefaultY +
               ", collisionOn=" + collisionOn +
               ", actionLockCounter=" + actionLockCounter +
               ", dialogues=" + Arrays.toString(dialogues) +
               ", dialogueIndex=" + dialogueIndex +
               '}';
    }
}


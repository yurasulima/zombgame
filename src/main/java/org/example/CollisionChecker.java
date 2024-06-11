package org.example;

import org.example.Entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.example.Utils.scaleIMage;

public class CollisionChecker {


    Game gamePanel;

    public CollisionChecker(Game gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void checkTileEntity(Entity entity) {
        int entityLeftWorldX = entity.screenX + entity.solidArea.x;
        int entityRightWorldX = entity.screenX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.screenY + entity.solidArea.y;
        int entityBottomWorldY = entity.screenY + entity.solidArea.y + entity.solidArea.height;



        int entityLeftColumn = entityLeftWorldX / gamePanel.tileSize;
        int entityRightColumn = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;
        if (entity.direction.equals("up")) {
            entityTopRow = (entityTopWorldY - entity.move) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.world.mapTileNum[entityRightColumn][entityTopRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("down")) {
            entityBottomRow = (entityBottomWorldY + entity.move) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityBottomRow];
            tileNum2 = gamePanel.world.mapTileNum[entityRightColumn][entityBottomRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("left")) {
            entityLeftColumn = (entityLeftWorldX - entity.move) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.world.mapTileNum[entityLeftColumn][entityBottomRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("right")) {
            entityRightColumn = (entityRightWorldX + entity.move) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.world.mapTileNum[entityRightColumn][entityBottomRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }

    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.screenX - gamePanel.cameraX + entity.solidArea.x;
        int entityRightWorldX = entity.screenX - gamePanel.cameraX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.screenY - gamePanel.cameraY + entity.solidArea.y;
        int entityBottomWorldY = entity.screenY - gamePanel.cameraY + entity.solidArea.y + entity.solidArea.height;



        int entityLeftColumn = entityLeftWorldX / gamePanel.tileSize;
        int entityRightColumn = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;
        if (gamePanel.keyHandler.upPressed) {
            entityTopRow = (entityTopWorldY - (entity.move / 2)) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.world.mapTileNum[entityRightColumn][entityTopRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (gamePanel.keyHandler.downPressed) {
            entityBottomRow = (entityBottomWorldY + (entity.move / 2)) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityBottomRow];
            tileNum2 = gamePanel.world.mapTileNum[entityRightColumn][entityBottomRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (gamePanel.keyHandler.leftPressed) {
            entityLeftColumn = (entityLeftWorldX - (entity.move / 2)) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.world.mapTileNum[entityLeftColumn][entityBottomRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
        if (gamePanel.keyHandler.rightPressed) {
            entityRightColumn = (entityRightWorldX + (entity.move / 2)) / gamePanel.tileSize;
            tileNum1 = gamePanel.world.mapTileNum[entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.world.mapTileNum[entityRightColumn][entityBottomRow];
            if (gamePanel.world.tile[tileNum1].collision || gamePanel.world.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }

    }

}

//    public int checkObject(Entity entity, boolean player) {
//        int index = 999;
//
//        for (int i = 0; i < gamePanel.objects.length; i++) {
//            if (gamePanel.objects[i] != null) {
//                entity.solidArea.x = entity.worldX + entity.solidArea.x;
//                entity.solidArea.y = entity.worldY + entity.solidArea.y;
//
//                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].solidArea.x + gamePanel.objects[i].worldX;
//                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].solidArea.y + gamePanel.objects[i].worldY;
//
//
//                switch (entity.direction) {
//                    case "up":
//                        entity.solidArea.y -= entity.speed;
//
//                        if (entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
//                            if (gamePanel.objects[i].collision) {
//                                entity.collisionOn = true;
//                            }
//                            if (player) {
//                                index = i;
//                            }
//                        }
//                        break;
//                    case "down":
//                        entity.solidArea.y += entity.speed;
//                        if (entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
//                            if (gamePanel.objects[i].collision) {
//                                entity.collisionOn = true;
//                            }
//                            if (player) {
//                                index = i;
//                            }
//                        }
//                        break;
//                    case "left":
//                        entity.solidArea.x -= entity.speed;
//
//                        if (entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
//                            if (gamePanel.objects[i].collision) {
//                                entity.collisionOn = true;
//                            }
//                            if (player) {
//                                index = i;
//                            }
//                        }
//                        break;
//                    case "right":
//                        entity.solidArea.x += entity.speed;
//
//                        if (entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
//                            if (gamePanel.objects[i].collision) {
//                                entity.collisionOn = true;
//                            }
//                            if (player) {
//                                index = i;
//                            }
//                        }
//                        break;
//                }
//
//
//                entity.solidArea.x = entity.solidAreaDefaultX;
//                entity.solidArea.y = entity.solidAreaDefaultY;
//
//                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].solidAreaDefaultX;
//                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].solidAreaDefaultY;
//            }
//        }
//
//        return index;
//    }
//

//        public int checkEntity (Entity entity, Entity[] target){
//
//            int index = 999;

//        for (int i = 0; i < target.length; i++) {
//            if (gamePanel.npc[i] != null) {
//                entity.solidArea.x = entity.worldX + entity.solidArea.x;
//                entity.solidArea.y = entity.worldY + entity.solidArea.y;
//
//                target[i].solidArea.x = target[i].solidArea.x + target[i].worldX;
//                target[i].solidArea.y = target[i].solidArea.y + target[i].worldY;
//
//
//                switch (entity.direction) {
//                    case "up":
//                        entity.solidArea.y -= entity.speed;
//
//                        if (entity.solidArea.intersects(target[i].solidArea)) {
//
//                            entity.collisionOn = true;
//                            index = i;
//
//                        }
//                        break;
//                    case "down":
//                        entity.solidArea.y += entity.speed;
//                        if (entity.solidArea.intersects(target[i].solidArea)) {
//
//                            entity.collisionOn = true;
//                            index = i;
//
//                        }
//                        break;
//                    case "left":
//                        entity.solidArea.x -= entity.speed;
//
//                        if (entity.solidArea.intersects(target[i].solidArea)) {
//                            entity.collisionOn = true;
//                            index = i;
//
//                        }
//                        break;
//                    case "right":
//                        entity.solidArea.x += entity.speed;
//
//                        if (entity.solidArea.intersects(target[i].solidArea)) {
//                            entity.collisionOn = true;
//                            index = i;
//                        }
//                        break;
//                }
//
//
//                entity.solidArea.x = entity.solidAreaDefaultX;
//                entity.solidArea.y = entity.solidAreaDefaultY;
//
//                target[i].solidArea.x = target[i].solidAreaDefaultX;
//                target[i].solidArea.y = target[i].solidAreaDefaultY;
//            }
//        }

//            return index;
//        }

//    public void checkPlayer(Entity entity){
//        entity.solidArea.x = entity.worldX + entity.solidArea.x;
//        entity.solidArea.y = entity.worldY + entity.solidArea.y;
//
//        gamePanel.player.solidArea.x = gamePanel.player.solidArea.x + gamePanel.player.worldX;
//        gamePanel.player.solidArea.y = gamePanel.player.solidArea.y + gamePanel.player.worldY;
//
//
//        switch (entity.direction) {
//            case "up":
//                entity.solidArea.y -= entity.speed;
//
//                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//            case "down":
//                entity.solidArea.y += entity.speed;
//                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//            case "left":
//                entity.solidArea.x -= entity.speed;
//
//                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//            case "right":
//                entity.solidArea.x += entity.speed;
//
//                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//        }
//
//
//        entity.solidArea.x = entity.solidAreaDefaultX;
//        entity.solidArea.y = entity.solidAreaDefaultY;
//
//        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
//        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
//    }
//    }
















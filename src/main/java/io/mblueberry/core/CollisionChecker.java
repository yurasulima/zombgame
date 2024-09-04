package io.mblueberry.core;

import io.mblueberry.Game;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.block.Block;

import java.util.ArrayList;

public class CollisionChecker {


    Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }


    public void checkTileEntity(Entity entity) {
        int entityLeftWorldX = entity.screenX + entity.solidArea.x;
        int entityRightWorldX = entity.screenX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.screenY + entity.solidArea.y;
        int entityBottomWorldY = entity.screenY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftColumn = entityLeftWorldX / game.tileSize;
        int entityRightColumn = entityRightWorldX / game.tileSize;
        int entityTopRow = entityTopWorldY / game.tileSize;
        int entityBottomRow = entityBottomWorldY / game.tileSize;

        Block block1, block2;
        if (entity.direction.equals("up")) {
            entityTopRow = (entityTopWorldY - entity.move) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityTopRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityRightColumn][entityTopRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("down")) {
            entityBottomRow = (entityBottomWorldY + entity.move) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityBottomRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("left")) {
            entityLeftColumn = (entityLeftWorldX - entity.move) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityTopRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("right")) {
            entityRightColumn = (entityRightWorldX + entity.move) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityTopRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }

    }

    public Block checkTile(Entity entity) {
        int entityLeftWorldX = entity.screenX - game.cameraX + entity.solidArea.x;
        int entityRightWorldX = entity.screenX - game.cameraX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.screenY - game.cameraY + entity.solidArea.y;
        int entityBottomWorldY = entity.screenY - game.cameraY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftColumn = entityLeftWorldX / game.tileSize;
        int entityRightColumn = entityRightWorldX / game.tileSize;
        int entityTopRow = entityTopWorldY / game.tileSize;
        int entityBottomRow = entityBottomWorldY / game.tileSize;

        Block block1, block2;
        if (game.keyHandler.upPressed) {
            entityTopRow = (entityTopWorldY - (entity.move - 2)) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityTopRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityRightColumn][entityTopRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block1;
            }
        }
        if (game.keyHandler.downPressed) {
            entityBottomRow = (entityBottomWorldY + (entity.move - 2)) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityBottomRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block1;
            }
        }
        if (game.keyHandler.leftPressed) {
            entityLeftColumn = (entityLeftWorldX - (entity.move - 2)) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityTopRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block1;
            }
        }
        if (game.keyHandler.rightPressed) {
            entityRightColumn = (entityRightWorldX + (entity.move - 2)) / game.tileSize;
            block1 = game.world.mapTileNum[entity.worldLayer][entityLeftColumn][entityTopRow];
            block2 = game.world.mapTileNum[entity.worldLayer][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block2;
            }
        }

        return null;

    }


    public Entity checkEntity(Entity entity, ArrayList<Entity> target) {

        Entity entity1 = null;

        for (int i = 0; i < target.size(); i++) {
            if (game.world.entities.get(i) != null) {
                entity.solidArea.x = entity.screenX + entity.solidArea.x;
                entity.solidArea.y = entity.screenY + entity.solidArea.y;

                target.get(i).solidArea.x = target.get(i).solidArea.x + target.get(i).screenX;
                target.get(i).solidArea.y = target.get(i).solidArea.y + target.get(i).screenY;

                if (entity.isPlayer) {
                    entity.solidArea.x -= game.cameraX;
                    entity.solidArea.y -= game.cameraY;
                }
                if (target.get(i).isPlayer) {
                    target.get(i).solidArea.x -= game.cameraX;
                    target.get(i).solidArea.y -= game.cameraY;
                }

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.move;
                        break;
                    case "down":
                        entity.solidArea.y += entity.move;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.move;
                        break;
                    case "right":
                        entity.solidArea.x += entity.move;
                        break;
                }

                Entity targetEnt = target.get(i);
                if (entity != targetEnt) {
                    if (entity.solidArea.intersects(target.get(i).solidArea)) {
                        entity.collisionOn = true;
                        entity1 = targetEnt;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                target.get(i).solidArea.x = target.get(i).solidAreaDefaultX;
                target.get(i).solidArea.y = target.get(i).solidAreaDefaultY;
            }
        }

        return entity1;
    }

}
//    public int checkObject(Entity entity, boolean player) {
//        int index = 999;
//
//        for (int i = 0; i < game.objects.length; i++) {
//            if (game.objects[i] != null) {
//                entity.solidArea.x = entity.worldX + entity.solidArea.x;
//                entity.solidArea.y = entity.worldY + entity.solidArea.y;
//
//                game.objects[i].solidArea.x = game.objects[i].solidArea.x + game.objects[i].worldX;
//                game.objects[i].solidArea.y = game.objects[i].solidArea.y + game.objects[i].worldY;
//
//
//                switch (entity.direction) {
//                    case "up":
//                        entity.solidArea.y -= entity.speed;
//
//                        if (entity.solidArea.intersects(game.objects[i].solidArea)) {
//                            if (game.objects[i].collision) {
//                                entity.collisionOn = true;
//                            }
//                            if (player) {
//                                index = i;
//                            }
//                        }
//                        break;
//                    case "down":
//                        entity.solidArea.y += entity.speed;
//                        if (entity.solidArea.intersects(game.objects[i].solidArea)) {
//                            if (game.objects[i].collision) {
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
//                        if (entity.solidArea.intersects(game.objects[i].solidArea)) {
//                            if (game.objects[i].collision) {
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
//                        if (entity.solidArea.intersects(game.objects[i].solidArea)) {
//                            if (game.objects[i].collision) {
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
//                game.objects[i].solidArea.x = game.objects[i].solidAreaDefaultX;
//                game.objects[i].solidArea.y = game.objects[i].solidAreaDefaultY;
//            }
//        }
//
//        return index;
//    }
//


//    public void checkPlayer(Entity entity){
//        entity.solidArea.x = entity.worldX + entity.solidArea.x;
//        entity.solidArea.y = entity.worldY + entity.solidArea.y;
//
//        game.player.solidArea.x = game.player.solidArea.x + game.player.worldX;
//        game.player.solidArea.y = game.player.solidArea.y + game.player.worldY;
//
//
//        switch (entity.direction) {
//            case "up":
//                entity.solidArea.y -= entity.speed;
//
//                if (entity.solidArea.intersects(game.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//            case "down":
//                entity.solidArea.y += entity.speed;
//                if (entity.solidArea.intersects(game.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//            case "left":
//                entity.solidArea.x -= entity.speed;
//
//                if (entity.solidArea.intersects(game.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//            case "right":
//                entity.solidArea.x += entity.speed;
//
//                if (entity.solidArea.intersects(game.player.solidArea)) {
//                    entity.collisionOn = true;
//                }
//                break;
//        }
//
//
//        entity.solidArea.x = entity.solidAreaDefaultX;
//        entity.solidArea.y = entity.solidAreaDefaultY;
//
//        game.player.solidArea.x = game.player.solidAreaDefaultX;
//        game.player.solidArea.y = game.player.solidAreaDefaultY;
//    }
//    }
















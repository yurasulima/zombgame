package io.mblueberry.core;

import io.mblueberry.Game;
import io.mblueberry.core.object.block.Block;
import io.mblueberry.core.object.entity.Direction;
import io.mblueberry.core.object.entity.Entity;
import io.mblueberry.core.object.entity.EntityOld;

import java.awt.*;
import java.util.ArrayList;

import static io.mblueberry.Const.WORLD_LAYER_WORLD;
import static io.mblueberry.Game.TILE_SIZE;

public class CollisionChecker {


    Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }

    public boolean checkCollision(Rectangle player, Direction direction) {
        // Отримуємо позицію гравця
        int playerX = player.x;
        int playerY = player.y;
        System.out.println("playerY = " + playerY);
        System.out.println("playerX = " + playerX);
        // Визначаємо координати перевірки залежно від напряму
        int checkX = playerX;
        int checkY = playerY;
        Block block1 = null;
        Block block2 = null;
        if (direction == Direction.UP) {
            checkY -= 6;
            block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
            checkX += player.width - 6;
            block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        }
        if (direction == Direction.DOWN) {
            checkY += player.height + 6;
            block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
            checkX += player.width;
            block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        }
        if (direction == Direction.RIGHT) {
            checkX += player.width + 6;
            block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
            checkY += player.height - 6;
            block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
            System.out.println(block2);

        }
        if (direction == Direction.LEFT) {

            checkX -= 6;
            block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
            checkY += player.height;
            block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        }

//        switch (direction) {
//            case DOWN -> checkY += player.height;
//            case RIGHT -> checkX += player.width;
//            case HOLD, IDLE -> {
//                return false;
//            }
//        }

        game.player.lockedDirection = Direction.LOCKED;

        if ((block1 != null || block2 != null) && (block1.solidArea != null && block1.isCollision()) || (block2.solidArea != null && block2.isCollision())) {
            game.world.testColl1 = block1;
            game.world.testColl2 = block2;
            boolean intersects = player.intersects(block1.solidArea);
            boolean intersects1 = player.intersects(block2.solidArea);
            if (intersects || intersects) {
                if (direction == Direction.UP) {
                    game.player.y += 6;
                    game.player.lockedDirection = Direction.UP;
                }
                if (direction == Direction.DOWN) {
                    game.player.y -= 6;
                    game.player.lockedDirection = Direction.DOWN;
                }
                if (direction == Direction.RIGHT) {
                    game.player.x -= 6;
                    game.player.lockedDirection = Direction.RIGHT;
                }
                if (direction == Direction.LEFT) {
                    game.player.x += 6;
                    game.player.lockedDirection = Direction.LEFT;
                }
            }

            return intersects || intersects1;// player.intersects(blockAhead.solidArea);
        }


        return false;
    }

    public void checkTileEntity(Entity entity) {
        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;

        int entityTopWorldY = entity.y + entity.solidArea.y;
        int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;


        int entityLeftColumn = entityLeftWorldX / TILE_SIZE;
        int entityRightColumn = entityRightWorldX / TILE_SIZE;

        int entityTopRow = entityTopWorldY / TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / TILE_SIZE;

        Block block1, block2;
        if (entity.direction == Direction.UP) {
            entityTopRow = (entityTopWorldY - entity.speed) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityTopRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityRightColumn][entityTopRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction == Direction.DOWN) {
            entityBottomRow = (entityBottomWorldY + entity.speed) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityBottomRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction == Direction.LEFT) {
            entityLeftColumn = (entityLeftWorldX - entity.speed) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityTopRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
        if (entity.direction == Direction.RIGHT) {
            entityRightColumn = (entityRightWorldX + entity.speed) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityTopRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
            }
        }
    }

    public Block checkTile(Entity entity) {
        int entityLeftWorldX = entity.x - game.cameraX + entity.solidArea.x;
        int entityRightWorldX = entity.x - game.cameraX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.y - game.cameraY + entity.solidArea.y;
        int entityBottomWorldY = entity.y - game.cameraY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftColumn = entityLeftWorldX / TILE_SIZE;
        int entityRightColumn = entityRightWorldX / TILE_SIZE;
        int entityTopRow = entityTopWorldY / TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / TILE_SIZE;

        Block block1, block2;
        if (game.keyHandler.upPressed) {
            entityTopRow = (entityTopWorldY - (entity.speed)) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityTopRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityRightColumn][entityTopRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block1;
            }
        }
        if (game.keyHandler.downPressed) {
            entityBottomRow = (entityBottomWorldY + (entity.speed)) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityBottomRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block1;
            }
        }
        if (game.keyHandler.leftPressed) {
            entityLeftColumn = (entityLeftWorldX - (entity.speed)) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityTopRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block1;
            }
        }
        if (game.keyHandler.rightPressed) {
            entityRightColumn = (entityRightWorldX + (entity.speed)) / TILE_SIZE;
            block1 = game.world.map[WORLD_LAYER_WORLD][entityLeftColumn][entityTopRow];
            block2 = game.world.map[WORLD_LAYER_WORLD][entityRightColumn][entityBottomRow];
            if (block1.isCollision() || block2.isCollision()) {
                entity.collisionOn = true;
                return block2;
            }
        }

        return null;

    }


    public EntityOld checkEntity(EntityOld entityOld, ArrayList<EntityOld> target) {

        EntityOld entityOld1 = null;

        for (int i = 0; i < target.size(); i++) {
            if (game.world.entities.get(i) != null) {
                entityOld.solidArea.x = entityOld.screenX + entityOld.solidArea.x;
                entityOld.solidArea.y = entityOld.screenY + entityOld.solidArea.y;

                target.get(i).solidArea.x = target.get(i).solidArea.x + target.get(i).screenX;
                target.get(i).solidArea.y = target.get(i).solidArea.y + target.get(i).screenY;

                if (entityOld.type.equals("player")) {
                    entityOld.solidArea.x -= game.cameraX;
                    entityOld.solidArea.y -= game.cameraY;
                }
                if (target.get(i).type.equals("player")) {
                    target.get(i).solidArea.x -= game.cameraX;
                    target.get(i).solidArea.y -= game.cameraY;
                }

                switch (entityOld.direction) {
                    case UP:
                        entityOld.solidArea.y -= entityOld.speed;
                        break;
                    case DOWN:
                        entityOld.solidArea.y += entityOld.speed;
                        break;
                    case LEFT:
                        entityOld.solidArea.x -= entityOld.speed;
                        break;
                    case RIGHT:
                        entityOld.solidArea.x += entityOld.speed;
                        break;
                }

                EntityOld targetEnt = target.get(i);
                if (entityOld != targetEnt) {
                    if (entityOld.solidArea.intersects(target.get(i).solidArea)) {
                        entityOld.collisionOn = true;
                        entityOld1 = targetEnt;
                    }
                }

                entityOld.solidArea.x = entityOld.solidAreaDefaultX;
                entityOld.solidArea.y = entityOld.solidAreaDefaultY;

                target.get(i).solidArea.x = target.get(i).solidAreaDefaultX;
                target.get(i).solidArea.y = target.get(i).solidAreaDefaultY;
            }
        }

        return entityOld1;
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
















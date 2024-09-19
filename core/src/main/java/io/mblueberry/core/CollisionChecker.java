package io.mblueberry.core;

import io.mblueberry.Game;
import io.mblueberry.core.object.block.Block;
import io.mblueberry.core.object.entity.Direction;
import io.mblueberry.core.object.entity.Entity;
import io.mblueberry.core.object.entity.EntityOld;
import io.mblueberry.core.object.entity.Player;

import java.awt.*;
import java.util.ArrayList;

import static io.mblueberry.Const.WORLD_LAYER_WORLD;
import static io.mblueberry.Game.TILE_SIZE;

public class CollisionChecker {


    Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkPlayerCollision(Player player) {
        game.world.collisionPlayer.clear();
        Rectangle tempSolidArea = player.entityCollision;


        if (game.player.upPressed) {
           checkUp(player, tempSolidArea);
        }
        if (game.player.rightPressed) {
            checkRight(player, tempSolidArea);
        }
        if (game.player.leftPressed) {
            checkLeft(player, tempSolidArea);

        }
        if (game.player.downPressed) {
            checkDown(player, tempSolidArea);
        }
    }

    private boolean checkDown(Player player, Rectangle tempSolidArea) {
        int checkX;
        Block block2;
        Block block1;
        int checkY;
        checkX = player.entityCollision.x;
        checkY = player.entityCollision.y;

        checkY  += player.entityCollision.height + 6;
        block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        checkX += player.entityCollision.width;
        block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);

        Rectangle blockCollision1 = new Rectangle();
        Rectangle blockCollision2 = new Rectangle();
        blockCollision1.x = block1.getX() * TILE_SIZE + game.cameraX + block1.blockCollision.x;
        blockCollision1.y = block1.getY() * TILE_SIZE + game.cameraY + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;
        blockCollision2.x = block2.getX() * TILE_SIZE + game.cameraX + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + game.cameraY + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;
        if (block1 != null) {
            if (block1.isCollision() && checkCollDown(tempSolidArea, blockCollision1)) {
                if (!player.lockedDirections.contains(Direction.DOWN)) {
                    player.lockedDirections.add(Direction.DOWN);
                    game.world.collisionPlayer.add(block1);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.DOWN);
            }
        }
        if (block2 != null) {
            if (block2.isCollision() && checkCollDown(tempSolidArea, blockCollision2)) {
                if (!player.lockedDirections.contains(Direction.DOWN)) {
                    player.lockedDirections.add(Direction.DOWN);
                    game.world.collisionPlayer.add(block2);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.DOWN);
            }
        }
        return false;
    }

    private boolean checkLeft(Player player, Rectangle tempSolidArea) {
        Block block2;
        int checkX;
        Block block1;
        int checkY;
        checkX = player.entityCollision.x;
        checkY = player.entityCollision.y;

        checkX -= 6;
        block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        checkY += player.entityCollision.height;
        block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        Rectangle blockCollision1 = new Rectangle();
        Rectangle blockCollision2 = new Rectangle();
        blockCollision1.x = block1.getX() * TILE_SIZE + game.cameraX + block1.blockCollision.x;
        blockCollision1.y = block1.getY() * TILE_SIZE + game.cameraY + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;
        blockCollision2.x = block2.getX() * TILE_SIZE + game.cameraX + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + game.cameraY + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;

        if (block1 != null) {
            if (block1.isCollision() && checkCollLeft(tempSolidArea, blockCollision1)) {
                if (!player.lockedDirections.contains(Direction.LEFT)) {
                    game.world.collisionPlayer.add(block1);
                    player.lockedDirections.add(Direction.LEFT);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.LEFT);
            }
        }
        if (block2 != null) {
            if (block2.isCollision() && checkCollLeft(tempSolidArea, blockCollision2)) {
                if (!player.lockedDirections.contains(Direction.LEFT)) {
                    game.world.collisionPlayer.add(block2);
                    player.lockedDirections.add(Direction.LEFT);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.LEFT);
            }
        }
        return false;
    }

    private boolean checkRight(Player player, Rectangle tempSolidArea) {
        Block block1;
        Block block2;
        int checkY;
        int checkX;
        checkX = player.entityCollision.x;
        checkY = player.entityCollision.y;
        checkX += player.entityCollision.width + 6;
        block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        checkY += player.entityCollision.height;
        block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(checkX);
        System.out.println(checkY);
        System.out.println(block2.getDisplayName());

        Rectangle blockCollision1 = new Rectangle();
        Rectangle blockCollision2 = new Rectangle();
        blockCollision1.x = (block1.getX() * TILE_SIZE) + game.cameraX + block1.blockCollision.x;
        blockCollision1.y = (block1.getY() * TILE_SIZE) + game.cameraY + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;


        blockCollision2.x = block2.getX() * TILE_SIZE + game.cameraX + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + game.cameraY + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;

        if (block1 != null) {
            if (block1.isCollision() && checkCollRight(tempSolidArea, blockCollision1)) {
              
                if (!player.lockedDirections.contains(Direction.RIGHT)) {
                    game.world.collisionPlayer.add(block1);
                    player.lockedDirections.add(Direction.RIGHT);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.RIGHT);
            }
        }
        if (block2 != null) {
            
            if (block2.isCollision() && checkCollRight(tempSolidArea, blockCollision2)) {

                if (!player.lockedDirections.contains(Direction.RIGHT)) {
                    game.world.collisionPlayer.add(block2);
                    player.lockedDirections.add(Direction.RIGHT);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.RIGHT);
            }
        }
        return false;
    }

    private boolean checkUp(Player player, Rectangle tempSolidArea) {

        int checkX = player.entityCollision.x;
        int checkY = player.entityCollision.y;
        Block block1;
        Block block2;
        checkY -= player.entityCollision.height;
        block1 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);
        checkX += player.entityCollision.width;
        block2 = game.world.getTileScreen(checkX - game.cameraX, checkY - game.cameraY);

        Rectangle blockCollision1 = new Rectangle();
        blockCollision1.x = block1.getX() * TILE_SIZE + game.cameraX + block1.blockCollision.x;
        blockCollision1.y = block1.getY() * TILE_SIZE + game.cameraY + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;
        Rectangle blockCollision2 = new Rectangle();
        blockCollision2.x = block2.getX() * TILE_SIZE + game.cameraX + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + game.cameraY + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;

        if (block1 != null) {
            if (block1.isCollision() && checkCollUp(tempSolidArea, blockCollision1)) {
                if (!player.lockedDirections.contains(Direction.UP)) {
                    player.lockedDirections.add(Direction.UP);
                    game.world.collisionPlayer.add(block1);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.UP);
            }
        }
        if (block2 != null) {
            if (block2.isCollision() && checkCollUp(tempSolidArea, blockCollision2)) {
                if (!player.lockedDirections.contains(Direction.UP)) {
                    player.lockedDirections.add(Direction.UP);
                    game.world.collisionPlayer.add(block2);
                }
                return true;
            } else {
                player.lockedDirections.remove(Direction.UP);
            }
        }
        return false;
    }

    private boolean checkCollUp(Rectangle tempSolidArea, Rectangle blockCollision1) {
        int upY = blockCollision1.y + blockCollision1.height;
        int i = tempSolidArea.y - upY;
        if ( i < 7) {
            if ((tempSolidArea.x + tempSolidArea.width) < blockCollision1.x) {
                return false;
            }
            if (tempSolidArea.x > blockCollision1.x + blockCollision1.width) {
                return false;
            }
            return true;
        }
        return false;
    }
    private boolean checkCollDown(Rectangle tempSolidArea, Rectangle blockCollision1) {
        int upY = blockCollision1.y;
        int i = tempSolidArea.y + tempSolidArea.height - upY;
        System.out.println("i = " + i);
        if ( i > -15 && i < 0) {
            if ((tempSolidArea.x + tempSolidArea.width) < blockCollision1.x) {
                return false;
            }
            if (tempSolidArea.x > blockCollision1.x + blockCollision1.width) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkCollRight(Rectangle tempSolidArea, Rectangle blockCollision1) {
     //   game.world.drawable = tempSolidArea;
        int rX = blockCollision1.x;
        int i = tempSolidArea.x + tempSolidArea.width - rX;
        if ( i > -7 && i < 0) {
            if ((tempSolidArea.y + tempSolidArea.height) < blockCollision1.y) {
                return false;
            }
            if (tempSolidArea.y > blockCollision1.y + blockCollision1.height) {
                return false;
            }

            return true;
        }
        return false;
    }
    private boolean checkCollLeft(Rectangle tempSolidArea, Rectangle blockCollision1) {
        int rX = blockCollision1.x + blockCollision1.width;
        int i =  rX - tempSolidArea.x;
        if ( i > -7 && i < 7) {


            if ((tempSolidArea.y + tempSolidArea.height) < blockCollision1.y) {
                return false;
            }
            if (tempSolidArea.y > blockCollision1.y + blockCollision1.height) {
                return false;
            }


            return true;
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
















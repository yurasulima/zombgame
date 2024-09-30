package io.mblueberry;

import io.mblueberry.object.block.Block;
import io.mblueberry.object.entity.Direction;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.Player;

import java.awt.*;

import static io.mblueberry.Const.TILE_SIZE;

public class CollisionChecker {
    Game game;

    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkEntityCollision(Entity entity) {
        Rectangle tempSolidArea = entity.entityCollision;
        Direction direction = entity.direction;
        if (direction.equals(Direction.RIGHT)) {
            checkRightEntity(entity, tempSolidArea);
        }
        if (direction.equals(Direction.LEFT)) {
            checkLeftEntity(entity, tempSolidArea);
        }
        if (direction.equals(Direction.UP)) {
            checkUpEntity(entity, tempSolidArea);
        }
        if (direction.equals(Direction.DOWN)) {
            checkDownEntity(entity, tempSolidArea);
        }
    }

    private boolean checkDownEntity(Entity entity, Rectangle tempSolidArea) {
        int checkX;
        int checkY;
        Block block2;
        Block block1;
        checkX = entity.entityCollision.x;
        checkY = entity.entityCollision.y;

        checkY += entity.entityCollision.height + entity.speed;
        block1 = game.world.getTileScreen(checkX, checkY);
        checkX += entity.entityCollision.width;
        block2 = game.world.getTileScreen(checkX, checkY);

        Rectangle blockCollision1 = new Rectangle();
        Rectangle blockCollision2 = new Rectangle();
        blockCollision1.x = block1.getX() * TILE_SIZE + block1.blockCollision.x;
        blockCollision1.y = block1.getY() * TILE_SIZE + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;
        blockCollision2.x = block2.getX() * TILE_SIZE + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;
        if (block1 != null) {
            System.out.println("block1 = " + block1);
            if (block1.isCollision() && checkCollDown(tempSolidArea, blockCollision1)) {
                if (!entity.lockedDirections.contains(Direction.DOWN)) {
                    entity.lockedDirections.add(Direction.DOWN);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.DOWN);
            }
        }
        if (block2 != null) {
            System.out.println("block2 = " + block2);
            if (block2.isCollision() && checkCollDown(tempSolidArea, blockCollision2)) {
                if (!entity.lockedDirections.contains(Direction.DOWN)) {
                    entity.lockedDirections.add(Direction.DOWN);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.DOWN);
            }
        }
        return false;
    }

    private boolean checkLeftEntity(Entity entity, Rectangle tempSolidArea) {
        Block block2;
        int checkX;
        Block block1;
        int checkY;
        checkX = entity.entityCollision.x;
        checkY = entity.entityCollision.y;
        checkX -= entity.speed;
        block1 = game.world.getTileScreen(checkX, checkY);
        checkY += entity.entityCollision.height;
        block2 = game.world.getTileScreen(checkX, checkY);
        Rectangle blockCollision1 = new Rectangle();
        Rectangle blockCollision2 = new Rectangle();
        blockCollision1.x = block1.getX() * TILE_SIZE + block1.blockCollision.x;
        blockCollision1.y = block1.getY() * TILE_SIZE + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;
        blockCollision2.x = block2.getX() * TILE_SIZE + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;

        if (block1 != null) {
            if (block1.isCollision() && checkCollLeft(tempSolidArea, blockCollision1)) {
                if (!entity.lockedDirections.contains(Direction.LEFT)) {
                    entity.lockedDirections.add(Direction.LEFT);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.LEFT);
            }
        }
        if (block2 != null) {
            if (block2.isCollision() && checkCollLeft(tempSolidArea, blockCollision2)) {
                if (!entity.lockedDirections.contains(Direction.LEFT)) {
                    entity.lockedDirections.add(Direction.LEFT);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.LEFT);
            }
        }
        return false;
    }

    private boolean checkRightEntity(Entity entity, Rectangle tempSolidArea) {
        Block block1;
        Block block2;
        int checkY;
        int checkX;
        checkX = entity.entityCollision.x;
        checkY = entity.entityCollision.y;
        checkX += entity.entityCollision.width + entity.speed;
        block1 = game.world.getTileScreen(checkX, checkY);
        checkY += entity.entityCollision.height;
        block2 = game.world.getTileScreen(checkX, checkY);


        Rectangle blockCollision1 = new Rectangle();
        Rectangle blockCollision2 = new Rectangle();
        blockCollision1.x = (block1.getX() * TILE_SIZE) + block1.blockCollision.x;
        blockCollision1.y = (block1.getY() * TILE_SIZE) + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;


        blockCollision2.x = block2.getX() * TILE_SIZE + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;

        if (block1 != null) {
            if (block1.isCollision() && checkCollRight(tempSolidArea, blockCollision1)) {

                if (!entity.lockedDirections.contains(Direction.RIGHT)) {
                    entity.lockedDirections.add(Direction.RIGHT);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.RIGHT);
            }
        }
        if (block2 != null) {

            if (block2.isCollision() && checkCollRight(tempSolidArea, blockCollision2)) {

                if (!entity.lockedDirections.contains(Direction.RIGHT)) {
                    entity.lockedDirections.add(Direction.RIGHT);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.RIGHT);
            }
        }
        return false;
    }

    private boolean checkUpEntity(Entity entity, Rectangle tempSolidArea) {

        int checkX = entity.entityCollision.x;
        int checkY = entity.entityCollision.y;
        Block block1;
        Block block2;
        checkY -= entity.entityCollision.height;
        block1 = game.world.getTileScreen(checkX, checkY);
        checkX += entity.entityCollision.width;
        block2 = game.world.getTileScreen(checkX, checkY);

        Rectangle blockCollision1 = new Rectangle();
        blockCollision1.x = block1.getX() * TILE_SIZE + block1.blockCollision.x;
        blockCollision1.y = block1.getY() * TILE_SIZE + block1.blockCollision.y;
        blockCollision1.width = block1.blockCollision.width;
        blockCollision1.height = block1.blockCollision.height;
        Rectangle blockCollision2 = new Rectangle();
        blockCollision2.x = block2.getX() * TILE_SIZE + block2.blockCollision.x;
        blockCollision2.y = block2.getY() * TILE_SIZE + block2.blockCollision.y;
        blockCollision2.width = block2.blockCollision.width;
        blockCollision2.height = block2.blockCollision.height;

        if (block1 != null) {
            if (block1.isCollision() && checkCollUp(tempSolidArea, blockCollision1)) {
                if (!entity.lockedDirections.contains(Direction.UP)) {
                    entity.lockedDirections.add(Direction.UP);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.UP);
            }
        }
        if (block2 != null) {
            if (block2.isCollision() && checkCollUp(tempSolidArea, blockCollision2)) {
                if (!entity.lockedDirections.contains(Direction.UP)) {
                    entity.lockedDirections.add(Direction.UP);
                }
                return true;
            } else {
                entity.lockedDirections.remove(Direction.UP);
            }
        }
        return false;
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

        checkY += player.entityCollision.height + 6;
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
        if (i < 7) {
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
        if (i > -15 && i < 0) {
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
        if (i > -7 && i < 0) {
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
        int i = rX - tempSolidArea.x;
        if (i > -7 && i < 7) {


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
}

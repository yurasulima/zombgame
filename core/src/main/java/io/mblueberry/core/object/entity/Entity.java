package io.mblueberry.core.object.entity;

import io.mblueberry.Game;
import io.mblueberry.core.container.Container;
import io.mblueberry.core.anim.Animation;
import io.mblueberry.core.object.item.GameObject;
import lombok.Data;

import java.awt.*;
import java.util.HashMap;

import static io.mblueberry.Game.DEBUG;
import static io.mblueberry.Game.TILE_SIZE;

@Data
public class Entity {

    public String type = "";
    public String username = "Entity";
    public Direction direction = Direction.IDLE;
    public int x = 0;
    public int y = 0;
    public Game game;
    public boolean onPath = false;
    public Container inventory;
    public HashMap<String, Animation> animations = new HashMap<>(); // Changed to HashMap for faster access

    public int maxLife;
    public int life;

    public int speed;
    public Rectangle solidArea;
    public Rectangle entityCollision;

    public GameObject rightHand;
    public String currentState;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(Game game) {
        this.game = game;
        inventory = new Container(game, 3);
        speed = 1;
        rightHand = inventory.get(0);
        currentState = "idle";
        solidArea = new Rectangle(16, 32, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void moveTo(int targetX, int targetY) {

        int dx = targetX - x;
        int dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {

            double stepX = (dx / distance) * speed;
            double stepY = (dy / distance) * speed;

            x += stepX;
            y += stepY;
        } else {
            x = targetX;
            y = targetY;
        }

    }

    public void searchPath(int toX, int toY) {
        int startX = (x + solidArea.x) / TILE_SIZE;
        int startY = (y + solidArea.y) / TILE_SIZE;
        game.pathFinder.setNodes(startX, startY, toX, toY, this);
        if (game.pathFinder.search()) {
            // System.out.println("Path finded");
            int nextX = game.pathFinder.pathList.get(0).x * TILE_SIZE;
            int nextY = game.pathFinder.pathList.get(0).y * TILE_SIZE;
            int enLeftX = x + solidArea.x;
            int enRightX = x + solidArea.x + solidArea.width;
            int enTopY = y + solidArea.y;
            int enBottomY = y + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE) {
                //     System.out.println("UP");
                direction = Direction.UP;
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE) {
                //   System.out.println("DOWN");
                direction = Direction.DOWN;
            } else if (enTopY >= nextY && enBottomY < nextX + TILE_SIZE) {
                if (enLeftX > nextX) {
                    //          System.out.println("LEFT");
                    direction = Direction.LEFT;
                }
                if (enLeftX < nextX) {
                    //      System.out.println("RIGHT");
                    direction = Direction.RIGHT;
                }

            } else if (enTopY > nextY && enLeftX > nextX) {
                //UP or LEFT
                //    System.out.println("UP or LEFT");
                direction = Direction.UP;
                checkCollision();
                if (true) {
                    //         System.out.println("UP or - LEFT");
                    direction = Direction.LEFT;
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                //UP or RIGHT
                //   System.out.println("UP or RIGHT");
                direction = Direction.UP;
                checkCollision();
                if (true) {
                    direction = Direction.RIGHT;
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                //DOWN or LEFT
                //  System.out.println("DOWN or LEFT");
                direction = Direction.DOWN;
                checkCollision();
                if (true) {
                    direction = Direction.LEFT;
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                //DOWN or RIGHT
                //    System.out.println("DOWN or RIGHT");
                direction = Direction.DOWN;
                checkCollision();
                if (true) {
                    direction = Direction.RIGHT;
                }
            }

//            int nextX1 = game.pathFinder.pathList.get(0).x;
//            int nextY1 = game.pathFinder.pathList.get(0).y;
//            if (nextX1 == toX && nextY1 == toY) {
//                onPath = false;
//            }
        } else {
        }

    }

    private void checkCollision() {
        collisionOn = false;
        //  game.collisionChecker.checkTileEntity(this);
    }

    public void update() {
        Animation currentAnimation = animations.get(currentState);
        if (currentAnimation != null) {
            currentAnimation.update();
        }

        solidArea.x = x + game.cameraX;
        solidArea.y= y + game.cameraY;

    }


    public void draw(Graphics2D g2) {
        Animation currentAnimation = animations.get(currentState);
        if (currentAnimation != null) {
            g2.drawImage(currentAnimation.getCurrentFrame(), x + game.cameraX, y + game.cameraY, null);
        }

        if (rightHand != null) {
            rightHand.draw(g2, this, game);
        }
        if (DEBUG) {
            g2.draw(solidArea);
        }
    }

    public void setAction() {
    }
}

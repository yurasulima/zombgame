package io.mblueberry.object.entity;

import io.mblueberry.Game;
import io.mblueberry.container.Container;
import io.mblueberry.anim.Animation;
import io.mblueberry.object.item.GameObject;
import io.mblueberry.object.item.Item;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static io.mblueberry.Const.DEBUG;
import static io.mblueberry.Const.TILE_SIZE;

@Data
public class Entity {

    public List<Direction> lockedDirections = new ArrayList<>();
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
    public boolean alive = true;

    public int speed;
    public Rectangle solidArea;
    public Rectangle entityCollision = new Rectangle(0,0,64,64);

    public GameObject rightHand;
    public String currentState;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    private boolean canTakeDamage = true; // Чи можна отримати пошкодження
    private long invincibilityStartTime = 0; // Час початку недоторканості
    private long invincibilityDuration = 1000; // Тривалість недоторканості в мілісекундах (1 секунда)


    public Entity(Game game) {
        this.game = game;
        inventory = new Container(game, 3);
        speed = 1;
        alive = true;
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
            int nextX = game.pathFinder.pathList.get(0).x * TILE_SIZE;
            int nextY = game.pathFinder.pathList.get(0).y * TILE_SIZE;
            int enLeftX = x + solidArea.x;
            int enRightX = x + solidArea.x + solidArea.width;
            int enTopY = y + solidArea.y;
            int enBottomY = y + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE) {
                direction = Direction.UP;
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE) {
                direction = Direction.DOWN;
            } else if (enTopY >= nextY && enBottomY < nextX + TILE_SIZE) {
                if (enLeftX > nextX) {
                    direction = Direction.LEFT;
                }
                if (enLeftX < nextX) {
                    direction = Direction.RIGHT;
                }

            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = Direction.UP;
                checkCollision();
                if (true) {
                    direction = Direction.LEFT;
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = Direction.UP;
                checkCollision();
                if (true) {
                    direction = Direction.RIGHT;
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = Direction.DOWN;
                checkCollision();
                if (true) {
                    direction = Direction.LEFT;
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = Direction.DOWN;
                checkCollision();
                if (true) {
                    direction = Direction.RIGHT;
                }
            }
        }

    }

    private void checkCollision() {
        collisionOn = false;
    }

    public void update() {
        Animation currentAnimation = animations.get(currentState);
        if (currentAnimation != null) {
            currentAnimation.update();
        }

        solidArea.x = x + game.cameraX;
        solidArea.y= y + game.cameraY;


        if (!canTakeDamage) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - invincibilityStartTime >= invincibilityDuration) {
                canTakeDamage = true;
                System.out.println("Invulnerability is over, the character can take damage again.");
            }
        }
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
            g2.draw(entityCollision);
        }
    }

    public void setAction() {
    }

    public void dead() {
        if (!inventory.getAll().isEmpty()) {
            for (GameObject gameObject : inventory.getAll()) {
                if (gameObject != null) {
                    ItemEntity itemEntity = new ItemEntity(game, gameObject);
                    int radx = new Random().nextInt(128) + -64;
                    int rady = new Random().nextInt(128) + -64;
                    itemEntity.x = x + radx;
                    itemEntity.y = y + rady;
                    dropItem(itemEntity);
                }
            }
        }

    }

    private void dropItem(ItemEntity itemEntity) {
        game.world.entities.add(itemEntity);
    }

    private void activateInvincibility() {
        canTakeDamage = false;
        invincibilityStartTime = System.currentTimeMillis();
    }

    public void damage(int damage, Entity attacker) {


        if (canTakeDamage) {
            life -= damage;
            activateInvincibility();
            System.out.println("Life: "+life+"/"+maxLife+" damage: "+damage);
        }
        if (life <= 0) {
            alive = false;
        }

    }
}

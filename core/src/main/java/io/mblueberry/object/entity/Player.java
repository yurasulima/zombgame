package io.mblueberry.object.entity;

import io.mblueberry.Game;
import io.mblueberry.anim.Animation;
import io.mblueberry.container.Container;
import io.mblueberry.level.Chunk;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.Const.CHUNK_SIZE;
import static io.mblueberry.Const.DEBUG;
import static io.mblueberry.Const.TILE_SIZE;
import static io.mblueberry.util.TextureUtil.loadFrames;

public class Player extends Entity implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean jumping = false;
    public List<Direction> lockedDirections = new ArrayList<>();


    public Player(Game game, int spawnX, int spawnY) {
        super(game);

        username = String.valueOf(System.currentTimeMillis());
        x = TILE_SIZE * spawnX;
        y = TILE_SIZE * spawnY;
        type = "player";
        solidArea = new Rectangle(16, 16, 32, 32);
        direction = Direction.IDLE;
        currentState = "idle";
        entityCollision = new Rectangle(16, 16, 32, 32);
        speed = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        inventory = new Container(game, 37);
        rightHand = null;
        loadAnimations();

    }

    private void loadAnimations() {

        List<BufferedImage> jumpLeftFrames = loadFrames("player", "jump_1", "jump_2", "jump_3", "jump_4", "jump_5");
        animations.put("jump_left", new Animation(jumpLeftFrames, 70));

        List<BufferedImage> idleFrames = loadFrames("player", "idle_1", "idle_1", "idle_1", "idle_1", "idle_2", "idle_1", "idle_1", "idle_1", "idle_1");
        animations.put("idle", new Animation(idleFrames, 500));


        List<BufferedImage> idleDownLeftFrames = loadFrames("player", "idle_down_left");
        animations.put("idle_down_left", new Animation(idleDownLeftFrames, 500));

        List<BufferedImage> idleDownRightFrames = loadFrames("player", "idle_down_right");
        animations.put("idle_down_right", new Animation(idleDownRightFrames, 500));

        List<BufferedImage> idleTopLeftFrames = loadFrames("player", "idle_top_left");
        animations.put("idle_top_left", new Animation(idleTopLeftFrames, 500));

        List<BufferedImage> idleTopRightFrames = loadFrames("player", "idle_top_right");
        animations.put("idle_top_right", new Animation(idleTopRightFrames, 500));


        List<BufferedImage> walkUpFrames = loadFrames("player", "walk_up_1", "walk_up_2");
        animations.put("walk_up", new Animation(walkUpFrames, 150));

        List<BufferedImage> walkLeftFrames = loadFrames("player", "walk_down_1", "walk_down_2");
        animations.put("walk_left", new Animation(walkLeftFrames, 150));

        List<BufferedImage> walkDownFrames = loadFrames("player", "walk_right_1", "walk_right_2");
        animations.put("walk_down", new Animation(walkDownFrames, 150));


        List<BufferedImage> walkRightFrames = loadFrames("player", "walk_right_1", "walk_right_2");
        animations.put("walk_right", new Animation(walkRightFrames, 150));
    }


    public void runAnimation(String anim) {
        currentState = anim;
    }

    @Override
    public void update() {
        // game.collisionChecker.checkTile(this);

        handleMove();

        if (leftPressed) {
            currentState = "walk_left";
        } else if (rightPressed) {
            currentState = "walk_right";
        } else if (upPressed) {
            currentState = "walk_up";
        } else if (downPressed) {
            currentState = "walk_down";
        } else if (jumping) {
            jumpAnim();
        } else {
            direction = getMouseDirection();
        }


        super.update();

    }

    public Direction getMouseDirection() {
        int mouseX = game.guiManager.gameUi.mouseX;
        int mouseY = game.guiManager.gameUi.mouseY;
        int centerX = x;
        int centerY = y;

        if (mouseX > centerX && mouseY < centerY) {
            currentState = "idle_top_right";
            return Direction.TOP_RIGHT;
        } else if (mouseX < centerX && mouseY < centerY) {
            currentState = "idle_top_left";
            return Direction.TOP_LEFT;
        } else if (mouseX > centerX && mouseY > centerY) {
            currentState = "idle_down_right";
            return Direction.DOWN_RIGHT;
        } else {
            currentState = "idle_down_left";
            return Direction.DOWN_LEFT;
        }
    }

    private void jumpAnim() {
        if (direction == Direction.LEFT) {
            currentState = "jump_left";
        }
        if (direction == Direction.RIGHT) {
            currentState = "jump_left";
        }
        if (direction == Direction.DOWN) {
            currentState = "jump_left";
        }
        if (direction == Direction.HOLD) {
            currentState = "jump_left";
        }
        if (direction == Direction.UP) {
            currentState = "jump_left";
        }
    }

    private void handleMove() {
        entityCollision = new Rectangle(x + 16, y + 32, 32, 32);
        int displayCenterX = game.getWidth() / 2;
        int displayCenterY = game.getHeight() / 2;


        collisionOn = false;
        //TODO game.collisionChecker.checkPlayerCollision(this);
        if (upPressed && !lockedDirections.contains(Direction.UP)) {
            direction = Direction.UP;
            if (y < (displayCenterY - 200)) {
                int tileY = (y - game.cameraY);
                if (tileY > y) {
                    game.cameraY += speed;
                } else {
                    y -= speed;
                }
            } else {
                y -= speed;
            }
        }
        if (downPressed && !lockedDirections.contains(Direction.DOWN)) {
            direction = Direction.DOWN;
            if (y > (displayCenterY + 200)) {
                int tileY = (y - game.cameraY);
                int screenRealX = game.getWidth();
                if (tileY - y > screenRealX) {
                    y += speed;
                } else {
                    game.cameraY -= speed;
                }
            } else {
                y += speed;
            }
        }
        if (rightPressed && !lockedDirections.contains(Direction.RIGHT)) {
            direction = Direction.RIGHT;
            if (x > (displayCenterX + 200)) {
                int tileX = (x - game.cameraX);
                int screenRealX = game.getHeight();
              //  if (tileX - x > screenRealX) {
                 //   x += speed;
                //} else {
                    game.cameraX -= speed;
               // }
            } else {
                x += speed;
            }

        }
        if (leftPressed && !lockedDirections.contains(Direction.LEFT)) {
            direction = Direction.LEFT;
            if (x < (displayCenterX - 200)) {
                int tileX = (x - game.cameraX);
                if (tileX > x) {
                    game.cameraX += speed;
                } else {
                    x -= speed;
                }
            } else {
                x -= speed;
            }
        }

        if (game.isOnline) {
            game.sendPlayerUpdate();
        }

        game.level.updateLoadedChunks();



    }

    private void jump() {
        int jumpSpeed = 7;
        switch (direction) {
            case UP -> {
                y -= jumpSpeed;
                game.cameraY += jumpSpeed;
            }
            case DOWN -> {
                y += jumpSpeed;
                game.cameraY -= jumpSpeed;
            }
            case RIGHT -> {
                x += jumpSpeed;
                game.cameraX -= jumpSpeed;
            }
            case LEFT -> {
                x -= jumpSpeed;
                game.cameraX += jumpSpeed;
            }
        }
    }


    public void draw(Graphics2D g2) {
        if (rightHand != null) {
            rightHand.draw(g2, this, game);
        }
        Animation currentAnimation = animations.get(currentState);
        if (currentAnimation != null) {
            g2.drawImage(currentAnimation.getCurrentFrame(), x, y, null);
        }

        if (DEBUG) {
            int xx = game.player.x - game.cameraX;
            int yy = game.player.y - game.cameraY;
            int chunkX = xx / (CHUNK_SIZE * TILE_SIZE);
            int chunkY = yy / (CHUNK_SIZE * TILE_SIZE);
            int chunkSX = CHUNK_SIZE * TILE_SIZE * chunkX + game.cameraX;
            int chunkSY = CHUNK_SIZE * TILE_SIZE * chunkY + game.cameraY;
            int chunkSize = CHUNK_SIZE * TILE_SIZE;
            g2.setColor(Color.RED);
            g2.drawRect(chunkSX, chunkSY, chunkSize, chunkSize);
            Rectangle solidArea1 = new Rectangle(x + 16, y + 32, 32, 32);
            g2.setColor(Color.cyan);
            g2.drawRect(solidArea1.x, solidArea1.y, solidArea1.width, solidArea1.height);
        }
    }



    // Обробка натискання клавіш
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_SPACE -> {
                jumping = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_SPACE -> {
                jumping = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Не використовується, але необхідно для KeyListener
    }
}

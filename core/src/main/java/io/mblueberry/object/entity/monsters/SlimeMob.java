package io.mblueberry.object.entity.monsters;

import io.mblueberry.anim.Animation;
import io.mblueberry.anim.SpriteLoader;
import io.mblueberry.object.entity.*;
import io.mblueberry.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.mblueberry.Const.TILE_SIZE;
import static io.mblueberry.util.TextureUtil.loadFrames;

public class SlimeMob extends Entity {

    private final int attack;
    private final int defense;
    private int actionLockCounter;

    public SlimeMob(Game game, int spawnX, int spawnY) {
        super(game);

        entityCollision = new Rectangle(16, 16, 32, 32);
        x = TILE_SIZE * spawnX;
        y = TILE_SIZE * spawnY;
        direction = Direction.IDLE;
        speed = 1;
        attack = 5;
        defense = 0;
        life = 4;
        maxLife = 4;


        loadAnimations();
        setAction();
    }

    @Override
    public void update() {
        super.update();
        setAction();
        entityCollision = new Rectangle(x + 16, y + 32, 32, 32);
        game.collisionChecker.checkEntityCollision(this);
        if (direction.equals(Direction.RIGHT) && !lockedDirections.contains(Direction.RIGHT)) {
            x += speed;
        }
        if (direction.equals(Direction.LEFT) && !lockedDirections.contains(Direction.LEFT)) {
            x -= speed;
        }
        if (direction.equals(Direction.UP) && !lockedDirections.contains(Direction.UP)) {
            y -= speed;
        }
        if (direction.equals(Direction.DOWN) && !lockedDirections.contains(Direction.DOWN)) {
            y += speed;
        }
    }

    private void loadAnimations() {
        List<BufferedImage> walkDownFrames = loadFrames("greenslime", "greenslime_down_1", "greenslime_down_2");

        animations.put("idle", new Animation(walkDownFrames, 500));
        animations.put("walk_up", new Animation(walkDownFrames, 150));
        animations.put("walk_left", new Animation(walkDownFrames, 150));
        animations.put("walk_down", new Animation(walkDownFrames, 150));
        animations.put("walk_right", new Animation(walkDownFrames, 150));
    }

    public void damageReaction(Player player) {
        actionLockCounter = 0;
        direction = player.direction;
    }
    public void setAction(){
        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = Direction.UP;

            }
            if (i > 25 && i <= 50) {
                direction = Direction.DOWN;
            }
            if (i > 50 && i <= 75) {
                direction = Direction.LEFT;
            }
            if (i > 75) {
                direction = Direction.RIGHT;
            }
            actionLockCounter = 0;
        }
    }

}

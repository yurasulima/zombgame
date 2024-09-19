package io.mblueberry.core.object.entity;

import io.mblueberry.Game;
import io.mblueberry.core.anim.Animation;
import io.mblueberry.core.container.Container;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static io.mblueberry.Game.DEBUG;
import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.TextureUtil.loadFrames;

public class MPlayer extends Entity {

    public MPlayer(Game game, int spawnX, int spawnY, String username) {
        super(game);
        x = TILE_SIZE * spawnX;
        y = TILE_SIZE * spawnY;
        setUsername(username);
        direction = Direction.IDLE;
        currentState = "idle";
        entityCollision = new Rectangle(16, 16, 32, 32);
        speed = 6;
        inventory = new Container(game, 37);
        rightHand = null;
        loadAnimations();
    }

    public void runAnimation(String anim) {
        currentState = anim;
    }


    private void loadAnimations() {

        java.util.List<BufferedImage> jumpLeftFrames = loadFrames("player", "jump_1", "jump_2", "jump_3", "jump_4", "jump_5");
        animations.put("jump_left", new Animation(jumpLeftFrames, 70));

        java.util.List<BufferedImage> idleFrames = loadFrames("player", "idle_1", "idle_1", "idle_1", "idle_1", "idle_2", "idle_1", "idle_1", "idle_1", "idle_1");
        animations.put("idle", new Animation(idleFrames, 500));


        java.util.List<BufferedImage> idleDownLeftFrames = loadFrames("player", "idle_down_left");
        animations.put("idle_down_left", new Animation(idleDownLeftFrames, 500));

        java.util.List<BufferedImage> idleDownRightFrames = loadFrames("player", "idle_down_right");
        animations.put("idle_down_right", new Animation(idleDownRightFrames, 500));

        java.util.List<BufferedImage> idleTopLeftFrames = loadFrames("player", "idle_top_left");
        animations.put("idle_top_left", new Animation(idleTopLeftFrames, 500));

        java.util.List<BufferedImage> idleTopRightFrames = loadFrames("player", "idle_top_right");
        animations.put("idle_top_right", new Animation(idleTopRightFrames, 500));


        java.util.List<BufferedImage> walkUpFrames = loadFrames("player", "walk_up_1", "walk_up_2");
        animations.put("walk_up", new Animation(walkUpFrames, 150));

        java.util.List<BufferedImage> walkLeftFrames = loadFrames("player", "walk_down_1", "walk_down_2");
        animations.put("walk_left", new Animation(walkLeftFrames, 150));

        java.util.List<BufferedImage> walkDownFrames = loadFrames("player", "walk_right_1", "walk_right_2");
        animations.put("walk_down", new Animation(walkDownFrames, 150));


        List<BufferedImage> walkRightFrames = loadFrames("player", "walk_right_1", "walk_right_2");
        animations.put("walk_right", new Animation(walkRightFrames, 150));
    }

    public void update() {
        super.update();
    }


    @Override
    public void draw(Graphics2D g2) {

        if (rightHand != null) {
            rightHand.draw(g2, this, game);
        }
        Animation currentAnimation = animations.get(currentState);
        if (currentAnimation != null) {
            g2.drawImage(currentAnimation.getCurrentFrame(), x + game.cameraX, y + game.cameraY, null);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(15F));
        g2.drawString(username,  game.cameraX + x, y + game.cameraY - 32);

        if (DEBUG) {

            Rectangle solidArea1 = new Rectangle(x + 16 + game.cameraX, y + 32 + game.cameraY, 32, 32);
            g2.setColor(Color.cyan);
            g2.drawRect(solidArea1.x, solidArea1.y, solidArea1.width, solidArea1.height);
        }
    }
}

package io.mblueberry.core.object.entity.npc;


import io.mblueberry.Game;
import io.mblueberry.core.container.Container;
import io.mblueberry.core.anim.Animation;
import io.mblueberry.core.object.entity.Direction;
import io.mblueberry.core.object.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.TextureUtil.loadFrames;

public class OldManNPC extends Entity {


    public OldManNPC(Game game, int spawnX, int spawnY) {
        super(game);
        type = "old_man_npc";
        x = TILE_SIZE * spawnX;
        y = TILE_SIZE * spawnY;
        direction = Direction.HOLD;
        speed = 2;
        life = 10;
        solidArea = new Rectangle(30, 30, 15, 15);
        maxLife = 10;
        inventory = new Container(game, 37);
        rightHand = null;
        setDialogue();
        loadAnimations();

    }


    private void loadAnimations() {

        List<BufferedImage> idleFrames = loadFrames("old_man", "oldman_down_1");
        animations.put("idle", new Animation(idleFrames, 500));

        List<BufferedImage> walkUpFrames = loadFrames("old_man", "oldman_up_1", "oldman_up_2");
        animations.put("walk_up", new Animation(walkUpFrames, 150));

        List<BufferedImage> walkLeftFrames = loadFrames("old_man", "oldman_left_2", "oldman_left_2");
        animations.put("walk_left", new Animation(walkLeftFrames, 150));

        List<BufferedImage> walkDownFrames = loadFrames("old_man", "oldman_down_1", "oldman_down_2");
        animations.put("walk_down", new Animation(walkDownFrames, 150));

        List<BufferedImage> walkRightFrames = loadFrames("old_man", "oldman_right_1", "oldman_right_2");
        animations.put("walk_right", new Animation(walkRightFrames, 150));
    }

    @Override
    public void update() {
        super.update();
        if (direction == Direction.UP) {
            y -= speed;
            currentState = "walk_up";
        }
        else if (direction == Direction.DOWN) {
            y += speed;
            currentState = "walk_down";
        }
        else if (direction == Direction.LEFT) {
            x -= speed;
            currentState = "walk_left";
        }
        else if (direction == Direction.RIGHT) {
            currentState = "walk_right";
            x += speed;
        } else {
            currentState = "idle";
        }
    }

    public void speak() {
        onPath = true;
    }


    public void setDialogue() {
        //TODO refactor dialog system
//        dialogues[0] = "Hello";
//        dialogues[1] = str("Вибач за біль,\nпосилені процедури моніторингу.\nЕт Нехай так буде. Відплата чудово");
//        dialogues[2] = "Maecenas potenti suspendisse.";
//        dialogues[3] = "Maecenas potenti suspendisse.";
//        dialogues[4] = "Nulla amet sed ligula.";
//        dialogues[5] = "Luctus semper eros.";

    }

    @Override
    public void setAction() {
        if (onPath) {
            int goalX = (game.player.x + game.player.solidArea.x - game.cameraX)  / TILE_SIZE;
            int goalY =  (game.player.y + game.player.solidArea.y - game.cameraY) / TILE_SIZE;
            super.searchPath(goalX, goalY);
        } else {
            direction = Direction.HOLD;
        }

//        actionLockCounter++;
//        if (actionLockCounter == 120) {
//
//            Random random = new Random();
//            int i = random.nextInt(100) + 1;
//            if (i <= 25) {
//                direction = Direction.UP;
//
//            }
//            if (i > 25 && i <= 50) {
//                direction = Direction.DOWN;
//            }
//            if (i > 50 && i <= 75) {
//                direction = Direction.LEFT;
//            }
//            if (i > 75) {
//                direction = Direction.RIGHT;
//            }
//            actionLockCounter = 0;
//        }
//    }
    }
}

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

public class HitNPC extends Entity {

    public HitNPC(Game game, int spawnX, int spawnY) {
        super(game);
        type = "hit_npc";
        x = TILE_SIZE * spawnX;
        y = TILE_SIZE * spawnY;
        direction = Direction.HOLD;
        currentState = "idle";
        speed = 2;
        life = 10;
        solidArea = new Rectangle(16, 32, 32, 32);
        maxLife = 10;
        inventory = new Container(game, 37);
        rightHand = null;

        loadAnimations();

    }

    private void loadAnimations() {

        List<BufferedImage> idleFrames = loadFrames("hit_npc", "hit_npc");
        animations.put("idle", new Animation(idleFrames, 500));

    }
}

package io.mblueberry.object.entity.npc;

import io.mblueberry.Game;
import io.mblueberry.container.Container;
import io.mblueberry.anim.Animation;
import io.mblueberry.object.block.Wall;
import io.mblueberry.object.entity.Direction;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.item.AxeItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static io.mblueberry.Const.TILE_SIZE;
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

        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        inventory.add(new AxeItem());
        loadAnimations();

    }

    private void loadAnimations() {

        List<BufferedImage> idleFrames = loadFrames("hit_npc", "hit_npc");
        animations.put("idle", new Animation(idleFrames, 500));

    }
}

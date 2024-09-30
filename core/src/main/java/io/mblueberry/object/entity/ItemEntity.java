package io.mblueberry.object.entity;

import io.mblueberry.Game;
import io.mblueberry.anim.Animation;
import io.mblueberry.object.item.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ItemEntity extends Entity{

    public GameObject gameObject;

    public ItemEntity(Game game, GameObject gameObject) {
        super(game);
        this.gameObject = gameObject;
        direction = Direction.HOLD;
        currentState = "idle";
        entityCollision = new Rectangle(16, 32, 32, 32);
        rightHand = null;
        loadAnimations();
    }

    private void loadAnimations() {

        List<BufferedImage> idleFrames = new ArrayList<>();
        idleFrames.add(gameObject.texture);
        animations.put("idle", new Animation(idleFrames, 500));

    }
}

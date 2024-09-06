package io.mblueberry.object.item;

import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.EntityTag;
import io.mblueberry.Game;

import java.awt.image.BufferedImage;

//TODO refactor
public class HeartItem extends Entity {


    public BufferedImage heartFull, heartHalf, heartBlank;
    public HeartItem(Game game) {
        super(game);
        direction = "hold";
        entityTag = EntityTag.OBJECT;
        hold = setupMini("/objects/heart_full");
        heartFull = setupMini("/objects/heart_full");
        heartHalf = setupMini("/objects/heart_half");
        heartBlank = setupMini("/objects/heart_blank");
    }


}

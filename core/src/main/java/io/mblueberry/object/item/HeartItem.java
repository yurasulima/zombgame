package io.mblueberry.object.item;

import io.mblueberry.object.entity.Direction;
import io.mblueberry.object.entity.EntityOld;
import io.mblueberry.object.entity.EntityTag;
import io.mblueberry.Game;

import java.awt.image.BufferedImage;

//TODO refactor
public class HeartItem extends EntityOld {


    public BufferedImage heartFull, heartHalf, heartBlank;
    public HeartItem(Game game) {
        super(game);
        direction = Direction.HOLD;
        entityTag = EntityTag.OBJECT;
        hold = setupMini("/objects/heart_full");
        heartFull = setupMini("/objects/heart_full");
        heartHalf = setupMini("/objects/heart_half");
        heartBlank = setupMini("/objects/heart_blank");
    }


}

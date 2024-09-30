package io.mblueberry.object.item;

import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class ShieldItem extends Item {

    public ShieldItem() {
        type = "shield_wood";
        texture = loadItemTexture("shield_wood");
        defenseValue = 2;
    }
}

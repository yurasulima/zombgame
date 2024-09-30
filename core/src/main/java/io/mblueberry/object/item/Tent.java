package io.mblueberry.object.item;

import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class Tent extends Item {

    public Tent() {
        type = "tent";
        texture =  loadItemTexture(type);
        displayName = type;

    }
}

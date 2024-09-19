package io.mblueberry.core.object.item;

import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class KeyItem extends Item {

    public KeyItem() {
        type = "key";
        texture = loadItemTexture(type);
    }
}

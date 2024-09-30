package io.mblueberry.object.item;


import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class AxeItem extends Item {

    public AxeItem() {
        type = "axe";
        texture = loadItemTexture(type);
    }
}

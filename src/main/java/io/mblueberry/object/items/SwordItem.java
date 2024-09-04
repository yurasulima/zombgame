package io.mblueberry.object.items;

public class SwordItem extends Item {
    public SwordItem() {
        type = "axe";
        texture = setup("/objects/sword_normal");
        icon = setup("/objects/sword_normal");
        attackValue = 1;
    }
}

package io.mblueberry.object.item;

public class SwordItem extends Item {
    public SwordItem() {
        type = "sword_normal";
        itemType = "sword_normal";
        //TODO refactor icon + texture load
        texture = setup("/objects/sword_normal");
        icon = setup("/objects/sword_normal");
        attackValue = 1;
    }
}

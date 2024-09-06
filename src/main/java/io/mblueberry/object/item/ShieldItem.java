package io.mblueberry.object.item;

public class ShieldItem extends Item {

    public ShieldItem() {
        type = "shield_wood";
        itemType = "shield_wood";
        texture = setup("/objects/shield_wood");
        icon = setup("/objects/shield_wood");
        defenseValue = 2;
    }
}

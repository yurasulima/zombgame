package io.mblueberry.object.items;

public class ShieldItem extends Item {

    public ShieldItem() {
        type = "shield_wood";
        texture = setup("/objects/shield_wood");
        icon = setup("/objects/shield_wood");
        defenseValue = 2;
    }
}

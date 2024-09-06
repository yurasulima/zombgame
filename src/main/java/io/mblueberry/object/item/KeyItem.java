package io.mblueberry.object.item;

public class KeyItem extends Item {

    public KeyItem() {
        type = "axe";
        itemType = "axe";
        //TODO refactor icon + texture load
        texture = setup("/objects/key");
        icon = setup("/objects/key");
    }
}

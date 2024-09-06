package io.mblueberry.object.item;


public class AxeItem extends Item {

    public AxeItem() {
        type = "axe";
        //TODO refactor icon + texture load
        texture = setup("/objects/axe");
        icon = setup("/objects/axe");
        itemType = "axe";
    }

}

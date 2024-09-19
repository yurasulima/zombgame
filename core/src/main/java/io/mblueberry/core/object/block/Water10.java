package io.mblueberry.core.object.block;

import java.awt.*;

public class Water10 extends Block {
    public Water10() {
        super("water10");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(44, 44, 20, 20);
    }
}

package io.mblueberry.core.object.block;

import java.awt.*;

public class Water09 extends Block {
    public Water09() {
        super("water09");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

package io.mblueberry.core.object.block;

import java.awt.*;

public class Water03 extends Block {
    public Water03() {
        super("water03");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

package io.mblueberry.core.object.block;

import java.awt.*;

public class Water05 extends Block {
    public Water05() {
        super("water05");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(0, 0, 20, 64);
    }
}

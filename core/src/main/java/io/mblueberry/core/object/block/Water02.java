package io.mblueberry.core.object.block;

import java.awt.*;

public class Water02 extends Block {
    public Water02() {
        super("water02");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

package io.mblueberry.object.block;

import java.awt.*;

public class Water07 extends Block {
    public Water07() {
        super("water07");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

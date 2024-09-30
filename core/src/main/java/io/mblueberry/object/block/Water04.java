package io.mblueberry.object.block;

import java.awt.*;

public class Water04 extends Block {
    public Water04() {
        super("water04");
        setCollision(true);
        setDisplayName("Вода");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

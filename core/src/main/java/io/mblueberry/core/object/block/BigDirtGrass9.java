package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass9 extends Block {
    public BigDirtGrass9() {
        super("big_dirt_grass9");
        setCollision(true);
        setDisplayName("Земляна стіна");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

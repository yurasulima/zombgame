package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass1 extends Block {
    public BigDirtGrass1() {
        super("big_dirt_grass1");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(44, 0, 20, 64));
    }
}

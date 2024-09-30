package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass2 extends Block {
    public BigDirtGrass2() {
        super("big_dirt_grass2");
        setCollision(true);
        setDisplayName("Земляна стіна");

        setBlockCollision(new Rectangle(0, 0, 64, 64));
    }
}

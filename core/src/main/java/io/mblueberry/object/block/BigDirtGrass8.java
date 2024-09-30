package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass8 extends Block {
    public BigDirtGrass8() {
        super("big_dirt_grass8");
        setCollision(true);
        setDisplayName("Земляна стіна");

        setBlockCollision(new Rectangle(0, 0, 64, 64));
    }
}

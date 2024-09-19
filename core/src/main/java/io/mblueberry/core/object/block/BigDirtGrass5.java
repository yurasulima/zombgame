package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass5 extends Block {
    public BigDirtGrass5() {
        super("big_dirt_grass5");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 0, 64,50));
    }
}

package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass3 extends Block {
    public BigDirtGrass3() {
        super("big_dirt_grass3");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 0, 20,64));
    }
}

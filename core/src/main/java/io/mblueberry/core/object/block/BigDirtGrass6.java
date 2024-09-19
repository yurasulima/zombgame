package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass6 extends Block {
    public BigDirtGrass6() {
        super("big_dirt_grass6");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 0, 20,50));
    }
}

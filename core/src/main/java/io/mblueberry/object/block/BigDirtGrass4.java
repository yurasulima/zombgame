package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass4 extends Block {
    public BigDirtGrass4() {
        super("big_dirt_grass4");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(44, 0, 20, 50));
    }
}

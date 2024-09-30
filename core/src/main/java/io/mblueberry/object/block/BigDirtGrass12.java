package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass12 extends Block {
    public BigDirtGrass12() {
        super("big_dirt_grass12");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(44, 0, 20, 50));
    }
}

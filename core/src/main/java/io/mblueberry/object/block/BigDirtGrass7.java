package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass7 extends Block {
    public BigDirtGrass7() {
        super("big_dirt_grass7");
        setCollision(true);
        setDisplayName("Земляна стіна");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass10 extends Block {
    public BigDirtGrass10() {
        super("big_dirt_grass10");
        setCollision(true);
        setDisplayName("Земляна стіна");
        blockCollision = new Rectangle(0,0,64,64);
    }
}

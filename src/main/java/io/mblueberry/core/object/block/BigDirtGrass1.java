package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass1 extends Block {
    public BigDirtGrass1() {
        super("big_dirt_grass1");
        setCollision(true);
        setDisplayName("Земляна стіна");
        solidArea = new Rectangle(46, 0, 20, 64);
        defaultSolidArea = new Rectangle(46, 0, 20, 64);
    }
}

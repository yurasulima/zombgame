package io.mblueberry.core.object.block;

import java.awt.*;

public class BigDirtGrass8 extends Block {
    public BigDirtGrass8() {
        super("big_dirt_grass8");
        setCollision(true);
        setDisplayName("Земляна стіна");

        solidArea = new Rectangle(46, 0, 20, 64);
        defaultSolidArea = new Rectangle(46, 0, 20, 64);
    }
}

package io.mblueberry.core.object.block;

import java.awt.*;

public class DirtGrass8 extends Block {
    public DirtGrass8() {
        super("dirt_grass_8");
        setCollision(true);
        setDisplayName("Земляна стіна");

        solidArea = new Rectangle(46, 0, 20, 64);
        defaultSolidArea = new Rectangle(46, 0, 20, 64);
    }
}

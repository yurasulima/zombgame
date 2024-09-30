package io.mblueberry.object.block;

import java.awt.*;

public class DirtGrass1 extends Block {
    public DirtGrass1() {
        super("dirt_grass_1");
        setCollision(true);
        setDisplayName("Земляна стіна");
        blockCollision = new Rectangle(0, 0, 64, 64);
    }
}

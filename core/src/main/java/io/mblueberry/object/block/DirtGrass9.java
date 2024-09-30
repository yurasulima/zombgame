package io.mblueberry.object.block;

import java.awt.*;

public class DirtGrass9 extends Block {
    public DirtGrass9() {
        super("dirt_grass_9");
        setCollision(true);
        setDisplayName("Земляна стіна");
        blockCollision = new Rectangle(44, 44, 20, 20);
    }
}

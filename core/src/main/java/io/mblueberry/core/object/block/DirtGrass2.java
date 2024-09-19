package io.mblueberry.core.object.block;

import java.awt.*;

public class DirtGrass2 extends Block {
    public DirtGrass2() {
        super("dirt_grass_2");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 0, 64,50));
    }
}

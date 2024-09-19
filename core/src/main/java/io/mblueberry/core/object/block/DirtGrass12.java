package io.mblueberry.core.object.block;

import java.awt.*;

public class DirtGrass12 extends Block {
    public DirtGrass12() {
        super("dirt_grass_12");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 0, 20,50));
    }
}

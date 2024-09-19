package io.mblueberry.core.object.block;

import java.awt.*;

public class DirtGrass4 extends Block {
    public DirtGrass4() {
        super("dirt_grass_4");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 0, 20, 64));
    }
}

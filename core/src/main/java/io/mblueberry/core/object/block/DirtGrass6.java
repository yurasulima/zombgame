package io.mblueberry.core.object.block;

import java.awt.*;

public class DirtGrass6 extends Block {
    public DirtGrass6() {
        super("dirt_grass_6");
        setCollision(true);
        setDisplayName("Земляна стіна");

        setBlockCollision(new Rectangle(0, 44, 64,20));
    }
}

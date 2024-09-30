package io.mblueberry.object.block;

import java.awt.*;

public class DirtGrass10 extends Block {
    public DirtGrass10() {
        super("dirt_grass_10");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(0, 44, 20, 20));
    }
}

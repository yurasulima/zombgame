package io.mblueberry.object.block;

import java.awt.*;

public class DirtGrass11 extends Block {
    public DirtGrass11() {
        super("dirt_grass_11");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(44, 0, 20, 50));
    }
}

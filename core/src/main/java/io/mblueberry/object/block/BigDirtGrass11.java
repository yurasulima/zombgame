package io.mblueberry.object.block;

import java.awt.*;

public class BigDirtGrass11 extends Block {
    public BigDirtGrass11() {
        super("big_dirt_grass11");
        setCollision(true);
        setDisplayName("Земляна стіна");
        setBlockCollision(new Rectangle(44, 0, 20, 65));
    }
}

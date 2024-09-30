package io.mblueberry.object.block;

import java.awt.*;

public class Water08 extends Block {
    public Water08() {
        super("water08");
        setCollision(true);
        setDisplayName("Вода");
        setBlockCollision(new Rectangle(0, 44, 64,20));
    }
}

package io.mblueberry.core.object.block;

import java.awt.*;

public class Water06 extends Block {
    public Water06() {
        super("water06");
        setCollision(true);
        setDisplayName("Вода");
        setBlockCollision(new Rectangle(44, 0, 20, 64));
    }
}

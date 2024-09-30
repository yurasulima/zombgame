package io.mblueberry.object.block;

public class Water00 extends Block {
    public Water00() {
        super("water00");
        setCollision(false);
        setDisplayName("Вода");
    }

    public Water00(int blockX, int blockY) {
        super("water00");
        setCollision(false);
        setDisplayName("Вода");
        setX(blockX);
        setY(blockY);
    }
}

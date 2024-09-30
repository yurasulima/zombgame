package io.mblueberry.object.block;

public class Grass00 extends Block {
    public Grass00() {
        super("grass00");
        setCollision(false);
        setDisplayName("Трава");
    }

    public Grass00(int blockX, int blockY) {
        super("grass00");
        setCollision(false);
        setDisplayName("Трава");
        setX(blockX);
        setY(blockY);
    }
}

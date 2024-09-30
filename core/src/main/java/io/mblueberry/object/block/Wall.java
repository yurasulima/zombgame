package io.mblueberry.object.block;

public class Wall extends Block {
    public Wall() {
        super("wall");
        setCollision(true);
        setDisplayName("Стіна");
    }

    public Wall(int blockX, int blockY) {
        super("wall");
        setCollision(true);
        setDisplayName("Стіна");
        setX(blockX);
        setY(blockY);
    }
}

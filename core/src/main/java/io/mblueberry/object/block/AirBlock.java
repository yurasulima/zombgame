package io.mblueberry.object.block;

public class AirBlock extends Block{

    public AirBlock() {
        super("air");
        setInteract(false);
        setCollision(false);
    }
}

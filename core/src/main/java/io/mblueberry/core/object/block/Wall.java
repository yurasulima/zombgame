package io.mblueberry.core.object.block;
    
    public class Wall extends Block {
    public Wall() {
        super("wall");
        setCollision(true);
        setDisplayName("Стіна");
    }
}

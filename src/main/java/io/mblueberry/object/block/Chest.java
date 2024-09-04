package io.mblueberry.object.block;

import io.mblueberry.Game;
import io.mblueberry.container.ChestContainer;

import java.awt.image.BufferedImage;

public class Chest extends Block{
    private boolean opened = false;
    private final BufferedImage stateClosed;
    private final BufferedImage stateOpen;
    public Chest() {
        super("chest");
        stateClosed = setupImage("/objects/chest");
        stateOpen = setupImage("/objects/chest_opened");
        setInteract(true);
        setCollision(false);
    }

    @Override
    public void interact() {
        super.interact();
        open();
        update();
    }

    @Override
    public void stopInteract() {
        super.stopInteract();
        close();
        update();
    }

    public void place(Game game, int x, int y) {

    }

    public void update(){
        if (opened) {
            icon = stateOpen;
            setImage(stateOpen);
        } else {
            icon = stateClosed;
            setImage(stateClosed);
        }
    }

    public void open() {
        opened = true;
    }

    public void close() {
        opened = false;
    }
}

package io.mblueberry.object.block;

import io.mblueberry.Game;
import io.mblueberry.container.ChestContainer;

import java.awt.image.BufferedImage;

public class Chest extends Block{
    private boolean opened = false;
    public ChestContainer container;
    private final BufferedImage stateClosed;
    private final BufferedImage stateOpen;
    private Game game;
    public Chest(Game game) {
        super("chest");
        this.game = game;
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
        this.setX(x);
        this.setY(y);
        System.out.println("PLACED " + this.getType());
        container = new ChestContainer(game, x, y);
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
        game.guiManager.chestInventoryUi.chest = this;
        game.guiManager.openChestUi();

        opened = true;
    }


    public void close() {
        opened = false;
    }
}

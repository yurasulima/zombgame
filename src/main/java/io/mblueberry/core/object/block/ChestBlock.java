package io.mblueberry.core.object.block;

import io.mblueberry.Game;
import io.mblueberry.core.container.ChestContainer;

import java.awt.*;
import java.awt.image.BufferedImage;

import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class ChestBlock extends Block{
    private final BufferedImage stateClosed;
    private final BufferedImage stateOpen;
    private final Game game;

    private boolean opened = false;
    public ChestContainer container;

    public ChestBlock(Game game) {
        super("chest");
        this.game = game;

        solidArea = new Rectangle(TILE_SIZE, TILE_SIZE, 54, 48);
        defaultSolidArea = new Rectangle(5, 12, 54, 48);
        stateClosed = loadItemTexture("chest");
        stateOpen = loadItemTexture("chest_opened");
        setInteract(true);
        setCollision(true);
        update();
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
       // System.out.println("PLACED " + this.getType());
        container = new ChestContainer(game, x, y);
    }

    public void update(){
        if (opened) {
            setTexture(stateOpen);
        } else {
            setTexture(stateClosed);
        }
    }

    public void open() {
        game.guiManager.chestInventoryUi.chestBlock = this;
        game.guiManager.openChestUi();
        opened = true;
        update();
    }


    public void close() {
        opened = false;
        update();
    }
}

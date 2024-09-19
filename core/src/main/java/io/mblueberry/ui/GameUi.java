package io.mblueberry.ui;

import io.mblueberry.core.object.block.Block;
import io.mblueberry.core.object.item.GameObject;
import io.mblueberry.Game;
import io.mblueberry.ui.component.Slot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.Utils.calculateDistance;
public class GameUi implements IBaseUi {
    private static final int SLOT_COUNT = 9;
    private static final int SLOT_PADDING = 10;
    private static final int HOTBAR_Y = 20;
    private static final int HOTBAR_X_START = 20;

    public Block currentPickBlock;
    private boolean currentPickBlockDist;
    public int mouseX, mouseY, currentSlot;
    private final Game game;
    private final List<Slot> slots = new ArrayList<>();
    //private final BufferedImage lifes = TextureUtil.loadUiTexture("lifes", 26 * 7, 9 * 4);

    public GameUi(Game game) {
        this.game = game;
        createHotBar();
        currentSlot = 0;
        game.player.rightHand = game.player.inventory.get(currentSlot);
    }

    private void createHotBar() {
        int x = HOTBAR_X_START;
        for (int i = 0; i < SLOT_COUNT; i++) {
            Slot slot = new Slot(x, HOTBAR_Y, TILE_SIZE, TILE_SIZE);
            slots.add(slot);
            x += SLOT_PADDING + TILE_SIZE;
        }
    }

    @Override
    public void update() {
        slots.forEach(slot -> slot.setSelected(false));
        Slot current = slots.get(currentSlot);
        current.setSelected(true);
        game.player.rightHand = game.player.inventory.get(currentSlot);
    }

    public void handleMouse(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void draw(Graphics2D g2) {
        drawHotBar(g2);
        drawGameObjects(g2);
        if (currentPickBlock != null) {
            drawPicker(g2);
        }
    }



    private void drawPicker(Graphics2D g2) {
        double distance = calculateDistance(
                currentPickBlock.getX() * TILE_SIZE + game.cameraX,
                currentPickBlock.getY() * TILE_SIZE + game.cameraY,
                game.player.x, game.player.y);

        currentPickBlockDist = distance <= 64.0 * 3;
        g2.setColor(currentPickBlockDist ? Color.GREEN : Color.RED);
        g2.drawRect(currentPickBlock.getX() * TILE_SIZE + game.cameraX,
                currentPickBlock.getY() * TILE_SIZE + game.cameraY,
                TILE_SIZE, TILE_SIZE);
    }

    private void drawGameObjects(Graphics2D g2) {
        int x = HOTBAR_X_START;
        for (int i = 0; i < SLOT_COUNT; i++) {
            GameObject gameObject = game.player.inventory.get(i);
            if (gameObject != null) {
                g2.drawImage(gameObject.getTexture(), x + 10, HOTBAR_Y + 10, TILE_SIZE - 20, TILE_SIZE - 20, null);
            }
            x += SLOT_PADDING + TILE_SIZE;
        }
    }

    private void interactWorldBlock() {
        GameObject gameObject = game.player.inventory.get(currentSlot);
        if (gameObject == null) return;

        switch (gameObject.type) {
            case "bow":
                // game.player.shoot(mouseX, mouseY);
                break;
            case "sword_normal":
            case "pike":
            case "boomerang":
                gameObject.use(game.player);
                break;
        }

        if (currentPickBlockDist && currentPickBlock.isInteract()) {
            currentPickBlock.interact();
        } else if ("tree".equals(currentPickBlock.type) && "axe".equals(gameObject.type)) {
            Block trunk = new Block("trunk");
            trunk.setX(currentPickBlock.getX());
            trunk.setY(currentPickBlock.getY());
            currentPickBlock = trunk;
            // Create particles...
        }
    }

    public void handleMouseClick(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            placeBlock();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            interactWorldBlock();
        }
    }

    private void placeBlock() {
        GameObject gameObject = game.player.inventory.get(currentSlot);
        if (!(gameObject instanceof Block block)) return;

        block.setX(currentPickBlock.getX());
        block.setY(currentPickBlock.getY());
        game.world.setBlock(new Block(block), currentPickBlock.getX(), currentPickBlock.getY());

        block.removeCount();
        if (block.stackCount == 0) {
            game.player.inventory.remove(currentSlot);
        }
    }

    public void handleMouseMove(MouseEvent e) {
        currentPickBlock = game.world.getTileScreen(e.getX() - game.cameraX, e.getY() - game.cameraY);
    }

    @Override
    public void handleMouseWheel(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            currentSlot = (currentSlot < SLOT_COUNT - 1) ? currentSlot + 1 : 0;
        } else {
            currentSlot = (currentSlot > 0) ? currentSlot - 1 : SLOT_COUNT - 1;
        }
    }

    private void drawHotBar(Graphics2D g2) {
        slots.forEach(slot -> slot.draw(g2));
    }
}

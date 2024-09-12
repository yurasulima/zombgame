package io.mblueberry.ui;

import io.mblueberry.core.object.block.Block;
import io.mblueberry.core.object.item.GameObject;
import io.mblueberry.Game;
import io.mblueberry.ui.component.Slot;
import io.mblueberry.util.TextureUtil;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.Utils.calculateDistance;


public class GameUi implements IBaseUi {
    //PICKER
    public Block currentPickBlock;
    private boolean currentPickBlockDist;
    
    
    public int mouseX;
    public int mouseY;
    private final Game game;
    public int currentSlot;

    Slot slot1;
    Slot slot2;
    Slot slot3;
    Slot slot4;
    Slot slot5;
    Slot slot6;
    Slot slot7;
    Slot slot8;
    Slot slot9;

    List<Slot> slots = new ArrayList<>();

    BufferedImage lifes = TextureUtil.loadUiTexture("lifes", 26*7, 9 *4 );

    public GameUi(Game game) {
        this.game = game;
        createHotBar();
        currentSlot = 0;
        game.player.rightHand = game.player.inventory.get(currentSlot);
    }

    private void createHotBar() {
        int y = 50;
        int x = 50;
        slot1 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot1);
        x+=15 + TILE_SIZE;
        slot2 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot2);
        x+=15 + TILE_SIZE;
        slot3 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot3);
        x+=15 + TILE_SIZE;
        slot4 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot4);
        x+=15 + TILE_SIZE;
        slot5 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot5);
        x+=15 + TILE_SIZE;
        slot6 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot6);
        x+=15 + TILE_SIZE;
        slot7 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot7);
        x+=15 + TILE_SIZE;
        slot8 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot8);
        x+=15 + TILE_SIZE;
        slot9 = new Slot(x, y, TILE_SIZE, TILE_SIZE);
        slots.add(slot9);

    }


    @Override
    public void update() {
        for (Slot slot : slots) {
            slot.setSelected(false);
        }
        Slot slot = slots.get(currentSlot);
        slot.setSelected(true);

        game.player.rightHand = game.player.inventory.get(currentSlot);
    }

    public void handleMouse(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void draw(Graphics2D g2) {
        drawHotBar(g2);
        drawGameObjects(g2);
        drawLifes(g2);
        if (currentPickBlock != null) {
            drawPicker(g2);

        }
    }

    private void drawLifes(Graphics2D g2) {
        //game.player.life
        g2.drawImage(lifes, 50 , 4 , 26*7, 9 * 4, null);
    }

    private void drawPicker(Graphics2D g2) {
        double v = calculateDistance(
                currentPickBlock.getX() * TILE_SIZE + game.cameraX,
                currentPickBlock.getY() * TILE_SIZE + game.cameraY,
                game.player.x,
                game.player.y);

        if (v > 64.0 * 3) {
            g2.setColor(Color.RED);
            currentPickBlockDist = false;
        } else {
            g2.setColor(Color.GREEN);
            currentPickBlockDist = true;
        }
        g2.drawRect(currentPickBlock.getX() * TILE_SIZE + game.cameraX, currentPickBlock.getY() * TILE_SIZE + game.cameraY, 64, 64);
    }

    private void drawGameObjects(Graphics2D g2) {
        int x = 50;
        int y = 50;
        for (int i = 0; i < 9; i++) {
            GameObject gameObject = game.player.inventory.get(i);
            if (gameObject != null) {
                g2.drawImage(gameObject.getTexture(), x + 10, y + 10, TILE_SIZE - 20, TILE_SIZE - 20, null);
            }
            x += 15 + 64;
        }

    }



    private void interactWorldBlock() {
        GameObject gameObject = game.player.inventory.get(game.guiManager.gameUi.currentSlot);
        if (gameObject == null) {
            return;
        }
        if (gameObject.type.equals("bow")) {
            // game.player.shoot(game.guiManager.gameUi.mouseX, game.guiManager.gameUi.mouseY);
        }
        if (gameObject.type.equals("sword_normal")) {
            gameObject.use();
        }
        if (gameObject.type.equals("boomerang")) {
            gameObject.use(game.player);
        }
        if (gameObject.type.equals("pike")) {
            gameObject.use();
        }

        if (currentPickBlockDist) {
            if (currentPickBlock.isInteract()) {
                currentPickBlock.interact();
            } else {
                if (currentPickBlock.type.equals("tree") && gameObject.type.equals("axe")) {

                    Block trunk = new Block("trunk");
                    trunk.setX(currentPickBlock.getX());
                    trunk.setY(currentPickBlock.getY());
                    currentPickBlock = new Block(trunk);
//
//                   //TODO int particleCount = 20; // Кількість частинок, які будуть створені
//
//                    for (int i = 0; i < particleCount; i++) {
//                        // Генеруємо випадкові напрямок та швидкість для кожної частинки
//                        double direction = Math.random() * 2 * Math.PI; // Випадковий напрямок у радіанах
//                        double speed = Math.random() * 2 + 1; // Випадкова швидкість
//
//                        // Створюємо нову частинку з позицією блоку, напрямком і швидкістю
//                        Particle particle = new Particle(game, currentPickBlock.getX() * tileSize + 32 + game.cameraX, currentPickBlock.getY() * tileSize + 32 + game.cameraY, direction, speed);
//
//                        // Додаємо частинку до системи частинок
//                        game.particleSystem.addParticle(particle);
//                    }
                }
            }
        }
    }

    public void handleMouseClick(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            GameObject gameObject = game.player.inventory.get(game.guiManager.gameUi.currentSlot);
            if (gameObject == null) {
                return;
            }

            Block gameObject1 = (Block) gameObject;
            gameObject1.setX(currentPickBlock.getX());
            gameObject1.setY(currentPickBlock.getY());
            Block bl = new Block(gameObject1);
            game.world.setBlock(bl, currentPickBlock.getX(), currentPickBlock.getY());

            gameObject1.removeCount();
            if (gameObject.stackCount == 0) {
                game.player.inventory.remove(game.guiManager.gameUi.currentSlot);;
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            interactWorldBlock();
        }

    }
    public void handleMouseMove(MouseEvent e) {
        currentPickBlock = game.world.getTileScreen(e.getX() - game.cameraX, e.getY() - game.cameraY);
    }
    @Override
    public void handleMouseWheel(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            // up
            if (currentSlot < 8){
                currentSlot++;
            } else {
                currentSlot = 0;
            }
        } else {
            // down
            if (currentSlot > 0){
                currentSlot--;
            } else {
                currentSlot = 8;
            }
        }
    }

    private void drawHotBar(Graphics2D g2) {
        slot1.draw(g2);
        slot2.draw(g2);
        slot3.draw(g2);
        slot4.draw(g2);
        slot5.draw(g2);
        slot6.draw(g2);
        slot7.draw(g2);
        slot8.draw(g2);
        slot9.draw(g2);
    }
}

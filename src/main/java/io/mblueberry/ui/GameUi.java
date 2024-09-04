package io.mblueberry.ui;

import io.mblueberry.object.items.GameObject;
import io.mblueberry.Game;
import io.mblueberry.ui.component.Slot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.Game.tileSize;

public class GameUi implements IBaseUi {

    private Game game;
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
    public GameUi(Game game) {
        this.game = game;
        createHotBar();
        currentSlot = 0;
    }

    private void createHotBar() {
        int y = 50;
        int x = 50;
        slot1 = new Slot(x, y, 64, 64);
        slots.add(slot1);
        x+=15 + 64;
        slot2 = new Slot(x, y, 64, 64);
        slots.add(slot2);
        x+=15 + 64;
        slot3 = new Slot(x, y, 64, 64);
        slots.add(slot3);
        x+=15 + 64;
        slot4 = new Slot(x, y, 64, 64);
        slots.add(slot4);
        x+=15 + 64;
        slot5 = new Slot(x, y, 64, 64);
        slots.add(slot5);
        x+=15 + 64;
        slot6 = new Slot(x, y, 64, 64);
        slots.add(slot6);
        x+=15 + 64;
        slot7 = new Slot(x, y, 64, 64);
        slots.add(slot7);
        x+=15 + 64;
        slot8 = new Slot(x, y, 64, 64);
        slots.add(slot8);
        x+=15 + 64;
        slot9 = new Slot(x, y, 64, 64);
        slots.add(slot9);

    }


    @Override
    public void update() {
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            slot.setSelected(false);
        }
        Slot slot = slots.get(currentSlot);
        slot.setSelected(true);
    }

    public void handleMouse(MouseEvent e){
        slot1.handleMouseEvent(e);
        slot2.handleMouseEvent(e);
        slot3.handleMouseEvent(e);
        slot4.handleMouseEvent(e);
        slot5.handleMouseEvent(e);
        slot6.handleMouseEvent(e);
        slot7.handleMouseEvent(e);
        slot8.handleMouseEvent(e);
        slot9.handleMouseEvent(e);
    }

    @Override
    public void draw(Graphics2D g2) {
        drawGameObjects(g2);
        drawHotBar(g2);
    }

    private void drawGameObjects(Graphics2D g2) {
        int x = 50;
        int y = 50;
        for (int i = 0; i < 9; i++) {
            GameObject gameObject = game.player.inventory.get(i);
            if (gameObject != null) {
                g2.drawImage(gameObject.icon, x, y, tileSize, tileSize, null);
            }
            x += 15 + 64;
        }

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
//        System.out.println("[DRAW] drawHotBar");
//        System.out.println("[DRAW] "+slot1.x);
//        System.out.println("[DRAW] "+slot1.y);
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

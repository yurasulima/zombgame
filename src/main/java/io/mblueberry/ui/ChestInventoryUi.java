package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.object.block.Chest;
import io.mblueberry.object.items.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ChestInventoryUi implements IBaseUi {
    private Game game;
    BufferedImage inventoryUi;
    public Chest chest;
    //NinePatch n = new NinePatch("/ui/item_background.9.png");
    public ChestInventoryUi(Game game) {
        this.game = game;

        try {
            inventoryUi = ImageIO.read(ChestInventoryUi.class.getResourceAsStream("/ui/chest_inv.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {


        Color c1=new Color(0f,0f,0f,0.5f );
        g2.setColor(c1);
        g2.fillRect(0, 0, 2000, 1700);
        Color c=new Color(0f,0f,0f,0.0f );
        g2.setColor(c);
        g2.drawImage(inventoryUi, 150, 150, 594, 470, null);
        drawInventory(g2);
        drawChestInventory(g2, chest);
    }

    private void drawInventory(Graphics2D g) {
        int startX = 150 + 5; // Початковий X
        int startY = 150 + 5 + 190 + 9; // Початковий Y
        int slotSize = 64; // Розмір одного слота (ширина і висота квадрата)
        int padding = 1; // Відступ між слотами
        int x = startX;
        int y = startY;
        List<GameObject> inventoryItems = game.player.inventory.getAll();
        for (int i = 0; i < inventoryItems.size(); i++) {
            GameObject item = inventoryItems.get(i);
            if (item != null && item.getIcon() != null) {
                g.drawImage(item.getIcon(), x + padding, y + padding, slotSize - padding * 2, slotSize - padding * 2, null);
            }
            x += slotSize + padding;
            if ((i + 1) % 9 == 0) {
                x = startX;
                y += slotSize + padding;
            }
        }
    }

    private void drawChestInventory(Graphics2D g, Chest chest) {
        int startX = 150 + 5; // Початковий X
        int startY = 150 + 5; // Початковий Y
        int slotSize = 64; // Розмір одного слота (ширина і висота квадрата)
        int padding = 1; // Відступ між слотами
        int x = startX;
        int y = startY;
        List<GameObject> inventoryItems = chest.container.getAll();
        for (int i = 0; i < inventoryItems.size(); i++) {
            GameObject item = inventoryItems.get(i);
            if (item != null && item.getIcon() != null) {
                g.drawImage(item.getIcon(), x + padding, y + padding, slotSize - padding * 2, slotSize - padding * 2, null);
            }
            x += slotSize + padding;
            if ((i + 1) % 9 == 0) {
                x = startX;
                y += slotSize + padding;
            }
        }
    }


    @Override
    public void handleMouseWheel(MouseWheelEvent e) {

    }
}

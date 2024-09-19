package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.core.object.item.GameObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static io.mblueberry.Game.TILE_SIZE;


public class InventoryUi implements IBaseUi {
    private Game game;
    private final BufferedImage inventoryUi;
    private int row;
    private int column;
    public InventoryUi(Game game) {
        this.game = game;

        try {
            inventoryUi = ImageIO.read(InventoryUi.class.getResourceAsStream("/textures/ui/inv.png"));
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
        g2.drawImage(inventoryUi, 150, 150, 594, 271, null);
        drawInventory(g2);
        drawInventoryItemCounts(g2);
        drawSelector(g2);
    }

    private void drawSelector(Graphics2D g2) {
        int startX = 150 + 5; // Початковий X
        int startY = 150 + 5; // Початковий Y
        int addX = TILE_SIZE * row + (row);
        int addY = TILE_SIZE * column + (column);
        if (column < 4 && row < 9) {

            g2.drawImage(game.player.inventory.selector, startX + addX, startY + addY, 64, 64, null);
//            g2.setColor(Color.GREEN);
//            g2.drawRect(startX + addX, startY + addY, 64, 64);
        }
    }

    private void drawInventory(Graphics2D g) {
        int startX = 150 + 5; // Початковий X
        int startY = 150 + 5; // Початковий Y
        int slotSize = 64; // Розмір одного слота (ширина і висота квадрата)
        int padding = 1; // Відступ між слотами
        int x = startX;
        int y = startY;
        List<GameObject> inventoryItems = game.player.inventory.getAll();
        for (int i = 0; i < inventoryItems.size(); i++) {
            GameObject item = inventoryItems.get(i);
            if (item != null && item.getTexture() != null) {
                g.drawImage(item.getTexture(), x + padding, y + padding, slotSize - padding * 2, slotSize - padding * 2, null);
            }
            x += slotSize + padding;
            if ((i + 1) % 9 == 0) {
                x = startX;
                y += slotSize + padding;
            }
        }
    }
    private void drawInventoryItemCounts(Graphics2D g) {
        int startX = 150 + 5; // Початковий X
        int startY = 150 + 5; // Початковий Y
        int slotSize = 64; // Розмір одного слота (ширина і висота квадрата)
        int padding = 1; // Відступ між слотами
        int x = startX;
        int y = startY;
        List<GameObject> inventoryItems = game.player.inventory.getAll();
        for (int i = 0; i < inventoryItems.size(); i++) {
            GameObject item = inventoryItems.get(i);
            if (item != null && item.getTexture() != null) {
                if (item.stackCount > 1) {
                    g.setColor(Color.WHITE);
                    g.setFont(g.getFont().deriveFont(18F));
                    g.drawString(String.valueOf(item.stackCount), x + padding + 42, y + padding + 60);
                }
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

    @Override
    public void handleMouse(MouseEvent e) {

    }

    public void handleMoveMouse(MouseEvent e) {
        int startX = 150 + 5;
        int startY = 150 + 5;
        int cellSize = 64;
        int padding = 1;
        int x = e.getX();
        int y = e.getY();
        if (x > startX && y > startY) {
            row = (x - startX) / (cellSize + padding);
            column = (y - startY) / (cellSize + padding);
        }
    }

}

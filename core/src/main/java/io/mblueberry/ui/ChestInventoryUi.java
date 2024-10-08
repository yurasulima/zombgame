package io.mblueberry.ui;

import io.mblueberry.Game;
import io.mblueberry.object.block.ChestBlock;
import io.mblueberry.object.item.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static io.mblueberry.Const.TILE_SIZE;

public class ChestInventoryUi implements IBaseUi {
    private Game game;
    private BufferedImage inventoryUi;
    private int row;
    private int column;
    public ChestBlock chestBlock;
    public GameObject selectedGameObject;
    public int selectedIndex;
    public GameObject cursorGameObject;
    private int mouseX;
    private int mouseY;
    private boolean chestContainer;

    //NinePatch n = new NinePatch("/ui/item_background.9.png");
    public ChestInventoryUi(Game game) {
        this.game = game;

        try {
            inventoryUi = ImageIO.read(ChestInventoryUi.class.getResourceAsStream("/textures/ui/chest_inv.png"));
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
        drawInventoryItemCount(g2);
        drawChestInventory(g2, chestBlock);
        drawChestInventoryItemCount(g2, chestBlock);
        drawSelector(g2);
        if (cursorGameObject != null) {
            g2.drawImage(cursorGameObject.texture, mouseX, mouseY, TILE_SIZE, TILE_SIZE, null);
        }
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
    private void drawInventoryItemCount(Graphics2D g) {
        int startX = 150 + 5; // Початковий X
        int startY = 150 + 5 + 190 + 9; // Початковий Y
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

    private void drawChestInventory(Graphics2D g, ChestBlock chestBlock) {
        int startX = 150 + 5;
        int startY = 150 + 5;
        int slotSize = 64;
        int padding = 1;
        int x = startX;
        int y = startY;
        List<GameObject> inventoryItems = chestBlock.container.getAll();
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
    private void drawChestInventoryItemCount(Graphics2D g, ChestBlock chestBlock) {
        int startX = 150 + 5;
        int startY = 150 + 5;
        int slotSize = 64;
        int padding = 1;
        int x = startX;
        int y = startY;
        List<GameObject> inventoryItems = chestBlock.container.getAll();
        for (int i = 0; i < inventoryItems.size(); i++) {
            GameObject item = inventoryItems.get(i);
            if (item != null && item.getTexture() != null) {
                if (item.stackCount > 1) {
                    g.setColor(Color.WHITE);
                    g.setFont(g.getFont().deriveFont(18F));
                    g.drawString(String.valueOf(item.stackCount), x + padding + 50, y + padding + 60);
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


    private void drawSelector(Graphics2D g2) {
        int startX = 150 + 5;
        int startY = 150 + 5;
        int addX = TILE_SIZE * column + (column);
        int addY = TILE_SIZE * row + (row);
        int columns = 9;
        int chestRows = 3;

        if (row < 7 && column < 9) {
            if (row > 2) {
                addY += 5;
            }
            if (row < chestRows) {
                selectedIndex = row * columns + column;
                selectedGameObject = chestBlock.container.get(selectedIndex);
                chestContainer = true;
            } else {
                int playerRow = row - chestRows;
                selectedIndex = playerRow * columns + column;
                selectedGameObject = game.player.inventory.get(selectedIndex);
                chestContainer = false;
            }

            g2.setColor(Color.GREEN);
            g2.drawRect(startX + addX, startY + addY, 64, 64);
        }
    }



    public void handleClickMouse(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            //в слоту є предмет
            if (selectedGameObject != null) {
                // на курсорі є предмет
                if (cursorGameObject != null) {
                    //додаєм з курсора в слот
                    if (chestContainer) {
                        chestBlock.container.addAt(cursorGameObject, selectedIndex);
                    } else {
                        game.player.inventory.addAt(cursorGameObject, selectedIndex);
                    }
                    if (cursorGameObject.stackCount <= 0) {
                        cursorGameObject = null;
                    }
                } else {
                    //берем предмет в курсор і забираєм з сундука або інвента
                    cursorGameObject = selectedGameObject;
                    if (chestContainer) {
                        chestBlock.container.remove(selectedIndex);
                    } else {
                        game.player.inventory.remove(selectedIndex);
                    }
                }
            } else

            //в слоту нема предмета
            if (selectedGameObject == null) {
                // на курсорі є предмет
                if (cursorGameObject != null) {
                    //кладем предмет в слот та забираєм з курсора
                    if (chestContainer) {
                        chestBlock.container.addAt(cursorGameObject, selectedIndex);
                    } else {
                        game.player.inventory.addAt(cursorGameObject, selectedIndex);
                    }
                    cursorGameObject = null;
                }

                // на курсорі нема предмета
                if (cursorGameObject == null) {

                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {

        }
    }

    public void handleMoveMouse(MouseEvent e) {
        int startX = 150 + 5;
        int startY = 150 + 5;
        int cellSize = 64;
        int padding = 1;
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX > startX && mouseY > startY) {
            column = (mouseX - startX) / (cellSize + padding);
            row = (mouseY - startY) / (cellSize + padding);
        }
    }
}

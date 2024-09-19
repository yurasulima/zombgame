package io.mblueberry.mapseditor;

import com.google.gson.Gson;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class EditorUi implements MouseListener, IBaseUi {
    public Button btnCreateMap;
    public Button btnLoadMap;
    public Button btnSaveMap;
    public MapsEditor mapsEditor;
    public Tile cursorTile;

    int startX = 5;
    int startY = 200;
    int endX = 6 * TILESET_SIZE + 5;
    int endY = 30 * TILESET_SIZE + 5;
    private int mouseX;
    private int mouseY;
    public static int TILESET_SIZE = 32;
    private int column;
    private int row;
    private int selectedIndex;
    private Tile selectedTile;

    public EditorUi(MapsEditor mapsEditor) {
        this.mapsEditor = mapsEditor;

        this.btnCreateMap = new Button(17, 10, 180, 50, "New map");
        this.btnLoadMap = new Button(17, 70, 180, 50, "Load map");
        this.btnSaveMap = new Button(17, 130, 180, 50, "Save map");
        btnCreateMap.setOnClick(() -> {
            mapsEditor.map = new Map(mapsEditor);
            System.out.println("New map created!");
        });
        btnLoadMap.setOnClick(() -> {
            mapsEditor.map = new Map(mapsEditor);
            loadMap();
            System.out.println("Map loaded!");
            mapsEditor.map.updateTiles();
        });
        btnSaveMap.setOnClick(() -> {
            saveMap();
            System.out.println("Map saved!");
        });
    }

    private void loadMap() {
        Gson gson = new Gson();
        try {
            File f = new File("testMap.json");
            try (FileInputStream is = new FileInputStream(f)) {
                String content = new String(is.readAllBytes());
                mapsEditor.map.worldInfo = gson.fromJson(content, WorldInfo.class);
                int maxWorldColumn = mapsEditor.map.worldInfo.getSize();
                int maxWorldRow = mapsEditor.map.worldInfo.getSize();
                mapsEditor.map.map = new Tile[3][maxWorldColumn][maxWorldRow];
                List<Integer> mapData = mapsEditor.map.worldInfo.getMap();
                for (int row = 0; row < maxWorldRow; row++) {
                    for (int column = 0; column < maxWorldColumn; column++) {
                        int index = row * maxWorldColumn + column;

                        int mapBlockIndex = mapData.get(index);
                        String blockType;
                        if (mapsEditor.map.worldInfo.getBlocks().size() > mapBlockIndex) {
                            blockType = mapsEditor.map.worldInfo.getBlocks().get(mapBlockIndex);
                        } else {
                            blockType = "holder";
                        }
                        System.out.println("blockType = " + blockType);
                        Optional<Tile> bl = mapsEditor.map.tiles.stream().filter(tile -> tile.name.equals(blockType)).findFirst();
                        Tile actual = bl.orElse(new Tile(mapsEditor.map.holder, "holder"));
                        Tile tile = new Tile(actual);
                        tile.setPos(column, row);
                        mapsEditor.map.map[0][column][row] = tile;
                    }
                }

                System.out.println("World " + mapsEditor.map.worldInfo.getName() + " loaded, size: " + maxWorldColumn + "x" + maxWorldRow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("mapsEditor = " + Arrays.deepToString(mapsEditor.map.map));
    }

    private void saveMap() {
        Map map = mapsEditor.map;
        List<String> names = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < map.tiles.size(); i++) {
            names.add(map.tiles.get(i).name);
        }
        for (int y = 0; y < mapsEditor.map.worldInfo.getSize(); y++) {
            for (int x = 0; x < mapsEditor.map.worldInfo.getSize(); x++) {
                Tile tile = map.map[0][x][y];
                Tile realTile = map.tiles.stream().filter(tile1 -> tile.name.equals(tile1.name)).findFirst().orElseThrow();
                int index = map.tiles.indexOf(realTile);
                indexs.add(index);
            }
        }
        String name = "TestMap";
        WorldInfo worldInfo = new WorldInfo();
        worldInfo.setMap(indexs);
        worldInfo.setBlocks(names);
        worldInfo.setSize(mapsEditor.map.worldInfo.getSize());
        worldInfo.setName(name);

        Gson gson = new Gson();
        String data = gson.toJson(worldInfo);
        File mapFile = new File("testMap.json");
//        int counter = 1;
//        while (mapFile.exists()) {
//            String newFileName = "newMap_" + counter + ".json";
//            mapFile = new File(newFileName);
//            counter++;
//        }

        // Запис JSON в файл
        try (FileWriter writer = new FileWriter(mapFile)) {
            writer.write(data);
            System.out.println("Успішно записано JSON дані у файл: " + mapFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        Color c = new Color(89, 32, 78);
        g2.setColor(c);
        g2.fillRect(0, 0, 214, 2000);
        btnCreateMap.draw(g2);
        btnLoadMap.draw(g2);
        btnSaveMap.draw(g2);
        if (mapsEditor.map != null) {
            drawTileSet(g2);
            drawSelector(g2);

            if (cursorTile != null) {
                g2.drawImage(cursorTile.texture, mouseX, mouseY, TILESET_SIZE, TILESET_SIZE, null);
            }
        }

    }

    private void handleTileSetMouse(MouseEvent e) {
        if (mouseX > startX && mouseY > startY && mouseX < endX && mouseY < endY) {
            if (selectedTile != null) {
                cursorTile = selectedTile;
            }
        }
    }

    private void drawTileSet(Graphics2D g2) {
        int x = 5;
        int y = 200;
        int column = 0;
        for (int i = 0; i < mapsEditor.map.tiles.size(); i++) {
            g2.drawImage(mapsEditor.map.tiles.get(i).texture, x + column, y, TILESET_SIZE, TILESET_SIZE, null);
            column += 1 + TILESET_SIZE;
            if (column > (TILESET_SIZE * 5 + 5)) {
                y += TILESET_SIZE + 2;
                column = 0;
            }
        }
    }

    @Override
    public void update() {
        if (mapsEditor.keyHandler.upPressed) {
            mapsEditor.cameraY += 5;
        }
        if (mapsEditor.keyHandler.downPressed) {
            mapsEditor.cameraY -= 5;
        }
        if (mapsEditor.keyHandler.rightPressed) {
            mapsEditor.cameraX -= 5;
        }
        if (mapsEditor.keyHandler.leftPressed) {
            mapsEditor.cameraX += 5;
        }
    }


    public void mouseMove(MouseEvent e) {
        int padding = 1;
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX > startX && mouseY > startY && mouseX < endX && mouseY < endY) {
            column = (mouseX - startX) / (TILESET_SIZE + padding);
            row = (mouseY - startY) / (TILESET_SIZE + padding);
        }
    }

    private void drawSelector(Graphics2D g2) {
        if (mouseX > startX && mouseY > startY && mouseX < endX && mouseY < endY) {
            int addX = TILESET_SIZE * column + column;
            int addY = TILESET_SIZE * row + row + row;

            int columns = 6;
            if (row < 30 && column < 6) {

                selectedIndex = row * columns + column;
                if (selectedIndex < mapsEditor.map.tiles.size()) {

                    selectedTile = mapsEditor.map.tiles.get(selectedIndex);
                    g2.setColor(Color.GREEN);
                    g2.drawRect(startX + addX, startY + addY, TILESET_SIZE, TILESET_SIZE);
                }
            }
        }
    }


    public void mousePressed(MouseEvent e) {
        btnCreateMap.handleMouseEvent(e);
        btnLoadMap.handleMouseEvent(e);
        btnSaveMap.handleMouseEvent(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        btnCreateMap.handleMouseEvent(e);
        btnCreateMap.handleMouseEvent(e);
        btnLoadMap.handleMouseEvent(e);
        btnSaveMap.handleMouseEvent(e);
        mapsEditor.map.handleMouse(e);
        handleTileSetMouse(e);


    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

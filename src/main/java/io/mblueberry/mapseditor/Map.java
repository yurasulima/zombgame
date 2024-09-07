package io.mblueberry.mapseditor;

import com.google.gson.Gson;
import io.mblueberry.Game;
import io.mblueberry.core.BlockLoader;
import io.mblueberry.object.block.Block;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.Game.tileSize;

public class Map implements IBaseUi {
    public static int TILE_SIZE = 64;
    public Tile[][][] map;
    public List<Tile> tiles = new ArrayList<>();
    public Tile currentPickBlock;
    public MapsEditor mapsEditor;

    BufferedImage holder;


    public Map(MapsEditor mapsEditor) {
        try {
            holder = ImageIO.read(getClass().getResourceAsStream("/editor/tiles/holder.png"));
        } catch (IOException e) {
            holder = null;
            throw new RuntimeException(e);
        }
        loadTiles();

        this.mapsEditor = mapsEditor;
        this.map = new Tile[3][100][100];
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Tile holders = new Tile(holder, "holder");
                holders.setPos(x, y);
                map[0][x][y] = holders;
            }
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                Tile tile = map[0][x][y];
                g2.drawImage(tile.texture, x * TILE_SIZE + mapsEditor.cameraX, y * TILE_SIZE + mapsEditor.cameraY, TILE_SIZE, TILE_SIZE, null);
            }
        }
        if (currentPickBlock != null) {
            g2.drawRect((currentPickBlock.x * TILE_SIZE) + mapsEditor.cameraX, (currentPickBlock.y * TILE_SIZE) + mapsEditor.cameraY, TILE_SIZE, TILE_SIZE);

        }
    }


    public void handleMouse(MouseEvent e) {
        if (mapsEditor.editorUi.cursorTile != null && currentPickBlock != null) {
            int y = currentPickBlock.y;
            int x = currentPickBlock.x;
            Tile temp = new Tile(mapsEditor.editorUi.cursorTile.texture, mapsEditor.editorUi.cursorTile.name);
            temp.setPos(x, y);
            map[0][x][y] = temp;

        }
    }
    public void handleMouseMove(MouseEvent e) {
        currentPickBlock = getTileScreen(e.getX() - mapsEditor.cameraX, e.getY() - mapsEditor.cameraY);

    }

    public Tile getTileScreen(int screenX, int screenY) {
        int x = screenX / TILE_SIZE;
        int y = screenY / TILE_SIZE;
        if (x < 0 || y < 0) {
            return null;
        }
        Tile tile = map[0][x][y];
        if (tile != null) {
            return tile;
        }
        return null;
    }

    @Override
    public void update() {

    }


    public void addTile() {

    }

    public void removeTile() {

    }

    public void loadTiles() {
        try {
            URL resourceUrl = BlockLoader.class.getClassLoader().getResource("editor/tiles");
            if (resourceUrl != null) {
                Path blocksPath = Paths.get(resourceUrl.toURI());
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(blocksPath, "*.png")) {
                    for (Path entry : stream) {
                        File file = entry.toFile();
                        BufferedImage texture = ImageIO.read(file);
                        tiles.add(new Tile(texture, file.getName().replace(".png", "")));
                        System.out.println("Load tileset = " + file.getName());
                    }
                }
            } else {
                System.out.println("Директорію 'blocks' не знайдено.");
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {

    }

    public void load() {

    }
}

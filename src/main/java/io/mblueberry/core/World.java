package io.mblueberry.core;

import com.google.gson.Gson;
import io.mblueberry.Game;
import io.mblueberry.object.bullet.Bullet;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.items.AxeItem;
import io.mblueberry.object.items.GameObject;
import io.mblueberry.object.items.KeyItem;
import io.mblueberry.object.entity.monsters.SlimeMob;
import io.mblueberry.object.entity.npc.OldManNPC;
import io.mblueberry.object.entity.Player;
import io.mblueberry.object.block.Block;
import io.mblueberry.model.WorldInfo;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

import static io.mblueberry.Const.*;
import static io.mblueberry.util.Utils.calculateDistance;

public class World {


    Game game;
    public List<Block> blocks;
    public Block[][][] mapTileNum;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public final ArrayList<Entity> entities = new ArrayList<>();
    public final ArrayList<GameObject> gameObjects = new ArrayList<>();
    public Block currentPickBlock;
    //public List<Block> blocks;
    public Entity npc;
    public Block defaultBlock;
    public World(Game game, Player player) {
        this.game = game;
        BlockLoader blockLoader = new BlockLoader();
        defaultBlock = new Block("tree");
        defaultBlock.setCollision(false);
        defaultBlock.setInteract(false);
        defaultBlock.setupImage();
        Dimension dim = getMapSize("/maps/world2.txt");
//        this.blocks = new Block[50];

        blocks = blockLoader.getBlocks();
        mapTileNum = new Block[WORLD_LAYERS][dim.width][dim.height];
//        getTileImage();
        loadMapJson("/maps/world.json");


//        Entity key = new KeyItem(game);
//        Entity oldMan = new OldManNPC(game, 21, 21);

        entities.add(new OldManNPC(game, 25, 25));
        entities.add(new OldManNPC(game, 22, 22));
        entities.add(new OldManNPC(game, 10, 10));
        entities.add(new SlimeMob(game, 10, 8));
        entities.add(new SlimeMob(game, 19, 35));
        entities.add(new SlimeMob(game, 21, 36));
        entities.add(new SlimeMob(game, 23, 35));
        entities.add(new SlimeMob(game, 19, 38));
        entities.add(new SlimeMob(game, 21, 39));

        AxeItem axe = new AxeItem();
        axe.spawn(game, 21, 20);
        gameObjects.add(axe);

        KeyItem keyItem = new KeyItem();
        keyItem.spawn(game, 21, 37);
        gameObjects.add(keyItem);

        entities.add(player);

    }

//    public void getTileImage() {
//        setup(0, "grass", false);
//        setup(1, "wall", true);
//        setup(2, "water", true);
//        setup(3, "earth", false);
//        setup(4, "tree", true);
//        setup(5, "sand", false);
//        setup(6, "sand", false);
//        setup(7, "sand", false);
//        setup(8, "sand", false);
//        setup(9, "sand", false);
//        setup(10, "grass00", false);
//        setup(11, "grass01", false);
//        setup(12, "water00", true);
//        setup(13, "water01", true);
//        setup(14, "water02", true);
//        setup(15, "water03", true);
//        setup(16, "water04", true);
//        setup(17, "water05", true);
//        setup(18, "water06", true);
//        setup(19, "water07", true);
//        setup(20, "water08", true);
//        setup(21, "water09", true);
//        setup(22, "water10", true);
//        setup(23, "water11", true);
//        setup(24, "water12", true);
//        setup(25, "water13", true);
//        setup(26, "road00", false);
//        setup(27, "road01", false);
//        setup(28, "road02", false);
//        setup(29, "road03", false);
//        setup(30, "road04", false);
//        setup(31, "road05", false);
//        setup(32, "road06", false);
//        setup(33, "road07", false);
//        setup(34, "road08", false);
//        setup(35, "road09", false);
//        setup(36, "road10", false);
//        setup(37, "road11", false);
//        setup(38, "road12", false);
//        setup(39, "earth", false);
//        setup(40, "wall", true);
//        setup(41, "tree", true);
//    }

//    public void setup(int index, String imagePath, boolean collision) {
//
//        try {
//            tile[index] = new Tile();
//            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
//            tile[index].image = Utils.scaleIMage(tile[index].image, game.tileSize, game.tileSize);
//            tile[index].collision = collision;
//            tile[index].name = imagePath;
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    public Dimension getMapSize(String mapPath) {
        try (InputStream is = getClass().getResourceAsStream(mapPath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

            String line = bufferedReader.readLine();
            if (line != null) {
                String[] numbers = line.split(" ");
                int columns = numbers.length;

                int rows = 1;
                while ((line = bufferedReader.readLine()) != null) {
                    rows++;
                }

                return new Dimension(columns, rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Dimension(0, 0);
    }

    public void loadMapJson(String mapPath) {
        Gson gson = new Gson();
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            String content = new String(is.readAllBytes());
            WorldInfo worldInfo = gson.fromJson(content, WorldInfo.class);
            int maxWorldColumn = worldInfo.getSize();
            int maxWorldRow = worldInfo.getSize();
            mapTileNum = new Block[WORLD_LAYERS][maxWorldColumn][maxWorldRow];
            List<Integer> mapData = worldInfo.getMap();
            for (int row = 0; row < maxWorldRow; row++) {
                for (int column = 0; column < maxWorldColumn; column++) {
                    int index = row * maxWorldColumn + column;

                    int mapBlockIndex = mapData.get(index);
                    String blockType;
                    if (worldInfo.getBlocks().size() > mapBlockIndex) {
                        blockType = worldInfo.getBlocks().get(mapBlockIndex);
                    } else {
                        blockType = "air";
                    }
                    Optional<Block> bl = blocks.stream().filter(block -> block.getType().equals(blockType)).findFirst();
                    Block actual = bl.orElse(defaultBlock);

                    Block block = new Block(actual);
                    block.setX(column);
                    block.setY(row);
                    mapTileNum[WORLD_LAYER_WORLD][column][row] = block;
                }
            }

            System.out.println("World " + worldInfo.getName() + " loaded, size: " + maxWorldColumn + "x" + maxWorldRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            String content = Files.readString(Path.of(mapPath));
//            WorldInfo worldInfo = gson.fromJson(content, WorldInfo.class);
//            int maxWorldColumn = worldInfo.getSize();
//            int maxWorldRow = worldInfo.getSize();
//
//
//            int row = 0;
//            String line;
//
//            while (row < maxWorldRow) {
//                String[] numbers = line.split(" ");
//                for (int column = 0; column < maxWorldColumn; column++) {
//                    mapTileNum[column][row] = Integer.parseInt(numbers[column]);
//                }
//                row++;
//            }
//          //  System.out.println("Worlds " + mapPath + " loaded, size: " + mapSize.width + " " + mapSize.height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    public void update(){
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        for (int i = 0; i < entities.size(); i++) {
            Entity currentEntity = entities.get(i);

            if (currentEntity.alive && !currentEntity.dying) {
                currentEntity.update();
            }

            if (!currentEntity.alive){
                game.world.entities.remove(currentEntity);
            }

        }

    }

    public void draw(Graphics2D g2) {


        Dimension mapSize = getMapSize("/maps/world2.txt");

        int maxWorldColumn = mapSize.width;
        int maxWorldRow = mapSize.height;
        int tileSize = game.tileSize;


        for (int row = 0; row < maxWorldRow; row++) {
            for (int col = 0; col < maxWorldColumn; col++) {
                Block block = mapTileNum[WORLD_LAYER_WORLD][col][row];
                int worldX = col * tileSize;
                int worldY = row * tileSize;
                int screenX = worldX;
                int screenY = worldY;
                int offsetY = game.cameraY;
                int offsetX = game.cameraX;
                g2.drawImage(block.getImage(), screenX + offsetX, screenY + offsetY, null);
            }
        }

        for (int row = 0; row < maxWorldRow; row++) {
            for (int col = 0; col < maxWorldColumn; col++) {
                Block block = mapTileNum[WORLD_LAYER_OBJECTS][col][row];
                if (block != null) {
                    int worldX = col * tileSize;
                    int worldY = row * tileSize;
                    int screenX = worldX;
                    int screenY = worldY;
                    int offsetY = game.cameraY;
                    int offsetX = game.cameraX;
                    g2.drawImage(block.getImage(), screenX + offsetX, screenY + offsetY, null);

                }
          }
        }


        Collections.sort(entities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                int a1y = e1.screenY;
                int a2y = e2.screenY;
                if (e2.isPlayer) {
                    a2y -= game.cameraY;
                }
                if (e1.isPlayer) {
                    a1y -= game.cameraY;
                }
                return Integer.compare(a1y, a2y );

            }
        });
//        game.player.draw(g2);
        for (Entity entity : entities) {
            entity.draw(g2);

        }
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2);

        }
  //      entities.clear();
        for (Bullet bullet : bullets) {
            bullet.draw(g2);
        }
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if (bullet.isRemoved()) {
                bulletIterator.remove();
            }
        }

        if (currentPickBlock != null) {
            double v = calculateDistance(
                    currentPickBlock.getX() * game.tileSize + game.cameraX,
                    currentPickBlock.getY() * game.tileSize + game.cameraY,
                    game.player.screenX,
                    game.player.screenY);

            if (v
                > 64.0 * 5) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.GREEN);
            }
            g2.drawRect(currentPickBlock.getX() * game.tileSize  + game.cameraX, currentPickBlock.getY() * game.tileSize  + game.cameraY, 64, 64);

        }

    }
    public Block getTileScreen(int screenX, int screenY) {
        int x = screenX / game.tileSize;
        int y = screenY / game.tileSize;
        Block block = mapTileNum[WORLD_LAYER_OBJECTS][x][y];
        if (block != null) {
            return block;
        }
        Block block1 = mapTileNum[WORLD_LAYER_WORLD][x][y];
        if (block1 != null) {
            return block1;
        }

        return null;
    }
    public void setBlock(GameObject block, int x, int y) {
        mapTileNum[2][x][y] = (Block) block;
        ((Block) block).place(game, x, y);

    }


    public void replaceBlock(Block bl, Block newBlock) {

    }

    public void handleMouseMove(MouseEvent e) {
        currentPickBlock = getTileScreen(e.getX() - game.cameraX, e.getY() - game.cameraY);
    }

    public void handleMouseClickWorld(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            GameObject gameObject = game.player.inventory.get(game.guiManager.gameUi.currentSlot);
            setBlock(gameObject, currentPickBlock.getX(), currentPickBlock.getY());
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            interactWorldBlock();
        }

    }

    private void interactWorldBlock() {
        System.out.println("in = ");
        if (currentPickBlock.isInteract()) {
            currentPickBlock.interact();
        }
    }
}


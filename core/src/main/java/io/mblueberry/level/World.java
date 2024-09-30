package io.mblueberry.level;

import com.google.gson.Gson;
import io.mblueberry.BlockLoader;
import io.mblueberry.Game;
import io.mblueberry.ai.Node;
import io.mblueberry.model.WorldInfo;
import io.mblueberry.object.block.AirBlock;
import io.mblueberry.object.block.Block;
import io.mblueberry.object.block.ChestBlock;
import io.mblueberry.object.bullet.Bullet;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.Player;
import io.mblueberry.object.entity.monsters.SlimeMob;
import io.mblueberry.object.item.GameObject;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.mblueberry.Const.*;
import static io.mblueberry.Const.DEBUG;
import static io.mblueberry.Const.TILE_SIZE;

public class World {
    Game game;
    public List<Block> allBlocks;
    public Block[][][] map;
    public WorldInfo worldInfo;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Entity> entities = new ArrayList<>();
    public ArrayList<Entity> players = new ArrayList<>();
    public ArrayList<GameObject> gameObjects = new ArrayList<>();
    public List<Block> collisionPlayer = new ArrayList<>();
    public Block defaultBlock;

    public World(Game game, Player player) {
        this.game = game;
        BlockLoader blockLoader = new BlockLoader();
        defaultBlock = new AirBlock();
        allBlocks = blockLoader.getBlocks();
        loadMapJson("/maps/world.json");

        //TODO remove player
        entities.add(player);
        entities.add(new SlimeMob(game, 10, 10));

    }

    public int getMapSize() {
        return worldInfo.getSize();
    }

    public void loadMapJson(String mapPath) {
        Gson gson = new Gson();
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            String content = new String(is.readAllBytes());
            worldInfo = gson.fromJson(content, WorldInfo.class);
            int maxWorldColumn = worldInfo.getSize();
            int maxWorldRow = worldInfo.getSize();
            map = new Block[WORLD_LAYERS][maxWorldColumn][maxWorldRow];
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
                    Optional<Block> bl = allBlocks.stream().filter(block -> block.getType().equals(blockType)).findFirst();
                    Block actual = bl.orElse(defaultBlock);

                    Block block = new Block(actual);
                    block.setX(column);
                    block.setY(row);
                    map[WORLD_LAYER_WORLD][column][row] = block;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entity getPlayer(String username) {
        return players.stream()
                .filter(entity -> entity.username.equals(username))
                .findFirst()
                .orElse(null);
    }

    public void update() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        if (game.player.inventory.get(game.guiManager.gameUi.currentSlot) != null) {
            if (game.player.inventory.get(game.guiManager.gameUi.currentSlot).type.equals("pike")) {
                game.player.inventory.get(game.guiManager.gameUi.currentSlot).update();
            }
            if (game.player.inventory.get(game.guiManager.gameUi.currentSlot).type.equals("sword_normal")) {
                game.player.inventory.get(game.guiManager.gameUi.currentSlot).update();
            }
            if (game.player.inventory.get(game.guiManager.gameUi.currentSlot).type.equals("boomerang")) {
                game.player.inventory.get(game.guiManager.gameUi.currentSlot).update();
            }
        }
        int mapSize = getMapSize();



        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update();
            if (!entity.alive) {
                entity.dead();
            }
        }

        for (int i = 0; i < players.size(); i++) {
            Entity entity = players.get(i);
            entity.update();
            //  currentEntityOld.setAction();
        }
        entities.removeIf(entity -> !entity.alive);

    }

    public void draw(Graphics2D g2) {

        drawWorld(g2);

        drawObjectsLayer(g2);
        drawEntity(g2);
        if (DEBUG) {
            drawDebug(g2);

        }

    }

    private void drawObjectsLayer(Graphics2D g2) {
        int mapSize = getMapSize();
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                Block block = map[WORLD_LAYER_OBJECTS][col][row];
                if (block != null) {
                    int worldX = col * TILE_SIZE;
                    int worldY = row * TILE_SIZE;
                    int screenX = worldX;
                    int screenY = worldY;
                    int offsetY = game.cameraY;
                    int offsetX = game.cameraX;
                    g2.drawImage(block.getTexture(), screenX + offsetX, screenY + offsetY, null);
                    if (block.isCollision()) {
                        block.solidArea.x = worldX + offsetX + block.defaultSolidArea.x;
                        block.solidArea.y = worldY + offsetY + block.defaultSolidArea.y;
                    }
                    if (DEBUG && block.isCollision()) {
                        g2.drawRect(block.solidArea.x, block.solidArea.y, block.defaultSolidArea.width, block.defaultSolidArea.height);
                    }
                }
            }
        }
    }

    private void drawDebug(Graphics2D g2) {
        int mapSize = getMapSize();
        drawPath(g2);


        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                Block block = map[WORLD_LAYER_WORLD][col][row];
                if (block.isCollision()) {
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawRect(col * TILE_SIZE + game.cameraX + block.blockCollision.x, row * TILE_SIZE + game.cameraY + block.blockCollision.y, block.blockCollision.width, block.blockCollision.height);

                }
            }
        }


        for (Block block : collisionPlayer) {
            Rectangle blockCollision2 = new Rectangle();
            blockCollision2.x = block.getX() * TILE_SIZE + game.cameraX + block.blockCollision.x;
            blockCollision2.y = block.getY() * TILE_SIZE + game.cameraY + block.blockCollision.y;
            blockCollision2.width = block.blockCollision.width;
            blockCollision2.height = block.blockCollision.height;
            g2.setColor(Color.MAGENTA);
            g2.drawRect(blockCollision2.x, blockCollision2.y, blockCollision2.width, blockCollision2.height);
        }


    }

    private void drawEntity(Graphics2D g2) {
        entities.sort((e1, e2) -> {

            int a1y = e1.y;
            int a2y = e2.y;
            if (e2.type.equals("player")) {
                a2y -= game.cameraY;
            }
            if (e1.type.equals("player")) {
                a1y -= game.cameraY;
            }
            return Integer.compare(a1y, a2y);
        });


        for (Entity entity : entities) {
            entity.draw(g2);

        }
        for (Entity entity : players) {
            entity.draw(g2);

        }
    }

    private void drawWorld(Graphics2D g2) {
        int mapSize = getMapSize();

        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                Block block = map[WORLD_LAYER_WORLD][col][row];
                int worldX = col * TILE_SIZE;
                int worldY = row * TILE_SIZE;
                int offsetY = game.cameraY;
                int offsetX = game.cameraX;
                g2.drawImage(block.getTexture(), worldX + offsetX, worldY + offsetY, null);
            }
        }
    }

    private void drawPath(Graphics2D g2) {
        g2.setColor(new Color(255, 0, 0, 70));
        for (int i = 0; i < game.pathFinder.pathList.size(); i++) {
            Node path = game.pathFinder.pathList.get(i);
            int x = path.x * TILE_SIZE;
            int y = path.y * TILE_SIZE;
            g2.fillRect(x + game.cameraX, y + game.cameraY, TILE_SIZE, TILE_SIZE);

        }
    }

    public Block getTileScreen(int screenX, int screenY) {
        int x = screenX / TILE_SIZE;
        int y = screenY / TILE_SIZE;
        Block block = map[WORLD_LAYER_OBJECTS][x][y];
        if (block != null) {
            return block;
        }
        Block block1 = map[WORLD_LAYER_WORLD][x][y];
        if (block1 != null) {
            return block1;
        }

        return null;
    }

    public void setBlock(Block block, int x, int y) {
        if (block.type.equals("chest")) {
            ChestBlock chestBlock = new ChestBlock(game);
            chestBlock.place(game, x, y);

            if (map[WORLD_LAYER_WORLD][x][y] == null) {
                map[WORLD_LAYER_WORLD][x][y] = chestBlock;
            } else if (map[WORLD_LAYER_OBJECTS][x][y] == null) {
                map[WORLD_LAYER_OBJECTS][x][y] = chestBlock;
            }
        } else {
            map[WORLD_LAYER_WORLD][x][y] = block;
//            if (mapTileNum[WORLD_LAYER_WORLD][x][y] == null) {
//                mapTileNum[WORLD_LAYER_WORLD][x][y] = block;
//            } else  if (mapTileNum[WORLD_LAYER_OBJECTS][x][y] == null) {
//                mapTileNum[WORLD_LAYER_OBJECTS][x][y] = block;
//            }
        }

    }

}


package io.mblueberry.level;

import io.mblueberry.Game;
import io.mblueberry.gen.NoiseGenerator;
import io.mblueberry.object.block.AirBlock;
import io.mblueberry.object.block.Block;
import io.mblueberry.object.bullet.Bullet;
import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.Player;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.mblueberry.Const.CHUNK_SIZE;
import static io.mblueberry.Const.TILE_SIZE;

@Data
public class Level {
    int seed;
    Game game;
    public NoiseGenerator noiseGenerator;
    List<Chunk> chunks = new ArrayList<>();

    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Entity> entities = new ArrayList<>();
    public ArrayList<Entity> players = new ArrayList<>();

    public Level(Game game) {
        this.game = game;
        seed = new Random().nextInt(Integer.MAX_VALUE);
        noiseGenerator = new NoiseGenerator(seed);

//        chunks.add(new Chunk(this, 1, 1));
//        chunks.add(new Chunk(this, 1, 2));
//        chunks.add(new Chunk(this, 1, 3));
//        chunks.add(new Chunk(this, 1, 4));
//
//        chunks.add(new Chunk(this, 2, 1));
//        chunks.add(new Chunk(this, 2, 2));
//        chunks.add(new Chunk(this, 2, 3));
//        chunks.add(new Chunk(this, 2, 4));
//
//        chunks.add(new Chunk(this, 3, 1));
//        chunks.add(new Chunk(this, 3, 2));
//        chunks.add(new Chunk(this, 3, 3));
//        chunks.add(new Chunk(this, 3, 4));

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
        for (Chunk chunk : chunks) {
            chunk.update();
        }

        //updatePlayerChunks();
    }
    private ExecutorService executor = Executors.newFixedThreadPool(4); // Створіть один раз

    public void draw(Graphics2D g2) {
//        for (Chunk chunk : chunks) {
//            chunk.draw(g2);
//        }

        Chunk playerChunk = findPlayerChunk();
        int chunkX = playerChunk.chunkX;
        int chunkY = playerChunk.chunkY;
        playerChunk.draw(g2);
        getChunk(chunkX, chunkY +1).draw(g2);
        getChunk(chunkX, chunkY -1).draw(g2);
        getChunk(chunkX +1, chunkY).draw(g2);
        getChunk(chunkX -1, chunkY).draw(g2);

        getChunk(chunkX -1, chunkY -1).draw(g2);
        getChunk(chunkX +1, chunkY +1).draw(g2);
        getChunk(chunkX -1, chunkY +1).draw(g2);
        getChunk(chunkX +1, chunkY -1).draw(g2);

        drawEntity(g2);
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

    public Chunk findPlayerChunk() {
        int chunkX = (game.player.x - game.cameraX) / (CHUNK_SIZE * TILE_SIZE);
        int chunkY = (game.player.y - game.cameraY) / (CHUNK_SIZE * TILE_SIZE);
        return getChunk(chunkX, chunkY);
    }

    private Chunk getChunk(int chunkX, int chunkY) {
        return chunks.stream()
                .filter(chunk -> chunk.chunkX == chunkX && chunk.chunkY == chunkY)
                .findFirst()
                .orElseGet(() -> {
                    Chunk generatedChunk = new Chunk(this, chunkX, chunkY);
                    chunks.add(generatedChunk);
                    return generatedChunk;
                });
    }


    public void spawn(Entity entity) {
        entities.add(entity);
    }

    public void spawn(Player player) {
        players.add(player);
    }

    public Block getBlock(int i, int i1) {
        return new AirBlock();
    }

    public void updateLoadedChunks() {
        Chunk playerChunk = findPlayerChunk();
        int chunkX = playerChunk.chunkX;
        int chunkY = playerChunk.chunkY;

        getChunk(chunkX, chunkY +1);
        getChunk(chunkX, chunkY -1);
        getChunk(chunkX +1, chunkY);
        getChunk(chunkX -1, chunkY);
    }
}

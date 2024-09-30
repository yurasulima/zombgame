package io.mblueberry.level;

import io.mblueberry.object.block.Block;
import io.mblueberry.object.block.Grass00;
import io.mblueberry.object.block.Wall;
import io.mblueberry.object.block.Water00;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.mblueberry.Const.CHUNK_SIZE;
import static io.mblueberry.Const.DEBUG;
import static io.mblueberry.Const.TILE_SIZE;

public class Chunk {
    Block[][] blocks = new Block[CHUNK_SIZE][CHUNK_SIZE];
    public int chunkX;
    public int chunkY;
    int seed;
    Level level;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Chunk(Level level, int x, int y) {
        this.level = level;
        this.chunkX = x;
        this.chunkY = y;
        this.seed = level.getSeed();
        generateChunk();
    }

    private void generateChunk() {
        executor.submit(() -> {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                for (int y = 0; y < CHUNK_SIZE; y++) {
                    int blockX = x + (chunkX * CHUNK_SIZE);
                    int blockY = y + (chunkY * CHUNK_SIZE);
                    double noiseValue = level.noiseGenerator.generateNoise(blockX, blockY);
                    if (noiseValue < -0.4) {
                        blocks[x][y] = new Water00(blockX, blockY);
                    } else if (noiseValue > 0.4) {
                        blocks[x][y] = new Wall(blockX, blockY);
                    } else {
                        blocks[x][y] = new Grass00(blockX, blockY);
                    }
                }
            }
            onChunkGenerated();
        });
    }

    private void onChunkGenerated() {
        // Тут ви можете реалізувати логіку для сповіщення,
        // що генерація чанка завершена (наприклад, оновлення UI)
    }

    public void draw(Graphics2D g2) {
        int chunkStartX = chunkX * (CHUNK_SIZE * TILE_SIZE) + level.getGame().cameraX;
        int chunkStartY = chunkY * (CHUNK_SIZE * TILE_SIZE) + level.getGame().cameraY;
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                Block block = blocks[x][y];
                if (block != null) { // Додано перевірку на null
                    g2.drawImage(block.getTexture(), chunkStartX + (x * TILE_SIZE), chunkStartY + (y * TILE_SIZE), TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        if (DEBUG) {
            g2.setColor(Color.CYAN);
            g2.drawRect(chunkStartX, chunkStartY, CHUNK_SIZE * TILE_SIZE, CHUNK_SIZE * TILE_SIZE);
        }
    }

    public void update() {
    }

    public void shutdown() {
        executor.shutdown();
    }
}

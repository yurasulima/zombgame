package io.mblueberry.core.gen;

import com.google.gson.Gson;
import com.raylabz.opensimplex.OpenSimplexNoise; // Переконайтеся, що бібліотека підключена правильно
import io.mblueberry.core.model.WorldInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class NoiseGenerator {
    private static List<Integer> tiles;
    private OpenSimplexNoise noise;

    public NoiseGenerator(long seed) {
        noise = new OpenSimplexNoise(seed); // Ініціалізація з сідом
    }

    public double generateNoise(double x, double y) {
        return noise.getNoise2D(x, y).getValue(); // Повертає значення типу double
    }

    public static void main(String[] args) {
        tiles = new ArrayList<>();
        int[][] map = new int[100][100];
        NoiseGenerator noiseGenerator = new NoiseGenerator(83221);
        List<String> blocks = new ArrayList<>();
        blocks.add("water00");
        blocks.add("grass00");
        blocks.add("earth");
        int mapSize = 200;
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {

                double noiseValue = noiseGenerator.generateNoise( x, y);
                if (noiseValue < -0.3) {
                    tiles.add(0);
                  //  map[x][y] = 0; // Річка
                } else if (noiseValue > 0.3) {
                //   map[x][y] = 1; // Пагорб
                    tiles.add(1);
                } else {
                  //  map[x][y] = 2; // Трава
                    tiles.add(2);
                }
            }
        }

//        for (int y = 0; y < 50; y++) {
//            for (int x = 0; x < 50; x++) {
//                int a = map[0][x][y];
//                Tile realTile = map.tiles.stream().filter(tile1 -> tile.name.equals(tile1.name)).findFirst().orElseThrow();
//                int index = map.tiles.indexOf(realTile);
//                indexs.add(index);
//            }
//        }


        WorldInfo worldInfo = new WorldInfo();
        worldInfo.setBlocks(blocks);
        worldInfo.setName("blocks_old");
        worldInfo.setMap(tiles);
        worldInfo.setSize(mapSize);


        Gson gson = new Gson();
        String data = gson.toJson(worldInfo);
        File mapFile = new File("testMap.json");

        try (FileWriter writer = new FileWriter(mapFile)) {
            writer.write(data);
            System.out.println("Успішно записано JSON дані у файл: " + mapFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

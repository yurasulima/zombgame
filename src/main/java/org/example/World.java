package org.example;

import org.example.Bullets.Bullet;
import org.example.Entity.Entity;
import org.example.Entity.items.KeyItem;
import org.example.Entity.npc.OldManNPC;
import org.example.Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class World {


    Game gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Entity> entities = new ArrayList<>();

    public Entity npc;
    public World(Game gamePanel, Player player) {
        this.gamePanel = gamePanel;
        Dimension dim = getMapSize("/maps/world2.txt");
        this.tile = new Tile[50];
        mapTileNum = new int[dim.width][dim.height];
        getTileImage();
        loadMap("/maps/world2.txt");


        Entity key = new KeyItem(gamePanel);
        Entity oldMan = new OldManNPC(gamePanel, 21, 21);

        entities.add(new OldManNPC(gamePanel, 25, 25));
        entities.add(new OldManNPC(gamePanel, 22, 22));
        entities.add(new OldManNPC(gamePanel, 10, 10));
        entities.add(key);
        entities.add(player);

    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
        setup(6, "sand", false);
        setup(7, "sand", false);
        setup(8, "sand", false);
        setup(9, "sand", false);
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
    }

    public void setup(int index, String imagePath, boolean collision) {

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tile[index].image = Utils.scaleIMage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
            tile[index].name = imagePath;




        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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


    //    public void loadMap(String mapPath) {
//        try {
//            InputStream is = getClass().getResourceAsStream(mapPath);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//            int column = 0;
//            int row = 0;
//
//
//            while (column < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
//                String line = bufferedReader.readLine();
//                while (column < gamePanel.maxWorldColumn) {
//                    String[] numbers = line.split(" ");
//                    int num = Integer.parseInt(numbers[column]);
//                    mapTileNum[column][row] = num;
//                    column++;
//
//                }
//                if (column == gamePanel.maxWorldColumn) {
//                    column = 0;
//                    row++;
//                }
//            }
//            bufferedReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void loadMap(String mapPath) {
        Dimension mapSize = getMapSize(mapPath);
        int maxWorldColumn = mapSize.width;
        int maxWorldRow = mapSize.height;
        try (InputStream is = getClass().getResourceAsStream(mapPath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

            int row = 0;
            String line;

            while ((line = bufferedReader.readLine()) != null && row < maxWorldRow) {
                String[] numbers = line.split(" ");
                for (int column = 0; column < maxWorldColumn; column++) {
                    mapTileNum[column][row] = Integer.parseInt(numbers[column]);
                }
                row++;
            }
          //  System.out.println("Worlds " + mapPath + " loaded, size: " + mapSize.width + " " + mapSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update(){
       // gamePanel.player.update();
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public void draw(Graphics2D g2) {


        int screenCenterX = gamePanel.getWidth() / 2;
        int screenCenterY = gamePanel.getHeight() / 2;

        Dimension mapSize = getMapSize("/maps/world2.txt");

        int maxWorldColumn = mapSize.width;
        int maxWorldRow = mapSize.height;
        int tileSize = gamePanel.tileSize;


        for (int row = 0; row < maxWorldRow; row++) {
            for (int col = 0; col < maxWorldColumn; col++) {
                int tileNum = mapTileNum[col][row];
                int worldX = col * tileSize;
                int worldY = row * tileSize;
                int screenX = worldX;
                int screenY = worldY;
                int offsetY = gamePanel.cameraY;
                int offsetX = gamePanel.cameraX;
                g2.drawImage(tile[tileNum].image, screenX + offsetX, screenY + offsetY, null);
            }
        }

//        entities.add(npc);
//        entities.add(gamePanel.player);

        Collections.sort(entities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                int a1y = e1.screenY;
                int a2y = e2.screenY;
                if (e2.isPlayer) {
                    a2y -= gamePanel.cameraY;
                }
                if (e1.isPlayer) {
                    a1y -= gamePanel.cameraY;
                }
                return Integer.compare(a1y, a2y );

            }
        });
//        gamePanel.player.draw(g2);
        for (Entity entity : entities) {
            entity.draw(g2);

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


    }
    public Tile getTileScreen(int screenX, int screenY) {
        int x = screenX / gamePanel.tileSize;
        int y = screenY / gamePanel.tileSize;
        int tileNum = mapTileNum[x][y];
        return tile[tileNum];
    }


}


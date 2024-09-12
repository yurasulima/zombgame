package io.mblueberry.mapseditor;

import lombok.ToString;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@ToString
public class Tile {
    public String name;
    public BufferedImage texture;
    public int x, y;

    public Tile(BufferedImage texture, String name) {
        this.texture = texture;
        this.name = name;
    }
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(Tile newTile) {
        this.x = newTile.x;;
        this.y = newTile.y;;
        this.texture = newTile.texture;;
        this.name = newTile.name;;
    }

}

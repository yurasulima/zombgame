package io.mblueberry.mapseditor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
}

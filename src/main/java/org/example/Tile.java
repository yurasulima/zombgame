package org.example;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
    public String name = "";



    @Override
    public String toString() {
        return "Tile{" +
                "image=" + image +
                ", collision=" + collision +
                '}';
    }
}

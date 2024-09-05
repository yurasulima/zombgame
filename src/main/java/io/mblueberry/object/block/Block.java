package io.mblueberry.object.block;

import io.mblueberry.util.Utils;
import lombok.Getter;
import lombok.Setter;
import io.mblueberry.object.items.GameObject;
import io.mblueberry.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static io.mblueberry.Game.tileSize;
import static io.mblueberry.util.Utils.scaleIMage;

@Getter
@Setter
public class Block extends GameObject {
    private String type;
    private int x;
    private int y;
    private boolean collision;
    private boolean interact;
    private String texture;
    private String name;
    private transient BufferedImage image = null;
    private transient Game game;
    private transient boolean hasSpawned = false;

    public void spawn(Game game, int spawnX, int spawnY) {
        this.x = spawnX;
        this.y = spawnY;
        this.game = game;
        hasSpawned = true;
    }
    public Block(String type) {
        this.type = type;
        this.texture = type;
    }

    public Block(Block other) {
        this.type = other.type;
        this.collision = other.collision;
        this.interact = other.interact;
        this.texture = other.texture;
        this.name = other.name;
        this.image = other.image;
    }
    public void setupImage() {
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/tiles/" + texture + ".png"));
            this.image = scaleIMage(img, 64, 64);
            this.icon = scaleIMage(img, 64, 64);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load or scale image for block: " + name, e);
        }
    }

    public BufferedImage setupImage(String imageName) {
        BufferedImage scaleImage = null;
        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }

    public void interact(){}
    public void stopInteract(){}
    public void place(Game game, int x, int y){}

    public void replace(Block newBlock) {
        this.type = newBlock.type;
        this.collision = newBlock.collision;
        this.interact = newBlock.interact;
        this.texture = newBlock.texture;
        this.name = newBlock.name;
        this.image = newBlock.image;
        this.icon = newBlock.icon;
        this.hasSpawned = newBlock.hasSpawned;
        //setupImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (hasSpawned) {
            g2.drawImage(icon, x + game.cameraX, y + game.cameraY, null);
        }
    }
}

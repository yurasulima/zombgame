package io.mblueberry.object.item;

import io.mblueberry.object.entity.EntityTag;
import io.mblueberry.Game;
import io.mblueberry.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static io.mblueberry.Game.tileSize;

public class Item extends GameObject{
    protected String type;
    protected int screenX;
    protected int screenY;
    protected boolean hasSpawned = false;
    protected BufferedImage texture;
    private final EntityTag entityTag = EntityTag.ITEM;
    private Game game;

    public void spawn(Game game, int spawnX, int spawnY) {
        this.screenX = spawnX;
        this.screenY = spawnY;
        this.game = game;
        hasSpawned = true;
    }

    public BufferedImage setup(String imageName) {
        BufferedImage scaleImage = null;
        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, tileSize, tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (hasSpawned) {
            g2.drawImage(texture, screenX * tileSize + game.cameraX, screenY * tileSize + game.cameraY, null);
        }



    }
}

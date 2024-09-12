package io.mblueberry.core.object.block;

import io.mblueberry.Game;
import io.mblueberry.core.object.item.GameObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.awt.image.BufferedImage;

import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.TextureUtil.loadBlockTexture;

@ToString
@Getter
@Setter
public class Block extends GameObject {
    private int x;
    private int y;
    private boolean collision;
    private boolean interact;
    private BufferedImage texture;
    public Rectangle solidArea;
    public Rectangle defaultSolidArea;
    private Game game;

    public Block(String type) {
        this.type = type;
        this.displayName = type;
        texture = loadBlockTexture(type);
        solidArea = new Rectangle(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
        defaultSolidArea = new Rectangle(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public Block(String type, int sizeX, int sizeY) {
        this.type = type;
        this.displayName = type;
        loadBlockTexture(type, sizeX, sizeY);
        solidArea = new Rectangle(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
        defaultSolidArea = new Rectangle(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public Block(Block other) {
        this.x = other.x;
        this.y = other.y;
        this.game = other.game;
        this.type = other.type;
        this.displayName = other.displayName;
        this.collision = other.collision;
        this.interact = other.interact;
        this.texture = other.texture;
        solidArea = other.solidArea;
        defaultSolidArea = other.defaultSolidArea;
    }


    public void removeCount() {
        stackCount--;
    }

    public void addCount() {
        stackCount++;
    }

    public void update(Game game) {

        solidArea.x = x * TILE_SIZE + game.cameraX;
        solidArea.y = y * TILE_SIZE + game.cameraY;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(this.texture, x + game.cameraX, y + game.cameraY, null);

    }

}

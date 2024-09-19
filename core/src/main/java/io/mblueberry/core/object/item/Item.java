package io.mblueberry.core.object.item;

import io.mblueberry.core.object.entity.EntityTag;
import io.mblueberry.Game;
import lombok.Getter;
import lombok.Setter;
import java.awt.*;

import static io.mblueberry.Game.TILE_SIZE;

@Getter
@Setter
public abstract class Item extends GameObject{
    public int x;
    public int y;
    public final EntityTag entityTag = EntityTag.ITEM;
    public Game game;
    public boolean hasPlaced;

    public void place(Game game, int spawnX, int spawnY) {
        this.x = spawnX;
        this.y = spawnY;
        this.game = game;
        hasPlaced = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (hasPlaced) {
            g2.drawImage(texture, x * TILE_SIZE + game.cameraX, y * TILE_SIZE + game.cameraY, null);
        }
    }

    public void update() {
    }
}

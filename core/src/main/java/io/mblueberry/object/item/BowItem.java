package io.mblueberry.object.item;

import io.mblueberry.Game;
import io.mblueberry.object.entity.Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class BowItem extends Item {

    public BufferedImage texture2;
    public boolean activated = false;

    public BowItem() {
        type = "bow";
        texture = loadItemTexture("bow1");
        texture2 = loadItemTexture("bow2");
    }

    @Override
    public void draw(Graphics2D g2, Entity entity, Game game) {
        super.draw(g2, entity, game);
        double angle = Math.atan2(game.guiManager.gameUi.mouseY - entity.y, game.guiManager.gameUi.mouseX - entity.x);
        AffineTransform transform = new AffineTransform();
        transform.translate(entity.x + 32, entity.y);
        transform.rotate(angle, 0, 32);
        g2.drawImage(texture, transform, null);
    }
}

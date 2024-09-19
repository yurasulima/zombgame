package io.mblueberry.core.object.item;


import io.mblueberry.Game;
import io.mblueberry.core.anim.ItemAnimation;
import io.mblueberry.core.object.entity.Entity;
import io.mblueberry.util.TextureUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class PikeItem extends Item {
    private List<BufferedImage> animationFrames = new ArrayList<>();
    private ItemAnimation useAnimation;
    private int sizeY = 19;
    private int sizeX = 58;
    public PikeItem() {
        type = "pike";
        texture = TextureUtil.loadAnimItemTexture("/pick/pick_1", 58, 19);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/pick/pick_1", 58, 19));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/pick/pick_2", 58, 19));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/pick/pick_3", 58, 19));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/pick/pick_3", 58, 19));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/pick/pick_3", 58, 19));
        this.useAnimation = new ItemAnimation(animationFrames, 5);
    }

    @Override
    public void draw(Graphics2D g2, Entity entity, Game game) {
        super.draw(g2, entity, game);
        AffineTransform transform = new AffineTransform();
        double x = entity.x + 48;
        double y = entity.y + 10;
        transform.translate(x, y);
        double scaleX = 3.0;
        double scaleY = 3.0;
        transform.scale(scaleX, scaleY);
        double angle = Math.atan2(game.guiManager.gameUi.mouseY - (y + (19 * scaleY)), game.guiManager.gameUi.mouseX - (x + (58 * scaleX) / 2.0));
        transform.rotate(angle, 0, 19 / 2.0);
        if (!useAnimation.isFinished()) {
            g2.drawImage(useAnimation.getCurrentFrame(), transform, null);
        } else {
            g2.drawImage(texture, transform, null);
        }
    }


    @Override
    public void use() {
        if (useAnimation.isFinished()) {
            useAnimation = new ItemAnimation(animationFrames, 5);
        }
    }

    @Override
    public void update() {
        super.update();
        if (!useAnimation.isFinished()) {
            useAnimation.update();
        }
    }


}

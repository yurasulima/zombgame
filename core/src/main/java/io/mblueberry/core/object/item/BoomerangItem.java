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

public class BoomerangItem extends Item {
    private List<BufferedImage> animationFrames = new ArrayList<>();
    private ItemAnimation useAnimation;
    private double startX, startY; // початкові координати гравця
    private double x, y; // поточні координати бумеранга
    private double angle; // кут кидка в радіанах
    private double speed = 5; // швидкість руху бумеранга
    private double maxDistance = 250; // максимальна відстань польоту
    private boolean returning; // чи повертається бумеранг до гравця
    private boolean isDroped = false;
    private Rectangle solidArea;
    public BoomerangItem() {
        type = "boomerang";

        solidArea = new Rectangle(16, 32, 32, 32);

        texture = TextureUtil.loadAnimItemTexture("/boomerang/boomerang2", 16, 16);
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        animationFrames.add(texture);
        animationFrames.add(TextureUtil.loadAnimItemTexture("/boomerang/boomerang1", 16, 16));
        this.useAnimation = new ItemAnimation(animationFrames, 10);
    }

    @Override
    public void draw(Graphics2D g2, Entity entity, Game game) {
        super.draw(g2, entity, game);
        AffineTransform transform = new AffineTransform();
        double entityX = entity.x + 48;
        double entityY = entity.y + 10;
        transform.translate(x, y);
        double scaleX = 3.0;
        double scaleY = 3.0;
        transform.scale(scaleX, scaleY);
        double rotationAngle = Math.atan2(game.guiManager.gameUi.mouseY - (entityY + (19 * scaleY)), game.guiManager.gameUi.mouseX - (entityX + (58 * scaleX) / 2.0));
        transform.rotate(rotationAngle, 0, 19 / 2.0);
        if (!useAnimation.isFinished()) {
            g2.drawImage(useAnimation.getCurrentFrame(), transform, null);
        } else {
            g2.drawImage(texture, transform, null);
        }
        if (Game.DEBUG) {
            g2.draw(solidArea);
        }
    }

    private double distanceToStart() {
        return Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
    }

    @Override
    public void update() {
        super.update();
        useAnimation.update();
        if (isDroped) {
            // Обчислюємо нові координати
            double dx = speed * Math.cos(angle);
            double dy = speed * Math.sin(angle);


            if (!returning) {
                x += dx;
                y += dy;

                // Перевіряємо, чи досяг бумеранг максимальної відстані
                if (distanceToStart() >= maxDistance) {
                    returning = true; // починаємо повертати бумеранг
                }
            } else {
                // Повернення бумеранга до гравця
                x -= dx;
                y -= dy;
            }
            solidArea = new Rectangle((int) x, (int) y, 32, 32);
            if (distanceToStart() == 0) {
                isDroped = false;
            }
        }

    }

    @Override
    public void use(Entity entity) {
        //if (useAnimation.isFinished()) {
        useAnimation = new ItemAnimation(animationFrames, 5);
        this.startX = entity.x + 48; // Початкові координати (позиція гравця)
        this.startY = entity.y + 10;
        this.x = startX; // Початкові координати бумеранга збігаються з гравцем
        this.y = startY;
        this.angle = Math.atan2(entity.game.guiManager.gameUi.mouseY - startY, entity.game.guiManager.gameUi.mouseX - startX); // Визначення кута між мишкою та гравцем
        this.returning = false; // Повертаємо бумеранг
        //}
        isDroped = true;
    }
}

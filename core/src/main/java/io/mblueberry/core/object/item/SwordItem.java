package io.mblueberry.core.object.item;

import io.mblueberry.Game;
import io.mblueberry.core.anim.ItemAnimation;
import io.mblueberry.core.object.entity.Entity;
import io.mblueberry.util.TextureUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.util.TextureUtil.loadItemTexture;

public class SwordItem extends Item {

    private List<BufferedImage> animationFrames = new ArrayList<>();
    private ItemAnimation useAnimation; // Анімація використання предмета
    double angle;
    int sizeX = 29;
    int sizeY = 38;
    Polygon attackPolygon;
    public SwordItem() {
        type = "sword_normal";
        texture = loadItemTexture("sword_normal");
        attackValue = 1;
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_1", sizeX, sizeY));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_2", sizeX, sizeY));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_3", sizeX, sizeY));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_3", sizeX, sizeY));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_3", sizeX, sizeY));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_3", sizeX, sizeY));
        animationFrames.add(TextureUtil.loadAnimItemTexture("/sword/sword_3", sizeX, sizeY));
        this.useAnimation = new ItemAnimation(animationFrames, 5);
    }

    @Override
    public void use() {
        // Початок анімації
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

    @Override
    public void draw(Graphics2D g2, Entity entity, Game game) {
        super.draw(g2, entity, game);
        AffineTransform transform = new AffineTransform();
        double x = entity.x + 25;
        double y = entity.y - 30;
        transform.translate(x, y);
        double scaleX = 4.0;
        double scaleY = 4.0;
        transform.scale(scaleX, scaleY);

        if (useAnimation.isFinished()) {
            angle = Math.atan2(game.guiManager.gameUi.mouseY - y, game.guiManager.gameUi.mouseX - x);
        }
        transform.rotate(angle, 5, sizeY / 2.0);
        if (!useAnimation.isFinished()) {
            g2.drawImage(useAnimation.getCurrentFrame(), transform, null);
            swordDamage(game.player, game.world.entities, game);
            //Draw hit zone
            if (Game.DEBUG) {
               if (attackPolygon != null) {
                   g2.draw(attackPolygon);
               }
            }
        }
    }

    public void swordDamage(Entity player, List<Entity> enemies, Game game) {
        double angle = Math.atan2(game.guiManager.gameUi.mouseY - player.y, game.guiManager.gameUi.mouseX - player.x);

        double px = player.x + 35;
        double py = player.y + 30;

        // Нормалізуємо напрямок атаки
        double dirX = Math.cos(angle);
        double dirY = Math.sin(angle);
        Point2D dir = new Point2D.Double(dirX, dirY);

        // Визначаємо напрямки для побудови багатокутника
        Point2D rightDir = rotateVector(dir, Math.PI / 2);
        Point2D leftDir = rotateVector(dir, -Math.PI / 2);

        // Масштабування
        double scaleFactor = 3.0; // Збільшити полігон в 2 рази

        // Визначаємо точки багатокутника з урахуванням масштабу
        int[] xPoints = new int[] {
                (int) (px + dir.getX() * 20 * scaleFactor),
                (int) (px + rotateVector(dir, Math.PI / 8).getX() * 20 * scaleFactor),
                (int) (px + rotateVector(dir, Math.PI / 4).getX() * 20 * scaleFactor),
                (int) (px + rotateVector(dir, 3 * Math.PI / 8).getX() * 20 * scaleFactor),
                (int) (px + rightDir.getX() * 22 * scaleFactor),
                (int) (px + rightDir.getX() * 22 * scaleFactor + rotateVector(rightDir, Math.PI / 2).getX() * 6 * scaleFactor),
                (int) (px + leftDir.getX() * 22 * scaleFactor + rotateVector(leftDir, -Math.PI / 2).getX() * 6 * scaleFactor),
                (int) (px + leftDir.getX() * 22 * scaleFactor),
                (int) (px + rotateVector(dir, 3 * -Math.PI / 8).getX() * 20 * scaleFactor),
                (int) (px + rotateVector(dir, -Math.PI / 4).getX() * 20 * scaleFactor),
                (int) (px + rotateVector(dir, -Math.PI / 8).getX() * 20 * scaleFactor)
        };

        int[] yPoints = new int[] {
                (int) (py + dir.getY() * 20 * scaleFactor),
                (int) (py + rotateVector(dir, Math.PI / 8).getY() * 20 * scaleFactor),
                (int) (py + rotateVector(dir, Math.PI / 4).getY() * 20 * scaleFactor),
                (int) (py + rotateVector(dir, 3 * Math.PI / 8).getY() * 20 * scaleFactor),
                (int) (py + rightDir.getY() * 22 * scaleFactor),
                (int) (py + rightDir.getY() * 22 * scaleFactor + rotateVector(rightDir, Math.PI / 2).getY() * 6 * scaleFactor),
                (int) (py + leftDir.getY() * 22 * scaleFactor + rotateVector(leftDir, -Math.PI / 2).getY() * 6 * scaleFactor),
                (int) (py + leftDir.getY() * 22 * scaleFactor),
                (int) (py + rotateVector(dir, 3 * -Math.PI / 8).getY() * 20 * scaleFactor),
                (int) (py + rotateVector(dir, -Math.PI / 4).getY() * 20 * scaleFactor),
                (int) (py + rotateVector(dir, -Math.PI / 8).getY() * 20 * scaleFactor)
        };

        // Створюємо багатокутник атаки мечем
        attackPolygon = new Polygon(xPoints, yPoints, xPoints.length);

        // Перевіряємо перетин з ворогами
        for (Entity enemy : enemies) {
            if (attackPolygon.intersects(enemy.solidArea)) {
                // Ворога вдарили мечем, обробка пошкодження
                if (!enemy.type.equals("player")) {
                    System.out.println("Ворога " + enemy.type + " вдарили мечем, обробка пошкодження");
                }
            }
        }

        // Можна також додати звуковий ефект для удару мечем
        double range = Math.random() / 4;
        // game.playSound("sword", 1, 1 + range);
    }


    private Point2D rotateVector(Point2D vector, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double newX = vector.getX() * cos - vector.getY() * sin;
        double newY = vector.getX() * sin + vector.getY() * cos;
        return new Point2D.Double(newX, newY);
    }

    // Метод для розрахунку напрямку відштовхування
    private Point2D calculateKnockbackDirection(Entity player, Entity enemy) {
        double dx = enemy.x - x;
        double dy = enemy.y - y;
        double length = Math.sqrt(dx * dx + dy * dy);
        return new Point2D.Double(dx / length, dy / length);
    }


}


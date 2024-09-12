package io.mblueberry.core.object.bullet;

import io.mblueberry.core.object.entity.Entity;
import io.mblueberry.core.object.entity.EntityOld;
import io.mblueberry.Game;
import lombok.Getter;

import java.awt.*;

import static io.mblueberry.Game.TILE_SIZE;


public class Bullet {
    double x, y;
    double dx, dy;
    double speed;
    Game game;

    @Getter
    private boolean removed = false;
    private int timeToLive;  // Додано таймер життя кулі

    public Bullet(double x, double y, double dx, double dy, double speed, int timeToLive, Game game) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.timeToLive = timeToLive;  // Ініціалізація таймера
        this.game = game;
    }

    public void update() {
        x += (dx * speed);
        y += (dy * speed);
        timeToLive--;  // Зменшення таймера з кожним оновленням
        if (timeToLive <= 0) {
            removed = true;
        }
        for (Entity entityOld : game.world.entities) {
//            if (checkCollision(entityOld)) {
//                removed = true;
//                game.world.entities.remove(entityOld);
//                break;
//            }
        }
    }

    public void draw(Graphics2D g2) {
        int localY = (int) y;
        int localX = (int) x;
        g2.fillOval(localX + game.cameraX, localY + game.cameraY, 10, 10);
    }

    private boolean checkCollision(EntityOld entityOld) {
        double entityX = entityOld.screenX;
        double entityY = entityOld.screenY;
        double entityWidth = TILE_SIZE;
        double entityHeight = TILE_SIZE;
        return x < entityX + entityWidth &&
               x + 7 > entityX &&
               y < entityY + entityHeight &&
               y + 7 > entityY;
    }
}
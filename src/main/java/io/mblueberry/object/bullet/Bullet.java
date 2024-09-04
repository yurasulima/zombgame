package io.mblueberry.object.bullet;

import io.mblueberry.object.entity.Entity;
import io.mblueberry.Game;

import java.awt.*;


public class Bullet {
    double x, y;
    double dx, dy;
    double speed;
    Game game;

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
        for (Entity entity : game.world.entities) {
            if (checkCollision(entity)) {
                removed = true;
                game.world.entities.remove(entity);
                break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int localY = (int) y;
        int localX = (int) x;
        g2.fillOval(localX + game.cameraX, localY + game.cameraY, 7, 7);
    }

    private boolean checkCollision(Entity entity) {
        double entityX = entity.screenX;
        double entityY = entity.screenY;
        double entityWidth = game.tileSize;
        double entityHeight = game.tileSize;
        return x < entityX + entityWidth &&
               x + 7 > entityX &&
               y < entityY + entityHeight &&
               y + 7 > entityY;
    }

    public boolean isRemoved() {
        return removed;
    }
}

//
//public class Bullet {
//    double x, y;
//    double dx, dy;
//    double speed;
//    Game game;
//
//    private boolean removed = false;
//
//    public Bullet(double x, double y, double dx, double dy, double speed, Game game) {
//        this.x = x;
//        this.y = y;
//        this.dx = dx;
//        this.dy = dy;
//        this.speed = speed;
//        this.game = game;
//    }
//
//    public void update() {
//        x += (dx * speed);
//        y += (dy * speed);
//        for (Entity entity : game.world.entities) {
//            if (checkCollision(entity)) {
//                removed = true;
//                game.world.entities.clear();
//                break;
//            }
//        }
//    }
//
//    public void draw(Graphics2D g2) {
//        int localY = (int) y;
//        int localX = (int) x;
//        g2.fillOval(localX + game.cameraX, localY + game.cameraY, 7, 7);
//    }
//
//    private boolean checkCollision(Entity entity) {
//        double entityX = entity.screenX;
//        double entityY = entity.screenY;
//        double entityWidth = game.tileSize;
//        double entityHeight = game.tileSize;
//        return x < entityX + entityWidth &&
//               x + 7 > entityX &&
//               y < entityY + entityHeight &&
//               y + 7 > entityY;
//    }
//
//    public boolean isRemoved() {
//        return removed;
//    }
//}
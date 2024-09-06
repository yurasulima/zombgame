package io.mblueberry.core.particle;

import io.mblueberry.Game;

import java.awt.*;

public class Particle {
    private final Game game;
    private double x, y; // Початкові координати частинки
    private double direction; // Напрямок руху частинки
    private double speed; // Швидкість руху частинки

    public Particle(Game game, double x, double y, double direction, double speed) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    // Логіка для оновлення позиції частинки
    public void update() {
        // Оновлюємо позицію частинки відповідно до напрямку та швидкості
        x += Math.cos(direction) * speed;
        y += Math.sin(direction) * speed;
    }

    // Метод для малювання частинки
    public void draw(Graphics2D g) {
        // Приклад відображення частинки (як невеликої точки)
        g.fillOval(((int) x) - game.cameraX, ((int) y) - game.cameraY, 8, 8);
    }
}


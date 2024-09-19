package io.mblueberry.core.particle;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem {
    private final List<Particle> particles = new ArrayList<>();

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    // Метод для оновлення позицій всіх частинок
    public void update() {
        for (Particle particle : particles) {
            particle.update();
        }
    }

    // Метод для відображення всіх частинок
    public void draw(Graphics2D g) {
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }
}

package io.mblueberry.particle;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ParticleSystem {
    private final ArrayList<Particle> particles = new ArrayList<>();

    public void spawnParticle(String type, float x, float y, Object args) {
        Particle particle = createParticle(type, x, y, args);
        if (particle != null) {
            particles.add(particle);
        }
    }

    private Particle createParticle(String type, float x, float y, Object args) {
        // Here, you would typically use a factory pattern or switch statement
        // to return the correct particle type based on `type`
        return null; // Replace with actual particle creation logic
    }

    public void update(float dt) {
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            p.update(dt);
            p.genericUpdate(dt);
        }

        for (int i = particles.size() - 1; i >= 0; i--) {
            if (particles.get(i).dead) {
                particles.remove(i);
            }
        }
    }

    public void draw() {
        for (Particle p : particles) {
            p.draw();
        }
    }
}
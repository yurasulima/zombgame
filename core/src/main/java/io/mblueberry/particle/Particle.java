package io.mblueberry.particle;

abstract class Particle {
    String type;
    Object args; // Use a more specific type if possible
    boolean dead;
    float timer;

    public Particle(String type, Object args, float x, float y) {
        this.type = type;
        this.args = args;
        this.dead = false;
        this.timer = 0;
        initParticle(x, y);
    }

    protected abstract void initParticle(float x, float y);

    public void genericUpdate(float dt) {
        if (timer > 0) {
            timer -= dt;
        } else {
            dead = true;
            timer = 0;
        }
    }

    public abstract void update(float dt);
    public abstract void draw();
}




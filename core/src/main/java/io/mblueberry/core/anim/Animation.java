package io.mblueberry.core.anim;

import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    private final List<BufferedImage> frames;  // List of animation frames
    private int currentFrame;
    private final long frameTime;              // Duration of each frame
    private long lastFrameChangeTime;     // Last time the frame was changed

    public Animation(List<BufferedImage> frames, long frameTime) {
        this.frames = frames;
        this.frameTime = frameTime;
        this.currentFrame = 0;
        this.lastFrameChangeTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size(); // Cycle through frames
            lastFrameChangeTime = currentTime;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames.get(currentFrame);
    }
}

package io.mblueberry.core.anim;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.util.TextureUtil.loadEntityTexture;

public class SpriteLoader {
    public static BufferedImage[] loadSpriteSheet(String filePath, int frameWidth, int frameHeight) throws IOException {
        BufferedImage spriteSheet = ImageIO.read(new File(filePath));
        int cols = spriteSheet.getWidth() / frameWidth;
        int rows = spriteSheet.getHeight() / frameHeight;
        BufferedImage[] frames = new BufferedImage[cols * rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                frames[(y * cols) + x] = spriteSheet.getSubimage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
            }
        }

        return frames;
    }
    public static List<BufferedImage> loadSpriteWalk() {
        BufferedImage frame1 = loadEntityTexture("run_down1", "player");
        BufferedImage frame2 = loadEntityTexture("run_down2", "player");
        List<BufferedImage>  frames = new ArrayList<>();
        frames.add(frame1);
        frames.add(frame2);
        return frames;
    }
    public static List<BufferedImage> loadSpriteIdle() {
        BufferedImage frame1 = loadEntityTexture("run_up1", "player");
        BufferedImage frame2 = loadEntityTexture("run_up2", "player");
        List<BufferedImage>  frames = new ArrayList<>();
        frames.add(frame1);
        frames.add(frame2);
        return frames;
    }
}


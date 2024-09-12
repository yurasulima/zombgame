package io.mblueberry.util;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static io.mblueberry.Game.TILE_SIZE;

public class TextureUtil {


    @SneakyThrows
    public static BufferedImage loadItemTexture(String imageName) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/objects");
        scaleImage = scaleTexture(scaleImage, TILE_SIZE, TILE_SIZE);
        return scaleImage;
    }

    @SneakyThrows
    public static BufferedImage loadItemTexture(String imageName, int sizeX, int sizeY) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/objects");
        scaleImage = scaleTexture(scaleImage, sizeX, sizeY);
        return scaleImage;
    }
    @SneakyThrows
    public static BufferedImage loadAnimItemTexture(String imageName, int sizeX, int sizeY) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/textures/items/");
        scaleImage = scaleTexture(scaleImage, sizeX, sizeY);
        return scaleImage;
    }
    @SneakyThrows
    public static BufferedImage loadUiTexture(String imageName, int sizeX, int sizeY) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/textures/ui/");
        scaleImage = scaleTexture(scaleImage, sizeX, sizeY);
        return scaleImage;
    }

    @SneakyThrows
    public static BufferedImage loadBlockTexture(String imageName) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/textures/blocks");
        System.out.println("scaleImage = " + scaleImage);
        scaleImage = scaleTexture(scaleImage, TILE_SIZE, TILE_SIZE);
        return scaleImage;
    }

    @SneakyThrows
    public static BufferedImage loadEntityTexture(String imageName, String enity) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/textures/" + enity);
        scaleImage = scaleTexture(scaleImage, TILE_SIZE, TILE_SIZE);
        return scaleImage;
    }


    public static java.util.List<BufferedImage> loadFrames(String entity, String... filenames) {
        List<BufferedImage> frames = new ArrayList<>();
        for (String filename : filenames) {
            frames.add(loadEntityTexture(filename, entity));
        }
        return frames;
    }



    @SneakyThrows
    public static BufferedImage loadBlockTexture(String imageName, int sizeX, int sizeY) {
        BufferedImage scaleImage = getBufferedImage(imageName, "/textures/blocks");
        scaleImage = scaleTexture(scaleImage, sizeX, sizeY);
        return scaleImage;
    }


    private static BufferedImage getBufferedImage(String imageName, String path) throws IOException {
        System.out.println(path + "/" + imageName + ".png");
        InputStream resourceAsStream = TextureUtil.class.getResourceAsStream(path + "/" + imageName + ".png");
        if (resourceAsStream == null) {
            resourceAsStream = TextureUtil.class.getResourceAsStream("/not_found.png");
        }
        if (resourceAsStream == null) {
            throw new FileNotFoundException("Texture '" + imageName + "' not found!");
        }
        return ImageIO.read(resourceAsStream);
    }

    public static BufferedImage scaleTexture(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}

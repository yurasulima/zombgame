package io.mblueberry.core;


import com.google.gson.Gson;
import lombok.Getter;
import io.mblueberry.object.block.Block;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class BlockLoader {

    @Getter
    private List<Block> blocks = new ArrayList<>();

    public BlockLoader() {
        try {
            // Завантажуємо всі JSON-файли з директорії resources/blocks
            URL resourceUrl = BlockLoader.class.getClassLoader().getResource("blocks");
            if (resourceUrl != null) {
                Path blocksPath = Paths.get(resourceUrl.toURI());

                // Отримуємо всі файли з розширенням .json
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(blocksPath, "*.json")) {
                    Gson gson = new Gson();

                    for (Path entry : stream) {
                        String content = Files.readString(entry);
                        Block block = gson.fromJson(content, Block.class);
                        System.out.println(block);
                        block.setupImage();
                        blocks.add(block);
                    }
                }
            } else {
                System.out.println("Директорію 'blocks' не знайдено.");
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BlockLoader blockLoader = new BlockLoader();
        List<Block> blocks = blockLoader.getBlocks();

        // Виведемо всі завантажені блоки
        for (Block block : blocks) {
            System.out.println(block);
        }
    }

}

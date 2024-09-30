package io.mblueberry;


import io.mblueberry.object.block.BigDirtGrass1;
import io.mblueberry.object.block.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BlockLoader {

    @Getter
    private List<Block> blocks = new ArrayList<>();

    @SneakyThrows
    public BlockLoader() {
        blocks.add(new BigDirtGrass1());
        blocks.add(new BigDirtGrass10());
        blocks.add(new BigDirtGrass11());
        blocks.add(new BigDirtGrass12());
        blocks.add(new BigDirtGrass2());
        blocks.add(new BigDirtGrass3());
        blocks.add(new BigDirtGrass4());
        blocks.add(new BigDirtGrass5());
        blocks.add(new BigDirtGrass6());
        blocks.add(new BigDirtGrass7());
        blocks.add(new BigDirtGrass8());
        blocks.add(new BigDirtGrass9());
        blocks.add(new DirtGrass1());
        blocks.add(new DirtGrass10());
        blocks.add(new DirtGrass11());
        blocks.add(new DirtGrass12());
        blocks.add(new DirtGrass2());
        blocks.add(new DirtGrass3());
        blocks.add(new DirtGrass4());
        blocks.add(new DirtGrass5());
        blocks.add(new DirtGrass6());
        blocks.add(new DirtGrass8());
        blocks.add(new DirtGrass1());
        blocks.add(new DirtGrass9());
        blocks.add(new Earth());
        blocks.add(new Grass00());
        blocks.add(new Grass01());
        blocks.add(new Hut());
        blocks.add(new Road00());
        blocks.add(new Road01());
        blocks.add(new Road02());
        blocks.add(new Road03());
        blocks.add(new Road04());
        blocks.add(new Road05());
        blocks.add(new Road06());
        blocks.add(new Road07());
        blocks.add(new Road08());
        blocks.add(new Road09());
        blocks.add(new Road10());
        blocks.add(new Road11());
        blocks.add(new Road12());
        blocks.add(new Tree());
        blocks.add(new Trunk());
        blocks.add(new Wall());
        blocks.add(new Water00());
        blocks.add(new Water01());
        blocks.add(new Water02());
        blocks.add(new Water03());
        blocks.add(new Water04());
        blocks.add(new Water05());
        blocks.add(new Water06());
        blocks.add(new Water07());
        blocks.add(new Water08());
        blocks.add(new Water09());
        blocks.add(new Water10());
        blocks.add(new Water11());
        blocks.add(new Water12());
        blocks.add(new Water13());
//        URL resourceUrl = BlockLoader.class.getClassLoader().getResource("blocks_old");
//        if (resourceUrl == null) {
//        }
//
//        Path blocksPath = Paths.get(resourceUrl.toURI());
//        DirectoryStream<Path> stream = Files.newDirectoryStream(blocksPath, "*.json");
//        for (Path entry : stream) {
//            Block block = getBlock(entry);
//            blocks.add(block);
//        }
    }

    private static Block getBlock(Path entry) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(entry.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(jsonContent.toString());
        String type = jsonObject.getString("type");
        boolean collision = jsonObject.getBoolean("collision");
        boolean interact = jsonObject.getBoolean("interact");
        String displayName = jsonObject.getString("name");

        // Створюємо об'єкт Block на основі отриманих даних
        Block block = new Block(type);
        block.setCollision(collision);
        block.setInteract(interact);
        block.setDisplayName(displayName);
        return block;
    }


    public static void main(String[] args) {
        BlockLoader blockLoader = new BlockLoader();
        List<Block> blocks = blockLoader.getBlocks();

        for (Block block : blocks) {
        }
    }

}

package io.mblueberry.core.object.entity.npc;



import io.mblueberry.core.object.entity.Direction;
import io.mblueberry.core.object.entity.EntityOld;
import io.mblueberry.core.object.entity.EntityTag;
import io.mblueberry.Game;

import java.util.Random;

import static io.mblueberry.Game.TILE_SIZE;

public class OldManNPC1 extends EntityOld {

    public OldManNPC1(Game game, int spawnX, int spawnY) {
        super(game);

        screenX = TILE_SIZE * spawnX;
        screenY = TILE_SIZE * spawnY;
        direction = Direction.DOWN;
        speed = 1;
        getImage();
        setDialogue();

        entityTag = EntityTag.NPC;
        life = 10;
        maxLife = 10;

    }

    public void getImage() {
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");

    }

    public void speak() {
        super.speak();
    }

    public void setDialogue() {
        //TODO refactor dialog system
//        dialogues[0] = "Hello";
//        dialogues[1] = str("Вибач за біль,\nпосилені процедури моніторингу.\nЕт Нехай так буде. Відплата чудово");
//        dialogues[2] = "Maecenas potenti suspendisse.";
//        dialogues[3] = "Maecenas potenti suspendisse.";
//        dialogues[4] = "Nulla amet sed ligula.";
//        dialogues[5] = "Luctus semper eros.";

    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = Direction.UP;

            }
            if (i > 25 && i <= 50) {
                direction = Direction.DOWN;
            }
            if (i > 50 && i <= 75) {
                direction = Direction.LEFT;
            }
            if (i > 75) {
                direction = Direction.RIGHT;
            }
            actionLockCounter = 0;
        }
    }
}

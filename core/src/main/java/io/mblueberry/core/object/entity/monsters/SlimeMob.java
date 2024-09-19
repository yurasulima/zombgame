package io.mblueberry.core.object.entity.monsters;

import io.mblueberry.core.object.entity.Direction;
import io.mblueberry.core.object.entity.EntityOld;
import io.mblueberry.core.object.entity.EntityTag;
import io.mblueberry.core.object.entity.PlayerOld;
import io.mblueberry.Game;

import java.util.Random;

import static io.mblueberry.Game.TILE_SIZE;

public class SlimeMob extends EntityOld {

    public SlimeMob(Game game, int spawnX, int spawnY) {
        super(game);

        screenX = TILE_SIZE * spawnX;
        screenY = TILE_SIZE * spawnY;
        direction = Direction.DOWN;
        speed = 1;
        attack = 5;
        defense = 0;
        entityTag = EntityTag.MONSTER;
        setAction();
        getImage();
        life = 4;
        maxLife = 4;
    }

    public void getImage() {
        up1 = setup("/mobs/greenslime_down_1");
        up2 = setup("/mobs/greenslime_down_2");
        down1 = setup("/mobs/greenslime_down_1");
        down2 = setup("/mobs/greenslime_down_2");
        left1 = setup("/mobs/greenslime_down_1");
        left2 = setup("/mobs/greenslime_down_2");
        right1 = setup("/mobs/greenslime_down_1");
        right2 = setup("/mobs/greenslime_down_2");
    }


    public void damageReaction(PlayerOld playerOld) {
        actionLockCounter = 0;
        direction = playerOld.direction;
    }
    public void setAction(){
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

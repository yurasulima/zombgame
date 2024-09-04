package io.mblueberry.object.entity.monsters;

import io.mblueberry.object.entity.Entity;
import io.mblueberry.object.entity.EntityTag;
import io.mblueberry.object.entity.Player;
import io.mblueberry.Game;

import java.util.Random;

public class SlimeMob extends Entity {

    public SlimeMob(Game game, int spawnX, int spawnY) {
        super(game);

        screenX = game.tileSize * spawnX;
        screenY = game.tileSize * spawnY;
        direction = "down";
        move = 1;
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


    public void damageReaction(Player player) {
        actionLockCounter = 0;
        direction = player.direction;
    }
    public void setAction(){
        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";

            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

}

package io.mblueberry.object.entity;

import io.mblueberry.Game;

import java.awt.*;

import static io.mblueberry.Game.tileSize;
import static io.mblueberry.util.Utils.getLocalCenterTextX;

public class MPlayer extends Entity {


    public Game game;

   // public String name;
    public MPlayer(Game game, int spawnX, int spawnY, String name) {
        super(game);

        screenX = tileSize * spawnX;
        screenY = tileSize * spawnY;
        this.game = game;
        this.name = name;
        getImage();
        direction = "down";
        move = 1;
        entityTag = EntityTag.MPLAYER;
        isMPlayer = true;
    }

    public void getImage() {
        hold = setup("/player/boy_up_1");
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");

    }

    public void update(){ }

//    public void setAction(){
//        actionLockCounter++;
//        if (actionLockCounter == 120) {
//
//            Random random = new Random();
//            int i = random.nextInt(100) + 1;
//            if (i <= 25) {
//                direction = "up";
//            }
//            if (i > 25 && i <= 50) {
//                direction = "down";
//            }
//            if (i > 50 && i <= 75) {
//                direction = "left";
//            }
//            if (i > 75) {
//                direction = "right";
//            }
//            actionLockCounter = 0;
//        }
//    }

    @Override
    public void draw(Graphics2D g2) {

        super.draw(g2);
//        g2.setFont(new Font("Arial", Font.PLAIN, 20));
//        g2.setColor(Color.WHITE);

        g2.drawString(name, screenX + game.cameraX - getLocalCenterTextX(name, g2) + 32, screenY + game.cameraY - 10);
    }
}

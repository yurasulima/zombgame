package io.mblueberry.core.object.entity;

import io.mblueberry.Game;
import io.mblueberry.input.KeyHandler;
import io.mblueberry.core.object.block.Block;
import io.mblueberry.core.object.bullet.Bullet;
import io.mblueberry.core.object.item.GameObject;
import io.mblueberry.core.object.item.ShieldItem;
import io.mblueberry.core.object.item.SwordItem;
import io.mblueberry.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static io.mblueberry.Game.TILE_SIZE;
import static io.mblueberry.util.Utils.getCenterTextX;
import static java.nio.charset.StandardCharsets.UTF_8;

public class PlayerOld extends EntityOld {

    private Game game;
    private KeyHandler keyHandler;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int lastScreenX = 0;
    public int lastScreenY = 0;
    BufferedImage img;

    public PlayerOld(Game game, KeyHandler keyHandler, int spawnX, int spawnY, String name) {
        super(game);
        this.name = name;
        this.screenX = TILE_SIZE * spawnX;
        this.screenY = TILE_SIZE * spawnY;
        game.cameraX -= TILE_SIZE * spawnX;
        game.cameraY -= TILE_SIZE * spawnY;
        this.game = game;
        this.keyHandler = keyHandler;
        entityTag = EntityTag.PLAYER;
        type = "player";
        attackArea.width = 36;
        attackArea.height = 36;
        solidArea = new Rectangle(16, 32, 32, 32);
        direction = Direction.HOLD;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            img = Utils.scaleIMage(img, 64, 64);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues() {
        speed = 9;
        maxLife = 10;
        life = 10;

        //PLAYER STATS
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        rightHand = new SwordItem();
        leftHand = new ShieldItem();
        attack = getAttack();
        defense = getDefense();

    }

    private int getDefense() {
        return dexterity * leftHand.defenseValue;
    }

    private int getAttack() {
        return strength * rightHand.attackValue;
    }

    public void getPlayerImage() {
        hold = setup("/player/new/run_right1");
        up1 = setup("/player/new/run_up_right_1");
        up2 = setup("/player/new/run_up_right_2");
        down1 = setup("/player/new/run_right1");
        down2 = setup("/player/new/run_right2");
        left1 = setup("/player/new/run_down1");
        left2 = setup("/player/new/run_down2");
        right1 = setup("/player/new/run_right1");
        right2 = setup("/player/new/run_right2");

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if (attacking) {
            attack();
        }
        switch (direction) {
            case UP:
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }

                break;
            case DOWN:
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }

                break;
            case RIGHT:
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }

                break;
            case LEFT:

                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }

                break;
        }


        g2.drawImage(image, tempScreenX, tempScreenY, null);
        int tileX = (screenX - game.cameraX) / TILE_SIZE;
        int tileY = (screenY - game.cameraY) / TILE_SIZE;

        byte[] a = (
                "player: " +
                "screenX: " + screenX + ", " +
                "screenY: " + screenY + " " +
                "x + offset: " + (screenX - game.cameraX) + ", " +
                "y + offset: " + (screenY - game.cameraY) + " " +
                "tile (" + tileX + ", " + tileY + "): " + (game.world.getTileScreen(screenX - game.cameraX + TILE_SIZE / 2, screenY - game.cameraY + TILE_SIZE / 2).getType())
        ).getBytes();

        String text = new String(a, UTF_8);
        int x = getCenterTextX(text, g2, game);
        int y = game.getHeight() / 2 - (TILE_SIZE * 2);
        g2.drawString(text, x, y);


        g2.setColor(Color.BLACK);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);


        int mouseX = game.guiManager.gameUi.mouseX;
        int mouseY = game.guiManager.gameUi.mouseY;
        int slot = game.guiManager.gameUi.currentSlot;
        double angle = Math.atan2(mouseY - screenY, mouseX - screenX);
        if (inventory.get(slot) != null) {
            GameObject item = inventory.get(slot);
            if (item.type.equals("bow")) {

            }
        }

    }

    public void shoot(double mouseX, double mouseY) {
        double dx = mouseX - screenX;
        double dy = mouseY - screenY;
        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;


        Bullet bullet = new Bullet(screenX + 32 - game.cameraX, screenY + 32 - game.cameraY, dx, dy, 10.0, 120, game);
        game.world.bullets.add(bullet);
    }

    public void update() {
//        if (keyHandler.spacePressed && !attacking) {
//            attacking = true;
//        }
//        if (!attacking) {
//            if (keyHandler.downPressed) {
//                direction = Direction.DOWN;
//            }
//            if (keyHandler.upPressed) {
//                direction = Direction.UP;
//            }
//            if (keyHandler.rightPressed) {
//                direction = Direction.RIGHt;
//            }
//            if (keyHandler.leftPressed) {
//                direction = Direction.LEFT;
//            }
//        }
//
//        int move = TILE_SIZE / 10;
//        if ((keyHandler.upPressed || keyHandler.downPressed) && (keyHandler.rightPressed || keyHandler.leftPressed)) {
//            move = TILE_SIZE / 12;
//        }
//        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
//
//            setState("walk_down");
////            spriteCounter++;
////            if (!attacking) {
////                if (spriteCounter > 10) {
////                    if (spriteNum == 1) {
////                        spriteNum = 2;
////                    } else if (spriteNum == 2) {
////                        spriteNum = 1;
////                    }
////                    spriteCounter = 0;
////                }
////            }
//        }
//
//
//        //CHECK TILE COLLISION
//        collisionOn = false;
//        Block bl = game.collisionChecker.checkTile(this);
//        //System.out.println("bl = " + bl);
//        contactBlock(bl);
//        EntityOld entityOldColl = game.collisionChecker.checkEntity(this, game.world.entities);
//        contactEntity(entityOldColl);
//
//        //TODO contact item
//        if (!collisionOn && !attacking) {
//
//            int displayCenterX = game.getWidth() / 2;
//            int displayCenterY = game.getHeight() / 2;
//
//            if (keyHandler.downPressed) {
//                direction = Direction.DOWN;
//                if (game.playerOld.screenY > (displayCenterY + 200)) {
//
//                    int tileY = (screenY - game.cameraY);
//                    int screenRealX = game.getWidth();
//                    if (tileY - screenY > screenRealX) {
//                        screenY += move;
//                    } else {
//                        game.cameraY -= move;
//                    }
//
//                } else {
//                    screenY += move;
//                }
//            }
//            if (keyHandler.upPressed) {
//                direction = Direction.UP;
//                if (game.playerOld.screenY < (displayCenterY - 200)) {
//
//                    int tileY = (screenY - game.cameraY);
//                    if (tileY > screenY) {
//                        game.cameraY += move;
//                    } else {
//                        screenY -= move;
//                    }
//                } else {
//                    screenY -= move;
//                }
//            }
//            if (keyHandler.rightPressed) {
//                direction = Direction.RIGHt;
//                if (game.playerOld.screenX > (displayCenterX + 200)) {
//                    int tileX = (screenX - game.cameraX);
//                    int screenRealX = game.getHeight();
//                    if (tileX - screenX > screenRealX) {
//                        System.out.println("detect");
//                        screenX += move;
//                    } else {
//                        game.cameraX -= move;
//                    }
//                } else {
//                    screenX += move;
//                }
//            }
//            if (keyHandler.leftPressed) {
//                direction = Direction.LEFT;
//                if (game.playerOld.screenX < (displayCenterX - 200)) {
//                    int tileX = (screenX - game.cameraX);
//                    if (tileX > screenX) {
//                        game.cameraX += move;
//                    } else {
//                        screenX -= move;
//                    }
//                } else {
//                    screenX -= move;
//                }
//            }
//            if (game.gameType.equals("client")) {
//                if (screenX != lastScreenX || screenY != lastScreenY) {
//                    game.client.sendMessage("/move " + name + " " + screenX + " " + screenY);
//                }
//                lastScreenX = screenX;
//                lastScreenY = screenY;
//            }
//        }
//
//        if (invincible) {
//            invincibleCounter += 1;
//            if (invincibleCounter > 60) {
//                invincible = false;
//                invincibleCounter = 0;
//            }
//        }

    }

    private void contactBlock(Block bl) {
        if (bl == null) {
            return;
        }

//        if (inventory.stream().anyMatch(entity -> entity.name.equals("axe"))) {
//            if (keyHandler.spacePressed) {
//                if (bl.getType().equals("tree")) {
//                    Block newBlock = game.world.blocks.stream().filter(block -> block.getType().equals("trunk")).findFirst().orElseThrow();
//                    bl.replace(newBlock);
//
//                }
//            }
//        }
    }

    private int attackFrame = 0; // Поточний кадр анімації атаки
    private final int maxAttackFrame = 15; // Загальна кількість кадрів анімації атаки

    // direction = "up", "down", "right", "left"


    private void attack() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

        } else {
            spriteCounter++;
        }
        //spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentScreenX = screenX;
            int currentScreenY = screenY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case UP:
                    screenY -= attackArea.height;
                    break;
                case DOWN:
                    screenY += attackArea.height;
                    break;
                case LEFT:
                    screenX -= attackArea.width;
                    break;
                case RIGHT:
                    screenX += attackArea.width;
                    break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

         //   EntityOld attackCheck = game.collisionChecker.checkEntity(this, game.world.entities);
           // damageMonster(attackCheck);
            screenX = currentScreenX;
            screenY = currentScreenY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 19) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageMonster(EntityOld entityOld) {
        if (entityOld == null) {
            return;
        }
        if (!entityOld.invincible) {

            int damage = attack - entityOld.defense;
            if (damage < 0) {
                damage = 0;
            }
            entityOld.life -= damage;
            entityOld.invincible = true;

            if (entityOld.life <= 0) {
                entityOld.dying = true;
            }

            entityOld.damageReaction(this);
        }
    }

    private void contactEntity(EntityOld entityOld) {

        if (entityOld == null) {
            return;
        }

        if (entityOld.entityTag == EntityTag.MONSTER) {
            if (!invincible) {
                int damage = attack - entityOld.defense;
                if (damage < 0) {
                    damage = 0;
                }
                entityOld.life -= damage;
                invincible = true;
            }
        }

    }


}

package io.mblueberry.object.entity;

import io.mblueberry.object.bullet.Bullet;
import io.mblueberry.object.item.ShieldItem;
import io.mblueberry.object.item.SwordItem;
import io.mblueberry.Game;
import io.mblueberry.util.Utils;
import io.mblueberry.object.block.Block;
import io.mblueberry.input.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static io.mblueberry.util.Utils.getCenterTextX;

public class Player extends Entity {

    private Game game;
    private KeyHandler keyHandler;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int lastScreenX = 0;
    public int lastScreenY = 0;
    BufferedImage img;

    public Player(Game game, KeyHandler keyHandler, int spawnX, int spawnY, String name) {
        super(game);
        this.name = name;
        this.screenX = game.tileSize * spawnX;
        this.screenY = game.tileSize * spawnY;
        game.cameraX -= game.tileSize * spawnX;
        game.cameraY -= game.tileSize * spawnY;
        this.game = game;
        this.keyHandler = keyHandler;
        entityTag = EntityTag.PLAYER;
        isPlayer = true;
        attackArea.width = 36;
        attackArea.height = 36;
        solidArea = new Rectangle(16, 32, 32, 32);
        direction = "down";
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        getPlayerAxeImage();

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            img = Utils.scaleIMage(img, 64, 64);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues() {
        move = 9;
        direction = "down";

        maxLife = 10;
        life = 10;

        //PLAYER STATS
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new SwordItem();
        currentShield = new ShieldItem();
        attack = getAttack();
        defense = getDefense();

    }

    private int getDefense() {
        return dexterity * currentShield.defenseValue;
    }

    private int getAttack() {
        return strength * currentWeapon.attackValue;
    }

    public void getPlayerImage() {
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

    public void getPlayerAxeImage() {
        axeUp1 = setupAttack("/player/boy_axe_up_1", game.tileSize, game.tileSize * 2);
        axeUp2 = setupAttack("/player/boy_axe_up_2", game.tileSize, game.tileSize * 2);
        axeDown1 = setupAttack("/player/boy_axe_down_1", game.tileSize, game.tileSize * 2);
        axeDown2 = setupAttack("/player/boy_axe_down_2", game.tileSize, game.tileSize * 2);
        axeLeft1 = setupAttack("/player/boy_axe_left_1", game.tileSize * 2, game.tileSize);
        axeLeft2 = setupAttack("/player/boy_axe_left_2", game.tileSize * 2, game.tileSize);
        axeRight1 = setupAttack("/player/boy_axe_right_1", game.tileSize * 2, game.tileSize);
        axeRight2 = setupAttack("/player/boy_axe_right_2", game.tileSize * 2, game.tileSize);
    }
    public void getPlayerAttackImage() {

        attackUp1 = setupAttack("/player/boy_attack_up_1", game.tileSize, game.tileSize * 2);
        attackUp2 = setupAttack("/player/boy_attack_up_2", game.tileSize, game.tileSize * 2);
        attackDown1 = setupAttack("/player/boy_attack_down_1", game.tileSize, game.tileSize * 2);
        attackDown2 = setupAttack("/player/boy_attack_down_2", game.tileSize, game.tileSize * 2);
        attackLeft1 = setupAttack("/player/boy_attack_left_1", game.tileSize * 2, game.tileSize);
        attackLeft2 = setupAttack("/player/boy_attack_left_2", game.tileSize * 2, game.tileSize);
        attackRight1 = setupAttack("/player/boy_attack_right_1", game.tileSize * 2, game.tileSize);
        attackRight2 = setupAttack("/player/boy_attack_right_2", game.tileSize * 2, game.tileSize);

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if (attacking) {
            attack();
        }
        switch (direction) {
            case "up":
                if (attacking) {
                    tempScreenY -= game.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                break;
            case "down":
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                break;
            case "right":
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                break;
            case "left":
                if (attacking) {
                    tempScreenX -= game.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                } else {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                break;
        }


        g2.drawImage(image, tempScreenX, tempScreenY, null);
        int tileX = (screenX - game.cameraX) / game.tileSize;
        int tileY = (screenY - game.cameraY) / game.tileSize;

        byte[] a = (
                "player: " +
                "screenX: " + screenX + ", " +
                "screenY: " + screenY + " " +
                "x + offset: " + (screenX - game.cameraX) + ", " +
                "y + offset: " + (screenY - game.cameraY) + " " +
                "tile (" + tileX + ", " + tileY + "): " + (game.world.getTileScreen(screenX - game.cameraX + game.tileSize / 2, screenY - game.cameraY + game.tileSize / 2).getName())
        ).getBytes();

        String text = new String(a, UTF_8);
        int x = getCenterTextX(text, g2, game);
        int y = game.getHeight() / 2 - (game.tileSize * 2);
        g2.drawString(text, x, y);


        g2.setColor(Color.BLACK);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);


    }

    public void shoot(double mouseX, double mouseY) {
        double dx = mouseX - screenX;
        double dy = mouseY - screenY;
        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;


        Bullet bullet = new Bullet(screenX - game.cameraX, screenY - game.cameraY, dx, dy, 10.0, 120, game);
        game.world.bullets.add(bullet);
    }

    public void update() {
        if (keyHandler.spacePressed && !attacking) {
            attacking = true;
        }
        if (!attacking) {
            if (keyHandler.downPressed) {
                direction = "down";
            }
            if (keyHandler.upPressed) {
                direction = "up";
            }
            if (keyHandler.rightPressed) {
                direction = "right";
            }
            if (keyHandler.leftPressed) {
                direction = "left";
            }
        }

        int move = game.tileSize / 10;
        if ((keyHandler.upPressed || keyHandler.downPressed) && (keyHandler.rightPressed || keyHandler.leftPressed)) {
            move = game.tileSize / 12;
        }
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            spriteCounter++;
            if (!attacking) {
                if (spriteCounter > 10) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
        }


        //CHECK TILE COLLISION
        collisionOn = false;
        Block bl = game.collisionChecker.checkTile(this);
        //System.out.println("bl = " + bl);
        contactBlock(bl);
        Entity entityColl = game.collisionChecker.checkEntity(this, game.world.entities);
        contactEntity(entityColl);

        //TODO contact item
        if (!collisionOn && !attacking) {

            int displayCenterX = game.getWidth() / 2;
            int displayCenterY = game.getHeight() / 2;

            if (keyHandler.downPressed) {
                direction = "down";
                if (game.player.screenY > (displayCenterY + 200)) {

                    int tileY = (screenY - game.cameraY);
                    int screenRealX = game.getWidth();
                    if (tileY - screenY > screenRealX) {
                        screenY += move;
                    } else {
                        game.cameraY -= move;
                    }

                } else {
                    screenY += move;
                }
            }
            if (keyHandler.upPressed) {
                direction = "up";
                if (game.player.screenY < (displayCenterY - 200)) {

                    int tileY = (screenY - game.cameraY);
                    if (tileY > screenY) {
                        game.cameraY += move;
                    } else {
                        screenY -= move;
                    }
                } else {
                    screenY -= move;
                }
            }
            if (keyHandler.rightPressed) {
                direction = "right";
                if (game.player.screenX > (displayCenterX + 200)) {
                    int tileX = (screenX - game.cameraX);
                    int screenRealX = game.getHeight();
                    if (tileX - screenX > screenRealX) {
                        System.out.println("detect");
                        screenX += move;
                    } else {
                        game.cameraX -= move;
                    }
                } else {
                    screenX += move;
                }
            }
            if (keyHandler.leftPressed) {
                direction = "left";
                if (game.player.screenX < (displayCenterX - 200)) {
                    int tileX = (screenX - game.cameraX);
                    if (tileX > screenX) {
                        game.cameraX += move;
                    } else {
                        screenX -= move;
                    }
                } else {
                    screenX -= move;
                }
            }
            if (game.gameType.equals("client")) {
                if (screenX != lastScreenX || screenY != lastScreenY) {
                    game.client.sendMessage("/move " + name + " " + screenX + " " + screenY);
                }
                lastScreenX = screenX;
                lastScreenY = screenY;
            }
        }

        if (invincible) {
            invincibleCounter += 1;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

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
                case "up":
                    screenY -= attackArea.height;
                    break;
                case "down":
                    screenY += attackArea.height;
                    break;
                case "left":
                    screenX -= attackArea.width;
                    break;
                case "right":
                    screenX += attackArea.width;
                    break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            Entity attackCheck = game.collisionChecker.checkEntity(this, game.world.entities);
            damageMonster(attackCheck);
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

    private void damageMonster(Entity entity) {
        if (entity == null) {
            return;
        }
        if (!entity.invincible) {

            int damage = attack - entity.defense;
            if (damage < 0) {
                damage = 0;
            }
            entity.life -= damage;
            entity.invincible = true;

            if (entity.life <= 0) {
                entity.dying = true;
            }

            entity.damageReaction(this);
        }
    }

    private void contactEntity(Entity entity) {

        if (entity == null) {
            return;
        }

        if (entity.entityTag == EntityTag.MONSTER) {
            if (!invincible) {
                int damage = attack - entity.defense;
                if (damage < 0) {
                    damage = 0;
                }
                entity.life -= damage;
                invincible = true;
            }
        }

    }


}

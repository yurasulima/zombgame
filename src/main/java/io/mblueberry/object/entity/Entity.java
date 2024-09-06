package io.mblueberry.object.entity;

import io.mblueberry.object.item.GameObject;
import io.mblueberry.Game;
import io.mblueberry.util.Utils;
import io.mblueberry.container.Container;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    public int worldLayer = 1;
    public int move;
    public String name = "";
    public boolean isPlayer = false;
    public boolean isMPlayer = false;

    public BufferedImage hold, up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,  attackLeft1,  attackLeft2,  attackRight1,  attackRight2;
    public BufferedImage axeUp1, axeUp2, axeDown1, axeDown2,  axeLeft1,  axeLeft2,  axeRight1,  axeRight2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Game game;
    public Container inventory;
    public Rectangle solidArea;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String[] dialogues = new String[20];
    public int dialogueIndex = 0;
    public int screenX = 0;
    public int screenY = 0;
    public boolean attacking = false;

    //entity stats
    boolean hpBarEnabled = false;
    public int maxLife;
    public int life;
    public boolean invincible = false;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;
    public int invincibleCounter = 0;
    public int hpBarCounter = 0;
    public EntityTag entityTag;

    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;

    public GameObject currentWeapon;
    public GameObject currentShield;

    public int defenseValue;
    public int attackValue;
    public Entity(Game game) {
        this.game = game;
        this.inventory = new Container(game, 37);
        entityTag = EntityTag.OBJECT;
        solidArea = new Rectangle(16, 32, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public BufferedImage setup(String imageName) {
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, game.tileSize, game.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }

    public BufferedImage setupMini(String imageName) {
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, game.tileSize / 2, game.tileSize / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }

    public BufferedImage setupAttack(String imageName, int width, int heigth) {
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, width, heigth);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }


    public void setAction() { }
    public void damageReaction(Player player) { }

    public void update() {

        setAction();


        collisionOn = false;
        game.collisionChecker.checkTileEntity(this);

        Entity entityColl = game.collisionChecker.checkEntity(this, game.world.entities);

        if (entityColl != null) {
            if (this.entityTag == EntityTag.MONSTER && entityColl.entityTag == EntityTag.PLAYER) {
                if (!entityColl.invincible) {
                    int damage = attack - entityColl.defense;
                    if (damage < 0) {
                        damage = 0;
                    }
                    entityColl.life -= damage;
                    entityColl.invincible = true;
                }
            }
        }

//        game.collisionChecker.checkObject(this, false);
//        game.collisionChecker.checkPlayer(this);

        if (!collisionOn) {
            if (direction.equals("up")) {
                screenY -= move;
            }
            if (direction.equals("down")) {
                screenY += move;
            }
            if (direction.equals("left")) {
                screenX -= move;
            }
            if (direction.equals("right")) {
                screenX += move;
            }
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invincible) {

            invincibleCounter += 1;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void speak() {
        switch (game.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;

            default:
                image = hold;
        }

        if(entityTag == EntityTag.MONSTER && hpBarEnabled) {
            double oneScale = (double) game.tileSize / maxLife;
            double hpBarValue = oneScale * life;
            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(screenX + game.cameraX - 1, screenY + game.cameraY - 16, game.tileSize+ 2, 12);
            g2.setColor(new Color(255, 0, 30));
            g2.fillRect(screenX + game.cameraX, screenY + game.cameraY - 15, (int) hpBarValue, 10);

            hpBarCounter++;

            if (hpBarCounter > 600) {
                hpBarEnabled = false;
                hpBarCounter = 0;
            }
        }

        if (dying) {
            dyingAnimation(g2);
        }
        if (invincible) {
            hpBarEnabled = true;
            hpBarCounter = 0;
        }

        g2.drawImage(image, screenX + game.cameraX, screenY + game.cameraY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

       // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 0f));
    }

    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int step = 5;
        if (dyingCounter <= step) { changeAlpha(g2, 0f); }
        if (dyingCounter > step && dyingCounter <= step * 2) { changeAlpha(g2, 1f); }
        if (dyingCounter > step * 2 && dyingCounter <= step * 3) { changeAlpha(g2, 0f); }
        if (dyingCounter > step * 3 && dyingCounter <= step * 4) { changeAlpha(g2, 1f); }
        if (dyingCounter > step * 4 && dyingCounter <= step * 5) { changeAlpha(g2, 0f); }
        if (dyingCounter > step * 5 && dyingCounter <= step * 6) { changeAlpha(g2, 1f); }
        if (dyingCounter > step * 6 && dyingCounter <= step * 7) { changeAlpha(g2, 0f); }
        if (dyingCounter > step * 7 && dyingCounter <= step * 8) { changeAlpha(g2, 1f); }
        if (dyingCounter > step * 8) {
            dying = false;
            alive = false;
        }
    }

    private void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void takeItem(GameObject gameObject) {
        inventory.add(gameObject);
        game.world.gameObjects.remove(gameObject);
    }
}


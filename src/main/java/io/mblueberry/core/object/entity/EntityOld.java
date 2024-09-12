package io.mblueberry.core.object.entity;

import io.mblueberry.core.anim.Animation;
import io.mblueberry.core.anim.SpriteLoader;
import io.mblueberry.core.object.item.GameObject;
import io.mblueberry.Game;
import io.mblueberry.util.Utils;
import io.mblueberry.core.container.Container;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.mblueberry.Game.TILE_SIZE;

public class EntityOld {
    public int worldLayer = 1;
    public int speed;
    public String name = "";
    public String type = "";
    public Direction direction = Direction.HOLD;

    private Map<String, Animation> animations;
    private String currentState;
    public int screenX = 0;
    public int screenY = 0;

    
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

    public GameObject leftHand;
    public GameObject rightHand;
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage right1;
    public BufferedImage right2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage hold;


    private void loadAnimations() {
        try {
            List<BufferedImage> walkFrames = SpriteLoader.loadSpriteWalk();
            List<BufferedImage> idleFrames = SpriteLoader.loadSpriteIdle();
            animations.put("walk_down", new Animation(walkFrames, 100)); // Анімація ходьби
            animations.put("walk_up", new Animation(walkFrames, 100)); // Анімація ходьби
            animations.put("walk_right", new Animation(walkFrames, 100)); // Анімація ходьби
            animations.put("walk_left", new Animation(walkFrames, 100)); // Анімація ходьби
            animations.put("idle", new Animation(idleFrames, 150)); // Анімація очікування

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void setState(String state) {
//        if (!state.equals(currentState)) {
//            currentState = state;
//            animations.get(currentState).start(); // Запуск анімації для нового стану
//        }
//    }

    public EntityOld(Game game) {
        this.game = game;
        this.inventory = new Container(game, 37);


        animations = new HashMap<>();
        loadAnimations();
        currentState = "idle"; // Початковий стан
        entityTag = EntityTag.OBJECT;
        solidArea = new Rectangle(16, 32, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public BufferedImage setup(String imageName) {
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, TILE_SIZE, TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }

    public BufferedImage setupMini(String imageName) {
        BufferedImage scaleImage = null;

        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaleImage = Utils.scaleIMage(scaleImage, TILE_SIZE / 2, TILE_SIZE / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleImage;
    }

//
//    public void stop() {
//        setState("idle"); // Переключення на анімацію очікування
//    }

    public void setAction() { }
    public void damageReaction(PlayerOld playerOld) { }

    public void update() {

        animations.get(currentState).update();
        setAction();



      //  EntityOld entityOldColl = game.collisionChecker.checkEntity(this, game.world.entities);

//        if (entityOldColl != null) {
//            if (this.entityTag == EntityTag.MONSTER && entityOldColl.entityTag == EntityTag.PLAYER) {
//                if (!entityOldColl.invincible) {
//                    int damage = attack - entityOldColl.defense;
//                    if (damage < 0) {
//                        damage = 0;
//                    }
//                    entityOldColl.life -= damage;
//                    entityOldColl.invincible = true;
//                }
//            }
//        }

//        game.collisionChecker.checkObject(this, false);
//        game.collisionChecker.checkPlayer(this);

        if (!collisionOn) {
            if (direction == Direction.UP) {
                screenY -= speed;
            }
            if (direction == Direction.DOWN) {
                screenY += speed;
            }
            if (direction == Direction.LEFT) {
                screenX -= speed;
            }
            if (direction == Direction.RIGHT) {
                screenX += speed;
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
//        direction = game.player.direction;
//        switch (game.playerOld.direction) {
//            case DOWN:
//                direction = Direction.UP;
//                break;
//            case UP:
//                direction = Direction.DOWN;
//                break;
//            case LEFT:
//                direction = Direction.RIGHt;
//                break;
//            case RIGHt:
//                direction = Direction.LEFT;
//                break;
//        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
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

            default:
                image = hold;
        }

        if(entityTag == EntityTag.MONSTER && hpBarEnabled) {
            double oneScale = (double) TILE_SIZE / maxLife;
            double hpBarValue = oneScale * life;
            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(screenX + game.cameraX - 1, screenY + game.cameraY - 16, TILE_SIZE+ 2, 12);
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
        BufferedImage currentFrame = animations.get(currentState).getCurrentFrame();
        g2.drawImage(currentFrame, screenX + game.cameraX, screenY + game.cameraY, null);
      //  g2.drawImage(image, screenX + game.cameraX, screenY + game.cameraY, null);
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


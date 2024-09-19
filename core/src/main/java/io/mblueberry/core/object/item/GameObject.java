package io.mblueberry.core.object.item;

import io.mblueberry.Game;
import io.mblueberry.core.object.entity.Entity;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Setter
@Getter
public abstract class GameObject {
    public transient BufferedImage texture;
    public String type;
    public String displayName;
    public int stackSize = 64;
    public int stackCount = 1;

    public int attackValue;
    public int defenseValue;

    public void use(){}
    public void use(Entity entity){}
    public void interact(){}
    public void stopInteract(){}
    public void place(Game game, int x, int y){}
    public void draw(Graphics2D g2){};

    public void draw(Graphics2D g2, Entity entity, Game game) {};

    public void update() {

    }
}
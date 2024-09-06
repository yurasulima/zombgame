package io.mblueberry.object.item;

import io.mblueberry.Game;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
public abstract class GameObject {
    public int attackValue;
    public int defenseValue;
    public transient BufferedImage icon;
    public String displayName;
    public int stackSize = 64;
    public String itemType;
    public int stackCount = 1;


    public void interact(){}
    public void stopInteract(){}
    public void place(Game game, int x, int y){}
    public abstract void draw(Graphics2D g2);
}
package io.mblueberry.object.items;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
public abstract class GameObject {
    public int attackValue;
    public int defenseValue;
    public transient BufferedImage icon;
    public String displayName;

    public abstract void draw(Graphics2D g2);
}
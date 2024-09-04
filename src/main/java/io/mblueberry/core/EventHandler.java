package io.mblueberry.core;

import io.mblueberry.Game;

import java.awt.*;

public class EventHandler {

    Game game;
    Rectangle eventRect;
    int x, y;


    public EventHandler(Game game) {
        this.game = game;
        eventRect = new Rectangle();
        eventRect.x = 31;
        eventRect.y = 31;
        eventRect.width = 2;
        eventRect.height = 2;

        x = eventRect.x;
        y = eventRect.y;
    }

    public void checkEvent(){}

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;


        return hit;
    }
}

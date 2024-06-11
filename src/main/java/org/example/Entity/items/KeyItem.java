package org.example.Entity.items;

import org.example.Entity.Entity;
import org.example.Game;

public class KeyItem extends Entity {


    public KeyItem(Game game) {
        super(game);
       // String name = "Key";
        direction = "hold";
        hold = setup("/objects/key");
    }


}

package io.mblueberry.core.container;


import com.google.gson.JsonObject;
import io.mblueberry.Game;
import lombok.Getter;
import lombok.Setter;

import static io.mblueberry.Const.CHEST_CONTAINER_SIZE;

@Setter
@Getter
public class ChestContainer extends Container {
    private int x;
    private int y;
    public ChestContainer(Game game, int x, int y) {
        super(game, CHEST_CONTAINER_SIZE);
    }

    public JsonObject save() {
        return null;
    }
}

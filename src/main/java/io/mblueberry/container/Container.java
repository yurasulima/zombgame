package io.mblueberry.container;

import io.mblueberry.object.items.GameObject;
import io.mblueberry.Game;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private Game game;
    private int size;
    List<GameObject> data;

    public Container(Game game, int size) {
        this.game = game;
        data = new ArrayList<>(size);
        for (int i = 0; i < size-1; i++) {
            data.add(null);
        }
    }


    public List<GameObject> getAll() {
        return data;
    }

    public GameObject get(int index) {
        return data.get(index);
    }

    public void add(GameObject gameObject) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                data.set(i, gameObject);
                return;
            }
        }
    }

    public void remove(int index) {
        data.remove(index);
    }

}

package io.mblueberry.container;

import io.mblueberry.object.item.GameObject;
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
    public void addAt(GameObject newItem, int i) {
        GameObject containerItem = data.get(i);
        if (containerItem == null) {
            data.set(i, newItem);
        } else {
            if (containerItem.itemType.equals(newItem.itemType)) {
                if (containerItem.stackCount < containerItem.stackSize) {
                    if (containerItem.stackSize >= containerItem.stackCount + newItem.getStackCount()) {
                        containerItem.stackCount += newItem.stackCount;
                        newItem.stackCount = 0;
                        data.set(i, containerItem);
                    } else {
                        int maxAdd = containerItem.stackSize - containerItem.getStackCount();
                        newItem.stackCount = newItem.stackCount - maxAdd;
                        containerItem.stackCount = containerItem.stackCount + maxAdd;
                        data.set(i, containerItem);
                    }
                }
            }
        }
    }



    public void save(int index) {
       //TODO save all chests
    }
    public void remove(int index) {
        data.set(index, null);
    }

}

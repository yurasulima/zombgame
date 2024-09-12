package io.mblueberry.core.ai;

import lombok.ToString;

@ToString
public class Node {

    Node parent;
    public int x;
    public int y;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

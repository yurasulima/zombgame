package io.mblueberry.ai;

import io.mblueberry.Const;
import io.mblueberry.Game;
import io.mblueberry.object.block.Block;
import io.mblueberry.object.entity.Entity;

import java.util.ArrayList;

public class PathFinder {
    Game game;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    int mapSize;
    public PathFinder(Game game) {
        this.game = game;

        mapSize = game.world.getMapSize();
        instantiateNodes();
    }

    public void instantiateNodes() {

        node = new Node[mapSize][mapSize];

        int x = 0;
        int y = 0;


        while (x < mapSize && y < mapSize) {
            node[x][y] = new Node(x, y);
            x++;
            if (x == mapSize) {
                x = 0;
                y++;
            }
        }
    }

    public void resetNode() {
        int x = 0;
        int y = 0;


        while (x < mapSize && y < mapSize) {
            node[x][y].open = false;
            node[x][y].checked = false;
            node[x][y].solid = false;

            x++;
            if (x == mapSize) {
                x = 0;
                y++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startX, int startY, int goalX, int goalY, Entity entity) {
        resetNode();
        startNode = node[startX][startY];
        currentNode = startNode;
        goalNode = node[goalX][goalY];
        openList.add(currentNode);
        int x = 0;
        int y = 0;

        while (x < mapSize && y < mapSize) {
            Block block = game.world.map[Const.WORLD_LAYER_WORLD][x][y];
            if (block != null && block.isCollision()) {
                node[x][y].solid = true;
            }
            getCost(node[x][y]);
            x++;


            if (x == mapSize) {
                x = 0;
                y++;
            }
        }
    }

    public void getCost(Node node) {

        int xDistance = Math.abs(node.x - startNode.x);
        int yDistance = Math.abs(node.y - startNode.y);
        node.gCost = xDistance + yDistance;


        xDistance = Math.abs(node.x - goalNode.x);
        yDistance = Math.abs(node.y - goalNode.y);
        node.hCost = xDistance + yDistance;


        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int x = currentNode.x;
            int y = currentNode.y;
            if (x < 1 || y < 1) {
                return false;
            }

            currentNode.checked = true;
            openList.remove(currentNode);

            if (x - 1 > 1) {
                openNode(node[x][y - 1]);
            }
            if (y - 1 > 1) {
                openNode(node[x - 1][y]);
            }
            if (x + 1 < mapSize) {
                openNode(node[x][y + 1]);
            }
            if (y + 1 < mapSize) {
                openNode(node[x + 1][y]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.isEmpty()) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    private void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
       }
    }
}

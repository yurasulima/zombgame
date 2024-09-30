package io.mblueberry.command;

import io.mblueberry.object.entity.Entity;

public class MoveToCommand implements Command {
    private final Entity entity;
    private final int targetX;
    private final int targetY;

    public MoveToCommand(Entity entity, int targetX, int targetY) {
        this.entity = entity;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void execute() {
        entity.moveTo(targetX, targetY);
    }
}

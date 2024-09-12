package io.mblueberry.core.command;

import io.mblueberry.core.object.entity.Entity;

public class MoveToCommand implements Command {
    private Entity entity;
    private int targetX;
    private int targetY;

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

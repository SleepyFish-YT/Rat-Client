package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.entity.Entity;

public class EventRenderItemOnGround extends Event {

    public Entity itemEntity;

    public EventRenderItemOnGround(Entity entity) {
        this.itemEntity = entity;
    }

}
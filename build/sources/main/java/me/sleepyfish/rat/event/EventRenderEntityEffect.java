package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.entity.Entity;

public class EventRenderEntityEffect extends Event {

    public Entity entity;

    public EventRenderEntityEffect(Entity entity) {
        this.entity = entity;
    }

}
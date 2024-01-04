package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.entity.Entity;

public class EventRenderHitbox extends Event {

    public Entity entity;
    public double x, y, z;

    public float entityYaw;
    public float partialTicks;

    public EventRenderHitbox(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;

        this.entityYaw = entityYaw;
        this.partialTicks = partialTicks;
    }

}
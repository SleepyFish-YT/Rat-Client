package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.entity.Entity;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class EventRenderItemOnGround extends Event {

    public Entity itemEntity;

    public EventRenderItemOnGround(Entity entity) {
        this.itemEntity = entity;
    }

}
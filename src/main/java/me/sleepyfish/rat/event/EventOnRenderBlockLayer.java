package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class EventOnRenderBlockLayer extends Event {

    public EnumWorldBlockLayer layer;
    public Entity entity;

    public EventOnRenderBlockLayer(EnumWorldBlockLayer layer, Entity entity) {
        this.layer = layer;
        this.entity = entity;
    }

}
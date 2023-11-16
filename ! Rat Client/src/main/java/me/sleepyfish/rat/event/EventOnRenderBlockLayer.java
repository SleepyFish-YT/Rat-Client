package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;

public class EventOnRenderBlockLayer extends Event {

    public EnumWorldBlockLayer layer;
    public Entity entity;

    public EventOnRenderBlockLayer(EnumWorldBlockLayer layer, Entity entity) {
        this.layer = layer;
        this.entity = entity;
    }

}
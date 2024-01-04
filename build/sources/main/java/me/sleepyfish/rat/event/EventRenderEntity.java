package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity extends Event {

    public EntityLivingBase entity;
    public float partialTicks;

    public <T extends EntityLivingBase> EventRenderEntity(T entity, float partialTicks) {
        this.entity = entity;
        this.partialTicks = partialTicks;
    }

}
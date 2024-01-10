package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class EventRenderEntity extends Event {

    public final EntityLivingBase entity;
    public final float partialTicks;

    public <T extends EntityLivingBase> EventRenderEntity(final T entity, final float partialTicks) {
        this.entity = entity;
        this.partialTicks = partialTicks;
    }

}
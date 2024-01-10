package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class EventRenderParticle extends Event {

    public WorldRenderer worldRender;
    public Entity entity;

    public float partialTicks;
    public float rotationX;
    public float rotationZ;
    public float rotationYZ;
    public float rotationXY;
    public float rotationXZ;

    public EventRenderParticle(WorldRenderer worldRender, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        this.worldRender = worldRender;
        this.entity = entity;
        this.partialTicks = partialTicks;
        this.rotationX = rotationX;
        this.rotationZ = rotationZ;
        this.rotationYZ = rotationYZ;
        this.rotationXY = rotationXY;
        this.rotationXZ = rotationXZ;
    }

}
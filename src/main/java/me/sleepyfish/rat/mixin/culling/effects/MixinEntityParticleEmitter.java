package me.sleepyfish.rat.mixin.culling.effects;

import me.sleepyfish.rat.event.EventRenderParticle;

import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.particle.EntityParticleEmitter;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
@Mixin(EntityParticleEmitter.class)
public class MixinEntityParticleEmitter {

    @Inject(method = "renderParticle", at = @At("HEAD"), cancellable = true)
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        EventRenderParticle event = new EventRenderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        event.call();

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}
package me.sleepyfish.rat.mixin.performance.effects;

import me.sleepyfish.rat.event.EventRenderEntityEffect;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EffectRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {

    @Inject(method = "renderParticles", at = @At("HEAD"), cancellable = true)
    public void spawnEffectParticle(Entity entity, float p_renderParticles_2_, CallbackInfo ci) {
        final EventRenderEntityEffect event = new EventRenderEntityEffect(entity);
        event.call();

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}
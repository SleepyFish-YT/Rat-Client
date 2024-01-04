package me.sleepyfish.rat.mixin.culling.effects;

import me.sleepyfish.rat.event.EventRenderEntityEffect;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {

    @Inject(method = "renderParticles", at = @At("HEAD"), cancellable = true)
    public void spawnEffectParticle(Entity entity, float p_renderParticles_2_, CallbackInfo ci) {
        EventRenderEntityEffect event = new EventRenderEntityEffect(entity);
        event.call();

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}
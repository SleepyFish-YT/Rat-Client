package me.sleepyfish.rat.mixin.culling;

import me.sleepyfish.rat.event.EventRenderHitbox;

import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {

    @Inject(method = "renderDebugBoundingBox", at = @At("HEAD"), cancellable = true)
    public void renderDebugBoundingBox(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventRenderHitbox event = new EventRenderHitbox(entity, x, y, z, entityYaw, partialTicks);
        event.call();

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

}
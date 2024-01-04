package me.sleepyfish.rat.mixin.culling;

import me.sleepyfish.rat.event.EventRenderHitbox;
import me.sleepyfish.rat.event.EventRenderItemOnGround;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.client.renderer.entity.RenderManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
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

    @Inject(method = "doRenderEntity", at = @At("HEAD"), cancellable = true)
    public void doRenderEntity(Entity entity, double a, double b, double c, float d, float e, boolean f, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof EntityItem) {
            EventRenderItemOnGround event = new EventRenderItemOnGround(entity);
            event.call();

            if (event.isCancelled()) {
                cir.setReturnValue(false);
            }
        }
    }

}
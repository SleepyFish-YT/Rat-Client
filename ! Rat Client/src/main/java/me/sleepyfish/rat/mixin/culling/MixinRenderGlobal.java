package me.sleepyfish.rat.mixin.culling;

import me.sleepyfish.rat.event.EventOnRenderBlockLayer;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.client.renderer.RenderGlobal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I", at = @At("HEAD"), cancellable = true)
    private void renderBlockLayer(EnumWorldBlockLayer layer, double r, int a, Entity entity, CallbackInfoReturnable<Integer> cir) {
        EventOnRenderBlockLayer event = new EventOnRenderBlockLayer(layer, entity);
        event.call();

        if (event.isCancelled()) {
            cir.setReturnValue(0);
        }
    }

}
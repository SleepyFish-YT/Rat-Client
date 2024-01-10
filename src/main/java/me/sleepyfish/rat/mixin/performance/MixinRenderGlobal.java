package me.sleepyfish.rat.mixin.performance;

import me.sleepyfish.rat.modules.impl.Performance;
import me.sleepyfish.rat.event.EventOnRenderBlockLayer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.multiplayer.WorldClient;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private WorldClient theWorld;

    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I", at = @At("HEAD"), cancellable = true)
    private void renderBlockLayer(EnumWorldBlockLayer layer, double partialTicks, int pass, Entity entity, CallbackInfoReturnable<Integer> cir) {
        final EventOnRenderBlockLayer event = new EventOnRenderBlockLayer(layer, entity);
        event.call();

        if (event.isCancelled()) {
            cir.setReturnValue(0);
        }
    }

    /**
     * @author sleepy
     * @reason modding entityFX renderer
     */
    @Overwrite
    private EntityFX spawnEntityFX(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... parameters) {
        if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
            if (Performance.effectsBehind.isEnabled()) {
                if (Performance.isBlockBehindPlayer(new BlockPos(xCoord, yCoord, zCoord)))
                    return null;
            }

            if (ignoreRange) {
                return this.mc.effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, parameters);
            } else {
                final double d0 = this.mc.getRenderViewEntity().posX - xCoord;
                final double d1 = this.mc.getRenderViewEntity().posY - yCoord;
                final double d2 = this.mc.getRenderViewEntity().posZ - zCoord;

                int i = this.mc.gameSettings.particleSetting;
                if (i == 1 && this.theWorld.rand.nextInt(3) == 0)
                    i = 2;

                return d0 * d0 + d1 * d1 + d2 * d2 > 256.0D ? null : (i > 1 ? null : this.mc.effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, parameters));
            }
        } else {
            return null;
        }
    }

}
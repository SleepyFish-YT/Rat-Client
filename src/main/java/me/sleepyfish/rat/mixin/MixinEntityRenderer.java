package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.impl.Animations;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.event.EventCameraRotation;
import me.sleepyfish.rat.event.EventPlayerHeadRotation;
import me.sleepyfish.rat.utils.interfaces.IMixinEntityLivingBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    private float rotationYaw;
    private float prevRotationYaw;

    private float rotationPitch;
    private float prevRotationPitch;

    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"))
    public void updateCameraAndRender(EntityPlayerSP entityPlayerSP, float yaw, float pitch) {
        final EventPlayerHeadRotation event = new EventPlayerHeadRotation(yaw, pitch);
        event.call();

        yaw = event.getYaw();
        pitch = event.getPitch();

        if (!event.isCancelled())
            entityPlayerSP.setAngles(yaw, pitch);
    }

    @Inject(method = "orientCamera", at = @At("HEAD"))
    public void orientCamera(float partialTicks, CallbackInfo ci) {
        rotationYaw = MinecraftUtils.mc.getRenderViewEntity().rotationYaw;
        prevRotationYaw = MinecraftUtils.mc.getRenderViewEntity().prevRotationYaw;
        rotationPitch = MinecraftUtils.mc.getRenderViewEntity().rotationPitch;
        prevRotationPitch = MinecraftUtils.mc.getRenderViewEntity().prevRotationPitch;
        float roll = 0;

        final EventCameraRotation event = new EventCameraRotation(rotationYaw, rotationPitch, roll);
        event.call();

        rotationYaw = event.getYaw();
        rotationPitch = event.getPitch();
        roll = event.getRoll();

        prevRotationYaw = event.getYaw();
        prevRotationPitch = event.getPitch();
        GlStateManager.rotate(roll, 0, 0, 1);
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"))
    public float getRotationYaw(Entity entity) {
        return rotationYaw;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"))
    public float getPrevRotationYaw(Entity entity) {
        return prevRotationYaw;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"))
    public float getRotationPitch(Entity entity) {
        return rotationPitch;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"))
    public float getPrevRotationPitch(Entity entity) {
        return prevRotationPitch;
    }

    @Inject(method = "renderStreamIndicator", at = @At("HEAD"), cancellable = true)
    private void renderStreamIndicator(CallbackInfo ci) {
        ci.cancel();
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float orientCamera(Entity entity) {
        if (entity instanceof EntityPlayer) {
            if (Animations.oldSneak.isEnabled()) {
                if (Rat.instance.moduleFields.Animations.isEnabled()) {
                    return Animations.getOldSneakValue(entity);
                }
            }
        }

        return entity.getEyeHeight();
    }

    @Redirect(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V"))
    public void setupCameraTransform(EntityRenderer entityRenderer, float f) {
        //if (!Rat.instance.moduleManager.getModByClass(MinimalBobbingMod.isEnabled())
        //    this.setupViewBobbing(f);
    }

    @Redirect(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;hurtCameraEffect(F)V"))
    public void setupCameraTransform2(EntityRenderer entityRenderer, float f) {
        //if (!Rat.instance.moduleManager.getModByClass(NoHurtcamMod.isEnabled())
        //    this.hurtCameraEffect(f);
    }

    @Inject(method = "renderHand", at = @At(value = "HEAD"))
    public void renderHand(float partialTicks, int xOffset, CallbackInfo callback) {
        if (Animations.oldBlock.isEnabled()) {
            if (Rat.instance.moduleFields.Animations.isEnabled()) {
                final Minecraft mc = MinecraftUtils.mc;

                if (mc.thePlayer != null && mc.objectMouseOver != null) {
                    if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                        if (mc.gameSettings.keyBindAttack.isKeyDown() && mc.gameSettings.keyBindUseItem.isKeyDown()) {
                            if (mc.thePlayer.getItemInUseCount() > 0 && (!mc.thePlayer.isSwingInProgress
                                    || mc.thePlayer.swingProgressInt >= ((IMixinEntityLivingBase) mc.thePlayer).accessArmSwingAnimationEnd()
                                    / 2 || mc.thePlayer.swingProgressInt < 0)) {
                                mc.thePlayer.swingProgressInt = -1;
                                mc.thePlayer.isSwingInProgress = true;
                            }
                        }
                    }
                }
            }
        }
    }

}
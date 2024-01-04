package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.event.EventPotionUpdate;
import me.sleepyfish.rat.utils.interfaces.IMixinEntityLivingBase;

import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements IMixinEntityLivingBase {

    @Shadow protected abstract int getArmSwingAnimationEnd();

    @Inject(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;onUpdate(Lnet/minecraft/entity/EntityLivingBase;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void updatePotionEffects(CallbackInfo ci, Iterator<Integer> iterator, Integer integer, PotionEffect potioneffect) {
        EventPotionUpdate event = new EventPotionUpdate(potioneffect);
        event.call();

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Override
    public int accessArmSwingAnimationEnd() {
        return this.getArmSwingAnimationEnd();
    }

}
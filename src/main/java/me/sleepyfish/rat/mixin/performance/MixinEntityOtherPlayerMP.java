package me.sleepyfish.rat.mixin.performance;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.impl.Performance;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityOtherPlayerMP.class)
public class MixinEntityOtherPlayerMP {

    @Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityOtherPlayerMP;updateArmSwingProgress()V", shift = At.Shift.AFTER), cancellable = true)
    private void onLivingUpdate(CallbackInfo ci) {
        if (Rat.instance.moduleFields.Performance.isEnabled()) {
            if (Performance.smallerMcChecks.isEnabled())
                ci.cancel();
        }
    }

}
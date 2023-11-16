package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.entity.AbstractClientPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

    @Shadow
    public abstract ResourceLocation getLocationSkin();

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> cir) {
        if (Rat.instance.capeManager == null)
            return;

        if (!Rat.instance.capeManager.getCurrentCape().equals("None"))
            if (MinecraftUtils.mc.thePlayer.getLocationSkin() == this.getLocationSkin())
                cir.setReturnValue(Rat.instance.capeManager.getCapeByName(Rat.instance.capeManager.getCurrentCape()).getCape());
    }

}
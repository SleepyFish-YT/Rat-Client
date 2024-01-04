package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.cheat.FastPlace;
import me.sleepyfish.rat.modules.impl.Hitdelay;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow private int rightClickDelayTimer;

    @Shadow private int leftClickCounter;

    @Inject(method = "startGame", at = @At("TAIL"))
    public void startGame(CallbackInfo ci) {
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        Rat.instance.configManager.save();
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void runTick(CallbackInfo ci) {
        if (Rat.instance.moduleManager.getModByClass(Hitdelay.class).isEnabled()) {
            this.leftClickCounter = 0;
        }

        if (Rat.instance.moduleManager.hasFailed()) {
            if (Rat.instance.moduleManager.getModByClass(FastPlace.class).isEnabled()) {
                this.rightClickDelayTimer = 0;
            }
        }
    }

}
package me.sleepyfish.rat.mixin.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends GuiScreen {

    @Shadow protected abstract boolean checkHotbarKeys(int par1);

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void mouseClickedTail(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        this.checkHotbarKeys(mouseButton - 100);
    }

}
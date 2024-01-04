package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.impl.Chat;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
@Mixin(GuiChat.class)
public class MixinGuiChat extends GuiScreen {

    @Inject(method = "initGui", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        Chat.barAnimation = new SimpleAnimation(0.0F);
        Chat.scrollY = 0F;
    }

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void drawScreenHead(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (Rat.instance.moduleManager.getModByClass(Chat.class).isEnabled()) {
            Chat.chatHead(this, ci);
        }
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreenTail(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (Rat.instance.moduleManager.getModByClass(Chat.class).isEnabled()) {
            Chat.chatTail();
        }
    }

}
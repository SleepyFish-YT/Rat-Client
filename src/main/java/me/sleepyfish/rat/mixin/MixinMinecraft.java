package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.interfaces.IMixinMinecraft;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.DefaultResourcePack;

import org.lwjgl.input.Keyboard;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

    @Shadow
    private int rightClickDelayTimer;

    @Shadow
    private int leftClickCounter;

    @Shadow
    private Entity renderViewEntity;

    @Shadow @Final
    public DefaultResourcePack mcDefaultResourcePack;

    @Shadow
    private Timer timer;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    public EntityRenderer entityRenderer;

    @Shadow
    public GameSettings gameSettings;

    @Shadow public GuiScreen currentScreen;

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        Rat.instance.configManager.unInject();
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void runTick(CallbackInfo ci) {
        if (Rat.instance.moduleManager.hasFailed()) {
            if (Rat.instance.moduleFields.FastPlace.isEnabled())
                this.rightClickDelayTimer = 0;
        }

        if (Rat.instance.moduleFields.Hitdelay.isEnabled())
            this.leftClickCounter = 0;
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    public void clearLoadedMaps(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        if (worldClientIn != this.theWorld)
            this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
    }

    @Redirect(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventCharacter()C", remap = false))
    private char resolveForeignKeyboards() {
        return (char) (Keyboard.getEventCharacter() + 256);
    }

    @Redirect(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/stream/IStream;func_152935_j()V"))
    public void twitch1(IStream instance) {  }

    @Redirect(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/stream/IStream;func_152922_k()V"))
    public void twitch2(IStream instance) {  }

    /**
     * @author Sleepy
     * @reason Smoother maingui
     */
    @Overwrite
    public int getLimitFramerate() {
        return this.currentScreen == null ? this.gameSettings.limitFramerate : WindowsUtils.refreshRate;
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public DefaultResourcePack getMcDefaultResourcePack() {
        return this.mcDefaultResourcePack;
    }

    @Override
    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }

}
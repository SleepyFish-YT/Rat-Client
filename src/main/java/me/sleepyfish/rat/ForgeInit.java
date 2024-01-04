package me.sleepyfish.rat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.impl.Crosshair;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.InjectionUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.impl.SettingModule;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(name = "Rat Client", modid = "rat", version = "0.9", acceptedMinecraftVersions = "1.8.9", modLanguage = "java")
public class ForgeInit {

    @Mod.EventHandler
    public void initialize(FMLPostInitializationEvent e) {
        Rat.instance.inject();

        MinecraftForge.EVENT_BUS.register(this);
        Rat.instance.eventManager.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (InputUtils.isKeyDown(SettingModule.guiKeybind.keycode)) {
            if (MinecraftUtils.mc.currentScreen == null) {
                InjectionUtils.oneTimeInjectChecks();
                MinecraftUtils.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiModuleMove());
            }
        }

        for (Module mod : Rat.instance.moduleManager.getModules()) {
            if (mod.isEnabled()) {
                if (mod.mc.thePlayer != null) {
                    mod.tickUpdate();
                }
            }
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {
        if (Rat.instance.guiManager.inClickGui()) {
            if (e.type == RenderGameOverlayEvent.Pre.ElementType.CROSSHAIRS) {
                if (Rat.instance.moduleManager.getModByClass(Crosshair.class).isEnabled())
                    return;

                e.setCanceled(true);
            }

            if (e.type == RenderGameOverlayEvent.Post.ElementType.CROSSHAIRS) {
                if (Rat.instance.moduleManager.getModByClass(Crosshair.class).isEnabled())
                    return;

                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        //Rat.instance.guiManager.getClickGui().renderHudModules = ((MinecraftUtils.mc.currentScreen == null || Rat.instance.guiManager.inClickGui()) && MinecraftUtils.mc.player != null);
        Rat.instance.guiManager.getRatGuiModuleMove().renderHudModules = (MinecraftUtils.mc.currentScreen == null || Rat.instance.guiManager.inClickGui());

        if (Rat.instance.guiManager.getRatGuiModuleMove().renderHudModules) {
            if (MinecraftUtils.mc.gameSettings.showDebugInfo)
                return;

            Rat.instance.guiManager.getRatGuiModuleMenu().modDrawUpdateAndHover();
        }
    }

}
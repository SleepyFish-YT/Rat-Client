package me.sleepyfish.rat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.InjectionUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.impl.SettingModule;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(name = "Rat Client", modid = "rat", version = "0.6", acceptedMinecraftVersions = "1.8.9", modLanguage = "java")
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
                MinecraftUtils.mc.displayGuiScreen(Rat.instance.guiManager.getClickGui());
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
    public void onRender(TickEvent.RenderTickEvent e) {
        //Rat.instance.guiManager.getClickGui().renderHudModules = ((MinecraftUtils.mc.currentScreen == null || Rat.instance.guiManager.inClickGui()) && MinecraftUtils.mc.player != null);
        Rat.instance.guiManager.getClickGui().renderHudModules = (MinecraftUtils.mc.currentScreen == null || Rat.instance.guiManager.inClickGui());

        if (Rat.instance.guiManager.getClickGui().renderHudModules) {
            if (MinecraftUtils.mc.gameSettings.showDebugInfo)
                return;

            for (Module mod : Rat.instance.moduleManager.getModules()) {

                if (mod.isHudMod()) {
                    if (mod.isEnabled()) {

                        if (mod.mc.thePlayer != null) {
                            mod.renderUpdate();
                            mod.drawComponent();
                        }

                        if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), mod.getX() - 2, mod.getY() - 2, mod.getWidth() + 4, mod.getHeight() + 4)) {
                            if (Rat.instance.guiManager.getClickGui().allowMoveHudModules) {
                                Rat.instance.guiManager.getClickGui().overAModule = true;
                                mod.drawHover();
                            }
                        } else {
                            Rat.instance.guiManager.getClickGui().overAModule = false;
                        }

                        if (mod.isMoving()) {
                            mod.setX(InputUtils.getX());
                            mod.setY(InputUtils.getY());
                        }
                    }
                }
            }
        }

    }

}
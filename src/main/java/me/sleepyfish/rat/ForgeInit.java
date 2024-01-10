package me.sleepyfish.rat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.impl.Performance;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.settings.SettingModule;

import me.sleepyfish.rat.utils.misc.WindowsUtils;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mod(name = "Rat Client", modid = "rat", version = "v1.1", acceptedMinecraftVersions = "1.8.9")
public class ForgeInit {

    @Mod.EventHandler
    public void initialize(FMLPostInitializationEvent e) {
        Rat.instance.inject();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (InputUtils.isKeyDown(SettingModule.guiKeybind.keycode)) {
            if (MinecraftUtils.mc.currentScreen == null) {
                MinecraftUtils.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiModuleMove());
            }
        }

        if (MinecraftUtils.mc.thePlayer != null) {
            for (final Module mod : Rat.instance.moduleManager.getModules()) {
                if (mod.isEnabled())
                    mod.tickUpdate();
            }
        }

        MinecraftUtils.mc.gameSettings.useVbo = true;
        MinecraftUtils.mc.gameSettings.fboEnable = true;
    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent e) {
        if (Performance.clearRam.isEnabled()) {
            if (e.entity == MinecraftUtils.mc.thePlayer) {
                if (Rat.instance.moduleFields.Performance.isEnabled())
                    WindowsUtils.clearRam();
            }
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {
        if (Rat.instance.guiManager.inClickGui()) {
            if (e.type == RenderGameOverlayEvent.Pre.ElementType.CROSSHAIRS) {
                if (Rat.instance.moduleFields.Crosshair.isEnabled())
                    return;

                e.setCanceled(true);
            }

            if (e.type == RenderGameOverlayEvent.Post.ElementType.CROSSHAIRS) {
                if (Rat.instance.moduleFields.Crosshair.isEnabled())
                    return;

                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if (MinecraftUtils.mc.currentScreen == null || MinecraftUtils.mc.currentScreen == Rat.instance.guiManager.getRatGuiModuleMove()) {
            if (MinecraftUtils.mc.gameSettings.showDebugInfo)
                return;

            Rat.instance.guiManager.getRatGuiModuleMove().modDrawUpdateAndHover();
        }
    }

}
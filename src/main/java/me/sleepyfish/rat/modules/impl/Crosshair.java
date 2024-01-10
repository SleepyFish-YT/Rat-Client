package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Crosshair extends Module {

    private final ToggleSetting render;

    public Crosshair() {
        super("Crosshair", "Change the Crosshair HUD");

        this.addSetting(render = new ToggleSetting("Render", false));

        this.setCustom(true);
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {

        // head
        if (e.type == RenderGameOverlayEvent.Pre.ElementType.CROSSHAIRS) {
            if (!render.isEnabled()) {
                e.setCanceled(true);
            }

            // custom crosshair head

        }

        // tail
        if (e.type == RenderGameOverlayEvent.Post.ElementType.CROSSHAIRS) {
            if (!render.isEnabled()) {
                e.setCanceled(true);
            }

            // custom crosshair tail

        }
    }

}
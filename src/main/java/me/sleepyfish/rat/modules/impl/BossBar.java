package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class BossBar extends Module {

    public BossBar() {
        super("Bossbar", "Change the Boss Bar HUD.");

        this.setCustom(true);
        this.toggle();
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.Pre.ElementType.BOSSHEALTH)
            e.setCanceled(true);
    }

}
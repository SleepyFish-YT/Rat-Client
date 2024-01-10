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
public class Tablist extends Module {

    private final ToggleSetting render;

    public Tablist() {
        super("Tablist", "Change the Player / Tab List HUD");

        this.addSetting(this.render = new ToggleSetting("Render", true));

        this.setCustom(true);
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {

        // head
        if (e.type == RenderGameOverlayEvent.Pre.ElementType.PLAYER_LIST) {
            if (!this.render.isEnabled()) {
                e.setCanceled(true);
            } else {
            }
        }

        // tail
        if (e.type == RenderGameOverlayEvent.Post.ElementType.PLAYER_LIST) {
            if (!this.render.isEnabled()) {
                e.setCanceled(true);
            } else {
            }
        }
    }

}
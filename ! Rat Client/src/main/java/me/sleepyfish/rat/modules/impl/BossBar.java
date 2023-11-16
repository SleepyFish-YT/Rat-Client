package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BossBar extends Module {

    private final ToggleSetting render;

    public BossBar() {
        super("Boss Bar", "Change the Boss Bar HUD.");

        this.addSetting(render = new ToggleSetting("Render", false));

        this.setCustom(true);
        this.toggle();
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {

        // head
        if (e.type == RenderGameOverlayEvent.Pre.ElementType.BOSSHEALTH) {
            if (!render.isEnabled()) {
                e.setCanceled(true);
            }

            // custom boss bar head

        }

        // tail
        if (e.type == RenderGameOverlayEvent.Post.ElementType.BOSSHEALTH) {
            if (!render.isEnabled()) {
                e.setCanceled(true);
            }

            // custom boss bar tail

        }
    }

}
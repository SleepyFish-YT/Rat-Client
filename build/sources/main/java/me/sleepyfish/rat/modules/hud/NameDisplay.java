package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;

public class NameDisplay extends Module {

    public NameDisplay() {
        super("Name", "Displays your Ingame Name on the HUD",20, 80);
    }

    @Override
    public void renderUpdate() {
        this.setText(mc.thePlayer.getName());
    }

}
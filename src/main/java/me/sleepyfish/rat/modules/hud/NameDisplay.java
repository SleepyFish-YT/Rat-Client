package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class NameDisplay extends Module {

    public NameDisplay() {
        super("Name", "Displays your Ingame Name on the HUD",20, 80);
    }

    @Override
    public void renderUpdate() {
        this.setText(mc.thePlayer.getName());
    }

}
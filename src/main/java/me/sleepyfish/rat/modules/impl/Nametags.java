package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Nametags extends Module {

    public static ToggleSetting removeAll;
    public static ToggleSetting renderBg;

    public Nametags() {
        super("Nametag", "Render your Nametag in F3");

        this.addSetting(this.removeAll = new ToggleSetting("Remove all", "Removes all Nametags", false));
        this.addSetting(this.renderBg = new ToggleSetting("Render backgrounds", "Render Nametags background", true));

        this.toggle();
    }

}
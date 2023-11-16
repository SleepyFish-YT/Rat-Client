package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

public class Chat extends Module {

    public static ToggleSetting render;

    public Chat() {
        super("Chat", "Change the Chat HUD.");

        this.addSetting(render = new ToggleSetting("Render", true));

        this.setCustom(true);
    }

}
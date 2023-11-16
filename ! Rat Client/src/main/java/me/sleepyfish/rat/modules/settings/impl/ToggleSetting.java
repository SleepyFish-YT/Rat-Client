package me.sleepyfish.rat.modules.settings.impl;

import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.SoundUtils;

public class ToggleSetting extends Setting {

    private String name;
    private String description;
    private boolean isEnabled;

    public ToggleSetting(String name, String description, boolean enabled) {
        super("toggle");

        this.name = name;
        this.description = description;
        this.isEnabled = enabled;
    }

    public ToggleSetting(String name, boolean enabled) {
        super("toggle");

        this.name = name;
        this.description = "-";
        this.isEnabled = enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void toggle() {
        SoundUtils.playSound("click", 1.0F, 0.8F);

        if (this.isEnabled) {
            this.onEnable();
            this.isEnabled = false;
        } else {
            this.onDisable();
            this.isEnabled = true;
        }
    }

    public void onEnable() {
        // event stuff here ...
    }

    public void onDisable() {
        // event stuff here ...
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void disable() {
        this.name = "";
        this.description = "";
        this.isEnabled = false;
    }
}
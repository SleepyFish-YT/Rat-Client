package me.sleepyfish.rat.modules.settings.impl;

import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.InputUtils;

public class KeybindSetting extends Setting {

    private String name;
    private String description;

    public int keycode;
    public final int oldKeycode;

    public KeybindSetting(String name, int keycode) {
        super("bind");

        this.name = name;
        this.description = "-";

        this.keycode = keycode;
        this.oldKeycode = keycode;
    }

    public KeybindSetting(String name, String description, int keycode) {
        super("bind");

        this.name = name;
        this.description = description;

        this.keycode = keycode;
        this.oldKeycode = keycode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDown() {
        return InputUtils.isButtonDown(this.keycode);
    }

    @Override
    public void disable() {
        this.name = "";
        this.description = "";
        this.keycode = 0;
    }

}
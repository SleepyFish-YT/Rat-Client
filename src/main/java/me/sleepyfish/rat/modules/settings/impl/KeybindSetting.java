package me.sleepyfish.rat.modules.settings.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.InputUtils;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class KeybindSetting extends Setting {

    private final String name;
    private final String description;

    public int keycode;
    public final int oldKeycode;

    public KeybindSetting(final String name, final int keycode) {
        super(name, "bind");

        this.name = name;
        this.description = "-";

        this.keycode = keycode;
        this.oldKeycode = keycode;
    }

    public KeybindSetting(final String name, final String description, final int keycode) {
        super(name, "bind");

        this.name = name;
        this.description = description;

        this.keycode = keycode;
        this.oldKeycode = keycode;
    }

    public String getName() {
        return name;
    }

    @Override
    public JsonElement getConfig() {
        JsonObject data = new JsonObject();
        data.addProperty("type", this.getType());
        data.addProperty("value", this.keycode);
        return data;
    }

    @Override
    public void applyConfig(JsonObject json) {
        if (!json.get("type").getAsString().equals(this.getType()))
            return;

        this.keycode = json.get("value").getAsInt();
    }

    public String getDescription() {
        return description;
    }

    public boolean isDown() {
        return InputUtils.isButtonDown(this.keycode);
    }

}
package me.sleepyfish.rat.modules.settings.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.InputUtils;

public class KeybindSetting extends Setting {

    private String name;
    private String description;

    public int keycode;
    public final int oldKeycode;

    public KeybindSetting(String name, int keycode) {
        super(name, "bind");

        this.name = name;
        this.description = "-";

        this.keycode = keycode;
        this.oldKeycode = keycode;
    }

    public KeybindSetting(String name, String description, int keycode) {
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

    @Override
    public void disable() {
        this.name = "";
        this.description = "";
        this.keycode = 0;
    }

}
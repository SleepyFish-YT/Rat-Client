package me.sleepyfish.rat.modules.settings.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.SoundUtils;

public class ToggleSetting extends Setting {

    private String name;
    private String description;
    private boolean isEnabled;

    public ToggleSetting(String name, String description, boolean enabled) {
        super(name, "toggle");

        this.name = name;
        this.description = description;
        this.isEnabled = enabled;
    }

    public ToggleSetting(String name, boolean enabled) {
        super(name, "toggle");

        this.name = name;
        this.description = "-";
        this.isEnabled = enabled;
    }

    public String getName() {
        return name;
    }

    @Override
    public JsonElement getConfig() {
        JsonObject data = new JsonObject();
        data.addProperty("type", this.getType());
        data.addProperty("value", this.isEnabled());
        return data;
    }

    @Override
    public void applyConfig(JsonObject json) {
        if (!json.get("type").getAsString().equals(this.getType()))
            return;

        this.isEnabled = json.get("value").getAsBoolean();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void toggle() {
        SoundUtils.playClick();

        if (this.isEnabled) {
            this.onEnableEvent();
            this.isEnabled = false;
        } else {
            this.onDisableEvent();
            this.isEnabled = true;
        }
    }

    public void onEnableEvent() {
        // event stuff here ...
    }

    public void onDisableEvent() {
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
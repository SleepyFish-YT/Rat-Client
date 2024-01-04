package me.sleepyfish.rat.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class Setting {

    private final String type;
    private final String name;

    public Setting(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public abstract void applyConfig(JsonObject data);

    public abstract JsonElement getConfig();

    public abstract void disable();
}
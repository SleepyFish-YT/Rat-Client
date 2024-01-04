package me.sleepyfish.rat.modules.settings;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
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
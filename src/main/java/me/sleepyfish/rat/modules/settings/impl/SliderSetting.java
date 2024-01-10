package me.sleepyfish.rat.modules.settings.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.sleepyfish.rat.modules.settings.Setting;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class SliderSetting extends Setting {

    private final String name;
    private final String description;

    private final double min;
    private final double def;
    private final double max;
    private final double inc;

    protected double val;

    public SliderSetting(final String name, final String description, final double def, final double min, final double max, final double inc) {
        super(name, "slider");

        this.name = name;
        this.description = description;

        this.def = def;
        this.val = def;
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    public SliderSetting(final String name, final double def, final double min, final double max, final  double inc) {
        super(name, "slider");

        this.name = name;
        this.description = "-";

        this.def = def;
        this.val = def;
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    public String getName() {
        return name;
    }

    @Override
    public JsonElement getConfig() {
        final JsonObject data = new JsonObject();
        data.addProperty("type", this.getType());
        data.addProperty("value", this.getValue());
        return data;
    }

    @Override
    public void applyConfig(JsonObject json) {
        if (json.get("type").getAsString().equals(this.getType()))
            this.val = json.get("value").getAsDouble();
    }

    public String getDescription() {
        return description;
    }

    public double getDef() {
        return def;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getInc() {
        return inc;
    }

    public double getValue() {
        return val;
    }

    public int getValueInt() {
        return (int) val;
    }

}
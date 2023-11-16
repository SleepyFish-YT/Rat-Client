package me.sleepyfish.rat.modules.settings;

public abstract class Setting {

    private final String type;

    public Setting(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public abstract void disable();
}
package me.sleepyfish.rat.modules.settings.impl;

import me.sleepyfish.rat.modules.settings.Setting;

public class SliderSetting extends Setting {

    private String name;
    private String description;

    protected double val;

    private double min;
    private double def;
    private double max;
    private double inc;

    public SliderSetting(String name, String description, double def, double min, double max, double inc) {
        super("slider");

        this.name = name;
        this.description = description;

        this.def = def;
        this.val = def;
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    public SliderSetting(String name, double def, double min, double max, double inc) {
        super("slider");

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

    public String getDescription() {
        return description;
    }

    public double getDef() {
        return def;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getInc() {
        return inc;
    }

    public void setInc(double inc) {
        this.inc = inc;
    }

    public double getValue() {
        return val;
    }

    public int getValueInt() {
        return (int) val;
    }

    public void setValue(double val) {
        this.val = val;
    }

    @Override
    public void disable() {
        this.name = "";
        this.description = "";
        this.val = 0;
        this.min = 0;
        this.def = 0;
        this.max = 0;
        this.inc = 0;
    }

}
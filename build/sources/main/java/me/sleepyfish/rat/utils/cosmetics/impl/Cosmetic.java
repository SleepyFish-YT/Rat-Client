package me.sleepyfish.rat.utils.cosmetics.impl;

import net.minecraft.util.ResourceLocation;

public class Cosmetic {

    public final String type;
    public String name;
    public ResourceLocation location;
    public ResourceLocation sample;

    public Cosmetic(String type, String name, ResourceLocation sample, ResourceLocation location) {
        this.type = type;

        this.name = name;
        this.sample = sample;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceLocation getSample() {
        return sample;
    }

    public String getType() {
        return type;
    }

    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    public ResourceLocation getLocation() {
        return location;
    }

}
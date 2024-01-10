package me.sleepyfish.rat.utils.cosmetics.impl;

import net.minecraft.util.ResourceLocation;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Cosmetic {

    public final String type;
    public final String name;
    public final ResourceLocation location;
    public final ResourceLocation sample;

    public Cosmetic(final String type, final String name, final ResourceLocation sample, final ResourceLocation location) {
        this.type = type;

        this.name = name;
        this.sample = sample;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getSample() {
        return sample;
    }

    public String getType() {
        return type;
    }

    public ResourceLocation getLocation() {
        return location;
    }

}
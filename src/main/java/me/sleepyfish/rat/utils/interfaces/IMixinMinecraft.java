package me.sleepyfish.rat.utils.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.util.Timer;
import net.minecraft.client.resources.DefaultResourcePack;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public interface IMixinMinecraft {
    Timer getTimer();

    DefaultResourcePack getMcDefaultResourcePack();

    boolean isRunning();

    Entity getRenderViewEntity();
}
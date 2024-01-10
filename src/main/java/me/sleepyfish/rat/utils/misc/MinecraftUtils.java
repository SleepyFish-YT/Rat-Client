package me.sleepyfish.rat.utils.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class MinecraftUtils {

    public static final String resourcePath = FileManager.resourcePath;

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final ScaledResolution res = new ScaledResolution(MinecraftUtils.mc);

}
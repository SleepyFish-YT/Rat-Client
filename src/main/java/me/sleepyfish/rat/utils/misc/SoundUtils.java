package me.sleepyfish.rat.utils.misc;

import me.sleepyfish.rat.modules.settings.SettingModule;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class SoundUtils {

    public static void playSound(final String name, final float volume, final float pitch) {
        if (!SettingModule.sounds.isEnabled())
            return;

        if (MinecraftUtils.mc.thePlayer != null) {
            switch (name) {
                case "click":
                    MinecraftUtils.mc.thePlayer.playSound("random.click", volume, pitch);
                    break;

                case "chestOpen":
                    MinecraftUtils.mc.thePlayer.playSound("random.chestOpen", volume, pitch);
                    break;

                case "chestClose":
                    MinecraftUtils.mc.thePlayer.playSound("random.chestClose", volume, pitch);
                    break;

                default:
                    MinecraftUtils.mc.thePlayer.playSound(name, volume, pitch);
                    break;
            }
        }
    }

    public static void playClick() {
        SoundUtils.playSound("click", 1F, 0.8F);
    }

}
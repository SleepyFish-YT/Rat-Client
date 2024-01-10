package me.sleepyfish.rat.utils.misc;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class RatUserUtil {

    public static boolean isRatClientUser(final String name) {
        return name.contains("sleepyfish_yt") || name.contains("smellon69420") ||
        name.contains("nosoykioshii") || name.contains("just_a_joel") ||
        name.contains("devofdeath") || name.contains("demonvv") ||
        name.contains("nahzto") || name.contains("nickthetrick123") ||
        name.contains( MinecraftUtils.mc.thePlayer.getName().toLowerCase() );
    }

}
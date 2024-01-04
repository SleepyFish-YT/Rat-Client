package me.sleepyfish.rat.utils.misc;

import net.minecraft.entity.Entity;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class BotUtils {

    public static boolean isBot(Entity target) {
        return target.getUniqueID().version() == 2
                || target.ticksExisted > 99999
                || target.getName().contains("-")
                || target.getName().contains("[")
                || target.getName().contains("]")
                || target.getName().contains(" ")
                || target.getName().length() <= 2;
    }

}
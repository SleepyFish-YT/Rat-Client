package me.sleepyfish.rat.utils.misc;

import net.minecraft.entity.Entity;

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
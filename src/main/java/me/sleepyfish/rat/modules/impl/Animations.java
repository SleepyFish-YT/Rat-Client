package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Animations extends Module {

    public static ToggleSetting oldSneak;
    public static ToggleSetting oldBlock;

    protected static float oldSneakEyeHeight;
    protected static long oldSneakLastUpdate;

    public Animations() {
        super("Animations", "Change to old 1.7 Animations");

        this.addSetting(oldSneak = new ToggleSetting("Old Sneak", true));
        this.addSetting(oldBlock = new ToggleSetting("Old Attack", true));

        this.toggle();
    }

    public static float getOldSneakValue(Entity entity) {
        final EntityPlayer player = (EntityPlayer) entity;

        float eyeHight = player.getEyeHeight();
        if (player.isSneaking())
            eyeHight += 0.08F;

        final float actual = player.isSneaking() ? 0.08F : 0;
        final long lastUpdate = System.currentTimeMillis() - oldSneakLastUpdate;

        oldSneakLastUpdate = System.currentTimeMillis();

        if (actual > oldSneakEyeHeight) {
            oldSneakEyeHeight += lastUpdate / 900F;
            if (actual < oldSneakEyeHeight)
                oldSneakEyeHeight = actual;

        } else if (actual < oldSneakEyeHeight) {
            oldSneakEyeHeight -= lastUpdate / 900F;
            if (actual > oldSneakEyeHeight)
                oldSneakEyeHeight = actual;
        }

        return eyeHight - oldSneakEyeHeight;
    }

}
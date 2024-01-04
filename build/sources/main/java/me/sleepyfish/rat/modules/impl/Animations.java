package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Animations extends Module {

    public static ToggleSetting oldSneak;
    public static ToggleSetting oldBlock;

    protected static float oldSneakEyeHeight;
    protected static long oldSneakLastUpdate;

    public Animations() {
        super("Animations", "Change to old 1.7 Animations.");

        this.addSetting(oldSneak = new ToggleSetting("Old Sneak", true));
        this.addSetting(oldBlock = new ToggleSetting("Old Attack", true));

        this.toggle();
    }

    public static float getOldSneakValue(Entity entity) {
        EntityPlayer player = (EntityPlayer) entity;

        float eyeHight = player.getEyeHeight();
        if (player.isSneaking()) {
            eyeHight += 0.08F;
        }

        float actual = player.isSneaking() ? 0.08F : 0;
        long lastUpdate = System.currentTimeMillis() - oldSneakLastUpdate;

        oldSneakLastUpdate = System.currentTimeMillis();

        if (actual > oldSneakEyeHeight) {
            oldSneakEyeHeight += lastUpdate / 500f;
            if (actual < oldSneakEyeHeight) {
                oldSneakEyeHeight = actual;
            }
        } else if (actual < oldSneakEyeHeight) {
            oldSneakEyeHeight -= lastUpdate / 500f;
            if (actual > oldSneakEyeHeight) {
                oldSneakEyeHeight = actual;
            }
        }

        return eyeHight - oldSneakEyeHeight;
    }

}
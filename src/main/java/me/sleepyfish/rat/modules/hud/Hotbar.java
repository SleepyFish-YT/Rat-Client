package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Hotbar extends Module {

    public static SimpleAnimation simpleAnimation;

    public static ToggleSetting animated;
    public static ToggleSetting clear;

    public static int posX;
    public static int posY;

    public Hotbar() {
        super("Hotbar", "Animate your hotbar", MinecraftUtils.mc.displayWidth / 2 - 15, MinecraftUtils.mc.displayHeight - 25);

        this.addSetting(animated = new ToggleSetting("Animated", true));
        this.addSetting(clear = new ToggleSetting("Clear", false));

        Hotbar.simpleAnimation = new SimpleAnimation(0F);

        this.removeSetting(this.background);
        this.removeSetting(this.outlines);
        this.removeSetting(this.brackets);
        this.removeSetting(this.rounded);

        this.setCustom(true);
        this.toggle();
    }

    public static void updatePos() {
        final Module thisMod = Rat.instance.moduleFields.Hotbar;
        posX = thisMod.getGuiX() + 80;
        posY = thisMod.getGuiY() + 15;
        thisMod.setHeight(20);
        thisMod.setWidth(160);
    }

}
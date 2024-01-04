package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

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

        Hotbar.simpleAnimation = new SimpleAnimation(0.0F);

        this.removeSetting(this.background);
        this.removeSetting(this.outlines);
        this.removeSetting(this.brackets);
        this.removeSetting(this.rounded);

        this.setCustom(true);
        this.toggle();
    }

    public static void updatePos() {
        posX = Rat.instance.moduleManager.getModByClass(Hotbar.class).getGuiX() + 80;
        posY = Rat.instance.moduleManager.getModByClass(Hotbar.class).getGuiY() + 15; // -10

        Rat.instance.moduleManager.getModByClass(Hotbar.class).setHeight(20);
        Rat.instance.moduleManager.getModByClass(Hotbar.class).setWidth(160);
    }

}
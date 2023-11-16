package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.modules.impl.SettingModule;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.Color;

public class ColorUtils {

    public static Color getBackgroundBrighterColor() {
        return new Color(185, 185, 185, 80);
    }

    public static Color getFontColor(Object target) {
        if (SettingModule.chroma.isEnabled()) {
            return getChroma(target.hashCode());
        } else {
            return new Color(0xAEB5B9);
        }
    }

    public static Color getIconColor() {
        return getBackgroundBrighterColor().darker();
    }

    public static Color getBackgroundDarkerColor() {
        return new Color(56, 56, 56, 170);
    }

    public static Color getBackgroundDarkerColorAlpha() {
        return new Color(56, 56, 56, 100);
    }

    public static Color getOutilneColor() {
        return new Color(20, 210, 90, 130);
    }

    public static Color getBlueColor() {
        return new Color(0, 160, 240, 130);
    }

    public static void setColorAlpha(int color) {
        float alpha = (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;
        GlStateManager.color(red, green, blue, alpha);
    }

    public static void setColor(int color) {
        ColorUtils.setColorAlpha(color);
    }

    public static void clearColor() {
        GlStateManager.color(1, 1, 1, 1);
    }

    public static Color getChroma(int index, double speed, float saturation, int opacity) {
        float angle = (float) ((System.currentTimeMillis() / speed + index) % 360) / 360F;
        Color c = new Color(Color.HSBtoRGB(angle, saturation, 1));
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
    }

    public static Color getChroma(int index) {
        return ColorUtils.getChroma(index, 35, 0.7F, 160);
    }

    public static Color getIconColorAlpha() {
        return new Color(185, 185, 185, 190);
    }

}
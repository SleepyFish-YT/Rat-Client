package me.sleepyfish.rat.utils.render.font;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class FontUtils {

    public static MinecraftFontRenderer text24, text18, text14, iconFont;
    public static MinecraftFontRenderer currentFont;

    private static int prevScale;

    public static void init() {
        final Map<String, Font> locationMap = new HashMap<>();
        final ScaledResolution sr = MinecraftUtils.res;
        final int scale = sr.getScaleFactor();

        if (scale != prevScale) {
            prevScale = scale;

            // arial, comfortaa, icon, verdana
            String mainFont = "verdana.ttf";

            Font i = getFont(locationMap, mainFont, 24F);
            text24 = new MinecraftFontRenderer(i);

            Font d = getFont(locationMap, mainFont, 18F);
            text18 = new MinecraftFontRenderer(d);

            Font a = getFont(locationMap, mainFont, 14F);
            text14 = new MinecraftFontRenderer(a);

            Font s = getFont(locationMap, "icon.ttf", 18F);
            iconFont = new MinecraftFontRenderer(s);

            currentFont = text18;
        }
    }

    public static Font getFont(final Map<String, Font> locationMap, final String location, float size) {
        final ScaledResolution sr = MinecraftUtils.res;

        size *= (float) sr.getScaleFactor() / 2F;

        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            } else {
                InputStream is = MinecraftUtils.mc
                        .getResourceManager().getResource(new ResourceLocation(MinecraftUtils.resourcePath + "/fonts/" + location))
                        .getInputStream();
                locationMap.put(location, font = Font.createFont(0, is));
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception e) {
            font = new Font("default", Font.PLAIN, (int) size);
            e.printStackTrace();
        }

        return font;
    }

    public static void drawFont(final String text, final float x, final float y, Color color) {
        //if (SettingModule.customFont.isEnabled()) {
            FontUtils.currentFont.drawString(text, x, y, color);
        //} else {
        //    MinecraftUtils.mc.fontRendererObj.drawString(text, x, y, color.getRGB(), SettingModule.drawShadow.isEnabled());
        //}
    }

    public static int getFontWidth(final String text) {
        //if (SettingModule.customFont.isEnabled()) {
            return (int) FontUtils.currentFont.getStringWidth(text);
        //} else {
        //    return MinecraftUtils.mc.fontRendererObj.getStringWidth(text);
        //}
    }

    public static int getFontHeight() {
        //if (SettingModule.customFont.isEnabled()) {
            return (int) FontUtils.currentFont.getHeight();
        //} else {
        //    return MinecraftUtils.mc.fontRendererObj.FONT_HEIGHT;
        //}
    }

}
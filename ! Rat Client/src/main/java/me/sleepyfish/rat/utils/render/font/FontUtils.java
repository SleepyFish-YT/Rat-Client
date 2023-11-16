package me.sleepyfish.rat.utils.render.font;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

public class FontUtils {

    public static MinecraftFontRenderer text24, text18, iconFont;
    public static MinecraftFontRenderer currentFont;

    private static int prevScale;

    public static void init() {
        Map<String, Font> locationMap = new HashMap<>();
        ScaledResolution sr = new ScaledResolution(MinecraftUtils.mc);
        int scale = sr.getScaleFactor();

        if (scale != prevScale) {
            prevScale = scale;

            // arial, comfortaa, icon, verdana
            String mainFont = "arial.tff";

            Font i = getFont(locationMap, mainFont, 24.0F);
            text24 = new MinecraftFontRenderer(i);

            Font d = getFont(locationMap, mainFont, 18.0F);
            text18 = new MinecraftFontRenderer(d);

            Font s = getFont(locationMap, "icon.ttf", 18.0F);
            iconFont = new MinecraftFontRenderer(s);

            currentFont = text18;
        }
    }

    public static Font getFont(Map<String, Font> locationMap, String location, float size) {
        ScaledResolution sr = new ScaledResolution(MinecraftUtils.mc);
        size *= (float) sr.getScaleFactor() / 2.0F;

        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(Font.PLAIN, size);
            } else {
                InputStream is = MinecraftUtils.mc
                        .getResourceManager().getResource(new ResourceLocation(MinecraftUtils.path + "/fonts/" + location))
                        .getInputStream();
                locationMap.put(location, font = Font.createFont(0, is));
                font = font.deriveFont(Font.PLAIN, size);
            }
        } catch (Exception ignored) {
            font = new Font("default", Font.PLAIN, (int) size);
        }

        return font;
    }

    public static void drawFont(String text, float x, float y, Color color) {
        //if (SettingModule.customFont.isEnabled()) {
            FontUtils.currentFont.drawString(text, x, y, color);
        //} else {
        //    MinecraftUtils.mc.fontRendererObj.drawString(text, x, y, color.getRGB(), SettingModule.drawShadow.isEnabled());
        //}
    }

    public static int getFontWidth(String text) {
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
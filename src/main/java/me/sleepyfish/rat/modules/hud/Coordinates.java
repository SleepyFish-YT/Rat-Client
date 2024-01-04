package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class Coordinates extends Module {

    private final ToggleSetting biome;

    private final int gap = 4;

    public Coordinates() {
        super("Coordinates", "Thats crazy", 20, 140);

        this.addSetting(biome = new ToggleSetting("Biome", true));
        this.removeSetting(this.brackets);

        this.setCustom(true);
    }

    @Override
    public void renderUpdate() {
        String posX = "X: " + Math.round(mc.thePlayer.posX);
        String posY = "Y: " + Math.round(mc.thePlayer.posY);
        String posZ = "Z: " + Math.round(mc.thePlayer.posZ);

        this.drawRectangle(this.getGuiX(), this.getGuiY(), this.getWidth(), this.getHeight());

        FontUtils.drawFont(posX, this.getGuiX() + gap, this.getGuiY() + gap, ColorUtils.getFontColor(1));
        FontUtils.drawFont(posY, this.getGuiX() + gap, this.getGuiY() + 10 + gap, ColorUtils.getFontColor(1));
        FontUtils.drawFont(posZ, this.getGuiX() + gap, this.getGuiY() + 20 + gap, ColorUtils.getFontColor(1));

        if (this.biome.isEnabled()) {
            String biome = "B: " + mc.theWorld.getBiomeGenForCoords(mc.thePlayer.getPosition()).biomeName;
            FontUtils.drawFont(biome,this.getGuiX() + gap, this.getGuiY() + 30 + gap, ColorUtils.getFontColor(1));

            this.setWidth(FontUtils.getFontWidth(biome) + (gap * 2));
            this.setHeight(36 + (gap * 2));
        } else {
            // best code 2023

            int width = 0;
            if (FontUtils.getFontWidth(posX) > FontUtils.getFontWidth(posY) || FontUtils.getFontWidth(posX) > FontUtils.getFontWidth(posZ))
                width = FontUtils.getFontWidth(posX);

            if (FontUtils.getFontWidth(posZ) > FontUtils.getFontWidth(posX) || FontUtils.getFontWidth(posZ) > FontUtils.getFontWidth(posY))
                width = FontUtils.getFontWidth(posZ);

            if (FontUtils.getFontWidth(posY) > FontUtils.getFontWidth(posZ) || FontUtils.getFontWidth(posY) > FontUtils.getFontWidth(posX))
                width = FontUtils.getFontWidth(posY);

            this.setWidth(width + (gap * 3));
            this.setHeight(26 + (gap * 2));
        }
    }

}
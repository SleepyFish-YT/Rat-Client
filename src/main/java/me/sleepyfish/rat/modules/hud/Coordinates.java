package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Coordinates extends Module {

    private final ToggleSetting biome;

    public Coordinates() {
        super("Coordinates", "Thats crazy", 20, 140);

        this.addSetting(biome = new ToggleSetting("Biome", true));
        this.removeSetting(this.brackets);

        this.setCustom(true);
    }

    @Override
    public void renderUpdate() {
        final String posX = "X: " + Math.round(mc.thePlayer.posX);
        final String posY = "Y: " + Math.round(mc.thePlayer.posY);
        final String posZ = "Z: " + Math.round(mc.thePlayer.posZ);

        this.drawRectangle(this.getGuiX(), this.getGuiY(), this.getWidth(), this.getHeight());

        final int gap = 4;

        FontUtils.drawFont(posX, this.getGuiX() + gap, this.getGuiY() + gap, ColorUtils.getFontColor(1));
        FontUtils.drawFont(posY, this.getGuiX() + gap, this.getGuiY() + 10 + gap, ColorUtils.getFontColor(1));
        FontUtils.drawFont(posZ, this.getGuiX() + gap, this.getGuiY() + 20 + gap, ColorUtils.getFontColor(1));

        if (this.biome.isEnabled()) {
            final String biome = "B: " + mc.theWorld.getBiomeGenForCoords(mc.thePlayer.getPosition()).biomeName;
            FontUtils.drawFont(biome,this.getGuiX() + gap, this.getGuiY() + 30 + gap, ColorUtils.getFontColor(1));

            this.setWidth(FontUtils.getFontWidth(biome) + (gap * 2));
            this.setHeight(36 + (gap * 2));
        } else {
            final int[] fontWidths = {FontUtils.getFontWidth(posX), FontUtils.getFontWidth(posY), FontUtils.getFontWidth(posZ)};
            int maxWidth = fontWidths[0];

            for (final int width : fontWidths) {
                if (width > maxWidth)
                    maxWidth = width;
            }

            this.setWidth(maxWidth + (gap * 3));
            this.setHeight(26 + (gap * 2));
        }
    }

}
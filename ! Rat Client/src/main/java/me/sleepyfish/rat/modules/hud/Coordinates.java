package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

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

        this.drawRectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        FontUtils.drawFont(posX, this.getX() + gap, this.getY() + gap, ColorUtils.getFontColor(1));
        FontUtils.drawFont(posY, this.getX() + gap, this.getY() + 10 + gap, ColorUtils.getFontColor(1));
        FontUtils.drawFont(posZ, this.getX() + gap, this.getY() + 20 + gap, ColorUtils.getFontColor(1));

        if (this.biome.isEnabled()) {
            String biome = "B: " + mc.theWorld.getBiomeGenForCoords(mc.thePlayer.getPosition()).biomeName;
            FontUtils.drawFont(biome,this.getX() + gap, this.getY() + 30 + gap, ColorUtils.getFontColor(1));

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
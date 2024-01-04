package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.GlUtils;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.cosmetics.impl.capes.Cape;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GuiRatCosmetic extends GuiScreen {

    private double scrollY;
    private SimpleAnimation scrollAnimation;

    private SimpleAnimation playerYawAnimation;
    private SimpleAnimation playerPitchAnimation;

    private boolean overExitGui;
    private boolean overRemoveCape;

    private final GuiScreen parent;

    public GuiRatCosmetic(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.scrollAnimation = new SimpleAnimation(0.0F);
        this.playerYawAnimation = new SimpleAnimation(0.0F);
        this.playerPitchAnimation = new SimpleAnimation(0.0F);

        this.overExitGui = false;
        this.overRemoveCape = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        // Hover setter
        this.overExitGui = InputUtils.isInside(this.width / 2F - 244, this.height / 2F + 121, 24, 24);
        this.overRemoveCape = InputUtils.isInside(this.width / 2F - 244, this.height / 2F + 90, 24, 24);

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));
        GuiUtils.drawCustomGui(1, this.width, this.height, true);
        GuiUtils.drawLogo(this.width, this.height, 2);

        // Render thePlayer
        this.playerPitchAnimation.setAnimation(this.height / 2F + 30 + (-InputUtils.mouseY), 12);
        this.playerYawAnimation.setAnimation(this.width / 2F + 175 + (-InputUtils.mouseX), 12);
        GlUtils.drawEntityOnScreen(this.mc.thePlayer, this.width, this.height, this.playerYawAnimation.getValue(), this.playerPitchAnimation.getValue());

        FontUtils.drawFont("Hint: Drag to move", this.width / 2F + 125, this.height / 2F + 75, ColorUtils.getFontColor(this));

        float scrollY = scrollAnimation.getValue();

        int index = 0;
        int index2 = 1;
        int offsetX = 45;
        int offsetY = 15;

        GlUtils.startScissors();
        GuiUtils.drawCustomGui(1, this.width, this.height, true);
        GlUtils.readScissors(1);

        for (Cape c : Rat.instance.cosmeticUtils.getInventoryCapes()) {

            if (Rat.instance.cosmeticUtils.getCurrentCape() == c.getName()) {
                RenderUtils.drawOutline(this.width / 2F - 243 + offsetX - 3, this.height / 2F - 150 + offsetY - 3 + scrollY, 60 + 6, 90 + 6, 2, 12, ColorUtils.getOutilneColor());
            }

            RenderUtils.drawRound(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + scrollY, 60 + 2, 90 + 2, 6, ColorUtils.getBackgroundDarkerColor().darker());
            RenderUtils.drawRound(this.width / 2F - 243 + offsetX, this.height / 2F - 150 + offsetY + scrollY, 60, 90, 6, ColorUtils.getBackgroundDarkerColor());

            if (InputUtils.isInside(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + scrollY, 60 + 2, 90 + 2)) {
                RenderUtils.drawRound(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + scrollY, 60 + 2, 90 + 2, 6, ColorUtils.getBackgroundDarkerColor().darker());
            }

            this.mc.getTextureManager().bindTexture(c.getSample());

            if (!c.getName().equals("None")) {
                RenderUtils.drawRoundTextured(this.width / 2F - 243 + offsetX + 8, this.height / 2F - 150 + offsetY + 6 + scrollY, 44, 70, 4, 1F);
            }

            FontUtils.drawFont(c.getName(), this.width / 2F - 243 + offsetX + (50 - FontUtils.getFontWidth(c.getName())), (int) (this.height / 2 - 150 + offsetY + 80 + scrollY), ColorUtils.getFontColor(this));

            index++;
            offsetX += 70;
            if (index == index2 * 4) {
                index2++;
                offsetX = 45;
                offsetY += 100;
            }
        }

        final InputUtils.SoarScroll scroll = InputUtils.getSoarScroll();

        if (scroll != null) {
            switch (scroll) {
                case UP: {
                    if (this.scrollY < -10) {
                        this.scrollY += 20;
                    } else {
                        if (index > 5)
                            this.scrollY = 0;
                    }

                    break;
                }

                case DOWN: {
                    double maxScale = (Rat.instance.cosmeticUtils.getInventoryCapes().size() * 0.5D);

                    if (this.scrollY > -(index * maxScale))
                        this.scrollY -= 20;

                    if (index > 5) {
                        if (this.scrollY < -(index * maxScale))
                            this.scrollY = -(index * maxScale);
                    }
                    break;
                }
            }
        }

        this.scrollAnimation.setAnimation((float) this.scrollY, 32);

        GlUtils.endScissors();
        RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 121, 24, 24, 5, !this.overExitGui ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().darker());
        RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 90, 24, 24, 5, !this.overRemoveCape ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().darker());

        RenderUtils.drawImage("/gui/icons/cross", this.width / 2 - 237, this.height / 2 + 121 + 8, 10, 10, ColorUtils.getIconColorAlpha());
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (b != InputUtils.MOUSE_LEFT)
            return;

        if (this.overExitGui) {
            SoundUtils.playClick();
            mc.displayGuiScreen(this.parent);
        } else if (this.overRemoveCape) {
            Rat.instance.cosmeticUtils.setCurrentCape("None");
        } else {
            int index = 0;
            int index2 = 1;
            int offsetX = 45;
            int offsetY = 15;

            GlUtils.startScissors();
            GuiUtils.drawCustomGui(1, this.width, this.height, true);
            GlUtils.readScissors(1);

            for (Cape c : Rat.instance.cosmeticUtils.getInventoryCapes()) {

                if (InputUtils.isInside(this.width / 2F - 243 + offsetX + 8, this.height / 2F - 150 + offsetY + 6 + this.scrollAnimation.getValue(), 44, 70)) {
                    Rat.instance.cosmeticUtils.setCurrentCape(c.getName());
                }

                index++;
                offsetX += 70;
                if (index == index2 * 4) {
                    index2++;
                    offsetX = 45;
                    offsetY += 100;
                }
            }
        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
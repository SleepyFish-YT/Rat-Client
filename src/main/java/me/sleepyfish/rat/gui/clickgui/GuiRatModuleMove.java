package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.TimerUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GuiRatModuleMove extends GuiScreen {

    private boolean overModuleList;
    private boolean overEmoteGui;
    private boolean overCosmeticGui;

    public boolean overAModule;
    public boolean renderHudModules;
    public boolean allowMoveHudModules;

    private TimerUtils logoTimer;
    private float logoAnimation;

    @Override
    public void initGui() {
        Rat.instance.guiManager.useMixinMainMenuAnimation = true;

        this.renderHudModules = true;
        this.allowMoveHudModules = false;
        this.overAModule = false;

        this.overModuleList = false;
        this.overEmoteGui = false;
        this.overCosmeticGui = false;

        this.logoTimer = new TimerUtils();
        this.logoAnimation = 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        this.allowMoveHudModules = true;

        this.renderModulesUpdate(mouseX, mouseY);

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));

        // Main menu icon animation
        if (this.logoTimer == null) {
            this.logoTimer = new TimerUtils();
        }

        if (this.logoTimer.delay(10)) {
            if (this.logoAnimation < 60)
                this.logoAnimation += 0.4F;
        }

        Color newColor = new Color(185, 185, 185, (int) Math.min(this.logoAnimation * 3F, 250F));

        // Render logos
        RenderUtils.drawImage("/gui/icon", this.width / 2 - 25, this.height / 2 - 20 - (int) this.logoAnimation, 50, 50, newColor);
        GuiUtils.drawLogo(this.width, this.height, 2);

        // Hover setter
        this.overModuleList = InputUtils.isInside(this.width / 2F - 60, this.height / 2F - 10, 120, 20);
        this.overEmoteGui = InputUtils.isInside(this.width / 2F - 85, this.height / 2F - 10, 20, 20);
        this.overCosmeticGui = InputUtils.isInside(this.width / 2F + 65, this.height / 2F - 10, 20, 20);

        // Module list buttons

        // Draw the rounded rectangle
        RenderUtils.drawRound(this.width / 2F - 60, this.height / 2F - 10, 120, 20, 5, !this.overModuleList ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());
        String buttonText = "Settings";
        FontUtils.drawFont(buttonText, (this.width / 2F - 60 + (120 - FontUtils.getFontWidth(buttonText)) / 2F), (this.height / 2F - 10 + (20 - FontUtils.getFontHeight()) / 2F), ColorUtils.getIconColorAlpha());

        // Gui Buttons with icons
        {
            int imageSize = 14;

            // Emote button
            RenderUtils.drawRound(this.width / 2F - 85, this.height / 2F - 10, 20, 20, 5, !this.overEmoteGui ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());

            // Emote icon
            RenderUtils.drawImage("/gui/icons/emote", (int) (this.width / 2F - 85 + (20 - imageSize) / 2F), (int) (this.height / 2F - 10 + (21 - imageSize) / 2F), imageSize, imageSize, ColorUtils.getIconColorAlpha());

            // Cosmetic button
            RenderUtils.drawRound(this.width / 2F + 65, this.height / 2F - 10, 20, 20, 5, !this.overCosmeticGui ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());

            // Cosmetic icon
            RenderUtils.drawImage("/gui/icons/cosmetic", (int) (this.width / 2F + 65 + (20 - imageSize) / 2F), (int) (this.height / 2F - 10 + (21 - imageSize) / 2F), imageSize, imageSize, ColorUtils.getIconColorAlpha());
        }

        // Module window outline
        RenderUtils.drawOutline(5, 5, this.width - 10, this.height - 10, 2, 1, ColorUtils.getOutilneColor());
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (b == InputUtils.MOUSE_LEFT) {
            if (this.overModuleList) {
                SoundUtils.playClick();
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatModuleGUI());
            }

            if (this.overCosmeticGui) {
                SoundUtils.playClick();
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiCosmetic());
            }

            if (this.overEmoteGui) {
                SoundUtils.playClick();
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiEmoteList());
            }

            if (this.allowMoveHudModules) {
                if (this.overCosmeticGui || this.overEmoteGui || this.overModuleList)
                    return;

                if (this.renderHudModules) {
                    for (Module mod : Rat.instance.moduleManager.getModules()) {
                        if (mod.isEnabled() && mod.isHudMod() && !mod.isMoving()) {
                            if (mod.overRedCross) {
                                mod.toggle();
                                mod.overRedCross = false;
                                return;
                            }

                            if (InputUtils.isInside(mod.getGuiX() - 8F, mod.getGuiY() - 5F, mod.getWidth() + 16, mod.getHeight())) {
                                mod.setMoving(true);
                                mod.guiX2 = x - mod.getGuiX();
                                mod.guiY2 = y - mod.getGuiY();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int x, int y, int button) {
        if (button == InputUtils.MOUSE_LEFT) {
            if (this.allowMoveHudModules) {
                if (this.overCosmeticGui || this.overEmoteGui || this.overModuleList)
                    return;

                if (this.renderHudModules) {
                    for (Module mod : Rat.instance.moduleManager.getModules()) {
                        if (mod.isEnabled() && mod.isHudMod() && mod.isMoving()) {
                            mod.setMoving(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            for (Module mod : Rat.instance.moduleManager.getModules()) {
                if (mod.isEnabled() && mod.isMoving()) {
                    mod.setMoving(false);
                }
            }

            this.allowMoveHudModules = false;

            Rat.instance.configManager.save();
            mc.displayGuiScreen(null);
        }
    }


    private void renderModulesUpdate(int x, int y) {
        for (Module mod : Rat.instance.moduleManager.getModules()) {
            if (mod.isEnabled()) {
                if (mod.isMoving()) {
                    mod.setUnsaveGuiX(x - mod.guiX2);
                    mod.setUnsaveGuiY(y - mod.guiY2);
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
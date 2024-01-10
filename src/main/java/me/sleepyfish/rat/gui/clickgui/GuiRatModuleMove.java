package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
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
 * @author SleepyFish 2024
 */
public class GuiRatModuleMove extends GuiScreen {

    public boolean allowMoveHudModules;

    private TimerUtils logoTimer;
    private float logoAnimation;

    private GuiUtils.Button moduleListButton;
    private GuiUtils.Button emoteGuiButton;
    private GuiUtils.Button cosmeticGuiButton;

    @Override
    public void initGui() {
        for (final Module mod : Rat.instance.moduleManager.getModules()) {
            if (mod.isEnabled() && mod.isMoving())
                mod.setMoving(false);
        }

        final double wid = this.width / 2F;
        final double hei = (this.height / 2F) - 10F;
        this.moduleListButton  = new GuiUtils.Button("Settings", wid - 60, hei, 120, 20, 8F);
        this.emoteGuiButton    = new GuiUtils.Button("", wid - 85, hei, 20, 20, 6F);
        this.cosmeticGuiButton = new GuiUtils.Button("", wid + 65, hei, 20, 20, 6F);

        Rat.instance.guiManager.useMixinMainMenuAnimation = true;

        this.allowMoveHudModules = false;

        this.logoTimer = new TimerUtils();
        this.logoAnimation = 0;

        Rat.instance.antiCheat.openGuiCheck();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        this.allowMoveHudModules = true;

        this.renderModulesUpdate(mouseX, mouseY);

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 150));

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
        RenderUtils.drawImage("/gui/icon", this.width / 2 - 40, this.height / 2 - 40 - (int) this.logoAnimation, 80, 80, newColor);
        GuiUtils.drawLogo(this.width, this.height, false);

        // Module list buttons
        this.moduleListButton.render();

        // Gui Buttons with icons
        {
            short imageSize = 14;

            // Emote button
            this.emoteGuiButton.render();

            // Emote icon
            RenderUtils.drawImage("/gui/icons/emote", (int) (this.width / 2F - 85 + (20 - imageSize) / 2F), (int) (this.height / 2F - 10 + (21 - imageSize) / 2F), imageSize, imageSize, ColorUtils.getIconColorAlpha());

            // Cosmetic button
            this.cosmeticGuiButton.render();

            // Cosmetic icon
            RenderUtils.drawImage("/gui/icons/cosmetic", (int) (this.width / 2F + 65 + (20 - imageSize) / 2F), (int) (this.height / 2F - 10 + (21 - imageSize) / 2F), imageSize, imageSize, ColorUtils.getIconColorAlpha());
        }

        // Module window outline
        RenderUtils.drawOutline(5, 5, this.width - 10, this.height - 10, 2, 1, ColorUtils.getOutilneColor());
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (b == InputUtils.MOUSE_LEFT) {
            if (this.moduleListButton.isInside()) {
                SoundUtils.playClick();
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiModuleMenu());
            }

            if (this.emoteGuiButton.isInside()) {
                SoundUtils.playClick();
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiEmoteList());
            }

            if (this.cosmeticGuiButton.isInside()) {
                SoundUtils.playClick();
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiCosmetic());
            }
        }

        if (b == InputUtils.MOUSE_RIGHT || b == InputUtils.MOUSE_LEFT) {
            if (this.allowMoveHudModules) {
                if (this.moduleListButton.isInside() || this.emoteGuiButton.isInside() || this.cosmeticGuiButton.isInside())
                    return;

                for (final Module mod : Rat.instance.moduleManager.getModules()) {
                    if (mod.isEnabled() && mod.isHudMod() && !mod.isMoving()) {
                        if (InputUtils.isInside(mod.getGuiX() - 8F, mod.getGuiY() - 5F, mod.getWidth() + 16, mod.getHeight())) {
                            if (b == InputUtils.MOUSE_RIGHT) {
                                mod.toggle();
                            }

                            if (b == InputUtils.MOUSE_LEFT) {
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
                if (this.moduleListButton.isInside() || this.emoteGuiButton.isInside() || this.cosmeticGuiButton.isInside())
                    return;

                for (final Module mod : Rat.instance.moduleManager.getModules()) {
                    if (mod.isEnabled() && mod.isHudMod() && mod.isMoving())
                        mod.setMoving(false);
                }
            }
        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            for (final Module mod : Rat.instance.moduleManager.getModules()) {
                if (mod.isEnabled() && mod.isMoving())
                    mod.setMoving(false);
            }

            this.allowMoveHudModules = false;

            Rat.instance.configManager.save();
            mc.displayGuiScreen(null);
        }
    }

    public void modDrawUpdateAndHover() {
        for (final Module mod : Rat.instance.moduleManager.getModules()) {

            if (mod.isHudMod()) {
                if (mod.isEnabled()) {

                    if (mod.mc.thePlayer != null) {
                        mod.renderUpdate();
                        mod.drawComponent();
                    }

                    if (MinecraftUtils.mc.currentScreen == this) {
                        final boolean isInside = InputUtils.isInside(mod.getGuiX() - 8F, mod.getGuiY() - 5F, mod.getWidth() + 16, mod.getHeight());
                        mod.editOpacityAnimation.setAnimation(isInside ? 255 : 0, 12);

                        float blah = ((float) FontUtils.currentFont.getHeight() + 1F) * 2F;
                        if (mod.isCustom()) {
                            blah = mod.getHeight();
                        }

                        final Color hoverColor = new Color(255, 255, 255, (int) mod.editOpacityAnimation.getValue());
                        RenderUtils.drawOutline(mod.getGuiX() - 9, mod.getGuiY() - 6, mod.getWidth() + 18, blah, 2, 2, hoverColor);
                    }
                }
            }
        }
    }

    private void renderModulesUpdate(int x, int y) {
        for (final Module mod : Rat.instance.moduleManager.getModules()) {
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
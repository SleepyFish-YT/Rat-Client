package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.GlUtils;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

public class GuiRatModuleMenu extends GuiScreen {

    private double scrollY;
    private SimpleAnimation scrollAnimation;

    public boolean overSlider;
    public boolean sliderMoving;

    private boolean overModuleExitGui;
    private boolean overEditHudLayout;

    private boolean overConfigs;

    public boolean inModuleSetting;
    public Module moduleSettingMod;

    private final GuiScreen parent;

    public GuiRatModuleMenu(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.scrollAnimation = new SimpleAnimation(0.0F);

        this.overSlider = false;
        this.sliderMoving = false;

        this.overModuleExitGui = false;
        this.overEditHudLayout = false;
        this.overConfigs = false;

        this.moduleSettingMod = null;
        this.inModuleSetting = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        Rat.instance.guiManager.getRatGuiModuleMove().allowMoveHudModules = false;
        float scrollY = scrollAnimation.getValue();

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));
        GuiUtils.drawCustomGui(1, this.width, this.height, false);
        GuiUtils.drawLogo(this.width, this.height, 2);
        RenderUtils.drawImage("/gui/icon_text", this.width / 2 - 238, this.height / 2 - 145, 80, 20);

        if (!this.inModuleSetting && this.moduleSettingMod == null) {

            this.overModuleExitGui = InputUtils.isInside(this.width / 2F + 222, this.height / 2F - 145, 20, 20);
            this.overEditHudLayout = InputUtils.isInside(this.width / 2F - 248, this.height / 2F + 123, 98, 15);
            this.overConfigs = InputUtils.isInside(this.width / 2F - 248, this.height / 2F - 115, 98, 15);

            this.overSlider = InputUtils.isInside(this.width / 2F + 236, this.height / 2F - 115, 8, 250);

            GuiUtils.drawHorizontalSlider(this.width, this.height, scrollY - 15, ColorUtils.getBackgroundDarkerColor(),
                    (overSlider || sliderMoving) ? ColorUtils.getBackgroundDarkerColor().brighter().brighter().brighter() : ColorUtils.getBackgroundDarkerColor().brighter());

            // RenderUtils.drawRound(this.width / 2F - 50, this.height / 2F - 140F, 40, 15, 2, this.overModuleTab ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor());

            // Exit button with icon
            RenderUtils.drawRound(this.width / 2F + 222, this.height / 2F - 145, 20, 20, 5, !this.overModuleExitGui ?
                    ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());
            RenderUtils.drawImage("/gui/icons/cross", (int) (this.width / 2F + 222 + (20 - 14) / 2F),
                    (int) (this.height / 2F - 145 + (21 - 14) / 2F), 14, 14, ColorUtils.getIconColorAlpha());

            // Edit Hud button
            RenderUtils.drawRound(this.width / 2F - 248, this.height / 2F + 123, 98, 15, 5,
                    !this.overEditHudLayout ? ColorUtils.getBlueColor() : ColorUtils.getBlueColor().darker());

            // Config button
            RenderUtils.drawRound(this.width / 2F - 248, this.height / 2F - 115, 98, 15, 5,
                    !this.overConfigs ? ColorUtils.getIconColor() : ColorUtils.getIconColor().darker());

            GlUtils.startScissors();
            RenderUtils.drawRoundCustom(this.width / 2F - 250F, this.height / 2F - 120F, 497F, 1F,
                    10F, Color.white, true, false, false, true);
            RenderUtils.drawRoundCustom(this.width / 2F - 213F + 65F, this.height / 2F - 100F - 20F, 395F, 260F,
                    10F, Color.white, false, false, true, false);
            GlUtils.readScissors(1);

            int index = 0;
            int index2 = 1;
            int offsetX = 100;
            int offsetY = 38;

            for (Module mod : Rat.instance.moduleManager.getModules()) {
                GuiUtils.drawModuleComponent(mod, this.width / 2F - 240 + offsetX, this.height / 2F - 145 + offsetY - 1 + scrollY);

                // Module hover setter
                mod.overModule  = InputUtils.isInside(this.width / 2F - 240 + offsetX, this.height / 2F - 145 + offsetY - 1 + scrollY, 115, 115);
                mod.overSetting = InputUtils.isInside(this.width / 2F - 242 + offsetX, this.height / 2F - 70 + offsetY - 1 + scrollY, 117, 20);
                mod.overEnable  = mod.canBeEnabled() && InputUtils.isInside(this.width / 2F - 242 + offsetX, this.height / 2F - 50 + offsetY - 1 + scrollY, 117, 20);

                index++;
                offsetX += 125;
                if (index == index2 * 3) {
                    index2++;
                    offsetX = 100;
                    offsetY += 125;
                }
            }

            final InputUtils.SoarScroll scroll = InputUtils.getSoarScroll();

            if (scroll != null) {
                switch (scroll) {
                    case UP:
                        if (this.scrollY < -10) {
                            this.scrollY += 20;
                        } else {
                            if (index > 5)
                                this.scrollY = 0;
                        }
                        break;

                    case DOWN:
                        int maxScale = (Rat.instance.moduleManager.getCount() * 2) - 18;

                        if (this.scrollY > -(index * maxScale))
                            this.scrollY -= 20;

                        if (index > 5) {
                            if (this.scrollY < -(index * maxScale))
                                this.scrollY = -(index * maxScale);
                        }
                        break;
                }
            }

            this.scrollAnimation.setAnimation((float) this.scrollY, 32);

            GlUtils.endScissors();
            FontUtils.drawFont("Configs", this.width / 2F - 236, this.height / 2F - 110, ColorUtils.getFontColor(this));
            FontUtils.drawFont("Edit HUD Layout", this.width / 2F - 236, this.height / 2F + 127, ColorUtils.getFontColor(this));
        } else {
            this.overModuleExitGui = false;
            this.overEditHudLayout = false;
            this.overConfigs = false;
            this.overSlider = false;

            FontUtils.drawFont(this.moduleSettingMod.getDescription(), this.width / 2F - 115, this.height / 2F - 113, ColorUtils.getFontColor(this));
            RenderUtils.drawRound(this.width / 2F - 140, this.height / 2F - 100, 370, 5, 2, ColorUtils.getBackgroundDarkerColor().darker().darker().darker().darker());

            GuiUtils.drawModuleSettingGui(this.moduleSettingMod, this.width / 2F - 135, this.height / 2F - 80);
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (b == InputUtils.MOUSE_LEFT) {
            if (!this.inModuleSetting && this.moduleSettingMod == null) {
                if (this.overModuleExitGui || this.overEditHudLayout) {
                    SoundUtils.playClick();
                    mc.displayGuiScreen(this.parent);
                }

                if (this.overConfigs) {
                    SoundUtils.playClick();
                    mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiConfig());
                }

                for (Module mod : Rat.instance.moduleManager.getModules()) {
                    if (mod.overSetting) {
                        SoundUtils.playClick();
                        this.moduleSettingMod = mod;
                        this.inModuleSetting = true;
                    }

                    if (mod.overEnable) {
                        SoundUtils.playClick();
                        mod.toggle();
                    }
                }
            } else {
                GuiUtils.mouseClickedInSettingGui(this.moduleSettingMod, this.width / 2F - 135, this.height / 2F - 80);
            }
        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key != Keyboard.KEY_ESCAPE) {
            if (this.inModuleSetting && this.moduleSettingMod != null) {
                if (!this.moduleSettingMod.isReBinding)
                    return;

                for (Setting setting : this.moduleSettingMod.getSettings()) {
                    if (setting instanceof KeybindSetting) {
                        ((KeybindSetting) setting).keycode = key;
                        this.moduleSettingMod.isReBinding = false;
                    }
                }
            }
        } else {
            if (this.inModuleSetting) {
                if (this.moduleSettingMod != null) {
                    if (this.moduleSettingMod.isReBinding) {
                        this.moduleSettingMod.isReBinding = false;
                        return;
                    }

                    this.moduleSettingMod = null;
                    this.inModuleSetting = false;
                }
            } else {
                for (Module mod : Rat.instance.moduleManager.getModules()) {
                    if (mod.isHudMod())
                        mod.setMoving(false);
                }

                mc.displayGuiScreen(this.parent);
            }
        }
    }

    public void modDrawUpdateAndHover() {
        for (Module mod : Rat.instance.moduleManager.getModules()) {

            if (mod.isHudMod()) {
                if (mod.isEnabled()) {

                    if (mod.mc.thePlayer != null) {
                        mod.renderUpdate();
                        mod.drawComponent();
                    }

                    if (InputUtils.isInside(mod.getGuiX() - 8F, mod.getGuiY() - 5F, mod.getWidth() + 16, mod.getHeight())) {
                        if (Rat.instance.guiManager.getRatGuiModuleMove().allowMoveHudModules) {
                            Rat.instance.guiManager.getRatGuiModuleMove().overAModule = true;
                            mod.drawHover();
                        }
                    } else {
                        Rat.instance.guiManager.getRatGuiModuleMove().overAModule = false;
                    }
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
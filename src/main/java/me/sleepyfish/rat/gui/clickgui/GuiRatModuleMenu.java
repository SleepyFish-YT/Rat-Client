package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.render.GlUtils;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.modules.impl.Performance;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class GuiRatModuleMenu extends GuiScreen {

    private SimpleAnimation scrollAnimation;
    private double scrollY;

    public boolean overSlider;

    public boolean inModuleSetting;
    public Module moduleSettingMod;

    private final GuiScreen parent;

    private GuiUtils.Button overConfigs;
    private GuiUtils.Button overEditHudLayout;
    private GuiUtils.Button overModuleExitGui;

    public GuiRatModuleMenu(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.scrollAnimation = new SimpleAnimation(0F);
        this.scrollY = 0;

        this.overSlider   = false;

        this.overModuleExitGui = new GuiUtils.Button("", this.width / 2F + 222, this.height / 2F - 145F, 20, 20, 4F);
        this.overEditHudLayout = new GuiUtils.Button("Edit Layout", this.width / 2F - 248, this.height / 2F + 123F, 98, 15, 7F);
        this.overConfigs       = new GuiUtils.Button("Configs", this.width / 2F - 248, this.height / 2F - 115F, 98, 15, 4F);

        this.moduleSettingMod = null;
        this.inModuleSetting = false;

        if (Rat.instance.fileManager.hasOptifine()) {
            if (Performance.fastMath.isEnabled()) {
                StringBuilder content = Rat.instance.fileManager.readOptifineConfig();
                Rat.instance.fileManager.setContentBasedOnCondition(content, content.toString(), true);
            }
        }

        Rat.instance.antiCheat.openGuiCheck();
    }

    @Override
    public void drawScreen(final int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        Rat.instance.guiManager.getRatGuiModuleMove().allowMoveHudModules = false;
        final float scrollY = scrollAnimation.getValue();

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));
        GuiUtils.drawCustomGui(1, this.width, this.height, false);
        GuiUtils.drawLogo(this.width, this.height, false);
        RenderUtils.drawImage("/gui/icon_text", this.width / 2 - 238, this.height / 2 - 145, 80, 20);

        final float calcX = this.width / 2F;
        final float calcY = this.height / 2F;

        if (!this.inModuleSetting && this.moduleSettingMod == null) {
            this.overSlider = InputUtils.isInside(calcX + 236, calcY - 115, 8, 250);

            GuiUtils.drawHorizontalSlider(this.width, this.height, scrollY / 4.45F, ColorUtils.getBackgroundDarkerColor(),
                    overSlider ? ColorUtils.getBackgroundDarkerColor().brighter().brighter().brighter() : ColorUtils.getBackgroundDarkerColor().brighter());

            // RenderUtils.drawRound(calcX - 50, calcY - 140F, 40, 15, 2, this.overModuleTab ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor());

            this.overModuleExitGui.render();

            RenderUtils.drawImage("/gui/icons/cross", (int) (calcX + 222 + (20 - 14) / 2F),
                    (int) (calcY - 145 + (21 - 14) / 2F), 14, 14, ColorUtils.getIconColorAlpha());

            this.overEditHudLayout.render();
            this.overConfigs.render();

            GlUtils.startScissors();
            RenderUtils.drawRoundCustom(calcX - 250F, calcY - 120F, 497F, 1F,
                    10F, Color.white, true, false, false, true);
            RenderUtils.drawRoundCustom(calcX - 213F + 65F, calcY - 100F - 20F, 395F, 260F,
                    10F, Color.white, false, false, true, false);
            GlUtils.readScissors(1);

            short index = 0;
            short index2 = 1;
            short offsetX = 100;
            short offsetY = 38;

            for (final Module mod : Rat.instance.moduleManager.getModules()) {
                GuiUtils.drawModuleComponent(mod, calcX - 240 + offsetX, calcY - 145 + offsetY - 1 + scrollY);

                // Module hover setter
                mod.overModule  = InputUtils.isInside(calcX - 240 + offsetX, calcY - 145 + offsetY - 1 + scrollY, 115, 115);
                mod.overSetting = InputUtils.isInside(calcX - 242 + offsetX, calcY - 70 + offsetY - 1 + scrollY, 117, 20);
                mod.overEnable  = mod.canBeEnabled() && InputUtils.isInside(calcX - 242 + offsetX, calcY - 50 + offsetY - 1 + scrollY, 117, 20);

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
                        final byte maxScale = 36;

                        if (this.scrollY > -(index * maxScale)) {
                            this.scrollY -= 20;
                        } else {
                            if (index > 5) {
                                if (this.scrollY < -(index * maxScale))
                                    this.scrollY = -(index * maxScale);
                            }
                        }
                        break;
                }
            }

            this.scrollAnimation.setAnimation((float) this.scrollY, 32);

            GlUtils.endScissors();
        } else {
            this.overSlider = false;

            FontUtils.drawFont(this.moduleSettingMod.getDescription(), calcX - 115, calcY - 113, ColorUtils.getFontColor(this));
            RenderUtils.drawRound(calcX - 140, calcY - 100, 370, 5, 2, ColorUtils.getBackgroundDarkerColor().darker().darker().darker().darker());

            GuiUtils.drawModuleSettingGui(this.moduleSettingMod, calcX - 135, calcY - 80);
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (b == InputUtils.MOUSE_LEFT) {
            if (!this.inModuleSetting && this.moduleSettingMod == null) {
                if (this.overModuleExitGui.isInside() || this.overEditHudLayout.isInside()) {
                    SoundUtils.playClick();
                    mc.displayGuiScreen(this.parent);
                }

                if (this.overConfigs.isInside()) {
                    SoundUtils.playClick();
                    mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiConfig());
                }

                for (final Module mod : Rat.instance.moduleManager.getModules()) {
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

                for (final Setting setting : this.moduleSettingMod.getSettings()) {
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
                for (final Module mod : Rat.instance.moduleManager.getModules()) {
                    if (mod.isHudMod())
                        mod.setMoving(false);
                }

                mc.displayGuiScreen(this.parent);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
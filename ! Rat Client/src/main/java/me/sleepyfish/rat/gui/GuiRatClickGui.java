package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.render.*;
import me.sleepyfish.rat.utils.capes.Cape;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.InjectionUtils;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import me.sleepyfish.rat.utils.render.font.FontUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.inventory.GuiInventory;

import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

import java.awt.Color;

public class GuiRatClickGui extends GuiScreen {

    public Module selectedModule;

    private boolean overExitGui;

    private boolean overModuleMenu;
    private boolean inModuleMenu;

    private boolean overEmotes;
    private boolean inEmotes;

    private boolean overModuleExitGui;
    private boolean overModuleExitGui2;

    private boolean overModuleTab;
    private boolean overSettingTab;

    private boolean overCapes;
    private boolean overRemoveCape;
    private boolean inCapes;

    public boolean overAModule;
    public boolean overSlider;
    public boolean sliderMoving;

    public boolean renderHudModules;
    public boolean allowMoveHudModules;
    public boolean inModuleSetting;

    private float capeSliderValueY;
    private float moduleSliderValueY = 15;

    private int animation;

    @Override
    public void initGui() {
        this.renderHudModules = true;

        this.overModuleMenu = false;
        this.overCapes = false;
        this.overRemoveCape = false;
        this.overEmotes = false;
        this.overAModule = false;

        this.overModuleTab = false;
        this.overSettingTab = false;

        this.overSlider = false;
        this.sliderMoving = false;

        this.allowMoveHudModules = false;
        this.inModuleSetting = false;
        this.selectedModule = null;

        this.capeSliderValueY = 0;

        this.animation = 0;

        try {
            Mouse.updateCursor();
            InjectionUtils.oneTimeInjectChecks();
        } catch (Exception e) {
            this.mc.thePlayer.sendChatMessage("Gui-Error: " + e.getMessage());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));

        if (!this.inModuleMenu && !this.inEmotes && !this.inCapes && !this.inModuleSetting) {
            this.allowMoveHudModules = true;
            this.selectedModule = null;

            // Main menu icon animation
            if (this.animation < 55) {
                this.animation++;
            }

            RenderUtils.drawImage("/gui/main_icon", this.width / 2 - 25, this.height / 2 - 20 - this.animation, 50, 50, ColorUtils.getIconColorAlpha());

            RenderUtils.drawRound(this.width / 2F - 60, this.height / 2F - 10, 120, 20, 5, !this.overModuleMenu ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());
            RenderUtils.drawRound(this.width / 2F - 85, this.height / 2F - 10, 20, 20, 5, !this.overCapes ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());
            RenderUtils.drawRound(this.width / 2F + 65, this.height / 2F - 10, 20, 20, 5, !this.overEmotes ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());

            RenderUtils.drawOutline(5, 5, this.width - 10, this.height - 10, 2, 1, ColorUtils.getOutilneColor());
        } else if (this.inModuleMenu) {
            this.animation = 0;
            this.allowMoveHudModules = false;
            this.renderHudModules = true;

            GuiUtils.drawCustomGui(1, this.width, this.height, false);

            if (!this.inModuleSetting) {
                this.selectedModule = null;

                GuiUtils.drawHorizontalSlider(this.width, this.height, this.moduleSliderValueY - 15, ColorUtils.getBackgroundDarkerColor(), (overSlider || sliderMoving) ? ColorUtils.getBackgroundDarkerColor().brighter().brighter().brighter() : ColorUtils.getBackgroundDarkerColor().brighter());

                // RenderUtils.drawRound(this.width / 2F - 50, this.height / 2F - 140F, 40, 15, 2, this.overModuleTab ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor());

                // Exit buttons
                RenderUtils.drawRound(this.width / 2F + 222, this.height / 2F - 145, 20, 20, 5, !this.overModuleExitGui ? ColorUtils.getBackgroundDarkerColor().brighter() : ColorUtils.getBackgroundDarkerColor().darker());
                RenderUtils.drawRound(this.width / 2F - 248, this.height / 2F + 123, 98, 15, 5, !this.overModuleExitGui2 ? ColorUtils.getBlueColor() : ColorUtils.getBlueColor().darker());

                GlUtils.startScissors();
                RenderUtils.drawRoundCustom(this.width / 2F - 250F, this.height / 2F - 120F, 497F, 1F, 10F, Color.white, true, false, false, true);
                RenderUtils.drawRoundCustom(this.width / 2F - 213F + 65F, this.height / 2F - 100F - 20F, 395F, 260F, 10F, Color.white, false, false, true, false);
                GlUtils.readScissors(1);

                int index = 0;
                int index2 = 1;
                int offsetX = 100;
                int offsetY = 38;

                for (Module mod : Rat.instance.moduleManager.getModules()) {
                    GuiUtils.drawModuleComponent(mod, this.width / 2F - 240 + offsetX, this.height / 2F - 145 + offsetY - 1 + this.moduleSliderValueY);

                    index++;
                    offsetX += 125;
                    if (index == index2 * 3) {
                        index2++;
                        offsetX = 100;
                        offsetY += 125;
                    }
                }

                GlUtils.endScissors();
                FontUtils.drawFont("Edit HUD Layout", this.width / 2F - 236, this.height / 2F + 128, ColorUtils.getFontColor(this));
            } else {
                if (this.selectedModule != null) {
                    this.allowMoveHudModules = false;
                    this.renderHudModules = true;

                    FontUtils.drawFont(this.selectedModule.getDescription(), this.width / 2F - 115, this.height / 2F - 113, ColorUtils.getFontColor(this));
                    RenderUtils.drawRound(this.width / 2F - 140, this.height / 2F - 100, 370, 5, 2, ColorUtils.getBackgroundDarkerColor().darker().darker().darker().darker());

                    GuiUtils.drawModuleSettingGui(this.selectedModule, this.width / 2F - 135, this.height / 2F - 80);
                } else {
                    InjectionUtils.oneTimeInjectChecks();
                    mc.displayGuiScreen(null);
                }
            }
        } else if (this.inEmotes) {
            this.animation = 0;
            this.allowMoveHudModules = false;
            this.renderHudModules = true;
            this.selectedModule = null;

            GuiUtils.drawCustomGui(2, this.width, this.height, true);
        } else if (this.inCapes) {
            this.animation = 0;
            this.allowMoveHudModules = false;
            this.renderHudModules = true;
            this.selectedModule = null;

            GuiUtils.drawCustomGui(1, this.width, this.height, true);

            GlUtils.disableSeeThru();
            GuiInventory.drawEntityOnScreen(this.width / 2 + 165, this.height / 2 + 60, 85, this.width / 2F + 175 + (-InputUtils.getX()), this.height / 2F + 30 + (-InputUtils.getY()), this.mc.thePlayer);
            GlUtils.enableSeeThru();

            FontUtils.drawFont("Hint: Drag to move", this.width / 2F + 125, this.height / 2F + 75, ColorUtils.getFontColor(this));

            int index = 0;
            int index2 = 1;
            int offsetX = 45;
            int offsetY = 15;

            GlUtils.startScissors();
            GuiUtils.drawCustomGui(1, this.width, this.height, true);
            GlUtils.readScissors(1);

            for (Cape c : Rat.instance.capeManager.getCapes()) {

                if (Rat.instance.capeManager.getCurrentCape() == c.getName()) {
                    RenderUtils.drawOutline(this.width / 2F - 243 + offsetX - 3, this.height / 2F - 150 + offsetY - 3 + this.capeSliderValueY, 60 + 6, 90 + 6, 2, 12, ColorUtils.getOutilneColor());
                }

                RenderUtils.drawRound(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2, 6, ColorUtils.getBackgroundDarkerColor().darker());
                RenderUtils.drawRound(this.width / 2F - 243 + offsetX, this.height / 2F - 150 + offsetY + this.capeSliderValueY, 60, 90, 6, ColorUtils.getBackgroundDarkerColor());

                if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2)) {
                    RenderUtils.drawRound(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2, 6, ColorUtils.getBackgroundDarkerColor().darker());
                }

                this.mc.getTextureManager().bindTexture(c.getSample());

                if (!c.getName().equals("None")) {
                    GlStateManager.enableBlend();
                    RenderUtils.drawRoundTextured(this.width / 2F - 243 + offsetX + 8, this.height / 2F - 150 + offsetY + 6 + this.capeSliderValueY, 44, 70, 4, 1F);
                    GlStateManager.disableBlend();
                }

                FontUtils.drawFont(c.getName(), this.width / 2F - 243 + offsetX + (50 - FontUtils.getFontWidth(c.getName())), (int) (this.height / 2 - 150 + offsetY + 80 + this.capeSliderValueY), ColorUtils.getFontColor(this));

                index++;
                offsetX += 70;
                if (index == index2 * 4) {
                    index2++;
                    offsetX = 45;
                    offsetY += 100;
                }
            }

            GlUtils.endScissors();

            RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 121, 24, 24, 5, !this.overExitGui ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().darker());
            RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 90, 24, 24, 5, !this.overRemoveCape ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().darker());
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        if (b == InputUtils.MOUSE_LEFT) {
            if (this.overSlider || this.overRemoveCape || this.overModuleExitGui2 || this.overModuleExitGui
                    || this.overExitGui || this.overAModule || (this.overCapes && this.inCapes)
                    || (this.overEmotes && this.inEmotes) || this.overModuleMenu) {
                SoundUtils.playSound("click", 1.0F, 0.8F);
            }

            if (this.overModuleMenu) {
                if (this.overEmotes || this.overCapes)
                    return;

                this.inModuleMenu = !this.inModuleMenu;
            }

            if (this.inModuleMenu) {
                if (this.overModuleExitGui) {
                    this.overModuleExitGui = false;
                    this.inModuleMenu = false;
                }

                if (this.overModuleExitGui2) {
                    this.overModuleExitGui2 = false;
                    this.inModuleMenu = false;
                }

                if (this.overSlider) {
                    this.sliderMoving = true;
                }

                if (this.inModuleSetting) {
                    if (this.selectedModule != null) {
                        GuiUtils.mouseClickedInSettingGui(this.selectedModule, this.width / 2F - 135, this.height / 2F - 80);
                    }
                }

                if (this.selectedModule != null) {
                    if (this.selectedModule.getSettings() != null) {
                        if (this.selectedModule.isEnabled()) {
                            this.selectedModule.drawHover();
                        }
                    }
                } else {
                    for (Module mod : Rat.instance.moduleManager.getModules()) {
                        if (mod.overSetting) {
                            SoundUtils.playSound("click", 1.0F, 0.8F);

                            this.selectedModule = mod;
                            this.inModuleSetting = true;
                        }

                        if (mod.overEnable) {
                            SoundUtils.playSound("click", 1.0F, 0.8F);
                            mod.toggle();
                        }
                    }
                }
            }

            if (this.overEmotes) {
                if (this.overModuleMenu || this.overCapes)
                    return;

                this.inCapes = true;
            }

            if (this.overCapes) {
                if (this.overModuleMenu)
                    return;

                this.inEmotes = true;
            }

            if (this.inCapes) {
                if (this.overRemoveCape) {
                    Rat.instance.capeManager.setCurrentCape("None");
                }

                if (this.overExitGui) {
                    this.overExitGui = false;
                    this.inCapes = false;
                }

                int index = 0;
                int index2 = 1;
                int offsetX = 45;
                int offsetY = 15;

                for (Cape c : Rat.instance.capeManager.getCapes()) {
                    if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2)) {
                        Rat.instance.capeManager.setCurrentCape(c.getName());
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

        if (this.allowMoveHudModules) {
            if (this.overEmotes || this.overCapes || this.overModuleMenu)
                return;

            if (this.renderHudModules) {
                for (Module mod : Rat.instance.moduleManager.getModules()) {
                    if (mod.isEnabled() && mod.isHudMod() && !mod.isMoving()) {
                        if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), mod.getX() - 2, mod.getY() - 2, mod.getWidth() + 4, mod.getHeight() + 4)) {
                            mod.setMoving(true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
        if (b == InputUtils.MOUSE_LEFT) {
            if (this.sliderMoving || !this.inModuleMenu) {
                this.sliderMoving = false;
            }

            if (this.allowMoveHudModules) {
                if (this.renderHudModules) {
                    for (Module mod : Rat.instance.moduleManager.getModules()) {
                        if (mod.isHudMod() && mod.isEnabled() && mod.isMoving()) {
                            mod.setMoving(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (this.inModuleSetting && this.selectedModule != null) {
            for (Setting setting : this.selectedModule.getSettings()) {
                if (setting instanceof KeybindSetting) {
                    if (this.selectedModule.isReBinding) {

                        if (!InputUtils.isBlockedKey(key)) {
                            ((KeybindSetting) setting).keycode = key;
                        }

                        this.selectedModule.isReBinding = false;
                    }
                }
            }
        }

        if (key == Keyboard.KEY_ESCAPE) {
            if (!this.inModuleSetting) {
                if (!this.inCapes && !this.inEmotes) {
                    if (this.inModuleMenu) {
                        this.inModuleMenu = false;
                    } else {
                        for (Module mod : Rat.instance.moduleManager.getModules()) {
                            if (mod.isHudMod()) {
                                mod.setMoving(false);
                            }
                        }

                        this.allowMoveHudModules = false;

                        InjectionUtils.oneTimeInjectChecks();
                        this.mc.displayGuiScreen(null);
                    }
                } else {
                    if (this.inCapes) {
                        this.inCapes = false;
                    }

                    if (this.inEmotes) {
                        this.inEmotes = false;
                    }
                }
            } else {
                if (this.selectedModule != null) {
                    if (this.selectedModule.isReBinding) {
                        this.selectedModule.isReBinding = false;
                        return;
                    }

                    this.inModuleSetting = false;
                    this.inModuleMenu = true;
                }

                this.selectedModule = null;
            }
        }
    }

    @Override
    public void updateScreen() {
        if (!this.inModuleMenu && !this.inEmotes && !this.inCapes && !this.inModuleSetting) {
            this.overModuleMenu = !this.overAModule && InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 60, this.height / 2F - 10, 120, 20);
            this.overCapes = !this.overAModule && InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 85, this.height / 2F - 10, 20, 20);
            this.overEmotes = !this.overAModule && InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F + 65, this.height / 2F - 10, 20, 20);
        } else if (this.inModuleMenu) {
            if (!this.inModuleSetting) {
                int index = 0;
                int index2 = 1;
                int offsetX = 100;
                int offsetY = 38;

                for (Module mod : Rat.instance.moduleManager.getModules()) {
                    mod.overModule = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 240 + offsetX, this.height / 2F - 145 + offsetY - 1 + this.moduleSliderValueY, 115, 115);
                    mod.overSetting = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 242 + offsetX, this.height / 2F - 70 + offsetY - 1 + this.moduleSliderValueY, 117, 20);
                    mod.overEnable = mod.canBeEnabled() && InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 242 + offsetX, this.height / 2F - 50 + offsetY - 1 + this.moduleSliderValueY, 117, 20);

                    index++;
                    offsetX += 125;
                    if (index == index2 * 3) {
                        index2++;
                        offsetX = 100;
                        offsetY += 125;
                    }
                }

                if (this.selectedModule == null) {
                    this.moduleSliderValueY += InputUtils.getScroll();
                }

                this.overModuleExitGui = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F + 222, this.height / 2F - 145, 20, 20);
                this.overModuleExitGui2 = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 248, this.height / 2F + 123, 98, 15);
                this.overSlider = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F + 236, this.height / 2F - 115, 8, 250);
                this.overModuleMenu = false;
            }
        } else if (this.inModuleSetting) {
            this.overExitGui = false;
        }

        if (this.inEmotes || this.inCapes) {
            this.overExitGui = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 244, this.height / 2F + 121, 24, 24);
        }

        if (this.inCapes) {
            this.capeSliderValueY += InputUtils.getScroll();
            this.overRemoveCape = InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), this.width / 2F - 244, this.height / 2F + 90, 24, 24);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
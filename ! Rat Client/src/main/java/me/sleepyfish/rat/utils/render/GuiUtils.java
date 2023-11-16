package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.modules.impl.SettingModule;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import me.sleepyfish.rat.utils.render.font.FontUtils;
import org.lwjgl.input.Keyboard;

import java.awt.Color;

public class GuiUtils {

    public static Object ob;

    /**
     * @implNote Provided x and y are auto calculated to render in the bottom right corner
     */
    public static void drawLogo(int x, int y, int mode) {

        if (mode == 1) {
            int size = 85;

            RenderUtils.drawImage("/gui/main_icon", x / 2 - (size / 2), y / 8, size, size, Color.white);
        }

        if (mode == 2) {
            int width = 160;
            int height = 25;

            if (SettingModule.drawIconInGui.isEnabled()) {
                RenderUtils.drawImage("/gui/icon", x - (width + 5), y - (height + 10), width, height, ColorUtils.getIconColor());
            }
        }
    }

    public static void drawCustomGui(int mode, float width, float height, boolean normalMode) {
        if (mode == 1) {
            if (normalMode) {
                RenderUtils.drawRoundCustom(width / 2F - 213F, height / 2F - 150F, 500F, 300F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker(), false, false, true, true);
                RenderUtils.drawRound(width / 2F - 214F, height / 2F - 150F, 1F, 300F, 1F, ColorUtils.getBackgroundDarkerColor().brighter());
                RenderUtils.drawRoundCustom(width / 2F - 250F, height / 2F - 150F, 37F, 300F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker(), true, true, false, false);
                ColorUtils.clearColor();
                RenderUtils.drawImage("/gui/icons/back", (int) (width / 2 - 237), (int) (height / 2 + 121 + 8), 10, 10, ColorUtils.getFontColor(ob));
            } else {
                RenderUtils.drawRoundCustom(width / 2F - 250F, height / 2F - 150F, 497F, 30F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker().darker().darker().darker(), true, false, false, true);
                RenderUtils.drawRoundCustom(width / 2F - 213F + 65F, height / 2F - 100F - 20F, 395F, 260F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker(), false, false, true, false);
                RenderUtils.drawRoundCustom(width / 2F - 250F, height / 2F - 100F - 20F, 102F, 260F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker(), false, true, false, false);
                ColorUtils.clearColor();
                RenderUtils.drawImage("/gui/icon", (int) (width / 2F - 240), (int) (height / 2F - 142), 75, 15, ColorUtils.getIconColorAlpha());
            }
        }

        if (mode == 2) {
            FontUtils.drawFont("No Emotes yet...", (int) (width / 2 - (FontUtils.getFontWidth("No Emotes yet...") / 2F)), (int) (height / 2F) + 15, ColorUtils.getFontColor(ob));
        }
    }

    public static void drawHorizontalSlider(int width, int height, float value, Color color, Color slideColor) {
        float yStart = (height / 2F) - 117F;

        RenderUtils.drawRound(width / 2F + 236F, yStart, 5F, 253F, 2F, color);
        RenderUtils.drawRound(width / 2F + 236F, yStart - (value / (Rat.instance.moduleManager.getModules().size() / 2F)), 5F, 35F, 2.4F, slideColor);
    }

    // module shit -----------------------------------------------------------------

    public static void drawModuleComponent(Module mod, float x, float y) {
        String name = mod.getName();
        Color fontColor = mod.getFontColor();
        Color backGround = ColorUtils.getBackgroundDarkerColorAlpha();

        Color moduleColor;
        String moduleState;

        if (mod.isEnabled()) {
            Color color = new Color(0, 230, 10, 160);

            if (mod.overEnable) {
                if (mod.canBeEnabled()) {
                    moduleColor = color.brighter().brighter().brighter().brighter();
                } else {
                    moduleColor = ColorUtils.getBackgroundDarkerColor().darker();
                }
            } else {
                if (mod.canBeEnabled()) {
                    moduleColor = color;
                } else {
                    moduleColor = ColorUtils.getBackgroundDarkerColor().brighter();
                }
            }

            moduleState = "Enabled";
        } else {
            Color color = new Color(230, 0, 10, 160);

            if (mod.overEnable) {
                if (mod.canBeEnabled()) {
                    moduleColor = color.brighter().brighter().brighter().brighter();
                } else {
                    moduleColor = ColorUtils.getBackgroundDarkerColor().darker();
                }
            } else {
                if (mod.canBeEnabled()) {
                    moduleColor = color;
                } else {
                    moduleColor = ColorUtils.getBackgroundDarkerColor().brighter();
                }
            }

            moduleState = "Disabled";
        }

        int size = 115;

        RenderUtils.drawOutline(x - 1, y - 1, size + 2, size + 1, 2, 10, backGround.brighter().brighter());

        RenderUtils.drawRound(x, y, size, size, 10, mod.overModule ? backGround.brighter() : backGround.darker());

        // Settings rectangle
        RenderUtils.drawRound(x, y + size - 41, size, 20, 0, mod.overSetting ? backGround.brighter() : backGround.darker());
        FontUtils.drawFont("Options", x + (size / 2F) - (FontUtils.getFontWidth("Options") / 2F), y + size - 35, fontColor);

        RenderUtils.drawRoundCustom(x, y + size - 21, size, 20, 8, moduleColor, false, true, true, false);
        FontUtils.drawFont(moduleState, (x + size / 2F - FontUtils.getFontWidth(moduleState) / 2F), (y + size / 2F + 42.5F), fontColor);

        FontUtils.drawFont(name, (x + size / 2F - FontUtils.getFontWidth(name) / 2F), (y + size / 2F) + 4, fontColor);
    }

    static int offset = 3;

    public static void mouseClickedInSettingGui(Module mod, float x, float y) {
        int index = 0;
        int index2 = offset;
        int offsetX = 0;
        int offsetY = 0;

        for (Setting setting : mod.getSettings()) {
            if (setting instanceof ToggleSetting) {
                if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), x + offsetX, y + offsetY, FontUtils.getFontWidth(((ToggleSetting) setting).getName()) + 35, 8)) {
                    ((ToggleSetting) setting).toggle();
                }
            }

            if (setting instanceof KeybindSetting) {
                if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), x + offsetX, y + offsetY, FontUtils.getFontWidth( ((KeybindSetting) setting).getName()) + 35, 8)) {
                    SoundUtils.playSound("click", 1.0F, 0.8F);
                    mod.isReBinding = !mod.isReBinding;
                }
            }

            index++;
            offsetX += 120;
            if (index == index2) {
                index = 0;
                offsetX = 0;
                offsetY += 22;
            }
        }
    }

    public static void drawModuleSettingGui(Module mod, float x, float y) {
        int index = 0;
        int index2 = offset;
        int offsetX = 0;
        int offsetY = 0;

        for (Setting setting : mod.getSettings()) {

            if (setting instanceof ToggleSetting) {
                if (((ToggleSetting) setting).getDescription() != "-") {
                    if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), x + offsetX, y + offsetY, FontUtils.getFontWidth(((ToggleSetting) setting).getName()) + 35, 8)) {
                        RenderUtils.drawRound(InputUtils.getX() + 5, InputUtils.getY() + 5, FontUtils.getFontWidth(((ToggleSetting) setting).getDescription()) + 10, 16, 4, mod.getBackgroundColor2());
                        FontUtils.drawFont(((ToggleSetting) setting).getDescription() + ".", InputUtils.getX() + 9, InputUtils.getY() + 9, mod.getFontColor());
                    }
                }

                FontUtils.drawFont(((ToggleSetting) setting).getName(), x + offsetX + 30, y + offsetY + 1, ColorUtils.getFontColor(ob));
                RenderUtils.drawRound(x + offsetX, y + offsetY, 25, 8, 2, ColorUtils.getBackgroundBrighterColor());

                if (((ToggleSetting) setting).isEnabled()) {
                    RenderUtils.drawRound(x + offsetX, y + offsetY, 15, 8, 2, Color.green);

                    GlUtils.startScale(0.5);
                    FontUtils.drawFont("On", (x + offsetX + 4) * 2, (y + offsetY + 2) * 2, ColorUtils.getFontColor(ob));
                    GlUtils.stopScale();
                } else {
                    RenderUtils.drawRound(x + offsetX + 10, y + offsetY, 15, 8, 2, Color.red);

                    GlUtils.startScale(0.5);
                    FontUtils.drawFont("Off", (x + offsetX + 14) * 2, (y + offsetY + 2) * 2, ColorUtils.getFontColor(ob));
                    GlUtils.stopScale();
                }
            }

            if (setting instanceof KeybindSetting) {
                if (((KeybindSetting) setting).getDescription() != "-") {
                    if (InputUtils.isInside(InputUtils.getX(), InputUtils.getY(), x + offsetX, y + offsetY, FontUtils.getFontWidth(((KeybindSetting) setting).getName()) + 35, 8)) {
                        RenderUtils.drawRound(InputUtils.getX() + 5, InputUtils.getY() + 5, FontUtils.getFontWidth(((KeybindSetting) setting).getDescription()) + 10, 16, 4, mod.getBackgroundColor2());
                        FontUtils.drawFont(((KeybindSetting) setting).getDescription() + ".", InputUtils.getX() + 9, InputUtils.getY() + 9, mod.getFontColor());
                    }
                }

                FontUtils.drawFont(((KeybindSetting) setting).getName() + ":", x + offsetX, y + offsetY + 1, ColorUtils.getFontColor(ob));

                String renderText;
                if (mod.isReBinding) {
                    renderText = "...";
                } else {
                    renderText = Keyboard.getKeyName(((KeybindSetting) setting).keycode)
                            .replace("NONE", "?").replace("0", "?").replace("BACK", "?")
                            .replace("SPACE", "?").replace("TAB", "?").replace("LCONTROL", "?");
                }

                int titleWidth = FontUtils.getFontWidth(((KeybindSetting) setting).getName() + ":");
                int keycodeWidth = FontUtils.getFontWidth(renderText);

                RenderUtils.drawRound(x + offsetX + titleWidth + 2, y + offsetY - 2, keycodeWidth + 10, 14, 2, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont(renderText, x + offsetX + titleWidth + 7, y + offsetY + 1, renderText.equals("?") ? new Color(208, 105, 108, 160) : ColorUtils.getFontColor(ob));
            }

            index++;
            offsetX += 120;
            if (index == index2) {
                index = 0;
                offsetX = 0;
                offsetY += 22;
            }
        }
    }

}
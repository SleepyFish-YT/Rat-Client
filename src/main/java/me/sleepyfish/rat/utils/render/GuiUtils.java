package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.SettingModule;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class GuiUtils {

    public final static Object ob = new Object();;

    /**
     * @implNote Provided x and y are auto calculated to render in the bottom right corner
     */
    public static void drawLogo(final int x, final int y, boolean mode) {
        if (mode) {
            final int size = 85;

            RenderUtils.drawImage("/gui/icon", x / 2 - (size / 2), y / 8, size, size, ColorUtils.getIconColorAlpha());
        } else {
            final int width = 160;
            final int height = 25;

            if (SettingModule.guiIcons.isEnabled()) {
                RenderUtils.drawImage("/gui/icon_text", x - (width + 5), y - (height + 10), width, height, ColorUtils.getIconColorAlpha());
            }
        }
    }

    /**
     * @implNote Provided x and y are auto calculated to render in the bottom right corner
     */
    public static void drawCustomGui(final int mode, final float width, final float height, final boolean normalMode) {
        final float calcX = width / 2F - 200F;
        final float calcY = height / 2F - 100F;

        if (mode == 1) {
            if (normalMode) {
                RenderUtils.drawRoundCustom(calcX - 13F, calcY - 50F, 500F, 300F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker(), false, false, true, true);
                RenderUtils.drawRound(calcX - 14F, calcY - 50F, 1F, 300F, 1F, ColorUtils.getBackgroundDarkerColor().brighter());
                RenderUtils.drawRoundCustom(calcX - 50F, calcY - 50F, 37F, 300F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker(), true, true, false, false);
            } else {
                RenderUtils.drawRoundCustom(calcX - 50F, calcY - 50F, 497F, 30F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker().darker().darker().darker(), true, false, false, true);
                RenderUtils.drawRoundCustom(calcX - 13F + 65F, calcY - 20F, 395F, 260F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker(), false, false, true, false);
                RenderUtils.drawRoundCustom(calcX - 50F, calcY - 20F, 102F, 260F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker(), false, true, false, false);
            }
        }

        if (mode == 2) {
            FontUtils.drawFont("No Emotes yet...", (int) (width / 2 - (FontUtils.getFontWidth("No Emotes yet...") / 2F)), (int) (height / 2F) + 15, ColorUtils.getFontColor(ob));
        }

        if (mode == 3) {
            RenderUtils.drawRoundCustom(calcX - 13F, calcY - 20F, 500F, 265F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker(), false, false, true, true);
            RenderUtils.drawRoundCustom(calcX - 50F, calcY - 20F, 37F, 265F, 10F, ColorUtils.getBackgroundDarkerColor().darker().darker().darker(), true, true, false, false);
        }
    }

    public static void drawHorizontalSlider(final int width, final int height, final float value, final Color color, final Color slideColor) {
        final float yStart = height / 2F - 117F;

        RenderUtils.drawRound(width / 2F + 236F, yStart, 5F, 253F, 2F, color);
        RenderUtils.drawRound(width / 2F + 236F, yStart - value, 5F, 27F, 2.4F, slideColor);
    }

    // module shit -----------------------------------------------------------------

    public static void drawModuleComponent(final Module mod, final float x, final float y) {
        final String name = mod.getName();
        final Color fontColor = mod.getFontColor();
        final Color backGround = ColorUtils.getBackgroundDarkerColorLessAlpha();

        Color moduleColor;
        String moduleState = "Disabled";

        if (mod.isEnabled()) {
            final Color color = new Color(0, 230, 10, 160);

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
            final Color color = new Color(230, 0, 10, 160);

            if (mod.overEnable) {
                if (mod.canBeEnabled()) {
                    moduleColor = color.brighter().brighter().brighter().brighter();
                } else {
                    moduleColor = ColorUtils.getBackgroundDarkerColor().darker();
                }
            } else {
                if (mod.canBeEnabled()) {
                    moduleColor = color;
                    moduleState = "Disabled";
                } else {
                    moduleColor = ColorUtils.getBackgroundDarkerColor().brighter();
                    moduleState = "Fixed";
                }
            }
        }

        RenderUtils.drawImage(mod.getIconPath(), (int) x + 26 + (64 / 4), (int) y + 10, 32, 32);

        final int size = 115;
        RenderUtils.drawOutline(x - 1, y - 1, size + 2, size + 1, 2, 10, backGround.brighter().brighter());

        RenderUtils.drawRound(x, y, size, size, 10, mod.overModule ? backGround.brighter() : backGround.darker());

        // Settings rectangle
        RenderUtils.drawRound(x, y + size - 41, size, 20, 0, mod.overSetting ? backGround.brighter() : backGround.darker());
        FontUtils.drawFont("Options", x + (size / 2F) - (FontUtils.getFontWidth("Options") / 2F), y + size - 35, fontColor);

        // Enable / Disable rect
        RenderUtils.drawRoundCustom(x, y + size - 21, size, 20, 8, moduleColor, false, true, true, false);

        // Enable / Disable text
        FontUtils.drawFont(moduleState, (x + size / 2F - FontUtils.getFontWidth(moduleState) / 2F), (y + size / 2F + 42.5F), fontColor);

        // Module name text
        FontUtils.drawFont(name, (x + size / 2F - FontUtils.getFontWidth(name) / 2F), (y + size / 2F) + 4, fontColor);
    }

    static int offset = 3;

    public static void mouseClickedInSettingGui(final Module mod, final float x, final float y) {
        short index = 0;
        int index2 = offset;
        short offsetX = 0;
        short offsetY = 0;

        for (final Setting setting : mod.getSettings()) {
            if (setting instanceof ToggleSetting) {
                if (InputUtils.isInside(x + offsetX, y + offsetY, FontUtils.getFontWidth(setting.getName()) + 35, 8)) {
                    ((ToggleSetting) setting).toggle();
                }
            }

            if (setting instanceof KeybindSetting) {
                if (InputUtils.isInside(x + offsetX, y + offsetY, FontUtils.getFontWidth(setting.getName()) + 35, 8)) {
                    SoundUtils.playClick();
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

    public static void drawModuleSettingGui(final Module mod, final float x, final float y) {
        short index = 0;
        int index2 = offset;
        short offsetX = 0;
        short offsetY = 0;

        for (final Setting setting : mod.getSettings()) {
            if (setting instanceof ToggleSetting) {
                if (!((ToggleSetting) setting).getDescription().startsWith("-")) {
                    if (InputUtils.isInside(x + offsetX, y + offsetY, FontUtils.getFontWidth((setting).getName()) + 35, 8)) {
                        RenderUtils.drawRound(InputUtils.mouseX + 10, InputUtils.mouseY + 10, FontUtils.getFontWidth(((ToggleSetting) setting).getDescription()) + 10, 16, 4, mod.getBackgroundColor2());
                        FontUtils.drawFont(((ToggleSetting) setting).getDescription() + ".", InputUtils.mouseX + 14, InputUtils.mouseY + 14, mod.getFontColor());
                    }
                }

                FontUtils.drawFont((setting).getName(), x + offsetX + 30, y + offsetY + 1, ColorUtils.getFontColor(ob));
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
            } else

            if (setting instanceof KeybindSetting) {
                if (!((KeybindSetting) setting).getDescription().startsWith("-")) {
                    if (InputUtils.isInside(x + offsetX, y + offsetY, FontUtils.getFontWidth((setting).getName()) + 35, 8)) {
                        RenderUtils.drawRound(InputUtils.mouseX + 10, InputUtils.mouseY + 10, FontUtils.getFontWidth(((KeybindSetting) setting).getDescription()) + 10, 16, 4, mod.getBackgroundColor2());
                        FontUtils.drawFont(((KeybindSetting) setting).getDescription() + ".", InputUtils.mouseX + 14, InputUtils.mouseY + 14, mod.getFontColor());
                    }
                }

                FontUtils.drawFont(setting.getName() + ":", x + offsetX, y + offsetY + 1, ColorUtils.getFontColor(ob));

                String renderText;
                if (mod.isReBinding) {
                    renderText = "...";
                } else {
                    renderText = Keyboard.getKeyName(((KeybindSetting) setting).keycode)
                            .replace("LMENU", "LAlt")
                            .replace("RSHIFT", "RShift")
                            .replace("SLASH", "Slash")
                            .replace("DELETE", "Delete")
                            .replace("RETURN", "Enter")
                            .replace("CAPITAL", "Caps")
                            .replace("LSHIFT", "LShift")
                            .replace("NONE", "None")
                            .replace("0", "?")
                            .replace("BACK", "Back")
                            .replace("SPACE", "Space")
                            .replace("TAB", "Tab")
                            .replace("LCONTROL", "LControl");
                }

                int titleWidth = FontUtils.getFontWidth(setting.getName() + ":");
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

    /**
     * This class is from Rat Client.
     * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
     * @author SleepyFish 2024
     */
    public static class Button {

        private final String text;
        private final SimpleAnimation animation;

        private final int posX;
        private final int posY;
        private final int width;
        private final int height;

        private final float maxAnimationValue;
        private final float minAnimationValue;
        private final float animationSpeed;

        private final float radius;

        /**
         * This class is from Rat Client.
         * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
         * @author SleepyFish 2024
         */
        public Button(final String text, final double posX, final double posY, final double width, final double height, final float radius) {
            this.posX = (int) posX;
            this.posY = (int) posY;
            this.width = (int) width;
            this.height = (int) height;

            this.animation = new SimpleAnimation(0F);
            this.radius = radius;

            this.maxAnimationValue = 190F;
            this.minAnimationValue = 0F;
            this.animationSpeed = 16F;

            this.text = text;
        }

        public float getAnimationValue() {
            return this.animation.getValue();
        }

        public void render() {
            this.animation.setAnimation(this.isInside() ? this.maxAnimationValue : this.minAnimationValue, this.animationSpeed);

            if (this.animation.getValue() < this.maxAnimationValue) {
                RenderUtils.drawRound(this.posX, this.posY, this.width, this.height, this.radius, ColorUtils.getIconColor().brighter());
            }

            final Color hoverColor = new Color(168, 168, 168, (int) this.animation.getValue());
            RenderUtils.drawRound(this.posX, this.posY, this.width, this.height, this.radius, hoverColor.darker());

            final int centerX = (this.posX + this.width / 2) - (FontUtils.getFontWidth(this.text) / 2);
            final int centerY = (this.posY + this.height / 2) - (FontUtils.getFontHeight() / 2);
            FontUtils.drawFont(this.text, centerX, centerY, ColorUtils.getFontColor(this));
        }

        public boolean isInside() {
            return InputUtils.isInside(this.posX, this.posY, this.width, this.height);
        }
    }

}
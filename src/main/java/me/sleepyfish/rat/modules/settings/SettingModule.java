package me.sleepyfish.rat.modules.settings;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import org.lwjgl.input.Keyboard;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class SettingModule extends Module {

    public static KeybindSetting guiKeybind;

    public static ToggleSetting sounds;
    public static ToggleSetting drawShadow;
    public static ToggleSetting guiIcons;
    public static ToggleSetting disableIcons;
    public static ToggleSetting chroma;

    public SettingModule() {
        super("Settings", "Customize the Appearance of the HUD");

        this.addSetting(drawShadow = new ToggleSetting("Drop Shadow", "Setting for Font Shadows",true));
        this.addSetting(guiIcons = new ToggleSetting("Icons on GUIs", "Shows menu Icons", true));
        this.addSetting(disableIcons = new ToggleSetting("Disable Icons", "Makes the GUI more clean",false));
        this.addSetting(chroma = new ToggleSetting("Chroma Font color", "Makes every Font color to Chroma",false));
        this.addSetting(sounds = new ToggleSetting("Gui Sounds", true));
        this.addSetting(guiKeybind = new KeybindSetting("Gui Keybind", Keyboard.KEY_RSHIFT));

        this.setCanBeEnabled(false);
    }

}
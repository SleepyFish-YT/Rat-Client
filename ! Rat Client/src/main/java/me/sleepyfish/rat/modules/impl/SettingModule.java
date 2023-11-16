package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import org.lwjgl.input.Keyboard;

public class SettingModule extends Module {

    public static KeybindSetting guiKeybind;

    public static ToggleSetting drawShadow;
    public static ToggleSetting drawIconInGui;
    public static ToggleSetting disableIcons;
    public static ToggleSetting chroma;

    public SettingModule() {
        super("Settings", "Customize the Appearance of the HUD.");

        this.addSetting(drawShadow = new ToggleSetting("Drop Shadow", "Setting for Font Shadows",true));
        this.addSetting(drawIconInGui = new ToggleSetting("Icon on GUI", "Shows a Icon on the Bottom Right corner",true));
        this.addSetting(disableIcons = new ToggleSetting("Disable Icons", "Makes the GUI more clean",true));
        this.addSetting(chroma = new ToggleSetting("Chroma Font color", "Makes every Font color to Chroma",false));
        this.addSetting(guiKeybind = new KeybindSetting("Gui Keybind", Keyboard.KEY_RSHIFT));

        this.setCanBeEnabled(false);
    }

}
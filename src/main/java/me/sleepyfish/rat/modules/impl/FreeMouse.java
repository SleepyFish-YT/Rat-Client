package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

public class FreeMouse extends Module {

    private final KeybindSetting key;

    public FreeMouse() {
        super("Free Mouse", "Frees your mouse");

        this.addSetting(this.key = new KeybindSetting("Free Key", Keyboard.KEY_LMENU));
    }

    @Override
    public void tickUpdate() {
        if (this.key.isDown()) {
            System.setProperty("fml.noGrab", "true");
            Mouse.setGrabbed(true);
            Mouse.updateCursor();
        } else {
            System.setProperty("fml.noGrab", "false");
            Mouse.setGrabbed(false);
            Mouse.updateCursor();
        }
    }

}
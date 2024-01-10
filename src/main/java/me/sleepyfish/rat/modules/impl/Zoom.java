package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Zoom extends Module {

    private final ToggleSetting slowerSens;
    private final ToggleSetting smoothCam;

    private final KeybindSetting keybindSetting;

    private boolean active;
    private float oldFov;
    private float oldSens;

    public Zoom() {
        super("Zoom", "Zoom in while playing minecraft");

        this.addSetting(slowerSens = new ToggleSetting("Slower sensitivity", true));
        this.addSetting(smoothCam = new ToggleSetting("Smooth camera", true));
        this.addSetting(keybindSetting = new KeybindSetting("Keybind", Keyboard.KEY_C));

        this.toggle();
    }

    @Override
    public void tickUpdate() {
        if (!PlayerUtils.canLegitWork())
            return;

        if (mc.thePlayer != null) {
            if (InputUtils.isKeyDown(keybindSetting.keycode)) {
                if (!active) {
                    this.oldSens = mc.gameSettings.mouseSensitivity;
                    this.oldFov = mc.gameSettings.fovSetting;
                    this.active = true;

                    if (this.slowerSens.isEnabled())
                        mc.gameSettings.mouseSensitivity /= 4F;

                    if (this.smoothCam.isEnabled())
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSmoothCamera.getKeyCode(), true);

                    mc.gameSettings.fovSetting = 35;
                }
            } else {
                if (this.active) {
                    this.active = false;

                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSmoothCamera.getKeyCode(), false);
                    mc.gameSettings.mouseSensitivity = this.oldSens;
                    mc.gameSettings.fovSetting = this.oldFov;
                }
            }
        }
    }

}
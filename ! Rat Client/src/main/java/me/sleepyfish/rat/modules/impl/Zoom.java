package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Keyboard;

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

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (this.isEnabled()) {
            if (mc.thePlayer != null) {
                if (Keyboard.isKeyDown(keybindSetting.keycode)) {
                    if (!active) {
                        oldSens = mc.gameSettings.mouseSensitivity;
                        oldFov = mc.gameSettings.fovSetting;
                        active = true;

                        if (slowerSens.isEnabled()) {
                            mc.gameSettings.mouseSensitivity /= 4F;
                        }

                        if (smoothCam.isEnabled()) {
                            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSmoothCamera.getKeyCode(), true);
                        }

                        mc.gameSettings.fovSetting = 25;
                    }
                } else {
                    if (active) {
                        active = false;

                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSmoothCamera.getKeyCode(), false);
                        mc.gameSettings.mouseSensitivity = oldSens;
                        mc.gameSettings.fovSetting = oldFov;
                    }
                }
            }
        }
    }

}
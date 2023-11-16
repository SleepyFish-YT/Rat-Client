package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

public class ToggleSprint extends Module {

    private final ToggleSetting flightSpeed;

    public ToggleSprint() {
        super("Toggle Sneak/Sprint", "Just works", 20, 100);

        this.addSetting(flightSpeed = new ToggleSetting("Flight Speed", "Makes flying faster when holding control",false));

        this.brackets.toggle();

        this.toggle();
    }

    @Override
    public void renderUpdate() {
        if (mc.thePlayer.capabilities.isCreativeMode) {
            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                if (Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())) {
                    if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
                        this.setText("Sprinting (Toggled)");
                    } else {
                        if (this.flightSpeed.isEnabled()) {
                            this.setText("Flying (x4)");
                            mc.thePlayer.capabilities.setFlySpeed(0.05F * 4);
                        } else {
                            this.setText("Flying");
                        }
                    }
                }
            } else {
                if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
                    this.setText("Sneaking (Held)");
                } else {
                    if (this.flightSpeed.isEnabled()) {
                        this.setText("Flying (x4)");
                        mc.thePlayer.capabilities.setFlySpeed(0.05F * 4);
                    } else {
                        this.setText("Flying");
                    }
                }
            }
        } else {
            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                if (Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())) {
                    this.setText("Sprinting (Held)");
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
                    this.setText("Sprinting (Toggled)");
                }
            } else {
                this.setText("Sneaking (Held)");
            }
        }
    }

}
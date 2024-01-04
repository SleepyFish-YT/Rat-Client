package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class ToggleSprint extends Module {

    private final ToggleSetting flightSpeed;

    public ToggleSprint() {
        super("Toggle Sprint", "Toggle Sprint", 20, 100);

        this.addSetting(flightSpeed = new ToggleSetting("Flight Speed", "Makes flying faster when holding control", true));

        this.brackets.toggle();

        this.toggle();
    }

    @Override
    public void renderUpdate() {
        if (this.flightSpeed.isEnabled()) {
            if (mc.thePlayer.capabilities.isFlying) {
                if (mc.thePlayer.capabilities.allowFlying) {
                    if (!mc.isSingleplayer())
                        return;

                    if (!mc.thePlayer.onGround) {
                        if (mc.gameSettings.keyBindSprint.isKeyDown()) {
                            if (PlayerUtils.isMoving()) {
                                this.setText("Flying (x4)");
                                mc.thePlayer.capabilities.setFlySpeed(0.05F * 4F);
                            } else {
                                this.setText("Flying");
                                mc.thePlayer.capabilities.setFlySpeed(0.05F);
                            }

                        } else {
                            this.setText("Flying");
                            mc.thePlayer.capabilities.setFlySpeed(0.05F);

                        }

                        return;
                    }
                }
            }
        }

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
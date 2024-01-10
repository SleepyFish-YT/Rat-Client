package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.event.function.RatEvent;
import me.sleepyfish.rat.event.EventCameraRotation;
import me.sleepyfish.rat.event.EventPlayerHeadRotation;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Freelook extends Module {

    private int oldState;
    public boolean active;

    public float yaw;
    public float pitch;

    private final ToggleSetting invertCamera;
    private final KeybindSetting keybindSetting;

    public Freelook() {
        super("Freelook", "Move camera without moving rotation");
        this.addSetting(this.invertCamera = new ToggleSetting("Invert Camera", false));
        this.addSetting(this.keybindSetting = new KeybindSetting("Keybind", Keyboard.KEY_F));

        this.active = false;

        this.toggle();
    }

    @Override
    public void tickUpdate() {
        if (!PlayerUtils.canLegitWork())
            return;

        if (InputUtils.isKeyDown(this.keybindSetting.keycode)) {
            if (!this.active) {
                this.active = true;
                this.oldState = mc.gameSettings.thirdPersonView;

                mc.gameSettings.thirdPersonView = 3;

                if (this.invertCamera.isEnabled()) {
                    this.yaw = -mc.thePlayer.rotationYaw;
                } else {
                    this.yaw = mc.thePlayer.rotationYaw;
                }

                this.pitch = mc.thePlayer.rotationPitch;
            }
        } else {
            if (this.active) {
                this.active = false;
                mc.gameSettings.thirdPersonView = this.oldState;
                mc.renderGlobal.setDisplayListEntitiesDirty();
            }
        }
    }

    @RatEvent
    public void cameraRotation(EventCameraRotation e) {
        if (this.active) {
            e.setYaw(this.yaw);
            e.setPitch(this.pitch);
        }
    }

    @RatEvent
    public void headRotation(EventPlayerHeadRotation e) {
        if (this.active) {
            final float yaw = e.getYaw();
            float pitch = e.getPitch();
            e.setCancelled(true);
            pitch = -pitch;

            if (this.invertCamera.isEnabled()) {
                this.yaw -= -(yaw * 0.15F);
            } else {
                this.yaw += yaw * 0.15F;
            }

            this.pitch = MathHelper.clamp_float(this.pitch + (pitch * 0.15F), -90F, 90F);

            mc.renderGlobal.setDisplayListEntitiesDirty();
        }
    }

    public float[] getRots() {
        return new float[] {this.yaw, this.pitch};
    }

    public final boolean isActive() {
        return this.active;
    }

}
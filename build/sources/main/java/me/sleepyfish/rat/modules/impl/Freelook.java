package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.event.function.RatEvent;
import me.sleepyfish.rat.event.EventCameraRotation;
import me.sleepyfish.rat.event.EventPlayerHeadRotation;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import me.sleepyfish.rat.utils.misc.PlayerUtils;
import net.minecraft.util.MathHelper;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Keyboard;

public class Freelook extends Module {

    private int oldState;
    public static boolean active;

    public static float yaw;
    public static float pitch;

    private final ToggleSetting invertCamera;
    private final KeybindSetting keybindSetting;

    public Freelook() {
        super("Freelook", "Move camera without moving rotation.");
        this.addSetting(this.invertCamera = new ToggleSetting("Invert Camera", false));
        this.addSetting(this.keybindSetting = new KeybindSetting("Keybind", Keyboard.KEY_F));

        this.active = false;

        this.toggle();
    }

    @Override
    public void tickUpdate() {
        if (!PlayerUtils.canLegitWork())
            return;

        if (Keyboard.isKeyDown(keybindSetting.keycode)) {
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
            float yaw = e.getYaw();
            float pitch = e.getPitch();
            e.setCancelled(true);
            pitch = -pitch;

            if (this.invertCamera.isEnabled()) {
                this.yaw -= -(yaw * 0.15F);
            } else {
                this.yaw += yaw * 0.15F;
            }

            this.pitch = MathHelper.clamp_float(this.pitch + (pitch * 0.15F), -90.0F, 90.0F);

            mc.renderGlobal.setDisplayListEntitiesDirty();
        }
    }

}
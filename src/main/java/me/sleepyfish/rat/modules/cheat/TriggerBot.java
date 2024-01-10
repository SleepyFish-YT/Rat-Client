package me.sleepyfish.rat.modules.cheat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.BotUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.TimerUtils;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.modules.settings.impl.KeybindSetting;

import net.minecraft.entity.Entity;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class TriggerBot extends Module {

    public final ToggleSetting weaponCheck;
    public final ToggleSetting silentAttack;
    public final KeybindSetting triggerKey;

    public TimerUtils timer;

    public TriggerBot() {
        super("Trigger Bot", "Hits for you if u hovering over a enemy");

        this.addSetting(this.weaponCheck = new ToggleSetting("Weapon Check", true));
        this.addSetting(this.silentAttack = new ToggleSetting("Silent Attack", false));
        this.addSetting(this.triggerKey = new KeybindSetting("Trigger Key", 0));

        this.timer = new TimerUtils();
    }

    @Override
    public void tickUpdate() {
        if (!PlayerUtils.canLegitWork())
            return;

        if (this.weaponCheck.isEnabled() && !PlayerUtils.holdingWeapon())
            return;

        if (this.triggerKey.keycode != 0 && !this.triggerKey.isDown())
            return;

        final Entity pointed = mc.pointedEntity;
        if (pointed != null) {
            if (!BotUtils.isBot(pointed)) {
                if (this.timer.cpsTimer(8, 12)) {

                    if (this.silentAttack.isEnabled()) {
                        mc.playerController.attackEntity(mc.thePlayer, pointed);
                        mc.thePlayer.swingItem();
                    } else {
                        InputUtils.pressKeybindOnce(mc.gameSettings.keyBindAttack);
                    }

                    this.timer.reset();
                }
            }
        }
    }
}
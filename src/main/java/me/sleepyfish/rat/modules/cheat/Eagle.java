package me.sleepyfish.rat.modules.cheat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.TimerUtils;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.client.settings.KeyBinding;

public class Eagle extends Module {

    private final ToggleSetting blocksOnly;
    private final ToggleSetting backwardsOnly;
    private final ToggleSetting fixForward;
    private final ToggleSetting onlyFixWithoutBlocks;

    private final TimerUtils timer;

    public Eagle() {
        super("Legit Scaffold", "Also called 'Eagle'");

        this.addSetting(this.blocksOnly = new ToggleSetting("Blocks Only", true));
        this.addSetting(this.backwardsOnly = new ToggleSetting("Backwards Only", "Only works when walking Back", true));
        this.addSetting(this.fixForward = new ToggleSetting("Fix forward", "Unsneak when you Walk forward", false));
        this.addSetting(this.onlyFixWithoutBlocks = new ToggleSetting("Only fix without blocks", true));

        this.timer = new TimerUtils();
    }

    @Override
    public void tickUpdate() {
        if (PlayerUtils.canLegitWork()) {
            if (this.blocksOnly.isEnabled() && !PlayerUtils.holdingBlock()) {
                return;
            }

            if (this.backwardsOnly.isEnabled() && !PlayerUtils.isMovingBackwards()) {
                return;
            }

            if (this.fixForward.isEnabled() && mc.thePlayer.isSneaking() && PlayerUtils.isMovingForward()) {
                if (this.onlyFixWithoutBlocks.isEnabled() && PlayerUtils.holdingBlock()) {
                    return;
                }

                if (this.timer.delay(500)) {
                    if (mc.thePlayer.isSneaking() && PlayerUtils.isMovingForward()) {
                        this.setShift(false);
                        this.timer.reset();
                    }
                }
            }

            if (PlayerUtils.overAir(1.0) && mc.thePlayer.onGround) {
                this.setShift(true);
            } else if (!PlayerUtils.overAir(1.0) && mc.thePlayer.isSneaking() && mc.thePlayer.onGround) {
                this.setShift(false);
            }
        }
    }

    private void setShift(boolean shift) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), shift);
    }

}
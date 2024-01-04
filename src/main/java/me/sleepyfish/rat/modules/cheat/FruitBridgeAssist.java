package me.sleepyfish.rat.modules.cheat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.TimerUtils;
import me.sleepyfish.rat.utils.misc.PlayerUtils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class FruitBridgeAssist extends Module {

    private final TimerUtils timer;

    public FruitBridgeAssist() {
        super("Fruit Assist", "Assists you in Fruit bridging");

        this.timer = new TimerUtils();
    }

    @Override
    public void tickUpdate() {
        if (!PlayerUtils.canLegitWork() || !PlayerUtils.holdingBlock())
            return;

        if (mc.thePlayer.rotationPitch < 65.0F)
            return;

        if (mc.thePlayer.onGround)
            return;

        if (mc.thePlayer.fallDistance < 0.05F)
            return;

        if (mc.objectMouseOver.sideHit == EnumFacing.UP)
            return;

        if (InputUtils.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (this.timer.cpsTimer(7, 13)) {
                    InputUtils.pressKeybindOnce(mc.gameSettings.keyBindUseItem);
                    this.timer.reset();
                }
            }
        }
    }

}
package me.sleepyfish.rat.modules.cheat;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.TimerUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.modules.settings.impl.SliderSetting;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

import java.util.Random;

public class Clicker extends Module {

    private final ToggleSetting right;
    private final SliderSetting rightCpsMin;
    private final SliderSetting rightCpsMax;
    private final ToggleSetting onlyWhileLooking;
    private final ToggleSetting onlyWhileHoldingBlock;

    private final ToggleSetting left;
    private final SliderSetting leftCpsMax;
    private final SliderSetting leftCpsMin;
    private final ToggleSetting onlyWhileTargeting;
    private final ToggleSetting checkBlocks;
    private final ToggleSetting weaponOnly;
    private final ToggleSetting hitselect;

    private final TimerUtils Ltimer;
    private final TimerUtils Rtimer;

    public Clicker() {
        super("Clicker", "Clicks for you");

        this.addSetting(this.right = new ToggleSetting("Right", true));
        this.addSetting(this.rightCpsMin = new SliderSetting("Right CPS Min", 9.0, 1.0, 24.0, 1.0));
        this.addSetting(this.rightCpsMax = new SliderSetting("Right CPS Max", 12.0, 2.0, 25.0, 1.0));
        this.addSetting(this.onlyWhileLooking = new ToggleSetting("Only while looking", true));
        this.addSetting(this.onlyWhileHoldingBlock = new ToggleSetting("Only block in hand", true));

        this.addSetting(this.left = new ToggleSetting("Left", true));
        this.addSetting(this.leftCpsMin = new SliderSetting("Left CPS Min", 9.0, 1.0, 24.0, 1.0));
        this.addSetting(this.leftCpsMax = new SliderSetting("Left CPS Max", 12.0, 2.0, 25.0, 1.0));
        this.addSetting(this.onlyWhileTargeting = new ToggleSetting("Only while targeting", false));
        this.addSetting(this.checkBlocks = new ToggleSetting("Check block Break", "Allows you to break blocks", true));
        this.addSetting(this.weaponOnly = new ToggleSetting("Weapon only", true));
        this.addSetting(this.hitselect = new ToggleSetting("Hit select", "Get insane combos with this", false));

        this.Ltimer = new TimerUtils();
        this.Rtimer = new TimerUtils();
    }

    @Override
    public void tickUpdate() {
        if (!PlayerUtils.canLegitWork())
            return;

        if (this.left.isEnabled() && InputUtils.isButtonDown(InputUtils.MOUSE_LEFT)) {
            if (this.onlyWhileTargeting.isEnabled() && mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
                return;

            if (this.weaponOnly.isEnabled() && !PlayerUtils.holdingWeapon())
                return;

            if (this.checkBlocks.isEnabled()) {
                BlockPos blockPos = mc.objectMouseOver.getBlockPos();

                if (blockPos != null) {
                    Block block = PlayerUtils.getBlock(blockPos);
                    if (block != Blocks.air && block != Blocks.lava && block != Blocks.water && block != Blocks.flowing_lava && block != Blocks.flowing_water)
                        return;
                }
            }

            if (!this.hitselect.isEnabled()) {
                if (this.Ltimer.cpsTimer((int) MathHelper.getRandomDoubleInRange(new Random(), 6, 9), (int) MathHelper.getRandomDoubleInRange(new Random(), 11, 14))) {
                //if (this.Ltimer.cpsTimer(this.leftCpsMin.getValueInt(), this.leftCpsMax.getValueInt())) {
                    InputUtils.pressKeybindOnce(mc.gameSettings.keyBindAttack);
                    this.Ltimer.reset();
                }
            } else if (this.Ltimer.cpsTimer(1, 2)) {
                InputUtils.pressKeybindOnce(mc.gameSettings.keyBindAttack);
                this.Ltimer.reset();
            }
        }

        if (this.right.isEnabled() && InputUtils.isButtonDown(InputUtils.MOUSE_RIGHT)) {
            if (this.onlyWhileLooking.isEnabled() && mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
                return;

            if (this.onlyWhileHoldingBlock.isEnabled() && !PlayerUtils.holdingBlock())
                return;

            if (this.Rtimer.cpsTimer(this.rightCpsMin.getValueInt(), this.rightCpsMax.getValueInt())) {
                InputUtils.pressKeybindOnce(mc.gameSettings.keyBindUseItem);
                this.Rtimer.reset();
            }
        }
    }

}
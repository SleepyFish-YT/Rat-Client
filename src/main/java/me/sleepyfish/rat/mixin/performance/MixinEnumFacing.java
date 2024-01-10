package me.sleepyfish.rat.mixin.performance;

import net.minecraft.util.EnumFacing;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This class contains code from "xCollateral/VulkanMod"
 * @author SleepyFish 2024
 */
@Mixin(EnumFacing.class)
public class MixinEnumFacing {

    @Shadow @Final
    public static EnumFacing[] VALUES;

    @Shadow @Final
    private int opposite;

    /**
     * @author xCollateral/VulkanMod
     * @reason better
     */
    @Overwrite
    public EnumFacing getOpposite() {
        return VALUES[this.opposite];
    }

}
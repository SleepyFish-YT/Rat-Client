package me.sleepyfish.rat.utils.misc;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class PlayerUtils {

    public static boolean canLegitWork() {
        return MinecraftUtils.mc.thePlayer != null
                && MinecraftUtils.mc.inGameHasFocus
                && MinecraftUtils.mc.thePlayer.isEntityAlive()
                && MinecraftUtils.mc.theWorld != null
                && MinecraftUtils.mc.currentScreen == null;
    }

    public static ItemStack getCurrentItem() {
        return MinecraftUtils.mc.thePlayer.getCurrentEquippedItem() == null
                ? new ItemStack(Blocks.air) : MinecraftUtils.mc.thePlayer.getCurrentEquippedItem();
    }

    public static boolean holdingBlock() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        String itemName = getCurrentItem().getDisplayName().toLowerCase();
        return item instanceof ItemBlock && !itemName.equals("tnt") && !itemName.contains("sand");
    }

    public static boolean holdingWeapon() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        return item instanceof ItemSword || item instanceof ItemAxe;
    }

    public static boolean overAir(double distance) {
        return MinecraftUtils.mc.theWorld.isAirBlock(
                new BlockPos(
                        MathHelper.floor_double(MinecraftUtils.mc.thePlayer.posX),
                        MathHelper.floor_double(MinecraftUtils.mc.thePlayer.posY - distance),
                        MathHelper.floor_double(MinecraftUtils.mc.thePlayer.posZ))
        );
    }

    public static boolean isMoving() {
        return MinecraftUtils.mc.thePlayer.moveStrafing != 0 || MinecraftUtils.mc.thePlayer.moveForward != 0;
    }

    public static boolean isMovingForward() {
        return MinecraftUtils.mc.thePlayer.moveForward > 0.0F;
    }

    public static boolean isMovingBackwards() {
        return MinecraftUtils.mc.thePlayer.moveForward < 0.0F;
    }

    public static Block getBlock(BlockPos blockPos) {
        return MinecraftUtils.mc.theWorld.getBlockState(blockPos).getBlock();
    }

}
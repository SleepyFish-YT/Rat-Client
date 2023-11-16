package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.event.*;
import me.sleepyfish.rat.event.function.RatEvent;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Performance extends Module {

    // This is not skidded it took me about 30 minutes, like 60 fps more

    private final ToggleSetting entityBehind;
    private final ToggleSetting blockSide;
    private final ToggleSetting smallerMcChecks;

    public Performance() {
        super("Performance", "Checks if objects can be seen better then minecraft");

        this.addSetting(entityBehind = new ToggleSetting("Cancel entities", "Cancel rendering entities that cant be seen", true));
        this.addSetting(blockSide = new ToggleSetting("Cancel block sides", "Cancel rendering block sides that cant be seen", true));
        this.addSetting(smallerMcChecks = new ToggleSetting("Smaller mc checks", true));

        this.toggle();
    }

    @RatEvent
    public void renderBlockSide(EventBlockShouldSideBeRendered e) {
        if (this.blockSide.isEnabled()) {
            if (PlayerUtils.getBlock(e.pos) != Blocks.air && PlayerUtils.getBlock(e.pos).isFullBlock()) {
                if (this.isBlockBehindPlayer(e.pos)) {
                    e.setCancelled(true);
                    return;
                }

                if (e.facing == EnumFacing.UP) {
                    Block pos1 = PlayerUtils.getBlock(e.pos.up());
                    if (pos1 != Blocks.air && pos1.isFullBlock())
                        e.setCancelled(true);
                }

                if (e.facing == EnumFacing.DOWN) {
                    Block pos1 = PlayerUtils.getBlock(e.pos.down());
                    if (pos1 != Blocks.air && pos1.isFullBlock())
                        e.setCancelled(true);
                }

                if (e.facing == EnumFacing.WEST) {
                    Block pos1 = PlayerUtils.getBlock(e.pos.west());
                    if (pos1 != Blocks.air && pos1.isFullBlock())
                        e.setCancelled(true);
                }

                if (e.facing == EnumFacing.NORTH) {
                    Block pos1 = PlayerUtils.getBlock(e.pos.north());
                    if (pos1 != Blocks.air && pos1.isFullBlock())
                        e.setCancelled(true);
                }
            }
        }
    }

    @RatEvent
    public void onHitboxRender(EventRenderHitbox e) {
        if (this.entityBehind.isEnabled()) {
            if (this.isEntityBehindPlayer(e.entity)) {
                e.setCancelled(true);
            }
        }
    }

    @RatEvent
    public void onRenderEntity(EventRenderEntity e) {
        if (this.entityBehind.isEnabled()) {
            if (isEntityBehindPlayer(e.entity)) {
                e.setCancelled(true);
            }
        }
    }

    @RatEvent
    public void onPotionUpdate(EventPotionUpdate e) {
        if (this.smallerMcChecks.isEnabled()) {
            if (e.potioneffect == null) {
                e.setCancelled(true);
            }
        }
    }

    private boolean isEntityBehindPlayer(Entity target) {
        Vec3 direction = mc.thePlayer.getLookVec().normalize();
        Vec3 targetToPlayer = target.getPositionVector().subtract(mc.thePlayer.getPositionVector()).normalize();

        return direction.dotProduct(targetToPlayer) < 0.0;
    }

    private boolean isBlockBehindPlayer(BlockPos target) {
        Vec3 playerToBlock = new Vec3(target.getX() - mc.thePlayer.posX, target.getY() - mc.thePlayer.posY, target.getZ() - mc.thePlayer.posZ);

        Vec3 lookDirection = mc.thePlayer.getLookVec();

        playerToBlock = playerToBlock.normalize();
        lookDirection = lookDirection.normalize();

        return playerToBlock.dotProduct(lookDirection) < 0.0;
    }

}
package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.event.*;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.PlayerUtils;
import me.sleepyfish.rat.event.function.RatEvent;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.util.Vec3;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class Performance extends Module {

    private final ToggleSetting entityBehind;
    public static ToggleSetting effectsBehind;
    private final ToggleSetting itemsBehind;
    private final ToggleSetting blockSide;
    private final ToggleSetting smallerMcChecks;

    public Performance() {
        super("Performance", "Checks if objects can be seen better then minecraft");

        this.addSetting(this.entityBehind = new ToggleSetting("Cancel Entities", "Cancel rendering entities that cant be seen", true));
        this.addSetting(effectsBehind = new ToggleSetting("Cancel Effects", "Cancel rendering effects that cant be seen", true));
        this.addSetting(this.itemsBehind = new ToggleSetting("Cancel Items", "Cancel rendering items that cant be seen", true));
        this.addSetting(this.blockSide = new ToggleSetting("Cancel block sides", "Cancel rendering block sides that cant be seen", true));
        this.addSetting(this.smallerMcChecks = new ToggleSetting("Smaller mc checks", true));

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
            if (this.entityCantBeSeen(e.entity)) {
                e.setCancelled(true);
            }
        }
    }

    @RatEvent
    public void onRenderEntity(EventRenderEntity e) {
        if (this.entityBehind.isEnabled()) {
            if (this.entityCantBeSeen(e.entity)) {
                e.setCancelled(true);
            }
        }
    }

    @RatEvent
    public void onRenderEffectEntity(EventRenderEntityEffect e) {
        if (effectsBehind.isEnabled()) {
            if (this.entityCantBeSeen(e.entity)) {
                e.setCancelled(true);
            }
        }
    }

    @RatEvent
    public void onRenderEffect(EventRenderParticle e) {
        if (effectsBehind.isEnabled()) {
            if (this.entityCantBeSeen(e.entity)) {
                e.setCancelled(true);
            }
        }
    }

    @RatEvent
    public void onRenderItemOnGround(EventRenderItemOnGround e) {
        if (this.itemsBehind.isEnabled()) {
            if (this.entityCantBeSeen(e.itemEntity)) {
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

    private boolean entityCantBeSeen(Entity target) {
        if (mc.gameSettings.thirdPersonView != 1)
            return false;

        Vec3 direction = MinecraftUtils.mc.thePlayer.getLookVec();
        Vec3 targetToPlayer = target.getPositionVector().subtract(MinecraftUtils.mc.thePlayer.getPositionVector());

        direction = direction.normalize();
        targetToPlayer = targetToPlayer.normalize();

        return (direction.dotProduct(targetToPlayer) < 0.0) ;
    }

    public static boolean isBlockBehindPlayer(BlockPos target) {
        Vec3 playerToBlock = new Vec3 (
                target.getX() - MinecraftUtils.mc.thePlayer.posX,
                target.getY() - MinecraftUtils.mc.thePlayer.posY,
                target.getZ() - MinecraftUtils.mc.thePlayer.posZ
        ).normalize();

        Vec3 playerForward = MinecraftUtils.mc.thePlayer.getLookVec();
        playerForward = playerForward.normalize();

        return playerToBlock.dotProduct(playerForward) > 0.5;
    }

}
package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class EventBlockShouldSideBeRendered extends Event {

    public IBlockAccess access;
    public BlockPos pos;
    public EnumFacing facing;

    public EventBlockShouldSideBeRendered(IBlockAccess access, BlockPos pos, EnumFacing facing) {
        this.access = access;
        this.pos = pos;
        this.facing = facing;
    }

}
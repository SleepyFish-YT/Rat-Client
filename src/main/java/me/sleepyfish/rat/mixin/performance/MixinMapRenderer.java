package me.sleepyfish.rat.mixin.performance;

import me.sleepyfish.rat.utils.interfaces.IBufferBuilder;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.WorldRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import org.lwjgl.MemoryUtil;

import java.nio.ByteBuffer;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This contains code from "RaphiMC/ImmediatelyFast"
 * @author SleepyFish 2024
 */
@Mixin(WorldRenderer.class)
public class MixinMapRenderer implements IBufferBuilder {

    @Shadow
    private ByteBuffer byteBuffer;

    @Override
    public boolean isReleased() {
        return this.byteBuffer == null;
    }

    @Override
    public void release() {
        if (!this.isReleased()) {
            GLAllocation.deleteDisplayLists((int) MemoryUtil.getAddress0(this.byteBuffer));
            this.byteBuffer = null;
        }
    }

}
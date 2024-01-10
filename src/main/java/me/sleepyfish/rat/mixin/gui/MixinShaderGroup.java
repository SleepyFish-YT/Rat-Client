package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.utils.interfaces.IMixinShaderGroup;

import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(ShaderGroup.class)
public abstract class MixinShaderGroup implements IMixinShaderGroup {

    @Override
    @Accessor
    public abstract List<Shader> getListShaders();

}
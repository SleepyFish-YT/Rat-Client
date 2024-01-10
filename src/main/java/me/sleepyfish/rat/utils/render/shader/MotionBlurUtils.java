package me.sleepyfish.rat.utils.render.shader;

import me.sleepyfish.rat.modules.impl.MotionBlur;
import me.sleepyfish.rat.utils.interfaces.IMixinShaderGroup;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * This class contains code of Soar shit skid client made by a retard named eldodebug
 * @author SleepyFish 2024
 */
public class MotionBlurUtils {

    private static final ResourceLocation location = new ResourceLocation("minecraft:shaders/post/motion_blur.json");
    public  static final MotionBlurUtils instance = new MotionBlurUtils();
    private final Minecraft mc = Minecraft.getMinecraft();
    private ShaderGroup shader;
    private float shaderBlur;

    public ShaderGroup getShader() {
        if (shader == null) {
            shaderBlur = Float.NaN;

            try {
                shader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), location);
                shader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            } catch (Exception e) {
                return null;
            }
        }

        if (shaderBlur != getBlurFactor()) {
            ((IMixinShaderGroup) shader).getListShaders().forEach((shader) -> {
                final ShaderUniform blendFactorUniform = shader.getShaderManager().getShaderUniform("BlurFactor");

                if (blendFactorUniform != null)
                    blendFactorUniform.set(getBlurFactor());
            });

            shaderBlur = getBlurFactor();
        }

        return shader;
    }

    public float getBlurFactor() {
        return MotionBlur.motionBlurValue;
    }

}
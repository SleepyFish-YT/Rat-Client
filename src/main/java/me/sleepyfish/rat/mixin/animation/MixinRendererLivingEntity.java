package me.sleepyfish.rat.mixin.animation;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */

/*
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Logger;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected ModelBase mainModel;

    @Shadow
    protected abstract float getSwingProgress(T p_getSwingProgress_1_, float p_getSwingProgress_2_);

    @Shadow
    protected abstract float interpolateRotation(float p_interpolateRotation_1_, float p_interpolateRotation_2_, float p_interpolateRotation_3_);

    @Shadow
    protected abstract void renderLivingAt(T p_renderLivingAt_1_, double p_renderLivingAt_2_, double p_renderLivingAt_4_, double p_renderLivingAt_4_2);

    @Shadow
    protected abstract float handleRotationFloat(T p_handleRotationFloat_1_, float p_handleRotationFloat_2_);

    @Shadow
    protected abstract void rotateCorpse(T p_rotateCorpse_1_, float p_rotateCorpse_2_, float p_rotateCorpse_3_, float p_rotateCorpse_4_);

    @Shadow
    protected abstract void preRenderCallback(T p_preRenderCallback_1_, float p_preRenderCallback_2_);

    @Shadow
    protected boolean renderOutlines;

    @Shadow
    protected abstract boolean setScoreTeamColor(T p_setScoreTeamColor_1_);

    @Shadow
    protected abstract void renderModel(T p_renderModel_1_, float p_renderModel_2_, float p_renderModel_3_, float p_renderModel_4_, float p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_);

    @Shadow
    protected abstract void unsetScoreTeamColor();

    @Shadow
    protected abstract void unsetBrightness();

    @Shadow
    protected abstract boolean setDoRenderBrightness(T p_setDoRenderBrightness_1_, float p_setDoRenderBrightness_2_);

    @Shadow
    @Final
    private static Logger logger;

    @Shadow
    protected abstract void renderLayers(T p_renderLayers_1_, float p_renderLayers_2_, float p_renderLayers_3_, float p_renderLayers_4_, float p_renderLayers_5_, float p_renderLayers_6_, float p_renderLayers_7_, float p_renderLayers_8_);

    public MixinRendererLivingEntity(RenderManager renderManager, ModelBase base, float shadowSize) {
        super(renderManager);
        this.mainModel = base;
        this.shadowSize = shadowSize;
    }

    /*
     * @author sleepy
     * @reason modding animations
     *
    @Overwrite
    public void doRender(T entity, double posX, double posY, double posZ, float p_doRender_8_, float p_doRender_9_) {

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, p_doRender_9_);
        boolean shouldSit = entity.isRiding() && entity.ridingEntity != null && entity.ridingEntity.shouldRiderSit();
        this.mainModel.isRiding = shouldSit;
        this.mainModel.isChild = entity.isChild();

        try {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, p_doRender_9_);
            float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, p_doRender_9_);
            float f2 = f1 - f;
            float f8;

            if (shouldSit && entity.ridingEntity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) entity.ridingEntity;
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, p_doRender_9_);
                f2 = f1 - f;
                f8 = MathHelper.wrapAngleTo180_float(f2);

                if (f8 < -85.0F)
                    f8 = -85.0F;

                if (f8 >= 85.0F)
                    f8 = 85.0F;

                f = f1 - f8;
                if (f8 * f8 > 2500.0F)
                    f += f8 * 0.2F;
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_doRender_9_;
            this.renderLivingAt(entity, posX, posY, posZ);
            f8 = this.handleRotationFloat(entity, p_doRender_9_);
            this.rotateCorpse(entity, f8, f, p_doRender_9_);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(entity, p_doRender_9_);

            GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
            float f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * p_doRender_9_;
            float f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - p_doRender_9_);

            if (entity.isChild())
                f6 *= 3.0F;

            if (f5 > 1.0F)
                f5 = 1.0F;

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, p_doRender_9_);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, 0.0625F, entity);
            boolean flag;
            if (this.renderOutlines) {
                flag = this.setScoreTeamColor(entity);
                this.renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
                if (flag) {
                    this.unsetScoreTeamColor();
                }
            } else {
                flag = this.setDoRenderBrightness(entity, p_doRender_9_);
                this.renderModel(entity, f6, f5, f8, f2, f7, 0.0625F);
                if (flag) {
                    this.unsetBrightness();
                }

                GlStateManager.depthMask(true);
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
                    this.renderLayers(entity, f6, f5, p_doRender_9_, f8, f2, f7, 0.0625F);
                }
            }

            GlStateManager.disableRescaleNormal();
        } catch (Exception var20) {
            logger.error("Couldn't render entity", var20);
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        if (!this.renderOutlines) {
            super.doRender(entity, posX, posY, posZ, p_doRender_8_, p_doRender_9_);
        }
    }


    @Override
    protected ResourceLocation getEntityTexture(T t) {
        return null;
    }

}
*/
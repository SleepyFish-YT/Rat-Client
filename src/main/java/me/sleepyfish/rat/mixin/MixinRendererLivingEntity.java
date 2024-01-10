package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.impl.Freelook;
import me.sleepyfish.rat.modules.impl.Nametags;
import me.sleepyfish.rat.modules.impl.Performance;
import me.sleepyfish.rat.utils.misc.RatUserUtil;
import me.sleepyfish.rat.event.EventRenderEntity;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity <T extends EntityLivingBase> extends Render<T> {

    protected MixinRendererLivingEntity(RenderManager m) {
        super(m);
    }

    @Redirect(method = "canRenderName*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    public Entity canRenderName(RenderManager renderManager) {
        if (Rat.instance.moduleFields.Nametags.isEnabled())
            return null;

        return renderManager.livingPlayer;
    }

    @Inject(method = "doRender*", at = @At("HEAD"), cancellable = true)
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        final EventRenderEntity event = new EventRenderEntity(entity, partialTicks);
        event.call();

        if (Rat.instance.moduleFields.Performance.isEnabled()) {
            if (Performance.effectsBehind.isEnabled())
                event.setCancelled(true);
        }

        if (event.isCancelled())
            ci.cancel();
    }

    /**
     * @author sleepy
     * @reason modding name rendering
     */
    @Overwrite
    public void renderName(T entity, double x, double y, double z) {
        if (Nametags.removeAll.isEnabled()) {
            if (Rat.instance.moduleFields.Nametags.isEnabled())
                return;
        }

        if (entity == MinecraftUtils.mc.thePlayer && MinecraftUtils.mc.currentScreen == Rat.instance.guiManager.getRatGuiCosmetic())
            return;

        short rotateYaw = (short) this.renderManager.playerViewY;
        short rotatePitch = (short) this.renderManager.playerViewX;

        final Freelook freelook = Rat.instance.moduleFields.Freelook;

        if (freelook.isActive()) {
            rotateYaw = (short) freelook.getRots()[0];
            rotatePitch = (short) freelook.getRots()[1];
        }

        if (this.canRenderName(entity)) {
            final boolean renderBG = Nametags.renderBg.isEnabled() || !Rat.instance.moduleFields.Nametags.isEnabled();

            final Tessellator tess = Tessellator.getInstance();
            final WorldRenderer renderer = tess.getWorldRenderer();

            GlStateManager.alphaFunc(516, 0.1F);
            final FontRenderer font = this.getFontRendererFromRenderManager();

            String s = entity.getDisplayName().getFormattedText();

            final boolean isRatClientUser = entity.isEntityAlive() && RatUserUtil.isRatClientUser(s.toLowerCase());
            if (isRatClientUser)
                s = "   " + s;

            final int i = font.getStringWidth(s) / 2;

            if (!entity.isSneaking()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) x + 0F, (float) y - (entity.isChild() ? (double) (entity.height / 2F) : 0.0) + entity.height + 0.5F, (float) z);
                GL11.glNormal3f(0F, 1F, 0F);
                GlStateManager.rotate(-rotateYaw, 0F, 1F, 0F);
                GlStateManager.rotate(rotatePitch, 1F, 0F, 0F);
                GlStateManager.scale(-0.016666668F * 1.6F, -0.016666668F * 1.6F, 0.016666668F * 1.6F);
                GlStateManager.disableLighting();

                if (renderBG) {
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    GlStateManager.disableTexture2D();

                    renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    renderer.pos(-i - 1, -1, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    renderer.pos(-i - 1, 8, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    renderer.pos(i + 1, 8, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    renderer.pos(i + 1, -1, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    tess.draw();

                    GlStateManager.enableTexture2D();
                    GlStateManager.enableDepth();
                    GlStateManager.disableBlend();
                    GlStateManager.depthMask(true);
                }

                if (isRatClientUser) {
                    font.drawString(s, -i, 0, new Color(0xFF0000).getRGB());
                    RenderUtils.drawImage("/gui/icon", -i - 1, -1, 10, 10, Color.white);
                } else {
                    font.drawString(s, -i, 0, -1);
                }

                GlStateManager.enableLighting();
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();

                GlStateManager.translate((float) x, (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2F : 0F), (float) z);
                GL11.glNormal3f(0F, 1F, 0F);

                GlStateManager.rotate(-rotateYaw, 0F, 1F, 0F);
                GlStateManager.rotate(rotatePitch, 1F, 0F, 0F);
                GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                GlStateManager.translate(0F, 9.374999F, 0F);
                GlStateManager.disableLighting();

                if (renderBG) {
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    GlStateManager.disableTexture2D();

                    renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    renderer.pos((-i - 1), -1, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    renderer.pos((-i - 1), 8, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    renderer.pos((i + 1), 8, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    renderer.pos((i + 1), -1, 0.0).color(0F, 0F, 0F, 0.25F).endVertex();
                    tess.draw();

                    GlStateManager.enableTexture2D();
                    GlStateManager.enableDepth();
                    GlStateManager.disableBlend();
                    GlStateManager.depthMask(true);
                }

                if (isRatClientUser) {
                    font.drawString(s, -i, 0, new Color(0xFF0000).darker().darker().darker().getRGB());
                    RenderUtils.drawImage("/gui/icon", -i - 1, -1, 10, 10, Color.white);
                } else {
                    font.drawString(s, -i, 0, 553648127);
                }

                GlStateManager.enableLighting();
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(T t) {
        return null;
    }

}
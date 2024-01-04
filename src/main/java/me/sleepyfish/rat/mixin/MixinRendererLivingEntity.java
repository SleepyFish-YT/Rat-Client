package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.impl.Freelook;
import me.sleepyfish.rat.modules.impl.Nametags;
import me.sleepyfish.rat.event.EventRenderEntity;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.user.RatUserUtil;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import org.lwjgl.opengl.GL11;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.client.renderer.entity.RendererLivingEntity.NAME_TAG_RANGE;
import static net.minecraft.client.renderer.entity.RendererLivingEntity.NAME_TAG_RANGE_SNEAK;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity <T extends EntityLivingBase> extends Render<T> {

    protected MixinRendererLivingEntity(RenderManager m) {
        super(m);
    }

    @Redirect(method = "canRenderName*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    public Entity canRenderName(RenderManager renderManager) {
        if (Rat.instance.moduleManager.getModByClass(Nametags.class).isEnabled()) {
            return null;
        }

        return renderManager.livingPlayer;
    }

    @Inject(method = "doRender*", at = @At("HEAD"), cancellable = true)
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventRenderEntity event = new EventRenderEntity( (T) entity, partialTicks);
        event.call();

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
            if (Rat.instance.moduleManager.getModByClass(Nametags.class).isEnabled())
                return;
        }

        if (entity == MinecraftUtils.mc.thePlayer && MinecraftUtils.mc.currentScreen == Rat.instance.guiManager.getRatGuiCosmetic())
            return;

        if (this.canRenderName(entity)) {
            float rotateYaw = this.renderManager.playerViewY;
            float rotatePitch = this.renderManager.playerViewX;
            if (Freelook.active) {
                rotateYaw = Freelook.yaw;
                rotatePitch = Freelook.pitch;
            }

            double distanceSqToEntity = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float sneakingState = entity.isSneaking() ? 32 : 64;

            if (distanceSqToEntity < (double) (sneakingState * sneakingState)) {
                Tessellator tess = Tessellator.getInstance();
                WorldRenderer renderer = tess.getWorldRenderer();

                GlStateManager.alphaFunc(516, 0.1F);
                FontRenderer fontrenderer = this.getFontRendererFromRenderManager();

                String s = entity.getDisplayName().getFormattedText();
                boolean isRatClientUser = entity instanceof EntityPlayer && RatUserUtil.isRatClientUser(s.toLowerCase());

                if (isRatClientUser)
                    s = "   " + s;

                int i = fontrenderer.getStringWidth(s) / 2;

                if (sneakingState == 32) {
                    GlStateManager.pushMatrix();

                    GlStateManager.translate((float) x, (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float) z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);

                    GlStateManager.rotate(-rotateYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(rotatePitch, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                    GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                    GlStateManager.disableLighting();

                    if ( (Nametags.renderBg.isEnabled() && Rat.instance.moduleManager.getModByClass(Nametags.class).isEnabled()) || !Rat.instance.moduleManager.getModByClass(Nametags.class).isEnabled() ) {
                        GlStateManager.depthMask(false);
                        GlStateManager.disableDepth();
                        GlStateManager.enableBlend();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                        GlStateManager.disableTexture2D();

                        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                        renderer.pos((-i - 1), -1, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        renderer.pos((-i - 1), 8, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        renderer.pos((i + 1), 8, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        renderer.pos((i + 1), -1, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        tess.draw();

                        GlStateManager.enableTexture2D();
                        GlStateManager.enableDepth();
                        GlStateManager.disableBlend();
                        GlStateManager.depthMask(true);
                    }

                    if (isRatClientUser) {
                        fontrenderer.drawString(s, -i, 0, new Color(0xFF0000).darker().darker().darker().getRGB());
                        RenderUtils.drawImage("/gui/icon", -i - 1, -1, 10, 10, Color.white);
                    } else {
                        fontrenderer.drawString(s, -i, 0, 553648127);
                    }

                    GlStateManager.enableLighting();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                } else {
                    if (distanceSqToEntity < (double) (64 * 64)) {
                        GlStateManager.pushMatrix();
                        GlStateManager.translate((float) x + 0.0F, (float) y - (entity.isChild() ? (double) (entity.height / 2.0F) : 0.0) + entity.height + 0.5F, (float) z);
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(-rotateYaw, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(rotatePitch, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(-0.016666668F * 1.6F, -0.016666668F * 1.6F, 0.016666668F * 1.6F);
                        GlStateManager.disableLighting();

                        if ( (Nametags.renderBg.isEnabled() && Rat.instance.moduleManager.getModByClass(Nametags.class).isEnabled()) || !Rat.instance.moduleManager.getModByClass(Nametags.class).isEnabled() ) {
                            GlStateManager.depthMask(false);
                            GlStateManager.disableDepth();
                            GlStateManager.enableBlend();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                            GlStateManager.disableTexture2D();

                            renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                            renderer.pos((-i - 1), -1, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                            renderer.pos((-i - 1), 8, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                            renderer.pos((i + 1), 8, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                            renderer.pos((i + 1), -1, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                            tess.draw();

                            GlStateManager.enableTexture2D();
                            GlStateManager.enableDepth();
                            GlStateManager.disableBlend();
                            GlStateManager.depthMask(true);
                        }

                        if (isRatClientUser) {
                            fontrenderer.drawString(s, -i, 0, new Color(0xFF0000).getRGB());
                            RenderUtils.drawImage("/gui/icon", -i - 1, -1, 10, 10, Color.white);
                        } else {
                            fontrenderer.drawString(s, -i, 0, -1);
                        }

                        GlStateManager.enableLighting();
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(T t) {
        return null;
    }
}
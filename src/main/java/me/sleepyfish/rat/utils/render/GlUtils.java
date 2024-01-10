package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.util.ChatComponentText;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class GlUtils {

    public static void startScissors() {
        final Framebuffer buffer = MinecraftUtils.mc.getFramebuffer();
        buffer.bindFramebuffer(false);

        if (buffer.depthBuffer > -1) {

            EXTFramebufferObject.glDeleteRenderbuffersEXT(buffer.depthBuffer);
            final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
            EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
            EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, MinecraftUtils.mc.displayWidth, MinecraftUtils.mc.displayHeight);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);

            buffer.depthBuffer = -1;
        }

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 1);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
        GL11.glColorMask(false, false, false, false);
    }

    public static void readScissors(final int ref) {
        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(GL11.GL_EQUAL, ref, 1);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
    }

    public static void endScissors() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    public static void enableSeeThru() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.disableAlpha();
        GlStateManager.disableLighting();
    }

    public static void disableSeeThru() {
        GlStateManager.enableLighting();
        GlStateManager.enableAlpha();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void startTranslate(final float x, final float y) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
    }

    public static void stopTranslate() {
        GlStateManager.popMatrix();
    }

    public static void startScale(final double value) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(value, value, value);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

    public static void drawEntityOnScreen(final EntityLivingBase entity, final int width, final int height, final float mouseX, final float mouseY) {
        GlUtils.disableSeeThru();
        
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2F + 165F, height / 2F + 60F, 50F);
        GlStateManager.scale(-85F, 85F, 85F);
        GlStateManager.rotate(180F, 0F, 0F, 1F);

        final float renderYawOffset = entity.renderYawOffset;
        final float rotationYaw = entity.rotationYaw;
        final float rotationPitch = entity.rotationPitch;
        final float prevRotationYawHead = entity.prevRotationYawHead;
        final float rotationYawHead = entity.rotationYawHead;

        GlStateManager.rotate(135F, 0F, 1F, 0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135F, 0F, 1F, 0F);
        GlStateManager.rotate(-((float)Math.atan(mouseY / 40F)) * 20F, 1F, 0F, 0F);

        entity.renderYawOffset = (float)Math.atan((mouseX / 40F)) * 20F;
        entity.rotationYaw = (float)Math.atan((mouseX / 40F)) * 40F;
        entity.rotationPitch = -((float)Math.atan((mouseY / 40F))) * 20F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;

        GlStateManager.translate(0F, 0F, 0F);

        final RenderManager renderManager = MinecraftUtils.mc.getRenderManager();
        renderManager.setPlayerViewY(180F);
        renderManager.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 1F);

        entity.renderYawOffset = renderYawOffset;
        entity.rotationYaw = rotationYaw;
        entity.rotationPitch = rotationPitch;
        entity.prevRotationYawHead = prevRotationYawHead;
        entity.rotationYawHead = rotationYawHead;

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlUtils.enableSeeThru();
    }

}
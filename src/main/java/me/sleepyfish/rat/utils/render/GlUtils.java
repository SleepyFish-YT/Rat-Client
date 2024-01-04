package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GlUtils {

    public static void checkSetupFBO(Framebuffer framebuffer) {
        if (framebuffer != null)
            if (framebuffer.depthBuffer > -1) {
                setupFBO(framebuffer);
                framebuffer.depthBuffer = -1;
            }
    }

    public static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, MinecraftUtils.mc.displayWidth, MinecraftUtils.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBufferID);
    }

    public static void startScissors() {
        MinecraftUtils.mc.getFramebuffer().bindFramebuffer(false);
        checkSetupFBO(MinecraftUtils.mc.getFramebuffer());
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 1);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
        GL11.glColorMask(false, false, false, false);
    }

    public static void readScissors(int ref) {
        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(GL11.GL_EQUAL, ref, 1);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
    }

    public static void endScissors() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    public static void enableSeeThru() {
        GL11.glDisable(0xB71);
        GlStateManager.disableAlpha();
        GlStateManager.disableLighting();
    }

    public static void disableSeeThru() {
        GlStateManager.enableLighting();
        GlStateManager.enableAlpha();
        GL11.glEnable(0xB71);
    }

    public static void startTranslate(float x, float y) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
    }

    public static void stopTranslate() {
        GlStateManager.popMatrix();
    }

    public static void startScale(double value) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(value, value, value);
    }

    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0F);
        GlStateManager.scale(scale, scale, 1.0F);
        GlStateManager.translate(-x, -y, 0.0F);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

    public static void drawEntityOnScreen(EntityLivingBase entity, int width, int height, float mouseX, float mouseY) {

        GlUtils.disableSeeThru();
        int xPos = width / 2 + 165;
        int yPos = height / 2 + 60;
        int scale = 85;

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos, yPos, 50.0F);
        GlStateManager.scale((-scale), scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        float renderYawOffset = entity.renderYawOffset;
        float rotationYaw = entity.rotationYaw;
        float rotationPitch = entity.rotationPitch;
        float prevRotationYawHead = entity.prevRotationYawHead;
        float rotationYawHead = entity.rotationYawHead;

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);

        entity.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
        entity.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;

        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager renderManager = MinecraftUtils.mc.getRenderManager();

        // Entity ----------------------------
        renderManager.setPlayerViewY(180.0F);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 1F);
        renderManager.setRenderShadow(true);

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

    /*
    public static void drawEntityOnScreen(EntityLivingBase entity, int width, int height) {

        GlUtils.disableSeeThru();
        int xPos = width / 2 + 165;
        int yPos = height / 2 + 60;
        int scale = 85;
        float mouseX = width / 2F + 175 + (-InputUtils.mouseX);
        float mouseY = height / 2F + 30 + (-InputUtils.mouseY);

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos, yPos, 50.0F);
        GlStateManager.scale((-scale), scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        float renderYawOffset = entity.renderYawOffset;
        float rotationYaw = entity.rotationYaw;
        float rotationPitch = entity.rotationPitch;
        float prevRotationYawHead = entity.prevRotationYawHead;
        float rotationYawHead = entity.rotationYawHead;

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);

        entity.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
        entity.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;

        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager renderManager = MinecraftUtils.mc.getRenderManager();

        // Entity ----------------------------
        renderManager.setPlayerViewY(180.0F);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 1F);
        renderManager.setRenderShadow(true);

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
 */
}
package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;

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

    public static void startScale(double value) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(value, value, value);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

}

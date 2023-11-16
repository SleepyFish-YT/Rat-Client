package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.impl.SettingModule;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import org.lwjgl.opengl.GL11;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {

    public static ShaderUtils roundedTexturedShader;
    public static ShaderUtils roundedShader;
    public static ShaderUtils roundedGradientShader;

    public static void drawImage(String image, int x, int y, int width, int height) {
        RenderUtils.drawImage(image, x, y, width, height, Color.white);
    }

    public static void drawImage(String image, int x, int y, int width, int height, Color color) {
        if (image.toLowerCase().startsWith("/gui/icons")) {
            if (SettingModule.disableIcons.isEnabled())
                return;
        }

        if (color == null) {
            GlStateManager.color(1, 1, 1);
        } else {
            ColorUtils.setColorAlpha(color.getRGB());
        }

        MinecraftUtils.mc.getTextureManager().bindTexture(new ResourceLocation(MinecraftUtils.path + image + ".png"));
        GlStateManager.enableBlend();
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
        GlStateManager.disableBlend();
    }

    /**
     * @author SMok Ghost Client by SleepyFish
     * @Modified: modified the customizability
     */
    public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
        RenderUtils.drawRoundCustom(x, y, width, height, radius, color, true, true, true, true);
    }

    /**
     * @author SMok Ghost Client by SleepyFish
     * @Modified: modified the customizability
     */
    public static void drawRoundCustom(float x, float y, float width, float height, float radius, Color c, boolean leftTop, boolean leftBottom, boolean rightBottom, boolean rightTop) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        x *= 2D;
        y *= 2D;
        width *= 2D;
        height *= 2D;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        ColorUtils.clearColor();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        ColorUtils.setColor(c.getRGB());
        int i;

        if (leftTop)
            for (i = 0; i <= 90; i += 3)
                GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180D) * radius * -1D, y + radius + Math.cos(i * Math.PI / 180D) * radius * -1D);
        else GL11.glVertex2d(x, y);

        if (leftBottom)
            for (i = 90; i <= 180; i += 3)
                GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180D) * radius * -1D, y + height - radius + Math.cos(i * Math.PI / 180D) * radius * -1D);
        else GL11.glVertex2d(x, y + height);

        if (rightBottom)
            for (i = 0; i <= 90; i += 3)
                GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180D) * radius, y + height - radius + Math.cos(i * Math.PI / 180D) * radius);
        else GL11.glVertex2d(x + width, y + height);

        if (rightTop)
            for (i = 90; i <= 180; i += 3)
                GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180D) * radius, y + radius + Math.cos(i * Math.PI / 180D) * radius);
        else GL11.glVertex2d(x + width, y);

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();
        GL11.glScaled(2D, 2D, 2D);
        GL11.glPopAttrib();
        ColorUtils.clearColor();
    }

    public static void drawOutline(float x, float y, float width, float height, float line, float radius, Color color) {
        GlStateManager.enableBlend();
        ColorUtils.setColor(-1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        width *= 2.0D;
        height *= 2.0D;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        ColorUtils.setColor(color.getRGB());
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glLineWidth(line);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);

        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, (y + height) - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);

        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d((x + width) - radius + Math.sin(i * Math.PI / 180.0D) * radius, (y + height) - radius + Math.cos(i * Math.PI / 180.0D) * radius);

        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d((x + width) - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);

        GL11.glEnd();
        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_FLAT);
        ColorUtils.setColor(-1);
    }

    public static void drawGradientRoundLR(float x, float y, float width, float height, float radius, Color color1, Color color2) {
        drawGradientRound(x, y, width, height, radius, color1, color2, color2, color1);
    }

    public static void drawRoundTextured(float x, float y, float width, float height, float radius, float alpha) {
        ColorUtils.clearColor();
        roundedTexturedShader.init();
        roundedTexturedShader.setUniform("textureIn", 0);
        setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
        roundedTexturedShader.setUniformf("alpha", alpha);
        ShaderUtils.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundedTexturedShader.unload();
        GlStateManager.disableBlend();
    }

    private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtils roundedTexturedShader) {
        ScaledResolution sr = new ScaledResolution(MinecraftUtils.mc);
        roundedTexturedShader.setUniformf("location", x * sr.getScaleFactor(),
                (MinecraftUtils.mc.displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        roundedTexturedShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * sr.getScaleFactor());
    }

    public static void drawScaledCustomSizeModalRect(double x, double y, float u, float v, int uWidth, int vHeight, double width, double height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getWorldRenderer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, (y + height), 0.0D).tex((u * f), ((v + (float)vHeight) * f1)).endVertex();
        bufferbuilder.pos((x + width), (y + height), 0.0D).tex(((u + (float)uWidth) * f), ((v + (float)vHeight) * f1)).endVertex();
        bufferbuilder.pos((x + width), y, 0.0D).tex(((u + (float)uWidth) * f), (v * f1)).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        ColorUtils.clearColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        roundedGradientShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
        roundedGradientShader.setUniformf("color1", topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f, topLeft.getAlpha() / 255f);
        roundedGradientShader.setUniformf("color2", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f, bottomRight.getAlpha() / 255f);
        roundedGradientShader.setUniformf("color3", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f, bottomLeft.getAlpha() / 255f);
        roundedGradientShader.setUniformf("color4", topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f, topRight.getAlpha() / 255f);
        ShaderUtils.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundedGradientShader.unload();
        GlStateManager.disableBlend();
    }

}
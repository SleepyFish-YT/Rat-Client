package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.modules.settings.SettingModule;
import me.sleepyfish.rat.utils.render.shader.ShaderUtils;

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

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class RenderUtils {

    public final static ShaderUtils roundedShader = new ShaderUtils(MinecraftUtils.resourcePath + "/shaders/roundedRect.frag");
    public final static ShaderUtils roundTextedShader = new ShaderUtils(MinecraftUtils.resourcePath + "/shaders/roundRectTextured.frag");
    public final static ShaderUtils roundedGrdntShader = new ShaderUtils(MinecraftUtils.resourcePath + "/shaders/roundedRectGradient.frag");

    public static void drawImage(final String image, final int x, final int y, final int width, final int height) {
        RenderUtils.drawImage(image, x, y, width, height, Color.white);
    }

    public static void drawImage(String image, final int x, final int y, final int width, final int height, final Color color) {
        if (Rat.instance.isDecember) {
            if (image.startsWith("/gui/icon"))
                image = "/gui/icon_dec";
        }

        if (image.toLowerCase().startsWith("/gui/icon_text")) {
            if (!SettingModule.guiIcons.isEnabled())
                return;
        }
        
        if (image.toLowerCase().startsWith("/gui/icons")) {
            if (SettingModule.disableIcons.isEnabled())
                return;
        }

        if (color == null) {
            GlStateManager.color(1, 1, 1);
        } else {
            ColorUtils.setColorAlpha(color.getRGB());
        }

        final ResourceLocation resource = new ResourceLocation(MinecraftUtils.resourcePath + image + ".png");
        MinecraftUtils.mc.getTextureManager().bindTexture(resource);
        GlStateManager.enableBlend();
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0F, 0F, width, height, width, height);
        GlStateManager.disableBlend();
    }

    /**
     * @author SMok Ghost Client by SleepyFish
     * @note : modified the customizability
     */
    public static void drawRound(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        if (radius == 0) {
           Gui.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), color.getRGB());
        } else {
            RenderUtils.drawRoundCustom(x, y, width, height, radius, color, true, true, true, true);
        }
    }

    /**
     * @author SMok Ghost Client by SleepyFish
     * @note : modified the customizability
     */
    public static void drawRoundCustom(float x, float y, float width, float height, final float radius, final Color c, final boolean leftTop, final boolean leftBottom, final boolean rightBottom, final boolean rightTop) {
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

        final double calcX = x + width - radius;
        final double calcY = y + height - radius;
        short i;

        if (leftTop)
            for (i = 0; i <= 90; i += 3)
                GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180D) * radius * -1D, y + radius + Math.cos(i * Math.PI / 180D) * radius * -1D);
        else GL11.glVertex2d(x, y);

        if (leftBottom)
            for (i = 90; i <= 180; i += 3)
                GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180D) * radius * -1D, calcY + Math.cos(i * Math.PI / 180D) * radius * -1D);
        else GL11.glVertex2d(x, y + height);

        if (rightBottom)
            for (i = 0; i <= 90; i += 3)
                GL11.glVertex2d(calcX + Math.sin(i * Math.PI / 180D) * radius, calcY + Math.cos(i * Math.PI / 180D) * radius);
        else GL11.glVertex2d(x + width, y + height);

        if (rightTop)
            for (i = 90; i <= 180; i += 3)
                GL11.glVertex2d(calcX + Math.sin(i * Math.PI / 180D) * radius, y + radius + Math.cos(i * Math.PI / 180D) * radius);
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

    public static void drawOutline(float x, float y, float width, float height, final float line, final float radius, final Color color) {
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

        final double calcX = x + width - radius;
        final double calcY = y + height - radius;
        short i;

        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);

        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, calcY + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);

        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(calcX + Math.sin(i * Math.PI / 180.0D) * radius, calcY + Math.cos(i * Math.PI / 180.0D) * radius);

        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(calcX + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);

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

    public static void drawGradientRoundLR(final float x, final float y, final float width, final float height, final float radius, final Color color1, final Color color2) {
        drawGradientRound(x, y, width, height, radius, color1, color2, color2, color1);
    }

    public static void drawRoundTextured(final float x, final float y, final float width, final float height, final float radius, final float alpha) {
        GlStateManager.enableBlend();
        ColorUtils.clearColor();

        roundTextedShader.init();
        roundTextedShader.setUniform("textureIn", 0);
        setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
        roundTextedShader.setUniformf("alpha", alpha);
        ShaderUtils.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundTextedShader.unload();

        GlStateManager.disableBlend();
    }

    private static void setupRoundedRectUniforms(final float x, final float y, final float width, final float height, final float radius, final ShaderUtils roundedTexturedShader) {
        final ScaledResolution sr = MinecraftUtils.res;
        roundedTexturedShader.setUniformf("location", x * sr.getScaleFactor(),
                (MinecraftUtils.mc.displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        roundedTexturedShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * sr.getScaleFactor());
    }

    public static void drawGradientRound(final float x, final float y, final float width, final float height, final float radius, final Color bottomLeft, final Color topLeft, final Color bottomRight, final Color topRight) {
        ColorUtils.clearColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        roundedGrdntShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, roundedGrdntShader);
        roundedGrdntShader.setUniformf("color1", topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f, topLeft.getAlpha() / 255f);
        roundedGrdntShader.setUniformf("color2", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f, bottomRight.getAlpha() / 255f);
        roundedGrdntShader.setUniformf("color3", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f, bottomLeft.getAlpha() / 255f);
        roundedGrdntShader.setUniformf("color4", topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f, topRight.getAlpha() / 255f);
        ShaderUtils.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundedGrdntShader.unload();

        GlStateManager.disableBlend();
    }

}
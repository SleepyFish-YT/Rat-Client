package me.sleepyfish.rat.utils.render.font;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.GlUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;

import org.lwjgl.opengl.GL11;

import java.awt.Font;
import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class MinecraftFontRenderer extends CFont {

    private final CFont.CharData[] boldChars = new CFont.CharData[256];
    private final CFont.CharData[] italicChars = new CFont.CharData[256];
    private final CFont.CharData[] boldItalicChars = new CFont.CharData[256];

    final String colorcodeIdentifiers = "0123456789abcdefklmnor";
    DynamicTexture texBold;
    DynamicTexture texItalic;
    DynamicTexture texItalicBold;
    int[] colorCode = new int[32];

    public MinecraftFontRenderer(Font font) {
        super(font, true, true);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public int drawString(String text, double x, double y, Color color) {
        return (int) this.drawString(text, x, y, color, false, 8.3F, false);
    }

    public float drawString(String text, double x, double y, Color color, boolean shadow, float kerning, boolean smooth) {
        final ScaledResolution sr = MinecraftUtils.res;

        if (text == null) {
            return 0F;
        } else {
            int co = color.getRGB();
            if (shadow) {
                co = (color.getRGB() & 16579836) >> 2 | color.getRGB() & 0xFF000000;
            }

            FontUtils.init();
            CFont.CharData[] currentData = this.charData;
            x = (x - 1.0) * (double) sr.getScaleFactor();
            y = (y - 3.0) * (double) ((float) sr.getScaleFactor()) - 0.2;
            GL11.glPushMatrix();
            GL11.glScaled(1.0 / (double) sr.getScaleFactor(), 1.0 / (double) sr.getScaleFactor(), 1.0 / (double) sr.getScaleFactor());
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            ColorUtils.setColor(new Color(co).getRGB());
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.tex.getGlTextureId());
            GL11.glBindTexture(3553, this.tex.getGlTextureId());
            GlStateManager.enableBlend();

            for (short index = 0; index < text.length(); ++index) {
                char character = text.charAt(index);
                if (character == 167) {
                    int colorIndex = 21;

                    try {
                        colorIndex = this.colorcodeIdentifiers.indexOf(text.charAt(index + 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (colorIndex < 16) {
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        if (colorIndex < 0) {
                            colorIndex = 15;
                        }

                        if (shadow) {
                            colorIndex += 16;
                        }

                        ColorUtils.setColor(new Color(this.colorCode[colorIndex]).getRGB());
                    } else {
                        ColorUtils.setColor(color.getRGB());
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                    }

                    ++index;
                } else if (character < currentData.length) {
                    this.drawLetter(x, y, currentData, false, false, character);
                    x += ((float) currentData[character].width - kerning + (float) this.charOffset);
                }
            }

            GlStateManager.disableBlend();
            GL11.glHint(3155, 4352);
            GlUtils.stopScale();
            GlStateManager.color(1F, 1F, 1F, 1F);
            return (float) x / 2F;
        }
    }

    private void drawLetter(double x, double y, CFont.CharData[] currentData, boolean strikethrough, boolean underline, char character) {
        GL11.glBegin(4);
        this.drawChar(currentData, character, (float) x, (float) y);
        GL11.glEnd();
        if (strikethrough) {
            this.drawLine(x, y + (double) (currentData[character].height / 2), x + (double) currentData[character].width - 8.0, y + (double) (currentData[character].height / 2));
        }

        if (underline) {
            this.drawLine(x, y + (double) currentData[character].height - 2.0, x + (double) currentData[character].width - 8.0, y + (double) currentData[character].height - 2.0);
        }
    }

    public double getStringWidth(String text) {
        final ScaledResolution sr = MinecraftUtils.res;

        if (text == null) {
            return 0.0;
        } else {
            float width = 0F;
            CFont.CharData[] currentData = this.charData;

            for (short index = 0; index < text.length(); ++index) {
                char character = text.charAt(index);
                if (character == 167) {
                    ++index;
                } else if (character < currentData.length) {
                    width += (float) currentData[character].width - 8.3F + (float) this.charOffset;
                }
            }

            return (double) width / (double) sr.getScaleFactor();
        }
    }

    public double getHeight() {
        final ScaledResolution sr = MinecraftUtils.res;
        return (double) (this.fontHeight - 8) / (double) sr.getScaleFactor();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    private void drawLine(double x, double y, double xEnd, double yEnd) {
        GL11.glDisable(3553);
        GL11.glLineWidth(1F);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(xEnd, yEnd);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void setupMinecraftColorcodes() {
        for (short index = 0; index < 32; ++index) {
            int noClue = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + noClue;
            int green = (index >> 1 & 1) * 170 + noClue;
            int blue = (index & 1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }

}
package me.sleepyfish.rat.utils.render.font;

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

// Class from SMok Client by SleepyFish
public class MinecraftFontRenderer extends CFont {

    CFont.CharData[] boldChars = new CFont.CharData[256];
    CFont.CharData[] italicChars = new CFont.CharData[256];
    CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    String colorcodeIdentifiers = "0123456789abcdefklmnor";
    DynamicTexture texBold;
    DynamicTexture texItalic;
    DynamicTexture texItalicBold;
    int[] colorCode = new int[32];

    public MinecraftFontRenderer(Font font) {
        super(font, true, true);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public int drawStringWithShadow(String text, double x2, double y2, Color color) {
        float shadowWidth = this.drawString(text, x2 + 0.5, y2 + 0.5, color, true, 8.3F, false);
        return (int) Math.max(shadowWidth, this.drawString(text, x2, y2, color, false, 8.3F, false));
    }

    public int drawString(String text, double x, double y, Color color) {
        return (int) this.drawString(text, x, y, color, false, 8.3F, false);
    }

    public int drawString(String text, double x, double y, Color color, boolean shadow) {
        return (int) this.drawString(text, x, y, color, shadow, 8.3F, false);
    }

    public int drawPassword(String text, double x2, float y2, Color color) {
        return (int) this.drawString(text.replaceAll("\\.", "."), x2, y2, color, false, 8.0F, false);
    }

    public float drawCenteredString(String text, float x2, float y2, Color color) {
        return (float) this.drawString(text, (x2 - (float) (this.getStringWidth(text) / 2.0)), y2, color);
    }

    public void drawCenteredStringWithShadow(String text, float x2, float y2, Color color) {
        this.drawStringWithShadow(text, (x2 - (float) (this.getStringWidth(text) / 2.0)), y2, color);
    }

    public float drawString(String text, double x, double y, Color color, boolean shadow, float kerning, boolean smooth) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (text == null) {
            return 0.0F;
        } else {
            int co = color.getRGB();
            if (shadow) {
                co = (color.getRGB() & 16579836) >> 2 | color.getRGB() & 0xFF000000;
            }

            FontUtils.init();
            CFont.CharData[] currentData = this.charData;
            float alpha = (float) (co >> 24 & 0xFF) / 255.0F;
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

            for (int index = 0; index < text.length(); ++index) {
                char character = text.charAt(index);
                if (character == 167) {
                    int colorIndex = 21;

                    try {
                        colorIndex = this.colorcodeIdentifiers.indexOf(text.charAt(index + 1));
                    } catch (Exception ignored) {
                    }

                    if (colorIndex < 16) {
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
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
                        currentData = this.charData;
                    }

                    ++index;
                } else if (character < currentData.length) {
                    this.drawLetter(x, y, currentData, false, false, character);
                    x += (double) ((float) currentData[character].width - kerning + (float) this.charOffset);
                }
            }

            GlStateManager.disableBlend();
            GL11.glHint(3155, 4352);
            GlUtils.stopScale();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return (float) x / 2.0F;
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
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (text == null) {
            return 0.0;
        } else {
            float width = 0.0F;
            CFont.CharData[] currentData = this.charData;

            for (int index = 0; index < text.length(); ++index) {
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

    public double getStringWidth(String text, float kerning) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (text == null) {
            return 0.0;
        } else {
            float width = 0.0F;
            CFont.CharData[] currentData = this.charData;

            for (int index = 0; index < text.length(); ++index) {
                char c = text.charAt(index);
                if (c == 167) {
                    ++index;
                } else if (c < currentData.length) {
                    width += (float) currentData[c].width - kerning + (float) this.charOffset;
                }
            }

            return (double) width / (double) sr.getScaleFactor();
        }
    }

    public double getHeight() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (double) (this.fontHeight - 8) / (double) sr.getScaleFactor();
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
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
        GL11.glLineWidth(1.0F);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(xEnd, yEnd);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
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

    private float getCharWidthFloat(char c) {
        if (c == 167) {
            return -1.0F;
        } else if (c == ' ') {
            return 2.0F;
        } else {
            int var2 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c);
            if (c > 0 && var2 != -1) {
                return (float) this.charData[var2].width / 2.0F - 4.0F;
            } else if ((float) this.charData[c].width / 2.0F - 4.0F != 0.0F) {
                int var3 = (int) ((float) this.charData[c].width / 2.0F - 4.0F) >>> 4;
                int var4 = (int) ((float) this.charData[c].width / 2.0F - 4.0F) & 15;
                var3 &= 15;
                ++var4;
                return (float) ((var4 - var3) / 2 + 1);
            } else {
                return 0.0F;
            }
        }
    }

}
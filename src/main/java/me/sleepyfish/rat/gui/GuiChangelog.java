package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GuiChangelog extends GuiScreen {

    public ArrayList<String> lines;
    public boolean sliderMoving;
    private float scrollY = 0F;

    public GuiChangelog() {
        this.lines = new ArrayList<>();
    }

    @Override
    public void initGui() {
        this.sliderMoving = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));
        GuiUtils.drawLogo(this.width, this.height, 2);

        int yOffset = 0;
        for (String text : lines) {
            if (text.startsWith("+ ")) {
                text = text.replace("+ ", EnumChatFormatting.DARK_GREEN + "[+] - ");
            } else if (text.startsWith("- ")) {
                text = text.replace("- ", EnumChatFormatting.DARK_RED + "[ -] - ");
            } else if (text.startsWith("* ")) {
                text = text.replace("* ", EnumChatFormatting.LIGHT_PURPLE + "[ !] - ");
            } else if (text.startsWith("? ")) {
                text = text.replace("? ", EnumChatFormatting.GOLD + "[?] - ");
            } else if (text.startsWith(". ")) {
                text = text.replace(". ", EnumChatFormatting.BLUE + "[.] - ");
            } else if (text.equals("#")) {
                text = " ";
            } else {
                return;
            }

            if (text.startsWith(EnumChatFormatting.BOLD  + "[ !] - ")) {
                FontUtils.text24.drawString(text, (this.width / 2F) - 200F, 60F + (float) (yOffset * FontUtils.text18.getHeight() + 8F) + this.scrollY, ColorUtils.getFontColor(this));
                yOffset += 2;
            } else {
                FontUtils.text18.drawString(text, (this.width / 2F) - 200F, 60F + (float) (yOffset * FontUtils.text18.getHeight() + 8F) + this.scrollY, ColorUtils.getFontColor(this));
                yOffset += 1;
            }
        }

        RenderUtils.drawRound(25F, 25F, 20F, 20F, 5F, new Color(0x00A100));
        FontUtils.drawFont("Added", 55F, 25F + 6F, ColorUtils.getFontColor(this));

        RenderUtils.drawRound(25F, 25F * 2F, 20F, 20F, 5F, new Color(0xAA0000));
        FontUtils.drawFont("Removed", 55F, 25F * 2F + 6F, ColorUtils.getFontColor(this));

        RenderUtils.drawRound(25F, 25F * 3F, 20F, 20F, 5F, new Color(0xFF55FF));
        FontUtils.drawFont("Header", 55F, 25F * 3F + 6F, ColorUtils.getFontColor(this));

        RenderUtils.drawRound(25F, 25F * 4F, 20F, 20F, 5F, new Color(0xFFAA00));
        FontUtils.drawFont("Fix", 55F, 25F * 4F + 6F, ColorUtils.getFontColor(this));

        RenderUtils.drawRound(25F, 25F * 4F, 20F, 20F, 5F, new Color(0xFF5555FF, true));
        FontUtils.drawFont("Info", 55F, 25F * 5F + 6F, ColorUtils.getFontColor(this));
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void updateScreen() {
        // Hover setter
        this.scrollY = InputUtils.getScroll();
    }

}
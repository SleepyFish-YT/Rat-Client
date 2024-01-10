package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.Rat;
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
 * @author SleepyFish 2024
 */
public class GuiChangelog extends GuiScreen {

    public final ArrayList<String> lines;
    private float scrollY;

    public GuiChangelog() {
        this.lines = new ArrayList<>();

        // Header: Fix: Added: Deleted: NewLine

        final String add = "+ ";
        final String del = "- ";
        final String header = "* Release: ";
        final String fix = "? ";
        final String info = ". ";
        final String newLn = "#";

        this.lines.add(header + "1.0");
        this.lines.add(fix + "Fixed Scroll wheel not working in Cosmetics Gui");
        this.lines.add(fix + "Fixed Chat cant be disabled bug");
        this.lines.add(add + "Made mouse keybinds in inventory work");
        this.lines.add(add + "Added Chat Animations");
        this.lines.add(add + "Added Chat setting to remove chat");
        this.lines.add(add + "Added Chat setting to remove background");
        this.lines.add(add + "Added Chat Limit unlocker");
        this.lines.add(add + "Added Chat Custom font");
        this.lines.add(add + "Added Smooth Module scroll animation");
        this.lines.add(add + "Added Smoothness to the Entity in cape gui");
        this.lines.add(add + "Added Rat client logo ontop of Inventory");
        this.lines.add(add + "Added Smooth Cape scroll animation");
        this.lines.add(del + "Removed the Username in cape gui");
        this.lines.add(newLn);

        this.lines.add(header + "0.9-PRE");
        this.lines.add(fix + "Fixed Keybinds");
        this.lines.add(add + "Made Font rendering better");
        this.lines.add(add + "Made Flying speed not work on servers");
        this.lines.add(newLn);

        this.lines.add(header + "0.8-PRE");
        this.lines.add(fix + "Fixed Keystrokes hover");
        this.lines.add(fix + "Fixed Walter cape");
        this.lines.add(fix + "Fixed Zoom working inside Gui's");
        this.lines.add(fix + "Fixed Freelook working inside Gui's");
        this.lines.add(fix + "Fixed wrong text in Toggle Sprint");
        this.lines.add(fix + "Fixed Flight speed on Toggle Sprint");
        this.lines.add(add + "Added some usernames to a list to render a rat icon nearby the nametag");
        this.lines.add(add + "Added Server IP");
        this.lines.add(add + "Added Setting IP Text");
        this.lines.add(add + "Added Server Ping");
        this.lines.add(add + "Added Server Ping text");
        this.lines.add(add + "Added Remove backgrounds for Nametags");
        this.lines.add(add + "Made Nametags rotate with your freecam rotations");
        this.lines.add(add + "Added Remove all Nametags");
        this.lines.add(add + "Made the crosshair stop rendering when in gui");
        this.lines.add(add + "Added Cosmetic icon in Module Move Gui");
        this.lines.add(add + "Added Settings text in Module Move Gui");
        this.lines.add(add + "Added a check if its december, then use a different main icon and render snowfall");
        this.lines.add(add + "Added Changelog Gui");
        this.lines.add(add + "Added Config manager");
        this.lines.add(add + "Made the Gui better");
        this.lines.add(add + "Made Freelook save the old state");
        this.lines.add(add + "Renamed Old animations to Animations");
        this.lines.add(add + "Renamed Boss Bar to Bossbar");
        this.lines.add(add + "Renamed Free look to Freelook");
        this.lines.add(del + "Removed useless font drawing features");
        this.lines.add(newLn);

        this.lines.add(header + "0.7-PRE");
        this.lines.add(add + "Added option to use vanilla Gui");
        this.lines.add(add + "Added Module icons");
        this.lines.add(add + "Added 'Cancel effects' which removes effects behind ur camera");
        this.lines.add(add + "Added 'Cancel items' which removes items behind ur camera");
        this.lines.add(newLn);

        this.lines.add(header + "0.6-PRE");
        this.lines.add(add + "Added 'Cancel Entities' which removes items behind ur camera");
        this.lines.add(add + "Added lunar like Main menu");
        this.lines.add(add + "Added lunar like Module menu");
        this.lines.add(add + "Added Cps");
        this.lines.add(add + "Added Fps");
        this.lines.add(add + "Added Name");
        this.lines.add(add + "Added Keystrokes");
        this.lines.add(add + "Added Zoom");
        this.lines.add(add + "Added Counter");
        this.lines.add(add + "Added More modules to change minecraft hud rendering");
        this.lines.add(add + "Added Gui click Sounds");
        this.lines.add(add + "Added Settings to change Keybinds");
        this.lines.add(info + "Changed icons");
        this.lines.add(add + "Added intro animation to the icon when opening gui");
        this.lines.add(fix + "Fixed memory leak (above 5gb of ram usage)");
        this.lines.add(add + "Added hypixel working Free Look");
        this.lines.add(add + "Added Fix Hit delay");
        this.lines.add(add + "Added custom font");
        this.lines.add(add + "Added Old sneak animation");
        this.lines.add(add + "Added Coordinates");
        this.lines.add(add + "Added Performance module");
        this.lines.add(add + "Added Cosmetics");
    }

    @Override
    public void initGui() {
        this.scrollY = 0;

        Rat.instance.antiCheat.openGuiCheck();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        if (this.scrollY > InputUtils.getScroll())
            this.scrollY = InputUtils.getScroll();

        // Render gui background
        this.drawDefaultBackground();
        GuiUtils.drawLogo(this.width, this.height, false);

        float yOffset = 0;
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

            if (text.startsWith(EnumChatFormatting.BOLD + "[ !] - ")) {
                FontUtils.text18.drawString(text, (this.width / 2F) - 200F, 60F + (float) (yOffset * FontUtils.text18.getHeight() + 8F) + this.scrollY, ColorUtils.getFontColor(this));
                yOffset += 1.8F;
            } else {
                FontUtils.text14.drawString(text, (this.width / 2F) - 200F, 60F + (float) (yOffset * FontUtils.text18.getHeight() + 8F) + this.scrollY, ColorUtils.getFontColor(this));
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

        RenderUtils.drawRound(25F, 25F * 5F, 20F, 20F, 5F, new Color(0x5555FF));
        FontUtils.drawFont("Info", 55F, 25F * 5F + 6F, ColorUtils.getFontColor(this));
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }

}
package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class GuiRatEmote extends GuiScreen {

    private final GuiScreen parent;

    public GuiRatEmote(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        Rat.instance.antiCheat.openGuiCheck();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));
        GuiUtils.drawCustomGui(1, this.width, this.height, false);
        GuiUtils.drawLogo(this.width, this.height, false);

        // No emotes :(    |   (cry more)
        FontUtils.drawFont("No emotes yet...", (this.width / 2F) - ((float) FontUtils.currentFont.getStringWidth("No emotes yet...") / 2F), this.height / 2F, Color.white);
    }

    @Override
    public void mouseClicked(int x, int y, int b) {

    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
}
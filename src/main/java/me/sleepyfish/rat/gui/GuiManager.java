package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.gui.clickgui.*;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.client.gui.GuiScreen;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GuiManager {

    public boolean useMixinMainMenuAnimation = true;

    private GuiRatModuleMenu ratGuiModuleMenu;
    private GuiRatModuleMove ratGuiModuleMove;
    private GuiRatCosmetic ratGuiCosmetic;
    private GuiChangelog ratGuiChangelog;
    private GuiRatEmote ratGuiEmoteList;
    private GuiRatConfig ratGuiConfig;

    public GuiManager() {
        this.ratGuiModuleMove = new GuiRatModuleMove();
        this.ratGuiModuleMenu = new GuiRatModuleMenu(this.ratGuiModuleMove);
        this.ratGuiCosmetic   = new GuiRatCosmetic(this.ratGuiModuleMove);
        this.ratGuiChangelog  = new GuiChangelog();
        this.ratGuiEmoteList  = new GuiRatEmote(this.ratGuiModuleMove);
        this.ratGuiConfig  = new GuiRatConfig(ratGuiModuleMenu);
    }

    public void unInject() {
        this.ratGuiModuleMenu = null;
        this.ratGuiModuleMove = null;
        this.ratGuiCosmetic   = null;

        this.ratGuiChangelog.lines.clear();
        this.ratGuiChangelog  = null;

        this.ratGuiEmoteList  = null;
        this.ratGuiConfig = null;
    }

    public boolean inClickGui() {
        GuiScreen s = MinecraftUtils.mc.currentScreen;
        return s == this.getRatGuiModuleMove() || s == this.getRatGuiModuleMenu() || s == this.getRatGuiCosmetic() || s == this.getRatGuiEmoteList();
    }

    public GuiRatModuleMenu getRatModuleGUI() {
        return this.ratGuiModuleMenu;
    }

    public GuiRatModuleMenu getRatGuiModuleMenu() {
        return ratGuiModuleMenu;
    }

    public GuiRatModuleMove getRatGuiModuleMove() {
        return ratGuiModuleMove;
    }

    public GuiRatCosmetic getRatGuiCosmetic() {
        return ratGuiCosmetic;
    }

    public GuiChangelog getRatGuiChangelog() {
        return ratGuiChangelog;
    }

    public GuiRatEmote getRatGuiEmoteList() {
        return ratGuiEmoteList;
    }

    public GuiRatConfig getRatGuiConfig() {
        return ratGuiConfig;
    }

}
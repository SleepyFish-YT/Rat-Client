package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.gui.clickgui.*;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.client.gui.GuiScreen;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class GuiManager {

    public boolean useMixinMainMenuAnimation = true;

    private final GuiRatModuleMove ratGuiModuleMove;
    private final GuiChangelog ratGuiChangelog;

    private final GuiRatModuleMenu ratGuiModuleMenu;
    private final GuiRatCosmetic ratGuiCosmetic;
    private final GuiRatEmote ratGuiEmoteList;
    private final GuiRatConfig ratGuiConfig;

    public GuiManager() {
        this.ratGuiModuleMove = new GuiRatModuleMove();
        this.ratGuiChangelog  = new GuiChangelog();

        this.ratGuiModuleMenu = new GuiRatModuleMenu(this.ratGuiModuleMove);
        this.ratGuiCosmetic   = new GuiRatCosmetic(this.ratGuiModuleMove);
        this.ratGuiEmoteList  = new GuiRatEmote(this.ratGuiModuleMove);
        this.ratGuiConfig  = new GuiRatConfig(this.ratGuiModuleMenu);
    }

    public boolean inClickGui() {
        final GuiScreen s = MinecraftUtils.mc.currentScreen;
        return s == this.getRatGuiModuleMove() || s == this.getRatGuiModuleMenu() || s == this.getRatGuiCosmetic() || s == this.getRatGuiEmoteList();
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
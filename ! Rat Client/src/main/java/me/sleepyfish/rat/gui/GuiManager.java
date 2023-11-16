package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.utils.misc.MinecraftUtils;

public class GuiManager {

    private GuiRatClickGui clickGui;

    public GuiManager() {
        this.clickGui = new GuiRatClickGui();
    }

    public void unInject() {
        this.clickGui = null;
    }

    public boolean inClickGui() {
        if (MinecraftUtils.mc.currentScreen != null) {
            return MinecraftUtils.mc.currentScreen == this.clickGui;
        }

        return false;
    }

    public GuiRatClickGui getClickGui() {
        return clickGui;
    }

}
package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;

public class Fullbright extends Module {

    private float oldGammaSetting;

    public Fullbright() {
        super("Fullbright", "Makes everything bright");
    }

    @Override
    public void onEnableEvent() {
        this.oldGammaSetting = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100F;
    }

    @Override
    public void onDisableEvent() {
        mc.gameSettings.gammaSetting = this.oldGammaSetting;
        this.oldGammaSetting = 0F;
    }

}
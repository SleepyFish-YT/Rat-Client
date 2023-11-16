package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;

public class FixHitDelay extends Module {

    public FixHitDelay() {
        super("Fix Hit Delay", "Fixes hit reg when not hitting anyone.");

        this.toggle();
    }

}
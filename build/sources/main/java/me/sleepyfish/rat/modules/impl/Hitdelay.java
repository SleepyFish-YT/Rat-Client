package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;

public class Hitdelay extends Module {

    public Hitdelay() {
        super("Hitdelay", "Fixes hit reg when not hitting anyone.");

        this.toggle();
    }

}
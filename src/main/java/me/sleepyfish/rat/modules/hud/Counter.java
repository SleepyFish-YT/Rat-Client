package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;

public class Counter extends Module {

    public Counter() {
        super("Counter", "Counts items in hand and renders it into the HUD",20, 20);
    }

    @Override
    public void renderUpdate() {
        if (mc.thePlayer.getCurrentEquippedItem() != null) {
            this.setText("" + mc.thePlayer.getCurrentEquippedItem().stackSize);
        } else {
            this.setText("0");
        }
    }

}
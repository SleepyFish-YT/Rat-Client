package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
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
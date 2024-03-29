package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Ping extends Module {

    private final ToggleSetting pingText;

    public Ping() {
        super("Ping", "Shows your ping into the HUD",20, 20);

        this.addSetting(this.pingText = new ToggleSetting("Ping Text", true));
    }

    @Override
    public void renderUpdate() {
        if (mc.isSingleplayer()) {
            this.setText("?");
        } else {
            String text = "" + mc.getCurrentServerData().pingToServer;

            if (this.pingText.isEnabled())
                text = "Ping: " + text;

            this.setText(text);
        }
    }

}
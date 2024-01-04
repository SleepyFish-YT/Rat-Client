package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

public class ServerIp extends Module {

    private final ToggleSetting ipText;

    public ServerIp() {
        super("Server IP", "Shows the Server IP into the HUD",20, 20);

        this.addSetting(this.ipText = new ToggleSetting("IP Text", true));
    }

    @Override
    public void renderUpdate() {
        if (mc.isSingleplayer()) {
            this.setText("?");
        } else {
            if (mc.getCurrentServerData().isOnLAN()) {
                this.setText("Lan World");
                return;
            }

            String text = mc.getCurrentServerData().serverIP;

            if (this.ipText.isEnabled()) {
                text = "IP: " + text;
            }

            this.setText(text);
        }
    }

}
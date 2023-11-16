package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

public class FpsCounter extends Module {

    private final ToggleSetting fpsText;

    public FpsCounter() {
        super("FPS", "Display your FPS on the HUD",20, 60);
        this.addSetting(fpsText = new ToggleSetting("FPS Text", true));

        this.brackets.toggle();

        this.toggle();
    }

    @Override
    public void renderUpdate() {
        String name = mc.getDebugFPS() + "";
        if (this.fpsText.isEnabled()) {
            name += " FPS";
        }

        this.setText(name);
    }

}
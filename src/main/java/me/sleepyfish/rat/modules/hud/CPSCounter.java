package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class CPSCounter extends Module {

    private boolean allowLeftClick;
    private boolean allowNextLeftClick;

    private boolean allowRightClick;
    private boolean allowNextRightClick;

    private final ToggleSetting left, right;
    private final ToggleSetting cpsText;

    public CPSCounter() {
        super("CPS", "Shows your Clicks per seconds on the HUD",20, 40);

        this.addSetting(left = new ToggleSetting("Left CPS", "Render Left cps",true));
        this.addSetting(right = new ToggleSetting("Right CPS", "Render Right cps",true));
        this.addSetting(cpsText = new ToggleSetting("CPS Text", "Render Cps text",false));

        this.brackets.toggle();

        this.toggle();
    }

    @Override
    public void renderUpdate() {
        if (left.isEnabled()) {
            if (InputUtils.isButtonDown(0)) {
                if (allowNextLeftClick) {
                    allowLeftClick = true;
                    allowNextLeftClick = false;
                }
            } else {
                allowLeftClick = false;
                allowNextLeftClick = true;
            }

            if (allowLeftClick) {
                InputUtils.MouseManager.addLeftClick();
                allowLeftClick = false;
            }
        }

        if (right.isEnabled()) {
            if (InputUtils.isButtonDown(1)) {
                if (allowNextRightClick) {
                    allowRightClick = true;
                    allowNextRightClick = false;
                }
            } else {
                allowRightClick = false;
                allowNextRightClick = true;
            }

            if (allowRightClick) {
                InputUtils.MouseManager.addRightClick();
                allowRightClick = false;
            }
        }

        if (cpsText.isEnabled()) {
            if (left.isEnabled()) {
                if (right.isEnabled()) {
                    this.setText("L: " + InputUtils.MouseManager.getLeftClickCounter() + " | R: " + InputUtils.MouseManager.getRightClickCounter());
                } else {
                    this.setText("L: " + InputUtils.MouseManager.getLeftClickCounter());
                }
            }

            if (right.isEnabled()) {
                if (!left.isEnabled()) {
                    this.setText("R: " + InputUtils.MouseManager.getRightClickCounter());
                }
            }
        } else {
            if (left.isEnabled()) {
                if (right.isEnabled()) {
                    this.setText(InputUtils.MouseManager.getLeftClickCounter() + " | " + InputUtils.MouseManager.getRightClickCounter());
                } else {
                    this.setText("" + InputUtils.MouseManager.getLeftClickCounter());
                }
            }

            if (right.isEnabled()) {
                if (!left.isEnabled()) {
                    this.setText("" + InputUtils.MouseManager.getRightClickCounter());
                }
            }
        }
    }

}
package me.sleepyfish.rat.modules.hud;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import org.lwjgl.input.Keyboard;

public class Keystrokes extends Module {

    private final ToggleSetting spaceBar;
    private final ToggleSetting mouseButtons;

    private final int keySize;
    private final int gaping;

    public Keystrokes() {
        super("Keystrokes", "Displays your Keystrokes on the HUD", 20, 300);

        this.addSetting(this.spaceBar = new ToggleSetting("Space bar", "Render spacebar rectangle", true));
        this.addSetting(this.mouseButtons = new ToggleSetting("Mouse buttons", "Render mouse rectangles", true));
        this.removeSetting(this.brackets);

        this.keySize = 20;
        this.gaping = 3;

        this.setCustom(true);
        this.toggle();
    }

    @Override
    public void renderUpdate() {
        int height;

        if (this.spaceBar.isEnabled()) {
            if (this.mouseButtons.isEnabled()) {
                height = 81;
            } else {
                height = 68;
            }
        } else {
            if (this.mouseButtons.isEnabled()) {
                height = 63;
            } else {
                height = 48;
            }
        }

        this.setHeight(height);
        this.setWidth(keySize * 2 + (gaping * 2) + 8);
    }

    @Override
    public void drawComponent() {
        String wKey = Keyboard.getKeyName(mc.gameSettings.keyBindForward.getKeyCode()).replace("NONE", "");
        String sKey = Keyboard.getKeyName(mc.gameSettings.keyBindBack.getKeyCode()).replace("NONE", "");
        String aKey = Keyboard.getKeyName(mc.gameSettings.keyBindLeft.getKeyCode()).replace("NONE", "");
        String dKey = Keyboard.getKeyName(mc.gameSettings.keyBindRight.getKeyCode()).replace("NONE", "");

        this.drawRectangle(this.getGuiX() + 17, this.getGuiY() + 20 - keySize - gaping, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(wKey, this.getGuiX() + 24, this.getGuiY() + 27 - keySize - gaping, this.getFontColor());

        this.drawRectangle(this.getGuiX() + 17, this.getGuiY() + 20, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(sKey, this.getGuiX() + 24, this.getGuiY() + 27, this.getFontColor());

        this.drawRectangle(this.getGuiX() + 17 - keySize - gaping, this.getGuiY() + 20, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(aKey, this.getGuiX() + 24 - keySize - gaping, this.getGuiY() + 27, this.getFontColor());

        this.drawRectangle(this.getGuiX() + 17 + keySize + gaping, this.getGuiY() + 20, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(dKey, this.getGuiX() + 24 + keySize + gaping, this.getGuiY() + 27, this.getFontColor());

        if (this.spaceBar.isEnabled()) {
            if (this.mouseButtons.isEnabled()) {
                this.drawRectangle(this.getGuiX() + 17 - keySize - gaping, this.getGuiY() + 34 + keySize + gaping, keySize * 3 + (gaping * 2), keySize - 5, Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()), this.getBackgroundWhite());
                RenderUtils.drawRound(this.getGuiX() + 17 - keySize - gaping + 5, this.getGuiY() + 34 + 0.5F + keySize + gaping + keySize / 2F - 4, keySize * 3 + (gaping * 2) - 10, 2, 2, this.getFontColor());

                float widthM = ((keySize * 3 + (gaping * 2) - 2) / 2F);
                this.drawRectangle(this.getGuiX() + 17 - keySize - gaping, this.getGuiY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM, keySize - 8, InputUtils.isButtonDown(0), this.getBackgroundWhite());
                this.drawRectangle(this.getGuiX() + 17 + widthM + gaping - keySize - gaping,  this.getGuiY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM - 0.5F, keySize - 8, InputUtils.isButtonDown(1), this.getBackgroundWhite());
            } else {
                this.drawRectangle(this.getGuiX() + 17 - keySize - gaping, this.getGuiY() + 20 + keySize + gaping, keySize * 3 + (gaping * 2), keySize - 5, Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()), this.getBackgroundWhite());
                RenderUtils.drawRound(this.getGuiX() + 17 - keySize - gaping + 5, this.getGuiY() + 20 + 0.5F + keySize + gaping + keySize / 2F - 4, keySize * 3 + (gaping * 2) - 10, 2, 2, this.getFontColor());
            }
        } else {
            if (mouseButtons.isEnabled()) {
                float widthM = ((keySize * 3 + (gaping * 2) - 2) / 2F);
                this.drawRectangle(this.getGuiX() + 17 - keySize - gaping, this.getGuiY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM, keySize - 8, InputUtils.isButtonDown(0), this.getBackgroundWhite());
                this.drawRectangle(this.getGuiX() + 17 + widthM + gaping - keySize - gaping,  this.getGuiY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM - 0.5F, keySize - 8, InputUtils.isButtonDown(1), this.getBackgroundWhite());
            }
        }
    }

}
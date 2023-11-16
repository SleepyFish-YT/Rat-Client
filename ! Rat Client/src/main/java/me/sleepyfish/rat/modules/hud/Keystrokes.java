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
        super("Keystrokes", "Displays your Keystrokes on the HUD.", 20, 300);

        this.addSetting(this.spaceBar = new ToggleSetting("Space bar", "Render spacebar rectangle.", true));
        this.addSetting(this.mouseButtons = new ToggleSetting("Mouse buttons", "Render mouse rectangles.", true));
        this.removeSetting(this.brackets);

        this.keySize = 20;
        this.gaping = 3;

        this.toggle();
    }

    @Override
    public void renderUpdate() {
        int height = 0;

        if (this.spaceBar.isEnabled()) {
            if (this.mouseButtons.isEnabled()) {
                height = 65;
            } else {
                height = 59;
            }
        } else {
            if (this.mouseButtons.isEnabled()) {
                height = 59;
            } else {
                height = 41;
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

        this.drawRectangle(this.getX() + 17, this.getY() + 20 - keySize - gaping, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(wKey, this.getX() + 24, this.getY() + 26 - keySize - gaping, this.getFontColor());

        this.drawRectangle(this.getX() + 17, this.getY() + 20, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(sKey, this.getX() + 24, this.getY() + 26, this.getFontColor());

        this.drawRectangle(this.getX() + 17 - keySize - gaping, this.getY() + 20, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(aKey, this.getX() + 24 - keySize - gaping, this.getY() + 26, this.getFontColor());

        this.drawRectangle(this.getX() + 17 + keySize + gaping, this.getY() + 20, keySize, keySize, Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()), this.getBackgroundWhite());
        FontUtils.drawFont(dKey, this.getX() + 24 + keySize + gaping, this.getY() + 26, this.getFontColor());

        if (this.spaceBar.isEnabled()) {
            if (this.mouseButtons.isEnabled()) {
                this.drawRectangle(this.getX() + 17 - keySize - gaping, this.getY() + 34 + keySize + gaping, keySize * 3 + (gaping * 2), keySize - 5, Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()), this.getBackgroundWhite());
                RenderUtils.drawRound(this.getX() + 17 - keySize - gaping + 5, this.getY() + 34 + 0.5F + keySize + gaping + keySize / 2F - 4, keySize * 3 + (gaping * 2) - 10, 2, 2, this.getFontColor());

                float widthM = ((keySize * 3 + (gaping * 2) - 2) / 2F);
                this.drawRectangle(this.getX() + 17 - keySize - gaping, this.getY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM, keySize - 8, InputUtils.isButtonDown(0), this.getBackgroundWhite());
                this.drawRectangle(this.getX() + 17 + widthM + gaping - keySize - gaping,  this.getY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM - 0.5F, keySize - 8, InputUtils.isButtonDown(1), this.getBackgroundWhite());
            } else {
                this.drawRectangle(this.getX() + 17 - keySize - gaping, this.getY() + 20 + keySize + gaping, keySize * 3 + (gaping * 2), keySize - 5, Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()), this.getBackgroundWhite());
                RenderUtils.drawRound(this.getX() + 17 - keySize - gaping + 5, this.getY() + 20 + 0.5F + keySize + gaping + keySize / 2F - 4, keySize * 3 + (gaping * 2) - 10, 2, 2, this.getFontColor());
            }
        } else {
            if (mouseButtons.isEnabled()) {
                float widthM = ((keySize * 3 + (gaping * 2) - 2) / 2F);
                this.drawRectangle(this.getX() + 17 - keySize - gaping, this.getY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM, keySize - 8, InputUtils.isButtonDown(0), this.getBackgroundWhite());
                this.drawRectangle(this.getX() + 17 + widthM + gaping - keySize - gaping,  this.getY() + 10 + gaping + 0.5F + keySize + gaping + keySize / 2F - 4, widthM - 0.5F, keySize - 8, InputUtils.isButtonDown(1), this.getBackgroundWhite());
            }
        }
    }

}
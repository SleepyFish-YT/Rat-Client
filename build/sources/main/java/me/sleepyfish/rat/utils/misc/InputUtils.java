package me.sleepyfish.rat.utils.misc;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.ArrayList;

public class InputUtils {

    public static int MOUSE_RIGHT;
    public static int MOUSE_RIGHT_EVENT;
    public static int MOUSE_LEFT;
    public static int MOUSE_LEFT_EVENT;

    public static int mouseX;
    public static int mouseY;

    public static boolean isInside(double x, double y, double width, double height) {
        return (double) mouseX > x && (double) mouseX < x + width && (double) mouseY > y && (double) mouseY < y + height;
    }

    public static boolean isButtonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    public static boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    public static void setMousePos(int x, int y) {
        Mouse.setCursorPosition(x, y);
    }

    public static float getRealScroll() {
        return (float) Mouse.getDWheel();
    }

    public static float getScroll() {
        return getRealScroll() * 0.15F;
    }

    public static boolean isBlockedKey(int bind) {
        return bind != 0;
    }

    public static void pressKeybindOnce(KeyBinding key) {
        if (key == MinecraftUtils.mc.gameSettings.keyBindUseItem)
            MouseManager.addRightClick();

        if (key == MinecraftUtils.mc.gameSettings.keyBindAttack)
            MouseManager.addLeftClick();

        KeyBinding.setKeyBindState(key.getKeyCode(), true);
        KeyBinding.onTick(key.getKeyCode());
        KeyBinding.setKeyBindState(key.getKeyCode(), false);
        KeyBinding.onTick(key.getKeyCode());
    }

    public static boolean isClicked(int key) {
        return Mouse.getEventButton() == key;
    }

    public static class MouseManager {

        private static final List<Long> leftClicks = new ArrayList<>();
        private static final List<Long> rightClicks = new ArrayList<>();

        public static long leftClickTimer = 0L;
        public static long rightClickTimer = 0L;

        public static void addLeftClick() {
            leftClicks.add(leftClickTimer = System.currentTimeMillis());
        }

        public static void addRightClick() {
            rightClicks.add(rightClickTimer = System.currentTimeMillis());
        }

        public static int getLeftClickCounter() {
            for (Long lon : leftClicks) {
                if (lon < System.currentTimeMillis() - 1000L) {
                    leftClicks.remove(lon);
                    break;
                }
            }

            return leftClicks.size();
        }

        public static int getRightClickCounter() {
            for (Long lon : rightClicks) {
                if (lon < System.currentTimeMillis() - 1000L) {
                    rightClicks.remove(lon);
                    break;
                }
            }

            return rightClicks.size();
        }
    }

    // Thank you soar $$$ (eldodebug sucks)
    public enum SoarScroll {
        DOWN, UP;
    }

    public static SoarScroll getSoarScroll() {
        int mouse = (int) InputUtils.getRealScroll();

        if (mouse > 0) {
            return SoarScroll.UP;
        } else if (mouse < 0) {
            return SoarScroll.DOWN;
        } else {
            return null;
        }
    }

}
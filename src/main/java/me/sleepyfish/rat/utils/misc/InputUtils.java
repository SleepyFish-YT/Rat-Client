package me.sleepyfish.rat.utils.misc;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class InputUtils {

    public final static int MOUSE_RIGHT = 1;
    public final static int MOUSE_RIGHT_EVENT = 4;
    public final static int MOUSE_LEFT = 0;
    public final static int MOUSE_LEFT_EVENT = 16;

    public static int mouseX;
    public static int mouseY;

    public static boolean isInside(final double x, final double y, final double width, final double height) {
        return (double) mouseX > x && (double) mouseX < x + width && (double) mouseY > y && (double) mouseY < y + height;
    }

    public static boolean isButtonDown(final int button) {
        return Mouse.isButtonDown(button);
    }

    public static boolean isKeyDown(final int key) {
        return Keyboard.isKeyDown(key);
    }

    public static void setMousePos(final int x, final int y) {
        Mouse.setCursorPosition(x, y);
    }

    public static float getRealScroll() {
        return (float) Mouse.getDWheel();
    }

    public static float getScroll() {
        return getRealScroll() * 0.15F;
    }

    public static boolean isBlockedKey(final int bind) {
        return bind != 0;
    }

    public static void pressKeybindOnce(final KeyBinding key) {
        if (key == MinecraftUtils.mc.gameSettings.keyBindUseItem)
            MouseManager.addRightClick();

        if (key == MinecraftUtils.mc.gameSettings.keyBindAttack)
            MouseManager.addLeftClick();

        KeyBinding.setKeyBindState(key.getKeyCode(), true);
        KeyBinding.onTick(key.getKeyCode());
        KeyBinding.setKeyBindState(key.getKeyCode(), false);
        KeyBinding.onTick(key.getKeyCode());
    }

    public static boolean isClicked(final int key) {
        return Mouse.getEventButton() == key;
    }

    public static class MouseManager {

        private static final LinkedList<Long> leftClicks = new LinkedList<>();
        private static final LinkedList<Long> rightClicks = new LinkedList<>();

        public static long leftClickTimer = 0L;
        public static long rightClickTimer = 0L;

        public static void addLeftClick() {
            leftClicks.add(leftClickTimer = System.nanoTime());
        }

        public static void addRightClick() {
            rightClicks.add(rightClickTimer = System.nanoTime());
        }

        public static int getLeftClickCounter() {
            final long currentTime = System.nanoTime();
            leftClicks.removeIf(time -> time < currentTime - 1000000000L);
            return leftClicks.size();
        }

        public static int getRightClickCounter() {
            final long currentTime = System.nanoTime();
            rightClicks.removeIf(time -> time < currentTime - 1000000000L);
            return rightClicks.size();
        }
    }

    // Thank you soar $$$ (eldodebug sucks)
    // Soar is skidded from everything u can imagine of anyways
    public enum SoarScroll {
        DOWN, UP;
    }

    public static SoarScroll getSoarScroll() {
        final int mouse = (int) InputUtils.getRealScroll();

        if (mouse > 0) {
            return SoarScroll.UP;
        } else if (mouse < 0) {
            return SoarScroll.DOWN;
        } else {
            return null;
        }
    }

}
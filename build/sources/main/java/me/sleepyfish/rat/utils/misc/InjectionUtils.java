package me.sleepyfish.rat.utils.misc;

import me.sleepyfish.rat.Rat;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.Desktop;

public class InjectionUtils {

    // main function
    public static void oneTimeInjectChecks() {
        if (failedTitleCheck() || failedWindowCheck() || minecraftCheck()) {
            WindowsUtils.crash();
        }
    }

    private static boolean minecraftCheck() {
        return MinecraftUtils.mc.isDemo() || MinecraftUtils.mc.gameSettings.keyBindForward.getKeyCode() != Keyboard.KEY_W;
    }

    // checks for window title changes
    private static boolean failedTitleCheck() {
        return WindowsUtils.getWindowTitle() != WindowsUtils.ratTitle;
    }

    // useless checks we love em
    private static boolean failedWindowCheck() {
        return !Display.isCreated() || !Desktop.getDesktop().isSupported(Desktop.Action.MAIL);
    }

    // has no function yet
    private static boolean failedInstanceCheck() {
        return Rat.instance == null;
    }

}
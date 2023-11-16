package me.sleepyfish.rat.utils.misc;

import org.lwjgl.opengl.Display;

import java.net.URI;
import java.awt.Desktop;

public class WindowsUtils {

    public static String ratTitle;

    public static String getWindowTitle() {
        return Display.getTitle();
    }

    public static void setWindowTitle(String title) {
        Display.setTitle(title);
    }

    public static void crash() {
        System.exit(-32);
    }

    public static void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
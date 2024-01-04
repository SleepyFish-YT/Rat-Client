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

    public static String capitalizeFirstLetter(String input) {
        char firstChar = Character.toUpperCase(input.charAt(0));
        return firstChar + input.substring(1);
    }

}
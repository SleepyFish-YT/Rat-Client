package me.sleepyfish.rat.utils.misc;

import me.sleepyfish.rat.Rat;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.net.URI;
import java.awt.Desktop;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class WindowsUtils {

    public static String ratTitle;

    public static int refreshRate;

    public static void init() {
        WindowsUtils.ratTitle = Rat.instance.getName() + " 1.8.9 (v" + Rat.instance.getVersion() + ")";
        WindowsUtils.setWindowTitle(WindowsUtils.ratTitle);

        try {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final GraphicsDevice gd = ge.getDefaultScreenDevice();
            final DisplayMode de = gd.getDisplayMode();
            WindowsUtils.refreshRate = de.getRefreshRate() + 10;
        } catch (Exception e) {
            WindowsUtils.refreshRate = 250;
            e.printStackTrace();
        }
    }

    public static String getWindowTitle() {
        return Display.getTitle();
    }

    public static void setWindowTitle(final String title) {
        Display.setTitle(title);
    }

    public static void crash() {
        System.exit(-32);
    }

    public static void clearRam() {
        System.gc();
    }

    public static void openURL(final String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkForOpenGLErrors() {
        final int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            MinecraftUtils.mc.thePlayer.addChatMessage(new ChatComponentText("OpenGL Error: " + EnumChatFormatting.YELLOW + error));
        }
    }

    public static String capitalizeFirstLetter(final String input) {
        final char firstChar = Character.toUpperCase(input.charAt(0));
        return firstChar + input.substring(1);
    }

}
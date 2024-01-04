package me.sleepyfish.rat.utils.file;

import java.io.File;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class FileManager {

    private final File mainPath;
    private final File capePath;
    private final File configPath;

    public static String resourcePath = "rat";

    public FileManager() {
        this.mainPath = new File(System.getProperty("user.home") + File.separator + ".ratclient");

        this.capePath = new File(mainPath.getPath() + File.separator + "capes");
        this.configPath = new File(mainPath.getPath() + File.separator + "configs");
    }

    public File getMainPath() {
        return mainPath;
    }

    public File getConfigPath() {
        return configPath;
    }

    public File getCapePath() {
        return capePath;
    }

}
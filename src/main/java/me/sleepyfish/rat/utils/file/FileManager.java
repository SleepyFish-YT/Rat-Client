package me.sleepyfish.rat.utils.file;

import java.io.File;

public class FileManager {

    public File mainPath;

    public File capePath;
    public File configPath;

    public FileManager() {
        this.mainPath = new File(System.getProperty("user.home") + File.separator + ".ratclient");

        this.capePath = new File(capePath.getPath() + File.separator + "capes");
        this.configPath = new File(capePath.getPath() + File.separator + "configs");
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
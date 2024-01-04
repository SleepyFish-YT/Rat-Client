package me.sleepyfish.rat.utils.config;

import me.sleepyfish.rat.Rat;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClientConfig {

    private final File configFile;
    private final String fileName = "RatConfig";
    private final String loadedConfigPrefix = "loaded-cfg~ ";

    public ClientConfig() {
        File configDir = new File(System.getProperty("user.home") + File.separator + ".ratclient");
        if (!configDir.exists())
            configDir.mkdir();

        configFile = new File(configDir, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception ignored) {
            }
        }
    }

    public void saveConfig() {
        List<String> config = new ArrayList<>();
        config.add(loadedConfigPrefix + Rat.instance.configManager.getModuleCfg().getName());

        try {
            PrintWriter writer;
            writer = new PrintWriter(this.configFile);
            for (String line : config)
                writer.println(line);

            writer.close();
        } catch (Exception ignored) {
        }
    }

    public void applyConfig() {
        List<String> config = this.parseConfigFile();

        for (String line : config) {
            if (line.startsWith(loadedConfigPrefix)) {
                Rat.instance.configManager.loadConfigByName(line.replace(loadedConfigPrefix, ""));
            }
        }
    }

    private List<String> parseConfigFile() {
        List<String> configFileContents = new ArrayList<>();
        Scanner reader = null;

        try {
            reader = new Scanner(this.configFile);
        } catch (Exception ignored) {
        }

        while (reader.hasNextLine())
            configFileContents.add(reader.nextLine());

        return configFileContents;
    }

    private List<String> StringListToList(String[] strings) {
        List<String> array = new ArrayList<>();
        Collections.addAll(array, strings);
        return array;
    }

}
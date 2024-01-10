package me.sleepyfish.rat.utils.config;

import me.sleepyfish.rat.Rat;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class ClientConfig {

    private final File configFile;
    private final String fileName = "RatConfig";
    private final String loadedConfigPrefix = "loaded-cfg~ ";

    public ClientConfig() {
        final File configDir = Rat.instance.fileManager.getMainPath();
        if (!configDir.exists())
            configDir.mkdir();

        this.configFile = new File(configDir, fileName);
        if (!this.configFile.exists()) {
            try {
                this.configFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveConfig() {
        final List<String> config = new ArrayList<>();
        config.add(loadedConfigPrefix + Rat.instance.configManager.getModuleCfg().getName());

        try {
            final PrintWriter writer = new PrintWriter(this.configFile);
            for (final String line : config)
                writer.println(line);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyConfig() {
        final List<String> config = this.parseConfigFile();

        for (final String line : config) {
            if (line.startsWith(loadedConfigPrefix)) {
                Rat.instance.configManager.loadConfigByName(line.replace(loadedConfigPrefix, ""));
            }
        }
    }

    private final List<String> parseConfigFile() {
        final List<String> configFileContents = new ArrayList<>();
        Scanner reader = null;

        try {
            reader = new Scanner(this.configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (reader.hasNextLine())
            configFileContents.add(reader.nextLine());

        return configFileContents;
    }

    private final List<String> StringListToList(final String[] strings) {
        final List<String> array = new ArrayList<>();
        Collections.addAll(array, strings);
        return array;
    }

}
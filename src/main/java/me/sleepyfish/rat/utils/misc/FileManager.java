package me.sleepyfish.rat.utils.misc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class FileManager {

    private final File mainPath;
    private final File capePath;
    private final File configPath;

    private final boolean hasOptifine;
    private final File optifineConfigPath;

    public static final String resourcePath = "rat";

    public FileManager() {
        this.mainPath = new File(System.getProperty("user.home") + File.separator + ".ratclient");
        if (!mainPath.isDirectory())
            mainPath.mkdirs();

        if (mainPath.exists()) {
            this.capePath = new File(mainPath.getPath() + File.separator + "capes");
            if (!capePath.isDirectory())
                capePath.mkdirs();

            this.configPath = new File(mainPath.getPath() + File.separator + "configs");
            if (!configPath.isDirectory())
                configPath.mkdirs();
        } else {
            this.capePath = null;
            this.configPath = null;
        }

        this.optifineConfigPath = new File(MinecraftUtils.mc.mcDataDir.getPath() + File.separator + "optionsof.txt");
        this.hasOptifine = this.optifineConfigPath.exists();
    }

    public final File getMainPath() {
        return mainPath;
    }

    public final File getConfigPath() {
        return configPath;
    }

    public final File getCapePath() {
        return capePath;
    }

    public final File getOptifineConfigPath() {
        return optifineConfigPath;
    }

    public boolean hasOptifine() {
        return hasOptifine;
    }

    public final StringBuilder readOptifineConfig() {
        if (!this.hasOptifine())
            return new StringBuilder("Optifine check failed");

        try {
            final StringBuilder c = new StringBuilder();
            try (final BufferedReader r = new BufferedReader(new FileReader(this.getOptifineConfigPath()))) {
                String l;
                while ((l = r.readLine()) != null) {
                    c.append(l).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return c;
        } catch (Exception e) {
            return new StringBuilder(e.getMessage());
        }
    }

    public void setContentBasedOnCondition(final StringBuilder content, final String state, final boolean isEnabled) {
        final String newLine = "ofFastMath:" + (isEnabled ? "true" : "false");
        content.append(newLine);

        try (final BufferedWriter w = new BufferedWriter(new FileWriter(this.getOptifineConfigPath()))) {
            w.write(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
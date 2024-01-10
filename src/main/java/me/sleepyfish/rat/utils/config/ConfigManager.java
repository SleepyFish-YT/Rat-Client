package me.sleepyfish.rat.utils.config;

import com.google.gson.JsonObject;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.cosmetics.impl.Cape;

import java.io.File;
import java.awt.Desktop;
import java.util.Objects;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class ConfigManager {

    private final ArrayList<Config> configs;
    private Config moduleCfg;
    public File cfgDir;

    public ConfigManager() {
        this.configs = new ArrayList<>();

        this.cfgDir = Rat.instance.fileManager.getConfigPath();

        this.discoverConfigs();
        final File defaultFile = new File(this.cfgDir, "default.cfg");
        this.moduleCfg = new Config(defaultFile);

        if (!defaultFile.exists()) {
            this.save();

            try {
                Desktop.getDesktop().open(this.cfgDir);
                Desktop.getDesktop().open(defaultFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            this.loadConfigByName("default");
        }
    }

    public void unInject() {
        this.save();

        this.cfgDir = null;
        this.moduleCfg = null;
        this.configs.clear();
    }

    public void discoverConfigs() {
        configs.clear();
        if (cfgDir.listFiles() == null || !(Objects.requireNonNull(cfgDir.listFiles()).length > 0))
            return;

        for (final File file : Objects.requireNonNull(cfgDir.listFiles())) {
            if (file.getName().endsWith(".cfg"))
                configs.add(new Config(new File(file.getPath())));
        }
    }

    public final Config getModuleCfg() {
        return moduleCfg;
    }

    public void save() {
        final JsonObject data = new JsonObject();
        data.addProperty("client", Rat.instance.getName());
        data.addProperty("version", Rat.instance.getVersion());
        data.addProperty("author", MinecraftUtils.mc.getSession().getUsername());
        data.addProperty("created", LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonth() + "/" + LocalDate.now().getYear());

        String nC = Rat.instance.cosmeticUtils.getCurrentCape();
        if (Objects.equals(nC, "")) {
            nC = Rat.instance.cosmeticUtils.getCapeByName("Black Rat").getName();
        }

        data.addProperty("cape", nC);

        final JsonObject ratsObj = new JsonObject();
        for (final Module mod : Rat.instance.moduleManager.getModules()) {
            ratsObj.add(mod.getName(), mod.getConfigAsJson());
        }

        data.add("modules", ratsObj);
        this.moduleCfg.save(data);
    }

    public void setModuleCfg(final Config moduleCfg) {
        this.save();

        this.moduleCfg = moduleCfg;
        final JsonObject cfgData = moduleCfg.getData();
        final Cape configCape = Rat.instance.cosmeticUtils.getCapeByName(cfgData.get("cape").getAsString());
        Rat.instance.cosmeticUtils.setCurrentCape(configCape.getName());

        final JsonObject ratData = cfgData.get("modules").getAsJsonObject();
        for (final Module mod : Rat.instance.moduleManager.getModules()) {
            if (ratData.has(mod.getName())) {
                mod.applyConfigFromJson(
                        ratData.get(mod.getName()).getAsJsonObject()
                );
            } else {
                if (mod.isEnabled())
                    mod.toggle();
            }
        }
    }

    public void loadConfigByName(final String replace) {
        this.discoverConfigs();

        if (configs.isEmpty()) {
            this.save();
            return;
        }

        for (final Config config : configs) {
            if (config.getName().equals(replace))
                this.setModuleCfg(config);
        }
    }

    public final ArrayList<Config> getConfigs() {
        this.discoverConfigs();
        return configs;
    }

    public void copyConfig(final Config config, final String s) {
        final File file = new File(cfgDir, s);
        final Config newConfig = new Config(file);
        newConfig.save(config.getData());
    }

    public void deleteConfig(final Config config) {
        config.file.delete();

        if (config.getName().equals(this.moduleCfg.getName())) {
            this.discoverConfigs();

            if (this.configs.size() < 2) {
                this.save();

                final File defaultFile = new File(cfgDir, "default.cfg");
                this.moduleCfg = new Config(defaultFile);

                this.save();
            } else {
                this.moduleCfg = this.configs.get(0);
            }

            this.save();
        }
    }

}
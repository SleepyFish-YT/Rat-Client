package me.sleepyfish.rat.utils.config;

import com.google.gson.JsonObject;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.cosmetics.impl.capes.Cape;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class ConfigManager {

    public File cfgDir;
    private final ArrayList<Config> configs;
    private Config moduleCfg;

    public ConfigManager() {
        this.cfgDir = new File(System.getProperty("user.home") + File.separator + ".ratclient" + File.separator + "configs");
        this.configs = new ArrayList<>();

        if (!cfgDir.isDirectory()) {
            cfgDir.mkdirs();
        }

        this.discoverConfigs();
        File defaultFile = new File(cfgDir, "default.cfg");
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

        for (File file : Objects.requireNonNull(cfgDir.listFiles())) {
            if (file.getName().endsWith(".cfg")) {
                configs.add(new Config(new File(file.getPath())));
            }
        }
    }

    public Config getModuleCfg() {
        return moduleCfg;
    }

    public void save() {
        JsonObject data = new JsonObject();
        data.addProperty("client", Rat.instance.getName());
        data.addProperty("version", Rat.instance.getVersion());
        data.addProperty("author", MinecraftUtils.mc.getSession().getUsername());
        data.addProperty("created", LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonth() + "/" + LocalDate.now().getYear());

        String nC = Rat.instance.cosmeticUtils.getCurrentCape();
        if (Objects.equals(nC, "")) {
            nC = Rat.instance.cosmeticUtils.getCapeByName("Black Rat").getName();
        }

        data.addProperty("cape", nC);

        JsonObject ratsObj = new JsonObject();
        for (Module mod : Rat.instance.moduleManager.getModules()) {
            ratsObj.add(mod.getName(), mod.getConfigAsJson());
        }

        data.add("modules", ratsObj);
        this.moduleCfg.save(data);
    }

    public void setModuleCfg(Config moduleCfg) {
        this.moduleCfg = moduleCfg;

        JsonObject cfgData = moduleCfg.getData();
        Cape configCape = Rat.instance.cosmeticUtils.getCapeByName(cfgData.get("cape").getAsString());
        if (configCape != null)
            Rat.instance.cosmeticUtils.setCurrentCape(configCape.getName());

        JsonObject ratData = cfgData.get("modules").getAsJsonObject();

        if (ratData == null)
            return;

        for (Module mod : Rat.instance.moduleManager.getModules()) {
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

    public void loadConfigByName(String replace) {
        this.discoverConfigs();

        if (configs.isEmpty()) {
            this.save();
            return;
        }

        for (Config config : configs) {
            if (config.getName().equals(replace))
                this.setModuleCfg(config);
        }
    }

    public ArrayList<Config> getConfigs() {
        this.discoverConfigs();
        return configs;
    }

    public void copyConfig(Config config, String s) {
        File file = new File(cfgDir, s);
        Config newConfig = new Config(file);
        newConfig.save(config.getData());
    }

    public void resetConfig() {
        Rat.instance.moduleManager.getModules().forEach(Module::unInject);
        this.save();
    }

    public void deleteConfig(Config config) {
        config.file.delete();

        if (config.getName().equals(this.moduleCfg.getName())) {
            this.discoverConfigs();

            if (this.configs.size() < 2) {
                this.resetConfig();

                File defaultFile = new File(cfgDir, "default.cfg");
                this.moduleCfg = new Config(defaultFile);

                this.save();
            } else {
                this.moduleCfg = this.configs.get(0);
            }

            this.save();
        }
    }

}
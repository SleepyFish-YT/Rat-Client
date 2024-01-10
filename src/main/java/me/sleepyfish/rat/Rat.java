package me.sleepyfish.rat;

import me.sleepyfish.rat.gui.GuiManager;
import me.sleepyfish.rat.modules.ModuleFields;
import me.sleepyfish.rat.modules.ModuleManager;
import me.sleepyfish.rat.utils.misc.FileManager;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.config.ConfigManager;
import me.sleepyfish.rat.event.function.EventManager;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.cosmetics.CosmeticManager;
import me.sleepyfish.rat.utils.anticheat.AntiCheatManager;

import java.time.Month;
import java.time.LocalDateTime;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Rat {

    public static final Rat instance = new Rat();

    private final String name, ver;

    public final boolean isDecember;

    private final String youTube, discord;

    public final FileManager fileManager;
    public final EventManager eventManager;

    public AntiCheatManager antiCheat;
    public ModuleManager moduleManager;
    public ModuleFields moduleFields;
    public CosmeticManager cosmeticUtils;
    public GuiManager guiManager;
    public ConfigManager configManager;

    public Rat() {
        if (this.getClass().getSigners() != null)
            WindowsUtils.crash();

        this.name = "Rat Client";
        this.ver = "1.1";

        // Booleans
        this.isDecember = LocalDateTime.now().getMonth() == Month.DECEMBER;

        this.youTube = "https://youtube.com/@SleepyFishh";
        this.discord = "https://discord.gg/7JXXvkufJK";

        // Managers
        this.fileManager = new FileManager();
        this.eventManager = new EventManager();
    }

    public void inject() {

        // Utils  ------------------------------------------
        {
            FontUtils.init();
            WindowsUtils.init();
        }

        // Managers  ---------------------------------------
        {
            this.antiCheat = new AntiCheatManager();
            this.moduleManager = new ModuleManager();
            this.moduleFields = new ModuleFields();
            this.cosmeticUtils = new CosmeticManager();
            this.configManager = new ConfigManager();
            this.guiManager = new GuiManager();
        }

        WindowsUtils.clearRam();
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.ver;
    }

    public String getAuthorYoutube() {
        return this.youTube;
    }

    public String getDiscord() {
        return this.discord;
    }

}
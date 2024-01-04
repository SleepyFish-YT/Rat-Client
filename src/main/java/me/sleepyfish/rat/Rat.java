package me.sleepyfish.rat;

import me.sleepyfish.rat.event.function.EventManager;
import me.sleepyfish.rat.gui.GuiManager;
import me.sleepyfish.rat.modules.ModuleManager;
import me.sleepyfish.rat.utils.cosmetics.CosmeticManager;
import me.sleepyfish.rat.utils.config.ConfigManager;
import me.sleepyfish.rat.utils.file.FileManager;
import me.sleepyfish.rat.utils.misc.InjectionUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.ShaderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

import java.time.Month;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class Rat {

    public static final Rat instance = new Rat();

    public boolean isDecember;

    public String name;
    public String ver;

    public String youTube;
    public String discord;

    public EventManager eventManager;
    public ModuleManager moduleManager;
    public CosmeticManager cosmeticUtils;
    public GuiManager guiManager;

    public FileManager fileManager;

    public ConfigManager configManager;

    public void inject() {
        if (this.getClass().getSigners() != null)
            WindowsUtils.crash();

        // Variables  --------------------------------------
        {
            this.name = "Rat Client";
            this.ver = "1.0";

            // Booleans
            this.isDecember = LocalDateTime.now().getMonth() == Month.DECEMBER;

            this.youTube = "https://youtube.com/c/SleepyFish_YT";
            this.discord = "https://discord.gg/7JXXvkufJK";
        }

        // Utils  ------------------------------------------
        {
            InputUtils.mouseX = 0;
            InputUtils.mouseY = 0;

            FontUtils.init();

            WindowsUtils.ratTitle = this.getName() + " 1.8.9 (v" + this.getVersion() + ")";
            WindowsUtils.setWindowTitle(WindowsUtils.ratTitle);

            GuiUtils.ob = new Object();

            RenderUtils.roundedShader = new ShaderUtils(MinecraftUtils.resourcePath + "/shaders/roundedRect.frag");
            RenderUtils.roundTextedShader = new ShaderUtils(MinecraftUtils.resourcePath + "/shaders/roundRectTextured.frag");
            RenderUtils.roundedGrdntShader = new ShaderUtils(MinecraftUtils.resourcePath + "/shaders/roundedRectGradient.frag");
        }

        // Managers  ---------------------------------------
        {
            this.fileManager = new FileManager();

            this.eventManager = new EventManager();
            this.moduleManager = new ModuleManager();
            this.cosmeticUtils = new CosmeticManager();

            this.guiManager = new GuiManager();

            {
                // Setting changelog for GuiChangelog
                ArrayList<String> lines = new ArrayList<>();

                // Header: Fix: Added: Deleted: NewLine

                String add = "+ ";
                String del = "- ";
                String header = "* Release: ";
                String fix = "? ";
                String info = ". ";
                String newLn = "# ";

                lines.add(header + "1.0");
                lines.add(fix + "Fixed Scroll wheel not working in Cosmetics Gui");
                lines.add(fix + "Fixed Chat cant be disabled bug");
                lines.add(add + "Made mouse keybinds in inventory work");
                lines.add(add + "Added Chat Animations");
                lines.add(add + "Added Chat setting to remove chat");
                lines.add(add + "Added Chat setting to remove background");
                lines.add(add + "Added Chat Limit unlocker");
                lines.add(add + "Added Chat Custom font");
                lines.add(add + "Added Smooth Module scroll animation");
                lines.add(add + "Added Smoothness to the Entity in cape gui");
                lines.add(add + "Added Rat client logo ontop of Inventory");
                lines.add(add + "Added Smooth Cape scroll animation");
                lines.add(del + "Removed the Username in cape gui");
                lines.add(newLn);

                lines.add(header + "0.9-PRE");
                lines.add(fix + "Fixed Keybinds");
                lines.add(add + "Made Font rendering better");
                lines.add(add + "Made Flying speed not work on servers");
                lines.add(newLn);

                lines.add(header + "0.8-PRE");
                lines.add(fix + "Fixed Keystrokes hover");
                lines.add(fix + "Fixed Walter cape");
                lines.add(fix + "Fixed Zoom working inside Gui's");
                lines.add(fix + "Fixed Freelook working inside Gui's");
                lines.add(fix + "Fixed wrong text in Toggle Sprint");
                lines.add(fix + "Fixed Flight speed on Toggle Sprint");
                lines.add(add + "Added some usernames to a list to render a rat icon nearby the nametag");
                lines.add(add + "Added Server IP");
                lines.add(add + "Added Setting IP Text");
                lines.add(add + "Added Server Ping");
                lines.add(add + "Added Server Ping text");
                lines.add(add + "Added Remove backgrounds for Nametags");
                lines.add(add + "Made Nametags rotate with your freecam rotations");
                lines.add(add + "Added Remove all Nametags");
                lines.add(add + "Made the crosshair stop rendering when in gui");
                lines.add(add + "Added Cosmetic icon in Module Move Gui");
                lines.add(add + "Added Settings text in Module Move Gui");
                lines.add(add + "Added a check if its december, then use a different main icon and render snowfall");
                lines.add(add + "Added Changelog Gui");
                lines.add(add + "Added Config manager");
                lines.add(add + "Made the Gui better");
                lines.add(add + "Made Freelook save the old state");
                lines.add(add + "Renamed Old animations to Animations");
                lines.add(add + "Renamed Boss Bar to Bossbar");
                lines.add(add + "Renamed Free look to Freelook");
                lines.add(del + "Removed useless font drawing features");
                lines.add(newLn);

                lines.add(header + "0.7-PRE");
                lines.add(add + "Added option to use vanilla Gui");
                lines.add(add + "Added Module icons");
                lines.add(add + "Added 'Cancel effects' which removes effects behind ur camera");
                lines.add(add + "Added 'Cancel items' which removes items behind ur camera");
                lines.add(newLn);

                lines.add(header + "0.6-PRE");
                lines.add(add + "Added 'Cancel Entities' which removes items behind ur camera");
                lines.add(add + "Added lunar like Main menu");
                lines.add(add + "Added lunar like Module menu");
                lines.add(add + "Added Cps");
                lines.add(add + "Added Fps");
                lines.add(add + "Added Name");
                lines.add(add + "Added Keystrokes");
                lines.add(add + "Added Zoom");
                lines.add(add + "Added Counter");
                lines.add(add + "Added More modules to change minecraft hud rendering");
                lines.add(add + "Added Gui click Sounds");
                lines.add(add + "Added Settings to change Keybinds");
                lines.add(info + "Changed icons");
                lines.add(add + "Added intro animation to the icon when opening gui");
                lines.add(fix + "Fixed memory leak (above 5gb of ram usage)");
                lines.add(add + "Added hypixel working Free Look");
                lines.add(add + "Added Fix Hit delay");
                lines.add(add + "Added custom font");
                lines.add(add + "Added Old sneak animation");
                lines.add(add + "Added Coordinates");
                lines.add(add + "Added Performance module");
                lines.add(add + "Added Cosmetics");

                this.guiManager.getRatGuiChangelog().lines = lines;
            }

            this.configManager = new ConfigManager();
        }

        System.gc();
        InjectionUtils.oneTimeInjectChecks();
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
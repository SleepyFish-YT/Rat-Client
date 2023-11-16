package me.sleepyfish.rat;

import me.sleepyfish.rat.event.function.EventManager;
import me.sleepyfish.rat.gui.GuiManager;
import me.sleepyfish.rat.modules.ModuleManager;
import me.sleepyfish.rat.utils.capes.CapeManager;
import me.sleepyfish.rat.utils.misc.InjectionUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.ShaderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import net.minecraft.client.Minecraft;

public class Rat {

    public static final Rat instance = new Rat();

    public String name;
    public String ver;

    public String youTube;
    public String discord;

    public EventManager eventManager;
    public ModuleManager moduleManager;
    public CapeManager capeManager;
    public GuiManager guiManager;

    public void inject() {

        // Variables  --------------------------------------
        {
            this.name = "Rat Client";
            this.ver = "0.6";

            this.youTube = "https://youtube.com/c/SleepyFish_YT";
            this.discord = "https://discord.gg/7JXXvkufJK";
        }

        // Utils  ------------------------------------------
        {
            MinecraftUtils.mc = Minecraft.getMinecraft();
            MinecraftUtils.path = "rat";

            InputUtils.MOUSE_RIGHT = 1;
            InputUtils.MOUSE_RIGHT_EVENT = 4;
            InputUtils.MOUSE_LEFT = 0;
            InputUtils.MOUSE_LEFT_EVENT = 16;
            InputUtils.mouseX = 0;
            InputUtils.mouseY = 0;

            FontUtils.init();

            WindowsUtils.ratTitle = this.getName() + " 1.8.9 (v" + this.getVersion() + ")";
            WindowsUtils.setWindowTitle(WindowsUtils.ratTitle);

            GuiUtils.ob = new Object();

            RenderUtils.roundedTexturedShader = new ShaderUtils(MinecraftUtils.path + "/shaders/roundRectTextured.frag");
            RenderUtils.roundedShader = new ShaderUtils(MinecraftUtils.path + "/shaders/roundedRect.frag");
            RenderUtils.roundedGradientShader = new ShaderUtils(MinecraftUtils.path + "/shaders/roundedRectGradient.frag");
        }

        // Managers  ---------------------------------------
        {
            this.eventManager = new EventManager();
            this.moduleManager = new ModuleManager();
            this.capeManager = new CapeManager();
            this.guiManager = new GuiManager();
        }

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
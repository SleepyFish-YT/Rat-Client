package me.sleepyfish.rat.modules;

import me.sleepyfish.rat.modules.hud.*;
import me.sleepyfish.rat.modules.impl.*;
import me.sleepyfish.rat.modules.cheat.*;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class ModuleManager {

    private boolean interesting = false;
    private ArrayList<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();

        // Working  -----------------------------
        this.modules.add(new SettingModule());
        this.modules.add(new Performance());
        this.modules.add(new Freelook());
        this.modules.add(new CPSCounter());
        this.modules.add(new FpsCounter());
        this.modules.add(new Keystrokes());
        this.modules.add(new ToggleSprint());
        this.modules.add(new Fullbright());
        this.modules.add(new BossBar());
        this.modules.add(new Hotbar());
        this.modules.add(new Hitdelay());
        this.modules.add(new Zoom());
        this.modules.add(new Ping());
        this.modules.add(new ServerIp());
        this.modules.add(new Counter());
        this.modules.add(new NameDisplay());
        this.modules.add(new Crosshair());
        this.modules.add(new Coordinates());
        this.modules.add(new Nametags());
        this.modules.add(new FreeMouse());
        this.modules.add(new Animations());

        // Not working  -------------------------
        this.modules.add(new Chat());
        this.modules.add(new Tablist());

        // Interesting  -------------------------
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_H) || MinecraftUtils.mc.getSession().getUsername().startsWith("SleepyFish_Y")) {
            this.interesting = true;

            this.modules.add(new Eagle());
            this.modules.add(new TriggerBot());
            this.modules.add(new Clicker());
            this.modules.add(new FastPlace());
            this.modules.add(new FruitBridgeAssist());
        }
    }

    public int getCount() {
        return this.modules.size();
    }

    public void unInject() {
        this.modules.forEach(Module::unInject);
        this.modules.clear();
        this.modules = null;
    }

    public Module getModByClass(Class<?> modClass) {
        return this.getModules().stream().filter(mod -> mod.getClass().equals(modClass)).findFirst().orElse(null);
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public boolean hasFailed() {
        return this.interesting;
    }

}
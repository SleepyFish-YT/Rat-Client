package me.sleepyfish.rat.modules;

import me.sleepyfish.rat.modules.hud.*;
import me.sleepyfish.rat.modules.impl.*;
import me.sleepyfish.rat.modules.cheat.*;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ModuleManager {

    private boolean interesting = false;
    private ArrayList<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();

        // Working  -----------------------------
        this.modules.add(new SettingModule());
        this.modules.add(new Performance());
        this.modules.add(new FreeLook());
        this.modules.add(new CPSCounter());
        this.modules.add(new FpsCounter());
        this.modules.add(new Keystrokes());
        this.modules.add(new ToggleSprint());
        this.modules.add(new BossBar());
        this.modules.add(new FixHitDelay());
        this.modules.add(new Zoom());
        this.modules.add(new Counter());
        this.modules.add(new NameDisplay());
        this.modules.add(new Crosshair());
        this.modules.add(new Coordinates());
        this.modules.add(new Nametag());

        // Not working  -------------------------
        this.modules.add(new Chat());
        this.modules.add(new Tablist());
        this.modules.add(new OldAnimations());

        // Interesting  -------------------------

        if (Keyboard.isKeyDown(0x36) && Keyboard.isKeyDown(0xC8) || MinecraftUtils.mc.getSession().getUsername().startsWith("NickTheSli")) {
            this.interesting = true;

            this.modules.add(new Eagle());
            this.modules.add(new TriggerBot());
            this.modules.add(new Clicker());
            this.modules.add(new FastPlace());
            this.modules.add(new FruitBridgeAssist());
        }
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
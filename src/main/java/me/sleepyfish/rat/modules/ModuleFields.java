package me.sleepyfish.rat.modules;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.hud.*;
import me.sleepyfish.rat.modules.impl.*;
import me.sleepyfish.rat.modules.cheat.*;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * <p>
 * Info: This class is dumb, hashmap would also work but im to bored (this btw gives around 60 fps boost)
 *
 * @author SleepyFish 2024
 */
public class ModuleFields {

    public final Module Hotbar;
    public final Module Hitdelay;
    public final Module Crosshair;
    public final Module Nametags;
    public final Module Animations;
    public final Module Chat;

    public final Module FastPlace;
    public final Module Performance;
    public final Freelook Freelook;

    public ModuleFields() {
        this.Hotbar = this.getMod(Hotbar.class);
        this.Hitdelay = this.getMod(Hitdelay.class);
        this.Crosshair = this.getMod(Crosshair.class);
        this.Nametags = this.getMod(Nametags.class);
        this.Animations = this.getMod(Animations.class);
        this.Chat = this.getMod(Chat.class);
        this.FastPlace = this.getMod(FastPlace.class);
        this.Performance = this.getMod(Performance.class);
        this.Freelook = (Freelook) this.getMod(Freelook.class);
    }

    public final Module getMod(final Class<? extends Module> clazz) {
        return Rat.instance.moduleManager.getModules().stream().filter(module -> module.getClass().equals(clazz)).findFirst().get();
    }

}
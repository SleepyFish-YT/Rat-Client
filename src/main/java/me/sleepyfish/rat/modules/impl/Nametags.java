package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.event.EventRenderEntity;
import me.sleepyfish.rat.event.function.RatEvent;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class Nametags extends Module {

    public static ToggleSetting removeAll;
    public static ToggleSetting renderBg;

    public Nametags() {
        super("Nametag", "Render your Nametag in F3");

        this.addSetting(this.removeAll = new ToggleSetting("Remove all", "Removes all Nametags", false));
        this.addSetting(this.renderBg = new ToggleSetting("Render backgrounds", "Render Nametags background", true));

        this.toggle();
    }

    public static <T extends EntityLivingBase> void renderName(T en, double x, double y, double z, CallbackInfo ci) {
        //ci.cancel();
    }

    @RatEvent
    public void onRenderEntity(EventRenderEntity e) {
        // e.setCancelled(true);
    }

}
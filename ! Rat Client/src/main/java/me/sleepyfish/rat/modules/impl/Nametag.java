package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.event.EventRenderEntity;
import me.sleepyfish.rat.event.function.RatEvent;

import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Nametag extends Module {

    public Nametag() {
        super("Nametag", "Render your Nametag in F3");

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
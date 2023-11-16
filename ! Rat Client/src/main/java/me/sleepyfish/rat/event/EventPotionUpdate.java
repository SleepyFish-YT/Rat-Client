package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;

import net.minecraft.potion.PotionEffect;

public class EventPotionUpdate extends Event {

    public PotionEffect potioneffect;

    public EventPotionUpdate(PotionEffect potioneffect)  {
        this.potioneffect = potioneffect;
    }

}
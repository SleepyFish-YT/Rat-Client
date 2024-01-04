package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;

import net.minecraft.potion.PotionEffect;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class EventPotionUpdate extends Event {

    public PotionEffect potioneffect;

    public EventPotionUpdate(PotionEffect potioneffect)  {
        this.potioneffect = potioneffect;
    }

}
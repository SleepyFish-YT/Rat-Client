package me.sleepyfish.rat.utils.render.animations.normal;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public enum Direction {

    FORWARDS, BACKWARDS;

    public Direction opposite() {
        return this == FORWARDS ? BACKWARDS : FORWARDS;
    }

}
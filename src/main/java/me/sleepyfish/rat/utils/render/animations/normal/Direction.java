package me.sleepyfish.rat.utils.render.animations.normal;

// Class from SMok Client by SleepyFish
public enum Direction {
    FORWARDS,
    BACKWARDS;

    public Direction opposite() {
        return this == FORWARDS ? BACKWARDS : FORWARDS;
    }

}
package me.sleepyfish.rat.event;

import me.sleepyfish.rat.event.function.Event;

public class EventPlayerHeadRotation extends Event {

    private float yaw;
    private float pitch;

    public EventPlayerHeadRotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

}
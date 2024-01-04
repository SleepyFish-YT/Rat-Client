package me.sleepyfish.rat.utils.render.animations.normal.impl;

import me.sleepyfish.rat.utils.render.animations.normal.Animation;

// Class from SMok Client by SleepyFish
public class DecelerateAnimation extends Animation {

    public DecelerateAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    @Override
    protected double getEquation(double x) {
        double x1 = x / (double) this.duration;
        return 1.0 - (x1 - 1.0) * (x1 - 1.0);
    }

}
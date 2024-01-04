package me.sleepyfish.rat.utils.render.animations.normal.impl;

import me.sleepyfish.rat.utils.render.animations.normal.Animation;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
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
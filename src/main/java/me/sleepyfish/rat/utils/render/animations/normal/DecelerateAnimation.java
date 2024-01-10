package me.sleepyfish.rat.utils.render.animations.normal;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class DecelerateAnimation extends Animation {

    public DecelerateAnimation(final int ms, final double endPoint) {
        super(ms, endPoint);
    }

    @Override
    protected double getEquation(final double x) {
        final double x1 = x / (double) this.duration;
        return 1.0 - (x1 - 1.0) * (x1 - 1.0);
    }

}
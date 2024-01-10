package me.sleepyfish.rat.utils.render.animations.simple;

import me.sleepyfish.rat.utils.render.animations.normal.Animation;
import me.sleepyfish.rat.utils.render.animations.normal.DecelerateAnimation;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class AnimationUtils {

    public static float calculateCompensation(final float target, float current, final double speed, final long delta) {
        final float diff = current - target;
        final double add = (double) delta * (speed / 50.0);

        if ((double) diff > speed) {
            if ((double) current - add > (double) target) {
                current = (float) ((double) current - add);
            } else {
                current = target;
            }
        } else if ((double) diff < -speed) {
            if ((double) current + add < (double) target) {
                current = (float) ((double) current + add);
            } else {
                current = target;
            }
        } else {
            current = target;
        }

        return current;
    }

    public static Animation getAnimation() {
        return new DecelerateAnimation(450, 1.0);
    }

}
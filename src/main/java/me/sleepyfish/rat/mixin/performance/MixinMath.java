package me.sleepyfish.rat.mixin.performance;

import me.sleepyfish.rat.modules.impl.Performance;

import org.apache.commons.math3.util.FastMath;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(Math.class)
public class MixinMath {

    @Shadow(remap = false) @Final
    public static double E = 2.71828D;

    @Shadow(remap = false) @Final
    public static double PI = 3.141592D;

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double sin(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.sin(a);
        } else {
            return StrictMath.sin(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double ceil(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.ceil(a);
        } else {
            return StrictMath.ceil(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double rint(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.rint(a);
        } else {
            return StrictMath.rint(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double floor(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.floor(a);
        } else {
            return StrictMath.floor(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double cos(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.cos(a);
        } else {
            return StrictMath.cos(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double tan(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.tan(a);
        } else {
            return StrictMath.tan(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double asin(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.asin(a);
        } else {
            return StrictMath.asin(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double acos(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.acos(a);
        } else {
            return StrictMath.acos(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double toRadians(final double angle) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.toRadians(angle);
        } else {
            return angle / 180.0 * Math.PI;
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double toDegrees(final double degree) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.toDegrees(degree);
        } else {
            return degree * 180.0 / Math.PI;
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double exp(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.exp(a);
        } else {
            return StrictMath.exp(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double log(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.log(a);
        } else {
            return StrictMath.log(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double log10(final double a) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.log10(a);
        } else {
            return StrictMath.log10(a);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double hypot(final double x, final double y) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.hypot(x, y);
        } else {
            return StrictMath.hypot(x, y);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double ulp(final double d) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.ulp(d);
        } else {
            return StrictMath.ulp(d);
        }
    }

    /**
     * @author SleepyFish
     * @reason faster math
     */
    @Overwrite(remap = false)
    public static double scalb(final double d, final int n) {
        if (Performance.fastMath.isEnabled()) {
            return FastMath.scalb(d, n);
        } else {
            return StrictMath.scalb(d, n);
        }
    }

}
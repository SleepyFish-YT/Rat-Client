package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;

public class MotionBlur extends Module {

    public static float motionBlurValue = 0.5f;

    public MotionBlur() {
        super("Motion Blur", "Makes fast moving objects appear Blurred");
    }

}
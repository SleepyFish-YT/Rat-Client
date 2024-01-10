package me.sleepyfish.rat.utils.misc;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class TimerUtils {

    private long lastMs;

    public TimerUtils() {
        this.lastMs = 0L;
    }

    public long getTime() {
        return lastMs;
    }

    public void setTime(final long lastMs) {
        this.lastMs = lastMs;
    }

    public boolean cpsTimer(final int min, final int max) {
        return this.delay(1000L / ThreadLocalRandom.current().nextInt(min, max + 1));
    }

    public boolean delay(final long nextDelay) {
        return System.currentTimeMillis() - lastMs >= nextDelay;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

}
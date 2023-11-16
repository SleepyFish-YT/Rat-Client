package me.sleepyfish.rat.utils.misc;

import java.util.concurrent.ThreadLocalRandom;

public class TimerUtils {

    private long lastMs;

    public TimerUtils() {
        this.lastMs = 0L;
    }

    public boolean cpsTimer(int min, int max) {
        return this.delay(1000L / ThreadLocalRandom.current().nextInt(min, max + 1));
    }

    public boolean delay(long nextDelay) {
        return System.currentTimeMillis() - lastMs >= nextDelay;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

}
package me.sleepyfish.rat.utils.misc;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
import java.util.concurrent.ThreadLocalRandom;

public class TimerUtils {

    private long lastMs;

    public TimerUtils() {
        this.lastMs = 0L;
    }

    public long getTime() {
        return lastMs;
    }

    public void setTime(long lastMs) {
        this.lastMs = lastMs;
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
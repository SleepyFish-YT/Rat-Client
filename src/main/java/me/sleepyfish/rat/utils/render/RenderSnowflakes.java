package me.sleepyfish.rat.utils.render;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.client.gui.GuiScreen;

import java.util.Random;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class RenderSnowflakes {

    private static final Random random = new Random();
    private static final int MIN_SNOWFLAKES = 80;
    private static final int MAX_SNOWFLAKES = 200;
    private static final int NUM_SNOWFLAKES = random.nextInt(MAX_SNOWFLAKES - MIN_SNOWFLAKES + 1) + MIN_SNOWFLAKES;

    private static Snowflake[] snowflakes = new Snowflake[NUM_SNOWFLAKES];

    public static void renderSnowfall(GuiScreen screen) {
        for (final Snowflake snowflake : snowflakes)
            snowflake.renderUpdate(screen);
    }

    public static void resetSnowflakes() {
        snowflakes = new Snowflake[NUM_SNOWFLAKES];
        for (int i = 0; i < NUM_SNOWFLAKES; i++)
            snowflakes[i] = new Snowflake();
    }

    static class Snowflake {

        private final Random random;
        private final double speed;
        private int x, y;

        public Snowflake() {
            this.random = new Random();
            this.x = (int) (random.nextDouble() * MinecraftUtils.mc.displayWidth);
            this.y = (int) (random.nextDouble() * MinecraftUtils.mc.displayHeight);
            this.speed = random.nextDouble() * 2 + 1;
        }

        public void renderUpdate(GuiScreen screen) {
            y += speed;
            if (y > screen.height) {
                y = random.nextInt(51);
                x = random.nextInt(screen.width);
            }

            int size = 5;
            RenderUtils.drawRound(x, y, size, size, size + 0.1F, ColorUtils.getIconColorAlpha());
        }
    }

    static {
        if (Rat.instance.isDecember) {
            for (int i = 0; i < NUM_SNOWFLAKES; i++)
                snowflakes[i] = new Snowflake();
        }
    }

}
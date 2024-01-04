package me.sleepyfish.rat.utils.misc;

public class SoundUtils {

    public static void playSound(String name, float volume, float pitch) {

        if (MinecraftUtils.mc.thePlayer != null) {
            switch (name) {
                case "click":
                    MinecraftUtils.mc.thePlayer.playSound("random.click", volume, pitch);
                    break;

                case "chestOpen":
                    MinecraftUtils.mc.thePlayer.playSound("random.chestOpen", volume, pitch);
                    break;

                case "chestClose":
                    MinecraftUtils.mc.thePlayer.playSound("random.chestClose", volume, pitch);
                    break;

                default:
                    MinecraftUtils.mc.thePlayer.playSound(name, volume, pitch);
                    break;
            }
        }
    }

    public static void playClick() {
        SoundUtils.playSound("click", 1.0F, 0.8F);
    }

}
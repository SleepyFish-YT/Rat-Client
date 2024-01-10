package me.sleepyfish.rat.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.profiler.Profiler;
import net.minecraft.crash.CrashReport;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.LanguageManager;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraftFixEarlyCrashNoReports {

    @Shadow @Final
    public Profiler mcProfiler;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    public GameSettings gameSettings;

    @Shadow @Final
    private String launchedVersion;

    @Shadow
    private LanguageManager mcLanguageManager;

    @Shadow public abstract void resize(int par1, int par2);

    /**
     *
     * @reason Fix early crashes (before an opengl context is created) causing crash reports not being generated
     * @author mojang
     */
    @Overwrite
    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport report) {
        final CrashReportCategory cat = report.getCategory();

        cat.addCrashSectionCallable("Launched Version", () -> MixinMinecraftFixEarlyCrashNoReports.this.launchedVersion);
        cat.addCrashSectionCallable("LWJGL", Sys::getVersion);

        try {
            GLContext.getCapabilities();
            cat.addCrashSectionCallable("OpenGL", () -> GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cat.addCrashSectionCallable("GL Caps", OpenGlHelper::getLogText);
        cat.addCrashSectionCallable("Using VBOs", () -> MixinMinecraftFixEarlyCrashNoReports.this.gameSettings.useVbo ? "Yes" : "No");

        cat.addCrashSectionCallable("Is Modded", () -> {
            final String string = ClientBrandRetriever.getClientModName();

            if (!string.equals("vanilla")) {
                return "Definitely; Client brand changed to '" + string + "'";
            } else {
                return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
            }
        });

        cat.addCrashSectionCallable("Type", () -> "Client (map_client.txt)");
        cat.addCrashSectionCallable("Resource Packs", () -> {
            final StringBuilder stringBuilder = new StringBuilder();

            for (final String string : MixinMinecraftFixEarlyCrashNoReports.this.gameSettings.resourcePacks) {
                if (stringBuilder.length() > 0)
                    stringBuilder.append(", ");

                stringBuilder.append(string);
                if (MixinMinecraftFixEarlyCrashNoReports.this.gameSettings.incompatibleResourcePacks.contains(string))
                    stringBuilder.append(" (incompatible)");
            }

            return stringBuilder.toString();
        });

        cat.addCrashSectionCallable("Current Language", () -> MixinMinecraftFixEarlyCrashNoReports.this.mcLanguageManager.getCurrentLanguage().toString());
        cat.addCrashSectionCallable("Profiler Position", () -> this.mcProfiler.profilingEnabled ? this.mcProfiler.getNameOfLastSection() : "N/A (disabled)");
        cat.addCrashSectionCallable("CPU", OpenGlHelper::getCpu);

        if (this.theWorld != null)
            this.theWorld.addWorldInfoToCrashReport(report);

        return report;
    }
}

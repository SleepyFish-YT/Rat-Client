package me.sleepyfish.rat.mixin;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.profiler.Profiler;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MixinMinecraftFixEarlyCrashNoReports {

    @Shadow @Final public Profiler mcProfiler;
    @Shadow public WorldClient theWorld;

    @Shadow public GameSettings gameSettings;

    @Shadow @Final private String launchedVersion;

    @Shadow private LanguageManager mcLanguageManager;

    /**
     *
     * @reason Fix early crashes (before an opengl context is created) causing crash reports not being generated
     * @author mojang
     */
    @Overwrite
    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable("Launched Version", () -> MixinMinecraftFixEarlyCrashNoReports.this.launchedVersion);
        crashReport.getCategory().addCrashSectionCallable("LWJGL", Sys::getVersion);

        // check if gl is available in the current thread
        try {
            GLContext.getCapabilities();
            crashReport.getCategory().addCrashSectionCallable("OpenGL", () -> GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936));
        } catch (IllegalStateException throwable) {
            // no-op if gl is not available
        }

        crashReport.getCategory().addCrashSectionCallable("GL Caps", OpenGlHelper::getLogText);
        crashReport.getCategory().addCrashSectionCallable("Using VBOs", () -> MixinMinecraftFixEarlyCrashNoReports.this.gameSettings.useVbo ? "Yes" : "No");

        crashReport.getCategory().addCrashSectionCallable("Is Modded", () -> {
            String string = ClientBrandRetriever.getClientModName();
            if (!string.equals("vanilla")) {
                return "Definitely; Client brand changed to '" + string + "'";
            } else {
                return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
            }
        });

        crashReport.getCategory().addCrashSectionCallable("Type", () -> "Client (map_client.txt)");
        crashReport.getCategory().addCrashSectionCallable("Resource Packs", () -> {
            StringBuilder stringBuilder = new StringBuilder();

            for (String string : MixinMinecraftFixEarlyCrashNoReports.this.gameSettings.resourcePacks) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }

                stringBuilder.append(string);
                if (MixinMinecraftFixEarlyCrashNoReports.this.gameSettings.incompatibleResourcePacks.contains(string)) {
                    stringBuilder.append(" (incompatible)");
                }
            }

            return stringBuilder.toString();
        });

        crashReport.getCategory().addCrashSectionCallable("Current Language", () -> MixinMinecraftFixEarlyCrashNoReports.this.mcLanguageManager.getCurrentLanguage().toString());
        crashReport.getCategory().addCrashSectionCallable("Profiler Position", () -> this.mcProfiler.profilingEnabled ? this.mcProfiler.getNameOfLastSection() : "N/A (disabled)");
        crashReport.getCategory().addCrashSectionCallable("CPU", OpenGlHelper::getCpu);

        if (this.theWorld != null)
            this.theWorld.addWorldInfoToCrashReport(crashReport);

        return crashReport;
    }
}

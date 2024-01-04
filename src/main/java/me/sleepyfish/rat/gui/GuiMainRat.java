package me.sleepyfish.rat.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.render.animations.snowflake.RenderSnowflakes;

import net.minecraft.client.gui.GuiScreen;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GuiMainRat {

    public static int logoAnimation;

    public static void drawGui(GuiScreen screen, int width, int height, boolean inCosmeticsGui, boolean inChangelogs, boolean accountFocused, boolean linksFocused,
                               boolean overChangelogs, boolean overRatCosmetics, boolean overAccounts, boolean overLinks, boolean overTheme,
                               boolean overMinecraftSettings, boolean overMinecraftLanguage, boolean overForgeModButton, boolean overShutdown,
                               boolean overSingleplayer, boolean overMultiplayer, boolean overExitGui, Object ob)
    {

        // Main menu icon animation
        if (Rat.instance.guiManager.useMixinMainMenuAnimation) {
            if (logoAnimation < 60)
                logoAnimation += 2;
            else Rat.instance.guiManager.useMixinMainMenuAnimation = false;
        } else {
            logoAnimation = 60;
        }

        Color newColor = new Color(185, 185, 185, (int) Math.min(logoAnimation * 2.5F, 250F));

        // Render logo with animation
        int logoSize = logoAnimation + 40;
        RenderUtils.drawImage("/gui/icon", width / 2 - (logoSize / 2), height / 2 - (logoSize - 70) - logoAnimation, logoSize, logoSize, newColor);

        if (logoAnimation >= 60) {

            // Rendering snowfall if its december
            if (Rat.instance.isDecember) {
                RenderSnowflakes.renderSnowfall(screen);
            }

            // Mojang copyrights
            String title = WindowsUtils.ratTitle;
            if (Rat.instance.moduleManager.hasFailed()) {
                title = WindowsUtils.ratTitle + " *";
            }

            FontUtils.drawFont(title, 4F, height - FontUtils.getFontHeight() - 2F, ColorUtils.getFontColor(1));
            FontUtils.drawFont("Copyright Mojang Studios. Do not distribute!", width - FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), height - FontUtils.getFontHeight() - 2F, ColorUtils.getFontColor(1));

            if (!inCosmeticsGui && !inChangelogs) {
                if (!accountFocused) {
                    RenderUtils.drawRound(10F, 10F, 15F, 15F, 5F, !overAccounts ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());

                    if (!linksFocused) {
                        RenderUtils.drawRound(35F, 10F, 15F, 15F, 5F, !overLinks ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                    } else {
                        RenderUtils.drawRound(35F, 10F, 47F, 32F, 5F, ColorUtils.getBackgroundBrighterColor());
                        FontUtils.drawFont("Discord", 37F, 12F, ColorUtils.getFontColor(ob));
                        FontUtils.drawFont("YouTube", 37F, 22F, ColorUtils.getFontColor(ob));
                        FontUtils.drawFont("Github", 37F, 32F, ColorUtils.getFontColor(ob));
                    }
                } else {
                    RenderUtils.drawRound(10F, 10F, FontUtils.getFontWidth(MinecraftUtils.mc.getSession().getUsername()) + 8F, 15F, 5F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont(MinecraftUtils.mc.getSession().getUsername(), 14, 14, ColorUtils.getFontColor(ob));
                }

                if (overChangelogs) {
                    RenderUtils.drawRound(width / 2F - 50F - FontUtils.getFontWidth(Rat.instance.getName() + " Changelogs") / 2F, height - 52F, FontUtils.getFontWidth(Rat.instance.getName() + " Changelogs") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont(Rat.instance.getName() + " Changelogs", width / 2F - 48 - FontUtils.getFontWidth(Rat.instance.getName() + " Changelogs") / 2F, height - 50F, ColorUtils.getFontColor(ob));
                }

                if (overRatCosmetics) {
                    RenderUtils.drawRound(width / 2F - 25F - FontUtils.getFontWidth(Rat.instance.getName() + " Cosmetics") / 2F, height - 52F, FontUtils.getFontWidth(Rat.instance.getName() + " Cosmethics") - 2F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont(Rat.instance.getName() + " Cosmetics", width / 2F - 23 - FontUtils.getFontWidth(Rat.instance.getName() + " Cosmetics") / 2F, height - 50F, ColorUtils.getFontColor(ob));
                }

                if (overMinecraftSettings) {
                    RenderUtils.drawRound(width / 2F - 0F - FontUtils.getFontWidth("Minecraft Settings") / 2F, height - 52F, FontUtils.getFontWidth("Minecraft Settings") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont("Minecraft Settings", width / 2F + 2 - FontUtils.getFontWidth("Minecraft Settings") / 2F, height - 50F, ColorUtils.getFontColor(ob));
                }

                if (overMinecraftLanguage) {
                    RenderUtils.drawRound(width / 2F + 25F - FontUtils.getFontWidth("Language") / 2F, height - 52F, FontUtils.getFontWidth("Language") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont("Language", width / 2F + 27 - FontUtils.getFontWidth("Language") / 2F, height - 50F, ColorUtils.getFontColor(ob));
                }

                if (overForgeModButton) {
                    RenderUtils.drawRound(width / 2F + 50F - FontUtils.getFontWidth("Forge Mods") / 2F, height - 52F, FontUtils.getFontWidth("Forge Mods") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont("Forge Mods", width / 2F + 52 - FontUtils.getFontWidth("Forge Mods") / 2F, height - 50F, ColorUtils.getFontColor(ob));
                }

                if (!overShutdown) {
                    RenderUtils.drawRound(width - 25F, 10F, 15F, 15F, 5F, ColorUtils.getBackgroundBrighterColor());
                } else {
                    RenderUtils.drawRound(width - 25F, 10F, 15F, 15F, 5F, new Color(0xF0B44747));
                    RenderUtils.drawRound(width - 27F, 35F, FontUtils.getFontWidth("Quit") + 4, 11, 1, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont("Quit", width - 25, 37, ColorUtils.getFontColor(ob));
                }

                RenderUtils.drawRound(width - 50F, 10F, 15F, 15F, 5F, !overTheme ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                RenderUtils.drawRound(width / 2F - 50F - 12.5F, height - 35F, 20F, 20F, 5F, !overChangelogs ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                RenderUtils.drawRound(width / 2F - 25F - 12.5F, height - 35F, 20F, 20F, 5F, !overRatCosmetics ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                RenderUtils.drawRound(width / 2F - 0F - 12.5F, height - 35F, 20F, 20F, 5F, !overMinecraftSettings ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                RenderUtils.drawRound(width / 2F + 25F - 12.5F, height - 35F, 20F, 20F, 5F, !overMinecraftLanguage ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                RenderUtils.drawRound(width / 2F + 50F - 12.5F, height - 35F, 20F, 20F, 5F, !overForgeModButton ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());

                RenderUtils.drawRound(width / 2F - 100, height / 2F + 30, 200, 16, 8, !overSingleplayer ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                FontUtils.drawFont("Single", width / 2F - (FontUtils.getFontWidth("Single") / 2F), (height / 2F + 30) + 6F, ColorUtils.getFontColor(ob));

                RenderUtils.drawRound(width / 2F - 100, height / 2F + 55, 200, 16, 8, !overMultiplayer ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                FontUtils.drawFont("Multi", width / 2F - (FontUtils.getFontWidth("Multi") / 2F), (height / 2F + 55) + 6F, ColorUtils.getFontColor(ob));
            }

            if (inCosmeticsGui) {
                GuiUtils.drawCustomGui(1, width, height, true);

                FontUtils.drawFont("Here's nothing yet...", (width / 2F - (FontUtils.getFontWidth("Here's nothing yet...") / 2F) + 15), (height / 2F) + 15F, ColorUtils.getFontColor(ob));
                RenderUtils.drawRound(width / 2F - 244, height / 2F + 121, 24, 24, 5, !overExitGui ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().brighter());
            }

            if (inChangelogs) {
                MinecraftUtils.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiChangelog());
            }
        }
    }

}
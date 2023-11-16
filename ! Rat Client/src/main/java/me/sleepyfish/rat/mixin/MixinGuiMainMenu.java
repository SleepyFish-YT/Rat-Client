package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.client.GuiModList;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {

    private static final String path = MinecraftUtils.path + "/gui/mainmenu/panorama_";

    @Final
    @Shadow
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{
            new ResourceLocation(path + "0.png"), new ResourceLocation(path + "1.png"),
            new ResourceLocation(path + "2.png"), new ResourceLocation(path + "3.png"),
            new ResourceLocation(path + "4.png"), new ResourceLocation(path + "5.png")
    };

    @Shadow
    protected abstract void renderSkybox(int mouseX, int mouseY, float partialTicks);

    // ---------------------------------------

    private boolean allowUpdateTheme;

    private boolean inSettingsGui;
    private boolean inCosmeticsGui;

    private boolean overExitGui;
    private boolean overRatCosmetics;
    private boolean overRatSettings;
    private boolean overMinecraftSettings;
    private boolean overMinecraftLanguage;
    private boolean overForgeModButton;
    private boolean overShutdown;
    private boolean overAccounts;

    private boolean linksFocused;
    private boolean accountFocused;

    private boolean overSingleplayer;
    private boolean overMultiplayer;
    private boolean overLinks;
    private boolean overTheme;

    private boolean overLinksExpanded;
    private boolean overAccountsExpanded;

    private int mouseX;
    private int mouseY;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ic) {
        this.allowUpdateTheme = false;
        this.inSettingsGui = false;
        this.inCosmeticsGui = false;

        this.overTheme = false;
        this.overExitGui = false;
        this.overShutdown = false;
        this.overAccounts = false;
        this.overLinks = false;

        this.linksFocused = false;
        this.accountFocused = false;

        this.overSingleplayer = false;
        this.overMultiplayer = false;
        this.overRatSettings = false;
        this.overMinecraftSettings = false;
        this.overMinecraftLanguage = false;
        this.overForgeModButton = false;
        this.overRatCosmetics = false;

        this.overLinksExpanded = false;
        this.overAccountsExpanded = false;

        this.buttonList.clear();
    }

    /**
     * @author SleepyFish
     * @reason modify GuiMainMenu
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        this.updateMouse();

        float newPartialTicks;
        if (allowUpdateTheme) {
            newPartialTicks = partialTicks * 2F;
        } else {
            newPartialTicks = partialTicks;
        }

        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, newPartialTicks);
        GlStateManager.enableAlpha();

        String title = WindowsUtils.ratTitle;
        if (Rat.instance.moduleManager.hasFailed()) {
            title = WindowsUtils.ratTitle + " *";
        }

        FontUtils.drawFont(title, 4F, this.height - FontUtils.getFontHeight() - 2F, ColorUtils.getFontColor(1));
        FontUtils.drawFont("Copyright Mojang Studios. Do not distribute!", this.width - FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), this.height - FontUtils.getFontHeight() - 2F, ColorUtils.getFontColor(1));

        // --------------------------------------------------------------------------------------------------------------------------------------------------

        if (!inCosmeticsGui && !inSettingsGui) {
            if (!accountFocused) {
                RenderUtils.drawRound(10F, 10F, 15F, 15F, 5F, !overAccounts ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());

                if (!linksFocused) {
                    RenderUtils.drawRound(35F, 10F, 15F, 15F, 5F, !overLinks ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
                } else {
                    RenderUtils.drawRound(35F, 10F, 47F, 32F, 5F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont("Discord", 37F, 12F, ColorUtils.getFontColor(this));
                    FontUtils.drawFont("YouTube", 37F, 22F, ColorUtils.getFontColor(this));
                    FontUtils.drawFont("Github", 37F, 32F, ColorUtils.getFontColor(this));
                }
            } else {
                RenderUtils.drawRound(10F, 10F, FontUtils.getFontWidth(mc.getSession().getUsername()) + 8F, 15F, 5F, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont(mc.getSession().getUsername(), 14, 14, ColorUtils.getFontColor(this));
            }

            RenderUtils.drawImage("/gui/main_icon", this.width / 2 - 40, this.height / 2 - 70, 80, 80, ColorUtils.getIconColorAlpha());

            if (overRatSettings) {
                RenderUtils.drawRound(this.width / 2F - 50F - FontUtils.getFontWidth(Rat.instance.getName() + " Settings") / 2F, this.height - 52F, FontUtils.getFontWidth(Rat.instance.getName() + " Settings") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont(Rat.instance.getName() + " Settings", this.width / 2F - 48 - FontUtils.getFontWidth(Rat.instance.getName() + " Settings") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
            }

            if (overRatCosmetics) {
                RenderUtils.drawRound(this.width / 2F - 25F - FontUtils.getFontWidth(Rat.instance.getName() + " Cosmetics") / 2F, this.height - 52F, FontUtils.getFontWidth(Rat.instance.getName() + " Cosmethics") - 2F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont(Rat.instance.getName() + " Cosmetics", this.width / 2F - 23 - FontUtils.getFontWidth(Rat.instance.getName() + " Cosmetics") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
            }

            if (overMinecraftSettings) {
                RenderUtils.drawRound(this.width / 2F - 0F - FontUtils.getFontWidth("Minecraft Settings") / 2F, this.height - 52F, FontUtils.getFontWidth("Minecraft Settings") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont("Minecraft Settings", this.width / 2F + 2 - FontUtils.getFontWidth("Minecraft Settings") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
            }

            if (overMinecraftLanguage) {
                RenderUtils.drawRound(this.width / 2F + 25F - FontUtils.getFontWidth("Language") / 2F, this.height - 52F, FontUtils.getFontWidth("Language") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont("Language", this.width / 2F + 27 - FontUtils.getFontWidth("Language") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
            }

            if (overForgeModButton) {
                RenderUtils.drawRound(this.width / 2F + 50F - FontUtils.getFontWidth("Forge Mods") / 2F, this.height - 52F, FontUtils.getFontWidth("Forge Mods") + 4F, 12F, 1F, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont("Forge Mods", this.width / 2F + 52 - FontUtils.getFontWidth("Forge Mods") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
            }

            if (!overShutdown) {
                RenderUtils.drawRound(this.width - 25F, 10F, 15F, 15F, 5F, ColorUtils.getBackgroundBrighterColor());
            } else {
                RenderUtils.drawRound(this.width - 25F, 10F, 15F, 15F, 5F, new Color(0xF0B44747));
                RenderUtils.drawRound(this.width - 27F, 35F, FontUtils.getFontWidth("Quit") + 4, 11, 1, ColorUtils.getBackgroundBrighterColor());
                FontUtils.drawFont("Quit", this.width - 25, 37, ColorUtils.getFontColor(this));
            }

            RenderUtils.drawRound(this.width - 50F, 10F, 15F, 15F, 5F, !overTheme ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            RenderUtils.drawRound(this.width / 2F - 50F - 12.5F, this.height - 35F, 20F, 20F, 5F, !overRatSettings ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            RenderUtils.drawRound(this.width / 2F - 25F - 12.5F, this.height - 35F, 20F, 20F, 5F, !overRatCosmetics ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            RenderUtils.drawRound(this.width / 2F - 0F - 12.5F, this.height - 35F, 20F, 20F, 5F, !overMinecraftSettings ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            RenderUtils.drawRound(this.width / 2F + 25F - 12.5F, this.height - 35F, 20F, 20F, 5F, !overMinecraftLanguage ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            RenderUtils.drawRound(this.width / 2F + 50F - 12.5F, this.height - 35F, 20F, 20F, 5F, !overForgeModButton ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());

            RenderUtils.drawRound(this.width / 2F - 100, this.height / 2F + 30, 200, 16, 8, !overSingleplayer ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            FontUtils.drawFont("Singleplayer", this.width / 2F - (FontUtils.getFontWidth("Singleplayer") / 2F), this.height / 2F + 36, ColorUtils.getFontColor(this));
            RenderUtils.drawRound(this.width / 2F - 100, this.height / 2F + 55, 200, 16, 8, !overMultiplayer ? ColorUtils.getBackgroundBrighterColor() : ColorUtils.getBackgroundBrighterColor().brighter());
            FontUtils.drawFont("Multiplayer", this.width / 2F - (FontUtils.getFontWidth("Multiplayer") / 2F), this.height / 2F + 61, ColorUtils.getFontColor(this));
        }

        if (inCosmeticsGui || inSettingsGui) {
            GuiUtils.drawCustomGui(1, this.width, this.height, true);

            FontUtils.drawFont("Here's nothing yet...", (width / 2F - (FontUtils.getFontWidth("Here's nothing yet...") / 2F) + 15), (height / 2F) + 15F, ColorUtils.getFontColor(this));
            RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 121, 24, 24, 5, !overExitGui ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().brighter());
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    public void mouseClicked(int x, int y, int b, CallbackInfo ic) {
        if (b == 0) {

            if (overTheme) {
                allowUpdateTheme = !allowUpdateTheme;
            }

            if (overExitGui) {
                inSettingsGui = false;
                inCosmeticsGui = false;
            }

            if (overSingleplayer) {
                mc.displayGuiScreen(new GuiSelectWorld(this));
            }

            if (overMultiplayer) {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }

            if (overRatSettings) {
                if (!inSettingsGui) {
                    inSettingsGui = true;
                }
            }

            if (overRatCosmetics) {
                if (!inCosmeticsGui) {
                    inCosmeticsGui = true;
                }
            }

            if (overMinecraftSettings) {
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            }

            if (overMinecraftLanguage) {
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
            }

            if (overForgeModButton) {
                mc.displayGuiScreen(new GuiModList(this));
            }

            if (overShutdown) {
                mc.shutdown();
            }

            if (overAccounts) {
                accountFocused = true;
            }

            if (accountFocused) {
                if (!overAccounts) {
                    if (!overAccountsExpanded) {
                        accountFocused = false;
                    }
                }
            }

            if (overLinks) {
                linksFocused = true;
            }

            if (linksFocused) {
                if (!overLinks && overLinksExpanded) {
                    if (InputUtils.isInside(this.mouseX, this.mouseY, 35F, 10F, 80F, 10F)) {
                        WindowsUtils.openURL(Rat.instance.getDiscord());
                    }

                    if (InputUtils.isInside(this.mouseX, this.mouseY, 35F, 24F, 80F, 10F)) {
                        WindowsUtils.openURL(Rat.instance.getAuthorYoutube());
                    }

                    if (InputUtils.isInside(this.mouseX, this.mouseY, 35F, 40F, 80F, 10F)) {
                        WindowsUtils.openURL("github does not exist yet");
                    }
                }

                if (!overLinks) {
                    if (!overLinksExpanded) {
                        linksFocused = false;
                    }
                }
            }
        }
    }

    @Inject(method = "keyTyped", at = @At("TAIL"))
    public void keyTyped(char chara, int keycode, CallbackInfo ic) {
        if (keycode == 0x01) {
            if (inSettingsGui) {
                inSettingsGui = false;
            }

            if (inCosmeticsGui) {
                inCosmeticsGui = false;
            }

            if (linksFocused) {
                linksFocused = false;
            }

            if (accountFocused) {
                accountFocused = false;
            }
        }
    }

    public void updateMouse() {
        if (!inCosmeticsGui && !inSettingsGui) {
            this.overAccounts = InputUtils.isInside(this.mouseX, this.mouseY, 10F, 10F, 15F, 15F);
            this.overAccountsExpanded = InputUtils.isInside(this.mouseX, this.mouseY, 10F, 10F, FontUtils.getFontWidth(mc.getSession().getUsername()) + 8, 15F);
            this.overLinks = InputUtils.isInside(this.mouseX, this.mouseY, 35F, 10F, 15F, 15F);
            this.overLinksExpanded = InputUtils.isInside(this.mouseX, this.mouseY, 35F, 10F, 80F, 40F);
            this.overShutdown = InputUtils.isInside(this.mouseX, this.mouseY, this.width - 25F, 10F, 15F, 15F);
            this.overTheme = InputUtils.isInside(this.mouseX, this.mouseY, this.width - 50F, 10F, 15F, 15F);
            this.overMultiplayer = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F - 100F, this.height / 2F + 55F, 200F, 16F);
            this.overSingleplayer = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F - 100F, this.height / 2F + 30F, 200F, 16F);
            this.overMinecraftSettings = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F - 0F - 12.5F, this.height - 35F, 20F, 20F);
            this.overForgeModButton = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F + 50F - 12.5F, this.height - 35F, 20F, 20F);
            this.overMinecraftLanguage = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F + 25F - 12.5F, this.height - 35F, 20F, 20F);
            this.overRatCosmetics = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F - 25F - 12.5F, this.height - 35F, 20F, 20F);
            this.overRatSettings = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F - 50F - 12.5F, this.height - 35F, 20F, 20F);
        } else {
            this.overExitGui = InputUtils.isInside(this.mouseX, this.mouseY, this.width / 2F - 244F, this.height / 2F + 121F, 24F, 24F);
        }
    }

}
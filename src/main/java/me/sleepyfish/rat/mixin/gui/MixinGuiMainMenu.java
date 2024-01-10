package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.render.RenderSnowflakes;

import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {

    private static final String path = MinecraftUtils.resourcePath + "/gui/mainmenu/panorama_";

    @Shadow
    private static ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {
            new ResourceLocation(path + "0.png"), new ResourceLocation(path + "1.png"),
            new ResourceLocation(path + "2.png"), new ResourceLocation(path + "3.png"),
            new ResourceLocation(path + "4.png"), new ResourceLocation(path + "5.png")
    };

    @Shadow
    protected abstract void renderSkybox(int mouseX, int mouseY, float partialTicks);

    private GuiUtils.Button overExitGui;
    private GuiUtils.Button overRatCosmetics;
    private GuiUtils.Button overChangelogs;
    private GuiUtils.Button overMinecraftSettings;
    private GuiUtils.Button overMinecraftLanguage;
    private GuiUtils.Button overForgeModButton;
    private GuiUtils.Button overShutdown;
    private GuiUtils.Button overAccounts;

    private GuiUtils.Button overSingleplayer;
    private GuiUtils.Button overMultiplayer;
    private GuiUtils.Button overLinks;
    private GuiUtils.Button overTheme;
    private GuiUtils.Button overLinksExpanded;
    private GuiUtils.Button overAccountsExpanded;

    private boolean inChangelogs;
    private boolean inCosmeticsGui;
    private boolean linksFocused;
    private boolean accountFocused;
    private boolean appliedBG;
    public boolean  useOldGui;

    public byte logoAnimation;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ci) {
        if (Rat.instance.isDecember)
            RenderSnowflakes.resetSnowflakes();

        this.logoAnimation = 0;

        this.useOldGui = false;
        this.appliedBG = false;

        this.inChangelogs = false;
        this.inCosmeticsGui = false;

        this.linksFocused = false;
        this.accountFocused = false;

        this.overAccounts = new GuiUtils.Button("", 10F, 10F, 15F, 15F, 5F);
        this.overAccountsExpanded = new GuiUtils.Button("", 10F, 10F, FontUtils.getFontWidth(this.mc.getSession().getUsername()) + 8, 15F, 5F);
        this.overLinks = new GuiUtils.Button("", 35F, 10F, 15F, 15F, 5F);
        this.overLinksExpanded = new GuiUtils.Button("", 35F, 10F, 80F, 40F, 5F);
        this.overShutdown = new GuiUtils.Button("", this.width - 25F, 10F, 15F, 15F, 5F);
        this.overTheme = new GuiUtils.Button("", this.width - 50F, 10F, 15F, 15F, 5F);

        final double wid = (this.width / 2F);
        this.overMultiplayer = new GuiUtils.Button("Multiplayer", wid - 100F, this.height / 2F + 55F, 200F, 16F, 10F);
        this.overSingleplayer = new GuiUtils.Button("Singleplayer", wid - 100F, this.height / 2F + 30F, 200F, 16F, 10F);

        final double hei = this.height - 35F;
        this.overMinecraftSettings = new GuiUtils.Button("", wid - 0F - 12.5F, hei, 20F, 20F, 5F);
        this.overForgeModButton = new GuiUtils.Button("", wid + 50F - 12.5F, hei, 20F, 20F, 5F);
        this.overMinecraftLanguage = new GuiUtils.Button("", wid + 25F - 12.5F, hei, 20F, 20F, 5F);
        this.overRatCosmetics = new GuiUtils.Button("", wid - 25F - 12.5F, hei, 20F, 20F, 5F);
        this.overChangelogs = new GuiUtils.Button("", wid - 50F - 12.5F, hei, 20F, 20F, 5F);
        this.overExitGui = new GuiUtils.Button("", wid - 244F, this.height / 2F + 121F, 24F, 24F, 5F);
    }

    /**
     * @author SleepyFish
     * @reason modify GuiMainMenu
     */
    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (!this.useOldGui) {
            ci.cancel();
        } else {
            return;
        }

        // Render not visable stuff to bypass white this

        if (!this.appliedBG) {
            String path = "textures/gui/title/background/panorama_";
            if (!this.useOldGui) {
                path = MixinGuiMainMenu.path;
            }

            titlePanoramaPaths = new ResourceLocation[] {
                    new ResourceLocation(path + "0.png"), new ResourceLocation(path + "1.png"),
                    new ResourceLocation(path + "2.png"), new ResourceLocation(path + "3.png"),
                    new ResourceLocation(path + "4.png"), new ResourceLocation(path + "5.png")
            };

            this.appliedBG = true;
        }

        this.overAccountsExpanded.render();
        this.overLinksExpanded.render();
        this.overExitGui.render();

        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        this.renderSkybox(mouseX, mouseY, partialTicks);

        // Main menu icon animation
        if (Rat.instance.guiManager.useMixinMainMenuAnimation) {
            if (this.logoAnimation < 60)
                this.logoAnimation += 2;
            else Rat.instance.guiManager.useMixinMainMenuAnimation = false;
        } else {
            this.logoAnimation = 60;
        }

        final Color newColor = new Color(185, 185, 185, (int) Math.min(this.logoAnimation * 2.5F, 250F));

        // Render logo with animation
        final byte logoSize = (byte) (this.logoAnimation + 40);
        RenderUtils.drawImage("/gui/icon", this.width / 2 - (logoSize / 2), this.height / 2 - (logoSize - 70) - this.logoAnimation, logoSize, logoSize, newColor);

        if (this.logoAnimation >= 60) {

            // Rendering snowfall if its december
            if (Rat.instance.isDecember) {
                RenderSnowflakes.renderSnowfall(this);
            }

            // Mojang copyrights
            String title = WindowsUtils.ratTitle;
            if (Rat.instance.moduleManager.hasFailed()) {
                title = WindowsUtils.ratTitle + " *";
            }

            FontUtils.drawFont(title, 4F, this.height - FontUtils.getFontHeight() - 2F, ColorUtils.getFontColor(1));
            FontUtils.drawFont("Copyright Mojang Studios. Do not distribute!", this.width - FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), this.height - FontUtils.getFontHeight() - 2F, ColorUtils.getFontColor(1));

            if (!this.inCosmeticsGui && !this.inChangelogs) {
                if (!this.accountFocused) {
                    this.overAccounts.render();

                    if (!this.linksFocused) {
                        this.overLinks.render();
                    } else {
                        RenderUtils.drawRound(35F, 10F, 47F, 32F, 5F, ColorUtils.getBackgroundBrighterColor());
                        FontUtils.drawFont("Discord", 37F, 12F, ColorUtils.getFontColor(this));
                        FontUtils.drawFont("YouTube", 37F, 22F, ColorUtils.getFontColor(this));
                        FontUtils.drawFont("Github", 37F, 32F, ColorUtils.getFontColor(this));
                    }
                } else {
                    RenderUtils.drawRound(10F, 10F, FontUtils.getFontWidth(MinecraftUtils.mc.getSession().getUsername()) + 8F, 15F, 5F, ColorUtils.getBackgroundBrighterColor());
                    FontUtils.drawFont(MinecraftUtils.mc.getSession().getUsername(), 14, 14, ColorUtils.getFontColor(this));
                }

                this.overChangelogs.render();
                if (this.overChangelogs.isInside()) {
                    FontUtils.drawFont(Rat.instance.getName() + " Changelogs", this.width / 2F - 48 - FontUtils.getFontWidth(Rat.instance.getName() + " Changelogs") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
                }

                this.overRatCosmetics.render();
                if (this.overRatCosmetics.isInside()) {
                    FontUtils.drawFont(Rat.instance.getName() + " Cosmetics", this.width / 2F - 23 - FontUtils.getFontWidth(Rat.instance.getName() + " Cosmetics") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
                }

                this.overMinecraftSettings.render();
                if (this.overMinecraftSettings.isInside()) {
                    FontUtils.drawFont("Minecraft Settings", this.width / 2F + 2 - FontUtils.getFontWidth("Minecraft Settings") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
                }

                this.overMinecraftLanguage.render();
                if (this.overMinecraftLanguage.isInside()) {
                    FontUtils.drawFont("Language", this.width / 2F + 27 - FontUtils.getFontWidth("Language") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
                }

                this.overForgeModButton.render();
                if (this.overForgeModButton.isInside()) {
                    FontUtils.drawFont("Forge Mods", this.width / 2F + 52 - FontUtils.getFontWidth("Forge Mods") / 2F, this.height - 50F, ColorUtils.getFontColor(this));
                }

                this.overShutdown.render();
                if (this.overShutdown.isInside()) {
                    final Color c = new Color(180, 70, 70, (int) this.overShutdown.getAnimationValue());
                    RenderUtils.drawRound(this.width - 25F, 10F, 15F, 15F, 5F, c);
                    FontUtils.drawFont("Quit", this.width - 25, 37, ColorUtils.getFontColor(this));
                }

                this.overTheme.render();
                this.overSingleplayer.render();
                this.overMultiplayer.render();
            }

            if (this.inCosmeticsGui) {
                GuiUtils.drawCustomGui(1, this.width, this.height, true);

                FontUtils.drawFont("Here's nothing yet...", (this.width / 2F - (FontUtils.getFontWidth("Here's nothing yet...") / 2F) + 15), (this.height / 2F) + 15F, ColorUtils.getFontColor(this));
                RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 121, 24, 24, 5, !overExitGui.isInside() ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().brighter());
            }

            if (this.inChangelogs) {
                this.mc.displayGuiScreen(Rat.instance.guiManager.getRatGuiChangelog());
            }
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void mouseClicked(int x, int y, int b, CallbackInfo ci) {
        if (!this.useOldGui) {
            ci.cancel();
        } else {
            return;
        }

        if (b == 0) {
            //if (InputUtils.isInside(width - FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), height - FontUtils.getFontHeight() - 2F, FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), FontUtils.getFontHeight())) {
            //
            //}

            if (this.overTheme.isInside()) {
                this.useOldGui = true;
                this.appliedBG = false;
            }

            if (this.overExitGui.isInside()) {
                this.inChangelogs = false;
                this.inCosmeticsGui = false;
            }

            if (this.overSingleplayer.isInside()) {
                mc.displayGuiScreen(new GuiSelectWorld(this));
            }

            if (this.overMultiplayer.isInside()) {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }

            if (this.overChangelogs.isInside()) {
                if (!this.inChangelogs) {
                    this.inChangelogs = true;
                }
            }

            if (this.overRatCosmetics.isInside()) {
                if (!this.inCosmeticsGui) {
                    this.inCosmeticsGui = true;
                }
            }

            if (this.overMinecraftSettings.isInside()) {
                this.mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            }

            if (this.overMinecraftLanguage.isInside()) {
                this.mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
            }

            if (this.overForgeModButton.isInside()) {
                this.mc.displayGuiScreen(new GuiModList(this));
            }

            if (this.overShutdown.isInside()) {
                this.mc.shutdown();
            }

            if (this.overAccounts.isInside()) {
                this.accountFocused = true;
            }

            if (this.accountFocused) {
                if (!this.overAccounts.isInside()) {
                    if (!this.overAccountsExpanded.isInside()) {
                        this.accountFocused = false;
                    }
                }
            }

            if (this.overLinks.isInside()) {
                this.linksFocused = true;
            }

            if (this.linksFocused) {
                if (!this.overLinks.isInside() && this.overLinksExpanded.isInside()) {
                    if (InputUtils.isInside(35F, 10F, 80F, 10F)) {
                        WindowsUtils.openURL(Rat.instance.getDiscord());
                    }

                    if (InputUtils.isInside(35F, 24F, 80F, 10F)) {
                        WindowsUtils.openURL(Rat.instance.getAuthorYoutube());
                    }

                    if (InputUtils.isInside(35F, 40F, 80F, 10F)) {
                        WindowsUtils.openURL("github does not exist yet");
                    }
                }

                if (!this.overLinks.isInside()) {
                    if (!this.overLinksExpanded.isInside()) {
                        this.linksFocused = false;
                    }
                }
            }
        }
    }

    @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
    public void keyTyped(char chara, int keycode, CallbackInfo ci) {
        if (!this.useOldGui) {
            ci.cancel();
        } else {
            return;
        }

        if (keycode == 0x01) {
            if (this.inChangelogs) {
                this.inChangelogs = false;
            }

            if (this.inCosmeticsGui) {
                this.inCosmeticsGui = false;
            }

            if (this.linksFocused) {
                this.linksFocused = false;
            }

            if (this.accountFocused) {
                this.accountFocused = false;
            }
        }
    }

        /*
    public void updateMouse() {
        if (!this.inCosmeticsGui && !this.inChangelogs) {
            this.overAccounts = InputUtils.isInside(10F, 10F, 15F, 15F);
            this.overAccountsExpanded = InputUtils.isInside(10F, 10F, FontUtils.getFontWidth(mc.getSession().getUsername()) + 8, 15F);
            this.overLinks = InputUtils.isInside(35F, 10F, 15F, 15F);
            this.overLinksExpanded = InputUtils.isInside(35F, 10F, 80F, 40F);
            this.overShutdown = InputUtils.isInside(this.width - 25F, 10F, 15F, 15F);
            this.overTheme = InputUtils.isInside(this.width - 50F, 10F, 15F, 15F);
            this.overMultiplayer = InputUtils.isInside(this.width / 2F - 100F, this.height / 2F + 55F, 200F, 16F);
            this.overSingleplayer = InputUtils.isInside(this.width / 2F - 100F, this.height / 2F + 30F, 200F, 16F);
            this.overMinecraftSettings = InputUtils.isInside(this.width / 2F - 0F - 12.5F, this.height - 35F, 20F, 20F);
            this.overForgeModButton = InputUtils.isInside(this.width / 2F + 50F - 12.5F, this.height - 35F, 20F, 20F);
            this.overMinecraftLanguage = InputUtils.isInside(this.width / 2F + 25F - 12.5F, this.height - 35F, 20F, 20F);
            this.overRatCosmetics = InputUtils.isInside(this.width / 2F - 25F - 12.5F, this.height - 35F, 20F, 20F);
            this.overChangelogs = InputUtils.isInside(this.width / 2F - 50F - 12.5F, this.height - 35F, 20F, 20F);
        } else {
            this.overExitGui = InputUtils.isInside(this.width / 2F - 244F, this.height / 2F + 121F, 24F, 24F);
        }
    }
    */

}
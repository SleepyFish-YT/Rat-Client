package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.gui.GuiMainRat;
import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.modules.impl.Hotbar;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.render.animations.normal.Animation;
import me.sleepyfish.rat.utils.render.animations.normal.Direction;
import me.sleepyfish.rat.utils.render.animations.normal.impl.EaseBackIn;
import me.sleepyfish.rat.utils.render.animations.snowflake.RenderSnowflakes;

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
 * @author Nexuscript 2024
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

    public boolean useOldGui;

    private boolean inChangelogs;
    private boolean inCosmeticsGui;

    private boolean overExitGui;
    private boolean overRatCosmetics;
    private boolean overChangelogs;
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

    private boolean appliedBG;

    public Animation introAnimation;
    public boolean close;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ci) {
        if (Rat.instance.isDecember)
            RenderSnowflakes.resetSnowflakes();

        Module hotbar = Rat.instance.moduleManager.getModByClass(Hotbar.class);

        if (hotbar.isEnabled()) {
            int x = hotbar.getGuiX() + 80;
            int y = hotbar.getGuiY() + 15;

            if (hotbar.getGuiX() + hotbar.getWidth() > this.width)
                x = this.width - ((hotbar.getWidth() + hotbar.getGuiX()) - hotbar.getGuiX()) - 2;

            if (hotbar.getGuiY() + hotbar.getHeight() > this.height)
                y = this.height - ((hotbar.getGuiY() + hotbar.getHeight()) - hotbar.getGuiY()) - 2;

            if (x < 0)
                x = 2;

            if (y < 0)
                y = 2;

            hotbar.setGuiX(x);
            hotbar.setGuiY(y);
        }

        GuiMainRat.logoAnimation = 0;

        useOldGui = false;
        appliedBG = false;

        inChangelogs = false;
        inCosmeticsGui = false;

        overTheme = false;
        overExitGui = false;
        overShutdown = false;
        overAccounts = false;
        overLinks = false;

        linksFocused = false;
        accountFocused = false;

        overSingleplayer = false;
        overMultiplayer = false;
        overChangelogs = false;
        overMinecraftSettings = false;
        overMinecraftLanguage = false;
        overForgeModButton = false;
        overRatCosmetics = false;

        overLinksExpanded = false;
        overAccountsExpanded = false;

        this.introAnimation = new EaseBackIn(450, 1, 2);
        this.close = false;
    }

    private void applyBG() {
        if (!this.appliedBG) {
            if (this.useOldGui) {
                String path = "textures/gui/title/background/panorama_";
                titlePanoramaPaths = new ResourceLocation[] {
                        new ResourceLocation(path + "0.png"), new ResourceLocation(path + "1.png"),
                        new ResourceLocation(path + "2.png"), new ResourceLocation(path + "3.png"),
                        new ResourceLocation(path + "4.png"), new ResourceLocation(path + "5.png")
                };
            } else {
                titlePanoramaPaths = new ResourceLocation[] {
                        new ResourceLocation(path + "0.png"), new ResourceLocation(path + "1.png"),
                        new ResourceLocation(path + "2.png"), new ResourceLocation(path + "3.png"),
                        new ResourceLocation(path + "4.png"), new ResourceLocation(path + "5.png")
                };
            }

            this.appliedBG = true;
        }
    }

    /**
     * @author SleepyFish
     * @reason modify GuiMainMenu
     */
    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {

        // Render not visable stuff to bypass white screen
        RenderUtils.drawRound(0, 0, 1, 1, 0, new Color(0, 0, 0, 0));
        FontUtils.drawFont("", 0, 0, new Color(0, 0, 0, 0));

        this.applyBG();

        if (!this.useOldGui) {
            ci.cancel();
        } else {
            return;
        }

        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        if(close) {
            introAnimation.setDirection(Direction.BACKWARDS);
            if(introAnimation.isDone(Direction.BACKWARDS)) {
                mc.displayGuiScreen(null);
            }
        }

        this.updateMouse();
        this.renderSkybox(mouseX, mouseY, partialTicks);

        GuiMainRat.drawGui(this, this.width, this.height, this.inCosmeticsGui, this.inChangelogs, this.accountFocused, this.linksFocused,
                this.overChangelogs, this.overRatCosmetics, this.overAccounts, this.overLinks, this.overTheme, this.overMinecraftSettings,
                this.overMinecraftLanguage, this.overForgeModButton, this.overShutdown, this.overSingleplayer, this.overMultiplayer,
                this.overExitGui, this);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void mouseClicked(int x, int y, int b, CallbackInfo ci) {
        if (!this.useOldGui) {
            ci.cancel();
        } else return;

        if (b == 0) {
            if (InputUtils.isInside(width - FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), height - FontUtils.getFontHeight() - 2F, FontUtils.getFontWidth("Copyright Mojang Studios. Do not distribute! "), FontUtils.getFontHeight())) {
                
            }

            if (this.overTheme) {
                this.useOldGui = true;
                this.appliedBG = false;
            }

            if (this.overExitGui) {
                this.inChangelogs = false;
                this.inCosmeticsGui = false;
            }

            if (this.overSingleplayer) {
                mc.displayGuiScreen(new GuiSelectWorld(this));
            }

            if (this.overMultiplayer) {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            }

            if (this.overChangelogs) {
                if (!this.inChangelogs) {
                    this.inChangelogs = true;
                }
            }

            if (this.overRatCosmetics) {
                if (!this.inCosmeticsGui) {
                    this.inCosmeticsGui = true;
                }
            }

            if (this.overMinecraftSettings) {
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            }

            if (this.overMinecraftLanguage) {
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
            }

            if (this.overForgeModButton) {
                mc.displayGuiScreen(new GuiModList(this));
            }

            if (this.overShutdown) {
                mc.shutdown();
            }

            if (this.overAccounts) {
                this.accountFocused = true;
            }

            if (this.accountFocused) {
                if (!this.overAccounts) {
                    if (!this.overAccountsExpanded) {
                        this.accountFocused = false;
                    }
                }
            }

            if (this.overLinks) {
                this.linksFocused = true;
            }

            if (this.linksFocused) {
                if (!this.overLinks && this.overLinksExpanded) {
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

                if (!this.overLinks) {
                    if (!this.overLinksExpanded) {
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

}
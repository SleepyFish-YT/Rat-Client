package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.impl.Chat;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.GlStateManager;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.Color;
import java.util.List;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat extends Gui {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    private boolean isScrolled;

    @Shadow
    public abstract int getLineCount();

    @Shadow @Final
    private List<ChatLine> drawnChatLines;

    @Shadow
    public abstract boolean getChatOpen();

    @Shadow
    public abstract float getChatScale();

    private int longestWidth = 5;

    /**
     * @author sleepy
     * @reason chat module
     */
    @Inject(method = "drawChat", at = @At("HEAD"), cancellable = true)
    public void drawChat(int yOffset, CallbackInfo ci) {
        if (Rat.instance.moduleFields.Chat.isEnabled()) {
            if (Chat.unlockChat.isEnabled())
                yOffset = 0;

            ci.cancel();

            if (Chat.scrollAnimation == null)
                return;

            if (this.mc.gameSettings.chatVisibility == EntityPlayer.EnumChatVisibility.HIDDEN)
                return;

            int lineCount = this.getLineCount();
            if (Chat.unlockChat.isEnabled()) {
                lineCount = 999 ^ 2;
            }

            final boolean chatOpen = this.getChatOpen();
            final int drawnChatLinesSize = this.drawnChatLines.size();

            float chatOpacity = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            if (Chat.removeChat.isEnabled())
                chatOpacity = 0F;

            if (drawnChatLinesSize > 0) {
                final float chatScale = this.getChatScale();
                final int chatLineHeight = longestWidth + 20;

                GlStateManager.pushMatrix();
                GlStateManager.translate(2F, 20F, 0F);
                GlStateManager.scale(chatScale, chatScale, 1F);

                int drawnLineIndex;
                int updateCounterDiff;
                int alpha;
                int drawnLineY;

                for (drawnLineIndex = 0; drawnLineIndex + (int) Chat.scrollAnimation.getValue() < drawnChatLinesSize && drawnLineIndex < lineCount; ++drawnLineIndex) {
                    final ChatLine chatLine = this.drawnChatLines.get(drawnLineIndex + (int) Chat.scrollAnimation.getValue());

                    if (chatLine != null) {
                        updateCounterDiff = yOffset - chatLine.getUpdatedCounter();

                        if (updateCounterDiff < 200 || chatOpen) {
                            double percentageFade = (double) updateCounterDiff / 200.0;
                            percentageFade = 1.0 - percentageFade;
                            percentageFade *= 10.0;
                            percentageFade = MathHelper.clamp_double(percentageFade, 0.0, 1.0);
                            percentageFade *= percentageFade;

                            alpha = (int) (255.0 * percentageFade);
                            if (chatOpen)
                                alpha = 255;

                            alpha = (int) ((float) alpha * chatOpacity);

                            if (alpha > 3) {
                                if (Chat.removeBackground.isEnabled())
                                    alpha = 0;

                                drawnLineY = 0;
                                final int drawnLinePosY = -drawnLineIndex * 9;

                                drawRect(drawnLineY, drawnLinePosY - 9, drawnLineY + chatLineHeight, drawnLinePosY, alpha / 2 << 24);

                                if (Chat.customFont.isEnabled()) {
                                    FontUtils.drawFont(chatLine.getChatComponent().getFormattedText(), (float) drawnLineY, (float) (drawnLinePosY - 8), new Color(16777215 + (alpha << 24)));

                                    if (longestWidth < FontUtils.getFontWidth(chatLine.getChatComponent().getFormattedText()))
                                        longestWidth = FontUtils.getFontWidth(chatLine.getChatComponent().getFormattedText());
                                } else {
                                    this.mc.fontRendererObj.drawStringWithShadow(chatLine.getChatComponent().getFormattedText(), (float) drawnLineY, (float) (drawnLinePosY - 8), 16777215 + (alpha << 24));

                                    if (longestWidth < mc.fontRendererObj.getStringWidth(chatLine.getChatComponent().getFormattedText()))
                                        longestWidth = mc.fontRendererObj.getStringWidth(chatLine.getChatComponent().getFormattedText());
                                }
                            }
                        }
                    }
                }

                if (chatOpen) {
                    int fontHeight = this.mc.fontRendererObj.FONT_HEIGHT;
                    if (Chat.customFont.isEnabled())
                        fontHeight = FontUtils.getFontHeight();

                    GlStateManager.translate(-3F, 0F, 0F);
                    final int totalHeight = drawnChatLinesSize * fontHeight + drawnChatLinesSize;
                    final int visibleHeight = lineCount * fontHeight + lineCount;
                    final float scrollPosPixels = Chat.scrollAnimation.getValue() * visibleHeight / drawnChatLinesSize;
                    final int scrollBarHeight = visibleHeight * visibleHeight / totalHeight;

                    if (totalHeight != visibleHeight) {
                        final int fadeAlpha = scrollPosPixels > 0 ? 170 : 96;
                        final int scrollBarColor = this.isScrolled ? 13382451 : 3355562;

                        RenderUtils.drawRound(0, -scrollPosPixels, 2, -scrollPosPixels - scrollBarHeight, 0, new Color(scrollBarColor + (fadeAlpha << 24)));
                        RenderUtils.drawRound(2, -scrollPosPixels, 1, -scrollPosPixels - scrollBarHeight, 0, new Color(13421772 + (fadeAlpha << 24)));
                    }

                    final InputUtils.SoarScroll scroll = InputUtils.getSoarScroll();

                    if (scroll != null) {
                        switch (scroll) {
                            case UP:
                                if (Chat.scrollY < -10) {
                                    Chat.scrollY += 20;
                                } else {
                                    if (drawnLineIndex > 5)
                                        Chat.scrollY = 0;
                                }
                                break;

                            case DOWN:
                                final byte maxScale = 4;

                                if (Chat.scrollY > -(drawnLineIndex * maxScale))
                                    Chat.scrollY -= 10;

                                if (drawnLineIndex > 5) {
                                    if (Chat.scrollY < -(drawnLineIndex * maxScale))
                                        Chat.scrollY = (drawnLineIndex * maxScale);
                                }
                                break;
                        }
                    }

                    Chat.scrollAnimation.setAnimation((float) Math.abs(Chat.scrollY), 8);
                }

                GlStateManager.popMatrix();
            }
        }
    }

    @Inject(method = "getChatComponent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiNewChat;scrollPos:I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getChatComponent(int mouseX, int mouseY, CallbackInfoReturnable<IChatComponent> cir, ScaledResolution scaledresolution, int i, float f, int j, int k, int l) {
        final int line = k / mc.fontRendererObj.FONT_HEIGHT;

        if (line >= getLineCount())
            cir.setReturnValue(null);
    }

    @Redirect(method = "deleteChatLine", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatLineID()I"))
    private int deleteChatLine(ChatLine instance) {
        if (instance == null)
            return -1;

        return instance.getChatLineID();
    }

}
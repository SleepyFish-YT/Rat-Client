package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.modules.hud.Hotbar;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;

import org.spongepowered.asm.mixin.Final;
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
@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {

    @Shadow
    @Final
    protected Minecraft mc;

    @Shadow
    @Final
    protected static ResourceLocation widgetsTexPath;

    @Shadow
    protected abstract void renderHotbarItem(int p_renderHotbarItem_1_, int p_renderHotbarItem_2_, int p_renderHotbarItem_3_, float p_renderHotbarItem_4_, EntityPlayer p_renderHotbarItem_5_);

    /**
     * @author sleepy
     * @reason hotbar module
     */
    @Inject(method = "renderTooltip", at = @At("HEAD"), cancellable = true)
    public void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        if (Rat.instance.moduleFields.Hotbar.isEnabled()) {
            ci.cancel();

            if (this.mc.getRenderViewEntity() == this.mc.thePlayer) {
                Hotbar.updatePos();

                GlStateManager.color(1F, 1F, 1F, 1F);
                this.mc.getTextureManager().bindTexture(widgetsTexPath);

                final EntityPlayer viewEntity = (EntityPlayer) this.mc.getRenderViewEntity();
                final int scaledWidth = Hotbar.posX;
                final float memZLevel = this.zLevel;
                this.zLevel = -90F;

                Hotbar.simpleAnimation.setAnimation(viewEntity.inventory.currentItem * 20, 20);

                if (Hotbar.clear.isEnabled()) {
                    final int itemSlotX = Hotbar.posX - 91 + (Hotbar.animated.isEnabled() ? (int) Hotbar.simpleAnimation.getValue() : (viewEntity.inventory.currentItem * 20));
                    drawRect(itemSlotX, Hotbar.posY, itemSlotX + 22, Hotbar.posY, new Color(230, 230, 230, 180).getRGB());
                } else {
                    this.drawTexturedModalRect(scaledWidth - 91, Hotbar.posY - 22, 0, 0, 182, 22);
                    this.drawTexturedModalRect(scaledWidth - 91 - 1 + (Hotbar.animated.isEnabled() ? Hotbar.simpleAnimation.getValue() : (viewEntity.inventory.currentItem * 20)), Hotbar.posY - 22 - 1, 0, 22, 24, 22);
                }

                this.zLevel = memZLevel;

                // ------  Items   ------

                GlStateManager.enableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                RenderHelper.enableGUIStandardItemLighting();

                for (short off = 0; off < 9; ++off) {
                    final int xx = Hotbar.posX - 90 + off * 20 + 2;
                    final int yy = Hotbar.posY - 16 - 3;
                    this.renderHotbarItem(off, xx, yy, partialTicks, viewEntity);
                }

                RenderHelper.disableStandardItemLighting();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
            }
        }
    }

}
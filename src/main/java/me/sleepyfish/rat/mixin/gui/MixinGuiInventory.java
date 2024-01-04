package me.sleepyfish.rat.mixin.gui;

import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;

import net.minecraft.inventory.Container;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiInventory.class)
public abstract class MixinGuiInventory extends InventoryEffectRenderer {

    @Shadow protected abstract void drawGuiContainerForegroundLayer(int a, int b);

    @Shadow private float oldMouseX;

    @Shadow private float oldMouseY;

    public MixinGuiInventory(Container c) {
        super(c);
    }

    protected void drawGuiContainerBackgroundLayer(float a, int b, int c) {
        RenderUtils.drawImage("/gui/icon_text", this.width / 2 - 60, this.height / 4 + 10, 120, 25, ColorUtils.getIconColorAlpha());

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(inventoryBackground);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        GuiInventory.drawEntityOnScreen(this.guiLeft + 51, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - this.oldMouseX, (float)(this.guiTop + 75 - 50) - this.oldMouseY, this.mc.thePlayer);
    }

}
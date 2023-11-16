package me.sleepyfish.rat.mixin;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.event.EventRenderEntity;
import me.sleepyfish.rat.modules.impl.Nametag;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity <T extends EntityLivingBase> extends Render<T> {

    protected MixinRendererLivingEntity(RenderManager m) {
        super(m);
    }

    @Redirect(method = "canRenderName*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;livingPlayer:Lnet/minecraft/entity/Entity;"))
    public Entity canRenderName(RenderManager renderManager) {
        if (Rat.instance.moduleManager.getModByClass(Nametag.class).isEnabled()) {
            return null;
        }

        return renderManager.livingPlayer;
    }

    @Inject(method = "doRender*", at = @At("HEAD"), cancellable = true)
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        EventRenderEntity event = new EventRenderEntity(entity, partialTicks);
        event.call();

        if (event.isCancelled())
            ci.cancel();
    }

    @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At(value = "HEAD"))
    public void renderName(T en, double x, double y, double z, CallbackInfo ci) {
        if (Rat.instance.moduleManager.getModByClass(Nametag.class).isEnabled()) {
            Nametag.renderName(en, x, y, z, ci);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(T t) {
        return null;
    }
}
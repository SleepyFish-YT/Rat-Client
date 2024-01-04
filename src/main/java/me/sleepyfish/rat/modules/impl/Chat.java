package me.sleepyfish.rat.modules.impl;

import me.sleepyfish.rat.modules.Module;
import me.sleepyfish.rat.utils.render.GlUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Chat extends Module {

    public static double scrollY;
    public static SimpleAnimation scrollAnimation;
    public static SimpleAnimation barAnimation;

    public static ToggleSetting removeTextBar;
    public static ToggleSetting animateTextBar;
    public static ToggleSetting removeChat;
    public static ToggleSetting removeBackground;
    public static ToggleSetting unlockChat;
    public static ToggleSetting customFont;

    // Mixin class: MixinGuiNewChat
    public Chat() {
        super("Chat", "Change the Chat HUD.");

        this.addSetting(removeTextBar = new ToggleSetting("Remove Text Bar", false));
        this.addSetting(removeChat = new ToggleSetting("Remove Chat", false));
        this.addSetting(animateTextBar = new ToggleSetting("Animate Text Bar", true));
        this.addSetting(removeBackground = new ToggleSetting("Remove BG", true));
        this.addSetting(unlockChat = new ToggleSetting("Unlock Chat Limit", false));
        this.addSetting(customFont = new ToggleSetting("Custom Font", false));

        Chat.scrollAnimation = new SimpleAnimation(0.0F);

        this.toggle();
    }

    public static void chatHead(GuiScreen screen, CallbackInfo ci) {
        if (Chat.removeTextBar.isEnabled()) {
            ci.cancel();
            return;
        }

        if (Chat.animateTextBar.isEnabled()) {
            barAnimation.setAnimation(30, 20);
            GlUtils.startTranslate(0, 29 - (int) barAnimation.getValue());
        }

        RenderUtils.drawImage("/gui/icon_text", screen.width - 100, screen.height - 40, 90, 20);
    }

    public static void chatTail() {
        if (Chat.animateTextBar.isEnabled()) {
            if (Chat.removeTextBar.isEnabled())
                return;

            GlUtils.stopTranslate();
        }
    }

}
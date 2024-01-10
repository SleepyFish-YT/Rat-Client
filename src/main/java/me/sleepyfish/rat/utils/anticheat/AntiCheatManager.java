package me.sleepyfish.rat.utils.anticheat;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.Display;

import java.awt.*;
import javax.swing.*;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class AntiCheatManager {

    private final Minecraft mc = MinecraftUtils.mc;
    private byte failed;

    public AntiCheatManager() {
        this.failed = 0;

        this.openGuiCheck();
    }

    public void runTickCheck() {
        if (this.mc.isSingleplayer())
            return;

        if (InputUtils.MouseManager.getRightClickCounter() > 100 || InputUtils.MouseManager.getLeftClickCounter() > 100)
            this.checkForFail();

        if (this.mc.getCurrentServerData() != null) {
            final EntityPlayer serverPlayer = this.mc.theWorld.getPlayerEntityByName(this.mc.getSession().getUsername());
            final boolean allowFly = serverPlayer.capabilities.allowFlying;

            if (!allowFly && this.mc.thePlayer.capabilities.isFlying && !serverPlayer.onGround) {
                this.checkForFail();
            }
        }
    }

    public void openGuiCheck() {
        final boolean instanceFlags = Rat.instance.getAuthorYoutube() != "https://youtube.com/@SleepyFishh" || Rat.instance.getDiscord() == "";
        final boolean windowFlag = !Display.isCreated() || !Desktop.getDesktop().isSupported(Desktop.Action.MAIL);
        final boolean minecraftFlags = this.mc.isDemo() || this.mc.gameSettings.anaglyph;
        final boolean titleFlag = WindowsUtils.getWindowTitle() != WindowsUtils.ratTitle;

        if (minecraftFlags || titleFlag || windowFlag || instanceFlags) {
            this.checkForFail();
        }
    }

    private void checkForFail() {
        if (this.failed == 1) {
            final JFrame frame = new JFrame(WindowsUtils.ratTitle);

            frame.setUndecorated(true);
            frame.setBackground(ColorUtils.getBackgroundDarkerColorMoreAlpha());
            frame.setSize(450, 200);
            frame.setLocation((MinecraftUtils.res.getScaledWidth() / 2) - (frame.getWidth() / 2), (MinecraftUtils.res.getScaledHeight() / 2) - (frame.getHeight() / 2));
            frame.add(new Button("U failed"));

            frame.setVisible(true);
        } else {
            this.failed++;
        }
    }

}
package me.sleepyfish.rat.gui.clickgui;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.config.Config;
import me.sleepyfish.rat.utils.render.GlUtils;
import me.sleepyfish.rat.utils.render.GuiUtils;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.WindowsUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.awt.Desktop;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class GuiRatConfig extends GuiScreen {

    private double scrollY;
    private SimpleAnimation scrollAnimation;
    private boolean overExitGui;
    private boolean overFolder;

    private GuiScreen parent;

    public GuiRatConfig(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.scrollAnimation = new SimpleAnimation(0.0F);
        this.scrollY = 0;
        this.overExitGui = false;
        this.overFolder = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        InputUtils.mouseX = mouseX;
        InputUtils.mouseY = mouseY;

        // Render gui background
        RenderUtils.drawRound(0, 0, this.width, this.height, 0, new Color(0, 0, 0, 95));
        GuiUtils.drawCustomGui(1, this.width, this.height, true);
        GuiUtils.drawLogo(this.width, this.height, 2);

        // Hover setter
        this.overExitGui = InputUtils.isInside(this.width / 2F - 244, this.height / 2F + 121, 24, 24);
        this.overFolder = InputUtils.isInside(this.width / 2F - 244, this.height / 2F + 90, 24, 24);

        if (!Rat.instance.configManager.getConfigs().isEmpty()) {

            GlUtils.startScissors();
            GuiUtils.drawCustomGui(3, this.width, this.height - 60, true);
            GlUtils.readScissors(1);

            float scrollY = scrollAnimation.getValue();

            int index = 0;
            int index2 = 1;
            int offsetX = 0;
            int offsetY = 0;

            for (Config c : Rat.instance.configManager.getConfigs()) {
                int maxX = this.width / 2 - 170 + offsetX;
                int maxY = (int) (this.height / 2 - 140 + offsetY + FontUtils.getFontHeight() + scrollY);
                int maxWidth = 200;

                String author = c.getData().get("author").getAsString();
                String version = "Ver: " + c.getData().get("version").getAsString();

                if (InputUtils.isInside(maxX, maxY, maxWidth, 30)) {
                    RenderUtils.drawRound(maxX, maxY, maxWidth, 30, 12F, ColorUtils.getBackgroundDarkerColorMoreAlpha().brighter());
                } else {
                    RenderUtils.drawRound(maxX, maxY, maxWidth, 30, 12F, ColorUtils.getBackgroundDarkerColorMoreAlpha());
                }

                FontUtils.drawFont(WindowsUtils.capitalizeFirstLetter(c.getName()), maxX + 15F, maxY + 12F, ColorUtils.getFontColor(this));
                FontUtils.text14.drawString(author, maxX + maxWidth - FontUtils.text14.getStringWidth(author) - 5, maxY + 12F, ColorUtils.getFontColor(this));
                FontUtils.text14.drawString(version, maxX + maxWidth - FontUtils.text14.getStringWidth(author) - 10 - FontUtils.text14.getStringWidth(version), maxY + 12F, ColorUtils.getFontColor(this));
                //FontUtils.text14.drawString(created, maxX + maxWidth - FontUtils.text14.getStringWidth(author) - 5, maxY + 12F, ColorUtils.getFontColor(this));

                index++;
                offsetX += maxWidth + 5;
                if (index == index2 * 2) {
                    index2++;
                    offsetX = 0;
                    offsetY += 35;
                }
            }

            final InputUtils.SoarScroll scroll = InputUtils.getSoarScroll();

            if (scroll != null) {
                switch (scroll) {
                    case UP: {
                        if (this.scrollY < -10) {
                            this.scrollY += 20;
                        } else {
                            if (index > 5)
                                this.scrollY = 0;
                        }

                        break;
                    }

                    case DOWN: {
                        double maxScale = Rat.instance.configManager.getConfigs().size() * 0.3D;

                        if (this.scrollY > -(index * maxScale))
                            this.scrollY -= 20;

                        if (index > 5) {
                            if (this.scrollY < -(index * maxScale))
                                this.scrollY = -(index * maxScale);
                        }
                        break;
                    }
                }
            }

            this.scrollAnimation.setAnimation((float) this.scrollY, 32);

            GlUtils.endScissors();
        }

        RenderUtils.drawRound(this.width / 2F - 208F, this.height / 2F + 120F, 490F, 25F, 10, ColorUtils.getBackgroundBrighterColor());

        RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 121, 24, 24, 5, !this.overExitGui ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().darker());
        RenderUtils.drawRound(this.width / 2F - 244, this.height / 2F + 90, 24, 24, 5, !this.overFolder ? ColorUtils.getBackgroundDarkerColor() : ColorUtils.getBackgroundDarkerColor().darker());

        RenderUtils.drawImage("/gui/icons/cross", this.width / 2 - 237, this.height / 2 + 121 + 8, 10, 10, ColorUtils.getIconColorAlpha());
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if (button == InputUtils.MOUSE_LEFT) {
            if (this.overExitGui) {
                SoundUtils.playClick();
                mc.displayGuiScreen(this.parent);
            } else if (this.overFolder) {
                try {
                    Desktop.getDesktop().open(Rat.instance.configManager.cfgDir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!Rat.instance.configManager.getConfigs().isEmpty()) {
                float scrollY = scrollAnimation.getValue();

                int index = 0;
                int index2 = 1;
                int offsetX = 0;
                int offsetY = 0;

                for (Config c : Rat.instance.configManager.getConfigs()) {
                    int maxX = this.width / 2 - 170 + offsetX;
                    int maxY = (int) (this.height / 2 - 140 + offsetY + FontUtils.getFontHeight() + scrollY);
                    int maxWidth = 200;

                    if (InputUtils.isInside(maxX, maxY, maxWidth, 30)) {
                        SoundUtils.playClick();
                        Rat.instance.configManager.setModuleCfg(c);
                    }

                    index++;
                    offsetX += maxWidth + 5;
                    if (index == index2 * 2) {
                        index2++;
                        offsetX = 0;
                        offsetY += 35;
                    }
                }
            }

        }
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

}
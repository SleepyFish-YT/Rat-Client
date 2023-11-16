package me.sleepyfish.rat.modules;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.ArrayList;

public class Module {

    private String name;
    private String description;

    private boolean canBeEnabled;
    private boolean enabled;

    private int x, y;
    private int width, height;

    private String text;
    private boolean moving;
    private boolean isCustom;
    private final boolean isHudMod;

    public boolean isReBinding;
    public boolean overModule;
    public boolean overEnable;
    public boolean overSetting;

    protected ToggleSetting rounded;
    protected ToggleSetting brackets;
    protected ToggleSetting outlines;
    protected ToggleSetting background;
    private final ArrayList<Setting> settings;

    public Minecraft mc;

    public Module(String name, String description) {
        this.name = name;
        this.description = description;

        this.enabled = false;
        this.canBeEnabled = true;
        this.isReBinding = false;

        this.overModule = false;
        this.overEnable = false;
        this.overSetting = false;

        this.mc = MinecraftUtils.mc;

        this.settings = new ArrayList<>();
        this.isHudMod = false;
    }

    public Module(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;

        this.enabled = false;
        this.canBeEnabled = true;
        this.isReBinding = false;

        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
        this.text = "";

        this.moving = false;
        this.isCustom = false;

        this.overModule = false;
        this.overEnable = false;
        this.overSetting = false;

        this.mc = MinecraftUtils.mc;

        this.settings = new ArrayList<>();
        this.isHudMod = true;

        this.addSetting(this.rounded = new ToggleSetting("Rounded", "Makes the background round", false));
        this.addSetting(this.brackets = new ToggleSetting("Brackets", "Add brackets to the text", false));

        this.addSetting(this.outlines = new ToggleSetting("Outlines", "Adds outlines to the background", true));
        this.addSetting(this.background = new ToggleSetting("Background", "Render background of the Module", true));
    }

    public void unInject() {
        this.onDisable();

        this.name = "";
        this.description = "";

        this.enabled = false;
        this.canBeEnabled = false;
        this.isReBinding = false;

        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.text = "";

        this.moving = false;
        this.isCustom = false;

        this.overModule = false;
        this.overEnable = false;
        this.overSetting = false;

        this.mc = null;

        this.getSettings().clear();

        this.rounded = null;
        this.brackets = null;
        this.outlines = null;
        this.background = null;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return MinecraftUtils.path + "/gui/modules/" + this.getName().toLowerCase() + ".png";
    }

    public String getDescription() {
        return description;
    }

    public boolean canBeEnabled() {
        return canBeEnabled;
    }

    public void setCanBeEnabled(boolean canBeEnabled) {
        this.canBeEnabled = canBeEnabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        if (!this.isEnabled()) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {
        this.enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
        Rat.instance.eventManager.register(this);
    }

    public void onDisable() {
        this.enabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        Rat.instance.eventManager.unregister(this);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public void addSetting(Setting setting) {
        this.getSettings().add(setting);
    }

    public void removeSetting(Setting setting) {
        this.getSettings().remove(setting);
    }

    public String getText() {
        if (this.isHudMod())
            return this.text;

        return "not a hud module";
    }

    public void setText(String text) {
        if (this.isHudMod())
            this.text = text;
    }

    public int getX() {
        if (this.isHudMod())
            return this.x;

        else return -1;
    }

    public void setX(int x) {
        if (this.isHudMod())
            this.x = x - (getWidth() / 2);
    }

    public int getY() {
        if (this.isHudMod())
            return this.y;

        else return -1;
    }

    public void setY(int y) {
        if (this.isHudMod())
            this.y = y - (getHeight() / 2);
    }

    public int getWidth() {
        if (this.isHudMod()) {
            if (this.width == 0) {
                if (this.brackets.isEnabled()) {
                    return FontUtils.getFontWidth("[" + this.getText() + "]") + 3;
                } else {
                    return FontUtils.getFontWidth(this.getText()) + 3;
                }
            }

            return this.width;
        }

        return -1;
    }

    public void setWidth(int width) {
        if (this.isHudMod())
            this.width = width;
    }

    public int getHeight() {
        if (this.isHudMod()) {
            if (this.height == 0)
                return 12;

            return this.height;
        }

        return -1;
    }

    public void setHeight(int height) {
        if (this.isHudMod())
            this.height = height;
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean custom) {
        this.isCustom = custom;
    }

    public boolean isHudMod() {
        return this.isHudMod;
    }

    public Color getFontColor() {
        return ColorUtils.getFontColor(this);
    }

    public Color getBackgroundColor() {
        return new Color(0, 0, 0, 0x80);
    }

    public Color getBackgroundColor2() {
        return new Color(20, 20, 20, 0xD0);
    }

    public Color getBackgroundWhite() {
        return new Color(169, 169, 169, 140);
    }

    public boolean isMoving() {
        if (this.isHudMod())
            return this.moving;

        return false;
    }

    public void setMoving(boolean move) {
        if (this.isHudMod())
            this.moving = move;
    }

    public void renderUpdate() {
        // ...
    }

    public void tickUpdate() {
        // ...
    }

    public void drawRectangle(float x, float y, float width, float height, Color color1, Color color2) {
        if (this.background.isEnabled()) {
            RenderUtils.drawRound(x, y, width, height, (this.rounded.isEnabled()) ? 4 : 0, color1);
        }

        if (this.outlines.isEnabled()) {
            RenderUtils.drawOutline(x, y, width, height, 2, (this.rounded.isEnabled()) ? 4 : 0, color2);
        }
    }

    public void drawRectangle(float x, float y, float width, float height, boolean bool, Color color) {
        this.drawRectangle(x, y, width, height, !bool ? new Color(Integer.MIN_VALUE, true) : color, this.getBackgroundColor().brighter().brighter());
    }

    public void drawRectangle(float x, float y, float width, float height) {
        this.drawRectangle(x, y, width, height, this.getBackgroundColor(), this.getBackgroundColor().brighter().brighter());
    }

    public void drawComponent() {
        if (!this.isCustom()) {
            if (this.isHudMod()) {
                float blah = (float) FontUtils.currentFont.getHeight();
                this.drawRectangle(this.getX() - 8F, this.getY() - 5F, this.getWidth() + 16, blah * 2F);

                String text = this.getText();
                if (this.brackets.isEnabled()) {
                    text = "[" + text + "]";
                }

                FontUtils.drawFont(text, this.getX() + 2.5F, this.getY() - 0.5F, this.getFontColor());
            }
        }
    }

    public void drawHover() {
        if (this.isHudMod()) {
            float blah = (float) FontUtils.currentFont.getHeight() + 1F;
            RenderUtils.drawOutline(getX() - 9, getY() - 6, getWidth() + 18, blah * 2F, 2, 2, ColorUtils.getOutilneColor());
        }
    }

}
package me.sleepyfish.rat.modules;

import com.google.gson.JsonObject;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.misc.InputUtils;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.render.animations.normal.Animation;
import me.sleepyfish.rat.utils.render.animations.normal.Direction;
import me.sleepyfish.rat.utils.render.animations.simple.AnimationUtils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class Module {

    private String name;
    private String description;

    private boolean enabled;
    private boolean canBeEnabled;

    public Animation animation;

    private int guiX, guiY;
    public int guiX2, guiY2;

    private int width, height;

    private String text;
    private boolean moving;
    private boolean isCustom;
    private final boolean isHudMod;

    public boolean isReBinding;
    public boolean overModule;
    public boolean overEnable;
    public boolean overSetting;

    public boolean overRedCross;

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

        this.overRedCross = false;

        this.animation = AnimationUtils.getAnimation();
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

        this.guiX = x;
        this.guiY = y;
        this.width = 0;
        this.height = 0;
        this.text = "";

        this.moving = false;
        this.isCustom = false;

        this.overModule = false;
        this.overEnable = false;
        this.overSetting = false;

        this.overRedCross = false;

        this.animation = AnimationUtils.getAnimation();
        this.mc = MinecraftUtils.mc;

        this.settings = new ArrayList<>();
        this.isHudMod = true;

        this.addSetting(this.rounded = new ToggleSetting("Rounded", "Makes the background round", true));
        this.addSetting(this.brackets = new ToggleSetting("Brackets", "Add brackets to the text", false));

        this.addSetting(this.outlines = new ToggleSetting("Outlines", "Adds outlines to the background", false));
        this.addSetting(this.background = new ToggleSetting("Background", "Render background of the Module", true));
    }

    public void unInject() {
        this.onDisable();

        this.name = "";
        this.description = "";

        this.enabled = false;
        this.canBeEnabled = false;
        this.isReBinding = false;

        this.guiX = 0;
        this.guiY = 0;
        this.width = 0;
        this.height = 0;
        this.text = "";

        this.moving = false;
        this.isCustom = false;

        this.overModule = false;
        this.overEnable = false;
        this.overSetting = false;

        this.overRedCross = false;

        this.animation = null;
        this.mc = null;

        this.getSettings().forEach(Setting::disable);
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
        return "/gui/modules/" + this.getName().toLowerCase().replace(" ", "_");
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

    private void onEnable() {
        this.enabled = true;
        this.animation.setDirection(Direction.FORWARDS);
        MinecraftForge.EVENT_BUS.register(this);
        Rat.instance.eventManager.register(this);

        this.onEnableEvent();
    }

    public void onEnableEvent() {
        // ...
    }

    private void onDisable() {
        this.enabled = false;
        this.animation.setDirection(Direction.BACKWARDS);
        MinecraftForge.EVENT_BUS.unregister(this);
        Rat.instance.eventManager.unregister(this);

        this.onDisableEvent();
    }

    public void onDisableEvent() {
        // ...
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

    public int getGuiX() {
        if (this.isHudMod())
            return this.guiX;

        return -1;
    }

    public void setGuiX(int guiX) {
        if (this.isHudMod())
            this.guiX = guiX - (getWidth() / 2);
    }

    public void setUnsaveGuiX(int guiX) {
        this.guiX = guiX;
    }

    public void setUnsaveGuiY(int guiY) {
        this.guiY = guiY;
    }

    public void unsaveSetX(int x) {
        this.guiX = x;
    }

    public int getGuiY() {
        if (this.isHudMod())
            return this.guiY;

        return -1;
    }

    public void setGuiY(int guiY) {
        if (this.isHudMod())
            this.guiY = guiY - (getHeight() / 2);
    }

    public void unsaveSetY(int y) {
        this.guiY = y;
    }

    public int getWidth() {
        if (this.isHudMod()) {
            if (this.width == 0) {
                if (this.brackets.isEnabled()) {
                    return FontUtils.getFontWidth("[" + this.getText() + "]") + 3;
                } else {
                    return FontUtils.getFontWidth(this.getText()) + 4;
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
        if (this.isHudMod()) {
            this.moving = move;
        }
    }

    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("enabled", this.isEnabled());

        if (this.isHudMod()) {
            data.addProperty("position-X", this.getGuiX());
            data.addProperty("position-Y", this.getGuiY());
        }

        if (this.getSettings() != null) {
            JsonObject settings = new JsonObject();

            for (Setting setting : this.getSettings()) {
                if (setting != null) {
                    JsonObject settingData = setting.getConfig().getAsJsonObject();
                    settings.add(setting.getName(), settingData);
                }
            }

            data.add("settings", settings);
        }

        return data;
    }

    public void applyConfigFromJson(JsonObject data) {
        try {
            if (data.get("enabled").getAsBoolean()) {
                if (!this.isEnabled())
                    this.toggle();
            }

            if (this.isHudMod()) {
                this.unsaveSetX(data.get("position-X").getAsInt());
                this.unsaveSetY(data.get("position-Y").getAsInt());
            }

            if (this.getSettings() != null) {
                JsonObject settingsData = data.get("settings").getAsJsonObject();
                for (Setting setting : this.getSettings()) {
                    if (settingsData.has(setting.getName())) {
                        setting.applyConfig(
                                settingsData.get(setting.getName()).getAsJsonObject()
                        );
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void renderUpdate() {
        // ...
    }

    public void tickUpdate() {
        // ...
    }

    public void drawRectangle(float x, float y, float width, float height, Color color1, Color color2) {
        if (this.background.isEnabled())
            RenderUtils.drawRound(x, y, width, height, (this.rounded.isEnabled()) ? 4 : 0, color1);

        if (this.outlines.isEnabled())
            RenderUtils.drawOutline(x, y, width, height, 2, (this.rounded.isEnabled()) ? 4 : 0, color2);
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
                //GlUtils.startScale((guiX + width) / 2F, (guiY + height) / 2F, this.animation.getValueF());

                float blah = (float) FontUtils.currentFont.getHeight();
                this.drawRectangle(this.getGuiX() - 8F, this.getGuiY() - 5F, this.getWidth() + 16, blah * 2F);

                String text = this.getText();
                if (this.brackets.isEnabled()) {
                    text = "[" + text + "]";
                }

                FontUtils.drawFont(text, this.getGuiX() + 2.5F, ((this.getGuiY() + this.getHeight()) - (blah * 2)), this.getFontColor());

                //GlUtils.stopScale();
            }
        }
    }

    public void drawHover() {
        if (this.isHudMod()) {
            float blah = ((float) FontUtils.currentFont.getHeight() + 1F) * 2F;
            if (this.isCustom()) {
                blah = this.getHeight();
            }

            if (this.isCustom()) {
                this.overRedCross = InputUtils.isInside(getGuiX() + getWidth() - 4, getGuiY() + (getHeight() / 2F) - 16, 14, 14);
                RenderUtils.drawImage("/gui/icons/cross", getGuiX() + getWidth() - 4, getGuiY() + getHeight() - 14, 14, 14, this.overRedCross ? Color.red.brighter().brighter().brighter() : Color.red.brighter().brighter());
            } else {
                this.overRedCross = InputUtils.isInside(getGuiX() + getWidth() - 4, getGuiY() + (getHeight() / 2F), 14, 14);
                RenderUtils.drawImage("/gui/icons/cross", getGuiX() + getWidth() - 4, getGuiY() + getHeight() - 14, 14, 14, this.overRedCross ? Color.red.brighter().brighter().brighter() : Color.red.brighter().brighter());
            }

            RenderUtils.drawOutline(getGuiX() - 9, getGuiY() - 6, getWidth() + 18, blah, 2, 2, ColorUtils.getOutilneColor());
        }
    }

}
package me.sleepyfish.rat.modules;

import com.google.gson.JsonObject;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.render.ColorUtils;
import me.sleepyfish.rat.utils.render.RenderUtils;
import me.sleepyfish.rat.modules.settings.Setting;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.render.font.FontUtils;
import me.sleepyfish.rat.modules.settings.impl.ToggleSetting;
import me.sleepyfish.rat.utils.render.animations.simple.SimpleAnimation;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class Module {

    private final String name;
    private final String description;

    private boolean enabled;
    private boolean canBeEnabled;

    public final SimpleAnimation editOpacityAnimation;

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

    protected ToggleSetting rounded;
    protected ToggleSetting brackets;
    protected ToggleSetting outlines;
    protected ToggleSetting background;
    private final ArrayList<Setting> settings;

    public final Minecraft mc;

    public Module(final String name, final String description) {
        this.name = name;
        this.description = description + ".";

        this.enabled = false;
        this.canBeEnabled = true;
        this.isReBinding = false;

        this.overModule = false;
        this.overEnable = false;
        this.overSetting = false;

        this.editOpacityAnimation = new SimpleAnimation(0F);
        this.mc = MinecraftUtils.mc;

        this.settings = new ArrayList<>();
        this.isHudMod = false;
    }

    public Module(final String name, final String description, final int x, final int y) {
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

        this.editOpacityAnimation = new SimpleAnimation(0F);
        this.mc = MinecraftUtils.mc;

        this.settings = new ArrayList<>();
        this.isHudMod = true;

        this.addSetting(this.rounded = new ToggleSetting("Rounded", "Makes the background round", true));
        this.addSetting(this.brackets = new ToggleSetting("Brackets", "Add brackets to the text", false));

        this.addSetting(this.outlines = new ToggleSetting("Outlines", "Adds outlines to the background", false));
        this.addSetting(this.background = new ToggleSetting("Background", "Render background of the Module", true));
    }

    public final String getName() {
        return name;
    }

    public final String getIconPath() {
        return "/gui/modules/" + this.getName().toLowerCase().replace(" ", "_");
    }

    public final String getDescription() {
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
        Rat.instance.eventManager.register(this);

        this.onEnableEvent();
    }

    public void onEnableEvent() {
        // ...
    }

    private void onDisable() {
        this.enabled = false;
        Rat.instance.eventManager.unregister(this);

        this.onDisableEvent();
    }

    public void onDisableEvent() {
        // ...
    }

    public final ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public void addSetting(final Setting setting) {
        this.getSettings().add(setting);
    }

    public void removeSetting(final Setting setting) {
        this.getSettings().remove(setting);
    }

    public final String getText() {
        if (this.isHudMod())
            return this.text;

        return "not a hud module";
    }

    public void setText(final String text) {
        if (this.isHudMod())
            this.text = text;
    }

    public final int getGuiX() {
        if (this.isHudMod())
            return this.guiX;

        return -1;
    }

    public void setUnsaveGuiX(final int guiX) {
        this.guiX = guiX;
    }

    public void setUnsaveGuiY(final int guiY) {
        this.guiY = guiY;
    }

    public void unsaveSetX(final int x) {
        this.guiX = x;
    }

    public final int getGuiY() {
        if (this.isHudMod())
            return this.guiY;

        return -1;
    }

    public void unsaveSetY(final int y) {
        this.guiY = y;
    }

    public final int getWidth() {
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

    public void setWidth(final int width) {
        if (this.isHudMod())
            this.width = width;
    }

    public final int getHeight() {
        if (this.isHudMod()) {
            if (this.height == 0)
                return 12;

            return this.height;
        }

        return -1;
    }

    public void setHeight(final int height) {
        if (this.isHudMod())
            this.height = height;
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(final boolean custom) {
        this.isCustom = custom;
    }

    public boolean isHudMod() {
        return this.isHudMod;
    }

    public final Color getFontColor() {
        return ColorUtils.getFontColor(this);
    }

    public final Color getBackgroundColor() {
        return new Color(0, 0, 0, 0x80);
    }

    public final Color getBackgroundColor2() {
        return new Color(20, 20, 20, 0xD0);
    }

    public final Color getBackgroundWhite() {
        return new Color(169, 169, 169, 140);
    }

    public boolean isMoving() {
        if (this.isHudMod())
            return this.moving;

        return false;
    }

    public void setMoving(final boolean move) {
        if (this.isHudMod()) {
            this.moving = move;
        }
    }

    public JsonObject getConfigAsJson() {
        final JsonObject data = new JsonObject();
        data.addProperty("enabled", this.isEnabled());

        if (this.isHudMod()) {
            data.addProperty("position-X", this.getGuiX());
            data.addProperty("position-Y", this.getGuiY());
        }

        if (this.getSettings() != null) {
            final JsonObject settings = new JsonObject();

            for (final Setting setting : this.getSettings()) {
                if (setting != null) {
                    final JsonObject settingData = setting.getConfig().getAsJsonObject();
                    settings.add(setting.getName(), settingData);
                }
            }

            data.add("settings", settings);
        }

        return data;
    }

    public void applyConfigFromJson(final JsonObject data) {
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
                final JsonObject settingsData = data.get("settings").getAsJsonObject();
                for (final Setting setting : this.getSettings()) {
                    if (settingsData.has(setting.getName()))
                        setting.applyConfig(settingsData.get(setting.getName()).getAsJsonObject());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderUpdate() {
        // ...
    }

    public void tickUpdate() {
        // ...
    }

    public void drawRectangle(final float x, final float y, final float width, final float height, final Color fill, final Color outln) {
        if (this.background.isEnabled())
            RenderUtils.drawRound(x, y, width, height, (this.rounded.isEnabled()) ? 4 : 0, fill);

        if (this.outlines.isEnabled())
            RenderUtils.drawOutline(x, y, width, height, 2, (this.rounded.isEnabled()) ? 4 : 0, outln);
    }

    public void drawRectangle(final float x, final float y, final float width, final float height, final boolean bool, final Color color) {
        this.drawRectangle(x, y, width, height, !bool ? new Color(Integer.MIN_VALUE, true) : color, this.getBackgroundColor().brighter().brighter());
    }

    public void drawRectangle(final float x, final float y, final float width, final float height) {
        this.drawRectangle(x, y, width, height, this.getBackgroundColor(), this.getBackgroundColor().brighter().brighter());
    }

    public void drawComponent() {
        if (!this.isCustom()) {
            if (this.isHudMod()) {
                final float blah = (float) FontUtils.currentFont.getHeight();
                this.drawRectangle(this.getGuiX() - 8F, this.getGuiY() - 5F, this.getWidth() + 16, blah * 2F);

                String text = this.getText();
                if (this.brackets.isEnabled())
                    text = "[" + text + "]";

                FontUtils.drawFont(text, this.getGuiX() + 2.5F, ((this.getGuiY() + this.getHeight()) - (blah * 2)), this.getFontColor());
            }
        }
    }

}
package me.sleepyfish.rat.utils.cosmetics;

import me.sleepyfish.rat.Rat;
import me.sleepyfish.rat.utils.cosmetics.impl.Emote;
import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;
import me.sleepyfish.rat.utils.cosmetics.impl.Cosmetic;
import me.sleepyfish.rat.utils.cosmetics.impl.Cape;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class CosmeticManager {

    private ArrayList<Cosmetic> cosmeticInventory;

    private ArrayList<Emote> inventoryEmotes;
    private ArrayList<Cape> inventoryCapes;

    private String currentCape;

    public CosmeticManager() {

        // Init Lists
        this.cosmeticInventory = new ArrayList<>();
        this.inventoryEmotes = new ArrayList<>();
        this.inventoryCapes = new ArrayList<>();

        // Connect to server and add ur purchased cosmetics to ur inventory
        this.connectToServerAndAddToInventory();

        // Setting the capes and emote list using the Inventory
        this.setCapesAndEmoteListsFromInventory();

        // Add free cosmetics
        this.addFreeCosmetics();

        // Set current cape
        if (Rat.instance.isDecember) {
            this.currentCape = "Rat Dec";
        } else {
            this.currentCape = "Juice";
        }
    }

    public void addFreeCosmetics() {
        this.inventoryCapes.add(new Cape("Rat B1", this.loadRatResource("1-s"), this.loadRatResource("1")));
        this.inventoryCapes.add(new Cape("Rat B2", this.loadRatResource("2-s"), this.loadRatResource("2")));
        this.inventoryCapes.add(new Cape("Rat B3", this.loadRatResource("6-s"), this.loadRatResource("6")));
        this.inventoryCapes.add(new Cape("Rat B4", this.loadRatResource("5-s"), this.loadRatResource("5")));
        this.inventoryCapes.add(new Cape("Rat W1", this.loadRatResource("3-s"), this.loadRatResource("3")));
        this.inventoryCapes.add(new Cape("Rat W2", this.loadRatResource("4-s"), this.loadRatResource("4")));
        this.inventoryCapes.add(new Cape("Rat W3", this.loadRatResource("7-s"), this.loadRatResource("7")));
        this.inventoryCapes.add(new Cape("Rat W4", this.loadRatResource("8-s"), this.loadRatResource("8")));
        this.inventoryCapes.add(new Cape("Rat Ez", this.loadRatResource("ez-s"), this.loadRatResource("ez")));

        //if (Rat.instance.isDecember)
            this.inventoryCapes.add(new Cape("Rat Dec", this.loadRatResource("9-s"), this.loadRatResource("9")));

        this.inventoryCapes.add(new Cape("Rat U1", this.loadRatResource("U1-s"), this.loadRatResource("U1")));
        this.inventoryCapes.add(new Cape("Walter", this.loadResource("walter1-s"), this.loadResource("walter1")));
        this.inventoryCapes.add(new Cape("Juice", this.loadResource("9991-s"), this.loadResource("9991")));
    }

    private void connectToServerAndAddToInventory() {
        /*
            connect to server ...

            get server user inventory by uuid ...
            set server inventory to this.cosmeticInventory

            a loop through the users cosmetic inventory ...
            if (server.get().type == "Cape") {
                this.inventoryCapes.add(server.get());
            }

            if (server.get().type == "Emote") {
                this.inventoryEmotes.add(server.get());
            }

         */
        this.cosmeticInventory = new ArrayList<>();
    }

    public final Cape getCapeByName(final String name) {
        return this.getInventoryCapes().stream().filter(cosmetics -> cosmetics.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    private ResourceLocation loadResource(final String path) {
        return new ResourceLocation(MinecraftUtils.resourcePath + "/capes/" + path + ".png");
    }

    private ResourceLocation loadRatResource(final String path) {
        return new ResourceLocation(MinecraftUtils.resourcePath + "/capes/rats/rat" + path + ".png");
    }

    public void setCapesAndEmoteListsFromInventory() {
        final ArrayList<Cape> capes = new ArrayList<>();
        final ArrayList<Emote> emotes = new ArrayList<>();

        for (final Cosmetic cos : this.getInventory()) {
            if (cos instanceof Cape)
                capes.add((Cape) cos);

            if (cos instanceof Emote)
                emotes.add((Emote) cos);
        }

        this.inventoryCapes = capes;
        this.inventoryEmotes = emotes;
    }

    public final String getCurrentCape() {
        return this.currentCape;
    }

    public void setCurrentCape(final String currentCape) {
        SoundUtils.playClick();
        this.currentCape = currentCape;
    }

    public final ArrayList<Cosmetic> getInventory() {
        return cosmeticInventory;
    }

    public final ArrayList<Cape> getInventoryCapes() {
        return inventoryCapes;
    }

    public final ArrayList<Emote> getInventoryEmotes() {
        return inventoryEmotes;
    }

}
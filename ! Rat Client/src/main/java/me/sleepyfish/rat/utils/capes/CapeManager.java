package me.sleepyfish.rat.utils.capes;

import me.sleepyfish.rat.utils.misc.SoundUtils;
import me.sleepyfish.rat.utils.misc.MinecraftUtils;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class CapeManager {

	private ArrayList<Cape> capes;
	private String currentCape;

	public CapeManager() {
		this.capes = new ArrayList<>();

		this.capes.add(new Cape("Rat B1", this.loadRatResource("rat1-s"), this.loadRatResource("rat1")));
		this.capes.add(new Cape("Rat B2", this.loadRatResource("rat2-s"), this.loadRatResource("rat2")));
		this.capes.add(new Cape("Rat B3", this.loadRatResource("rat6-s"), this.loadRatResource("rat6")));
		this.capes.add(new Cape("Rat B4", this.loadRatResource("rat5-s"), this.loadRatResource("rat5")));
		this.capes.add(new Cape("Rat W1", this.loadRatResource("rat3-s"), this.loadRatResource("rat3")));
		this.capes.add(new Cape("Rat W2", this.loadRatResource("rat4-s"), this.loadRatResource("rat4")));
		this.capes.add(new Cape("Rat W3", this.loadRatResource("rat7-s"), this.loadRatResource("rat7")));
		this.capes.add(new Cape("Rat W4", this.loadRatResource("rat8-s"), this.loadRatResource("rat8")));

		this.capes.add(new Cape("Rat U1", this.loadRatResource("ratU1-s"), this.loadRatResource("ratU1")));
		this.capes.add(new Cape("Blowsy", this.loadResource("blowsy1-s"), this.loadResource("blowsy1")));
		this.capes.add(new Cape("Marcel", this.loadResource("marcel1-s"), this.loadResource("marcel1")));
		this.capes.add(new Cape("Walter", this.loadResource("walter1-s"), this.loadResource("walter1")));
		this.capes.add(new Cape("Juice", this.loadResource("9991-s"), this.loadResource("9991")));

		this.currentCape = "Rat U1";
	}

	public void unInject() {
		this.capes.clear();
		this.capes = null;
		this.currentCape = "None";
	}

	public Cape getCapeByName(String name) {
		return this.getCapes().stream().filter(cosmetics -> cosmetics.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	private ResourceLocation loadResource(String path) {
		return new ResourceLocation(MinecraftUtils.path + "/capes/" + path + ".png");
	}

	private ResourceLocation loadRatResource(String path) {
		return new ResourceLocation(MinecraftUtils.path + "/capes/rats/" + path + ".png");
	}

	public String getCurrentCape() {
		return this.currentCape;
	}

	public void setCurrentCape(String currentCape) {
		SoundUtils.playSound("click", 1.0F, 0.8F);
		this.currentCape = currentCape;
	}

	public ArrayList<Cape> getCapes() {
		return this.capes;
	}

}
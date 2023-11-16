package me.sleepyfish.rat.utils.capes;

import net.minecraft.util.ResourceLocation;

public class Cape {

	private String name;
	private ResourceLocation cape;
	private final ResourceLocation sample;

	public Cape(String name, ResourceLocation sample, ResourceLocation cape) {
		this.name = name;
		this.sample = sample;
		this.cape = cape;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourceLocation getSample() {
		return sample;
	}

	public void setCape(ResourceLocation cape) {
		this.cape = cape;
	}

	public ResourceLocation getCape() {
		return cape;
	}

}
package net.derexxd.goldendandelion;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class GoldenDandelionSounds {

	public static final SoundEvent ITEM_USE = createSound("item.golden_dandelion.use");
	public static final SoundEvent ITEM_UNUSE = createSound("item.golden_dandelion.unuse");

	private static SoundEvent createSound(String name) {
		ResourceLocation location = new ResourceLocation(GoldenDandelionMod.MODID, name);
		return new SoundEvent(location).setRegistryName(location);
	}

	private GoldenDandelionSounds() {
	}
}

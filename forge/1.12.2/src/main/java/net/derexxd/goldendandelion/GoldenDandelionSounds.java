package net.derexxd.goldendandelion;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * Made by derexxd ported from the other golden dandelion ports for future versions I work on, made using mcreator boilerplate template for ease of speeding up
 */
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

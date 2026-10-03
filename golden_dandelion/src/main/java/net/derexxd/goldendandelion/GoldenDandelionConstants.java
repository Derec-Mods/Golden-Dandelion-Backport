package net.derexxd.goldendandelion;

public final class GoldenDandelionConstants {

	public static final String NBT_AGE_LOCKED = "AgeLocked";
	public static final String NBT_AGE_LOCKED_LEGACY = "isDandelion";
	public static final String NBT_SAVED_AGE = "GoldenDandelionSavedAge";
	public static final String NBT_COOLDOWN = "GoldenDandelionCooldown";

	/** 2-second cooldown per mob (40 ticks) */
	public static final long COOLDOWN_TICKS = 40L;

	/** Standard baby age (-24000 ticks = 20 minutes) */
	public static final int LOCKED_BABY_AGE = -24000;

	private GoldenDandelionConstants() {
	}
}

package net.minecraftforge.fml.relauncher;

/**
 * Made by derexxd ported from the other golden dandelion ports for future versions I work on, made using mcreator boilerplate template for ease of speeding up
 */
public enum Side {
	CLIENT,
	SERVER;

	public boolean isServer() {
		return !this.isClient();
	}

	public boolean isClient() {
		return this == CLIENT;
	}
}

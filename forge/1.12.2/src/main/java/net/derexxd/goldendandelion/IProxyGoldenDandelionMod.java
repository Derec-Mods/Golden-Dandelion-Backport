package net.derexxd.goldendandelion;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Made by derexxd ported from the other golden dandelion ports for future versions I work on, made using mcreator boilerplate template for ease of speeding up
 */
public interface IProxyGoldenDandelionMod {
	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void postInit(FMLPostInitializationEvent event);

	void serverLoad(FMLServerStartingEvent event);
}

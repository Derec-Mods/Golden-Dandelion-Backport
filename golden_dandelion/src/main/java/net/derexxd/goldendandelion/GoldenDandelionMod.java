package net.derexxd.goldendandelion;

import net.derexxd.goldendandelion.block.BlockGoldenDandelion;
import net.derexxd.goldendandelion.handler.GoldenDandelionAgeHandler;
import net.derexxd.goldendandelion.item.ItemBlockGoldenDandelion;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;

@Mod(
		modid = GoldenDandelionMod.MODID,
		name = GoldenDandelionMod.NAME,
		version = GoldenDandelionMod.VERSION,
		acceptedMinecraftVersions = "[1.12.2]"
)
public class GoldenDandelionMod {

	public static final String MODID = "golden_dandelion";
	public static final String NAME = "Golden Dandelion";
	public static final String VERSION = "1.0.6.1";

	public static final BlockGoldenDandelion GOLDEN_DANDELION = new BlockGoldenDandelion();

	@Mod.Instance(MODID)
	public static GoldenDandelionMod instance;

	@SidedProxy(
			clientSide = "net.derexxd.goldendandelion.ClientProxyGoldenDandelionMod",
			serverSide = "net.derexxd.goldendandelion.ServerProxyGoldenDandelionMod"
	)
	public static IProxyGoldenDandelionMod proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new GoldenDandelionAgeHandler());
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		proxy.serverLoad(event);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		GOLDEN_DANDELION.setRegistryName(MODID, "golden_dandelion");
		event.getRegistry().register(GOLDEN_DANDELION);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(
				new ItemBlockGoldenDandelion(GOLDEN_DANDELION).setRegistryName(GOLDEN_DANDELION.getRegistryName())
		);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		final ModelResourceLocation model = new ModelResourceLocation(GOLDEN_DANDELION.getRegistryName(), "normal");
		ModelLoader.setCustomStateMapper(GOLDEN_DANDELION, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return model;
			}
		});
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(GOLDEN_DANDELION),
				0,
				new ModelResourceLocation(GOLDEN_DANDELION.getRegistryName(), "inventory")
		);
	}
}

package net.derexxd.goldendandelion.client;

import net.derexxd.goldendandelion.GoldenDandelionMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class GoldenDandelionModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(GoldenDandelionMod.GOLDEN_DANDELION, RenderType.cutout());
	}
}


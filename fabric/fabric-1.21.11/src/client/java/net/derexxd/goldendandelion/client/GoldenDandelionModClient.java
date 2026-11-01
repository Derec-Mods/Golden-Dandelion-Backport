package net.derexxd.goldendandelion.client;

import net.derexxd.goldendandelion.GoldenDandelionMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class GoldenDandelionModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.putBlock(GoldenDandelionMod.GOLDEN_DANDELION, ChunkSectionLayer.CUTOUT);
	}
}

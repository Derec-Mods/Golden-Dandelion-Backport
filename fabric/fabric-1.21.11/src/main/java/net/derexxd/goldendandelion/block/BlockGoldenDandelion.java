package net.derexxd.goldendandelion.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockGoldenDandelion extends FlowerBlock {
	public BlockGoldenDandelion() {
		super(MobEffects.SATURATION, 7, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("golden_dandelion", "golden_dandelion"))));
	}
}

package net.derexxd.goldendandelion.block;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockGoldenDandelion extends FlowerBlock {
	public BlockGoldenDandelion() {
		super(MobEffects.SATURATION, 7, BlockBehaviour.Properties.copy(Blocks.DANDELION));
	}
}

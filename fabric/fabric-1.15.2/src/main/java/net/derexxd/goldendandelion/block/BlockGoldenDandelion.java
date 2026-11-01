package net.derexxd.goldendandelion.block;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;

public class BlockGoldenDandelion extends FlowerBlock {
	public BlockGoldenDandelion() {
		super(MobEffects.SATURATION, 7, Block.Properties.copy(Blocks.DANDELION));
	}
}

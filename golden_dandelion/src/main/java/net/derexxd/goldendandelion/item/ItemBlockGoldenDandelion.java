package net.derexxd.goldendandelion.item;

import net.derexxd.goldendandelion.GoldenDandelionConstants;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class ItemBlockGoldenDandelion extends ItemBlock {

	public ItemBlockGoldenDandelion(Block block) {
		super(block);
		setMaxStackSize(64);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		if (!(target instanceof EntityAnimal)) {
			return false;
		}

		EntityAnimal ageable = (EntityAnimal) target;
		if (!ageable.isChild()) {
			return false;
		}

		NBTTagCompound data = ageable.getEntityData();
		if (data.getBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED)) {
			return false;
		}

		if (player.world.isRemote) {
			return true;
		}

		data.setBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED, true);
		ageable.setGrowingAge(GoldenDandelionConstants.LOCKED_BABY_AGE);

		if (!player.capabilities.isCreativeMode) {
			stack.shrink(1);
		}

		return true;
	}
}

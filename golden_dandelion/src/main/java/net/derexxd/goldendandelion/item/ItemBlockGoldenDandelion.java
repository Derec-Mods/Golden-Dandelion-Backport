package net.derexxd.goldendandelion.item;

import net.derexxd.goldendandelion.GoldenDandelionConstants;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
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
		if (!(target instanceof EntityAgeable) || (target instanceof EntityVillager)) {
			return false;
		}

		EntityAgeable ageable = (EntityAgeable) target;
		if (!ageable.isChild()) {
			return false;
		}

		NBTTagCompound data = ageable.getEntityData();
		long currentTime = player.world.getTotalWorldTime();
		long cooldownUntil = data.getLong(GoldenDandelionConstants.NBT_COOLDOWN);
		if (currentTime < cooldownUntil) {
			return false;
		}

		boolean isLocked = data.getBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED)
				|| data.getBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED_LEGACY);

		if (player.world.isRemote) {
			player.swingArm(hand);
			return true;
		}

		// 2-second cooldown per mob (40 ticks)
		data.setLong(GoldenDandelionConstants.NBT_COOLDOWN, currentTime + GoldenDandelionConstants.COOLDOWN_TICKS);

		if (isLocked) {
			// Resuming Growth: reset lock tag
			data.removeTag(GoldenDandelionConstants.NBT_AGE_LOCKED);
			data.removeTag(GoldenDandelionConstants.NBT_AGE_LOCKED_LEGACY);

			int savedAge = data.hasKey(GoldenDandelionConstants.NBT_SAVED_AGE)
					? data.getInteger(GoldenDandelionConstants.NBT_SAVED_AGE)
					: GoldenDandelionConstants.LOCKED_BABY_AGE;
			data.removeTag(GoldenDandelionConstants.NBT_SAVED_AGE);

			ageable.setGrowingAge(savedAge);
		} else {
			// Halting Growth: set lock tag
			int currentAge = ageable.getGrowingAge();
			data.setInteger(GoldenDandelionConstants.NBT_SAVED_AGE, currentAge);
			data.setBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED, true);
			ageable.setGrowingAge(GoldenDandelionConstants.LOCKED_BABY_AGE);
			ageable.enablePersistence();
		}

		if (!player.capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		player.swingArm(hand);

		return true;
	}
}

package net.derexxd.goldendandelion.item;

import net.derexxd.goldendandelion.GoldenDandelionConstants;
import net.derexxd.goldendandelion.GoldenDandelionSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;

/**
 * Made by derexxd ported from the other golden dandelion ports for future versions I work on, made using mcreator boilerplate template for ease of speeding up
 */
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

		data.setLong(GoldenDandelionConstants.NBT_COOLDOWN, currentTime + GoldenDandelionConstants.COOLDOWN_TICKS);

		if (isLocked) {
			data.removeTag(GoldenDandelionConstants.NBT_AGE_LOCKED);
			data.removeTag(GoldenDandelionConstants.NBT_AGE_LOCKED_LEGACY);

			int savedAge = data.hasKey(GoldenDandelionConstants.NBT_SAVED_AGE)
					? data.getInteger(GoldenDandelionConstants.NBT_SAVED_AGE)
					: GoldenDandelionConstants.LOCKED_BABY_AGE;
			data.removeTag(GoldenDandelionConstants.NBT_SAVED_AGE);

			ageable.setGrowingAge(savedAge);

			if (player.world instanceof WorldServer) {
				spawnFeedbackParticles((WorldServer) player.world, ageable, false);
			}
			player.world.playSound(null, target.posX, target.posY, target.posZ, GoldenDandelionSounds.ITEM_UNUSE, SoundCategory.PLAYERS, 1.0F, 1.0F);
		} else {
			int currentAge = ageable.getGrowingAge();
			data.setInteger(GoldenDandelionConstants.NBT_SAVED_AGE, currentAge);
			data.setBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED, true);
			ageable.setGrowingAge(GoldenDandelionConstants.LOCKED_BABY_AGE);
			ageable.enablePersistence();

			if (player.world instanceof WorldServer) {
				spawnFeedbackParticles((WorldServer) player.world, ageable, true);
			}
			player.world.playSound(null, target.posX, target.posY, target.posZ, GoldenDandelionSounds.ITEM_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}

		if (!player.capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		player.swingArm(hand);

		return true;
	}

	private void spawnFeedbackParticles(WorldServer world, EntityAgeable target, boolean locking) {
		double width = target.width;
		double height = target.height;
		int count = 16;

		for (int i = 0; i < count; i++) {
			double px = target.posX + (world.rand.nextDouble() - 0.5D) * width * 1.2D;
			double pz = target.posZ + (world.rand.nextDouble() - 0.5D) * width * 1.2D;
			double motionX = (world.rand.nextDouble() - 0.5D) * 0.04D;
			double motionZ = (world.rand.nextDouble() - 0.5D) * 0.04D;

			if (locking) {
				double py = target.posY + height * 0.6D + world.rand.nextDouble() * height * 0.4D;
				world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, px, py, pz, 0, motionX, -0.12D, motionZ, 1.0D);
			} else {
				double py = target.posY + world.rand.nextDouble() * height * 0.5D;
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, 0, motionX, 0.08D, motionZ, 1.0D);
			}
		}
	}
}

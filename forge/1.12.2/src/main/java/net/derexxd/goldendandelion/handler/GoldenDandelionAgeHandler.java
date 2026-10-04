package net.derexxd.goldendandelion.handler;

import net.derexxd.goldendandelion.GoldenDandelionConstants;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Made by derexxd ported from the other golden dandelion ports for future versions I work on, made using mcreator boilerplate template for ease of speeding up
 */
public class GoldenDandelionAgeHandler {

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntityLiving().world.isRemote) {
			return;
		}

		if (!(event.getEntityLiving() instanceof EntityAgeable)) {
			return;
		}

		if (event.getEntityLiving() instanceof EntityVillager) {
			return;
		}

		EntityAgeable ageable = (EntityAgeable) event.getEntityLiving();
		NBTTagCompound data = ageable.getEntityData();
		if (!data.getBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED)
				&& !data.getBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED_LEGACY)) {
			return;
		}

		if (!ageable.isChild()) {
			return;
		}

		if (ageable.getGrowingAge() != GoldenDandelionConstants.LOCKED_BABY_AGE) {
			ageable.setGrowingAge(GoldenDandelionConstants.LOCKED_BABY_AGE);
		}
	}
}

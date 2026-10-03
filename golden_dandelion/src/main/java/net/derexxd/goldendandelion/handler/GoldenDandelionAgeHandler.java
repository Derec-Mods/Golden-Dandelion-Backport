package net.derexxd.goldendandelion.handler;

import net.derexxd.goldendandelion.GoldenDandelionConstants;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GoldenDandelionAgeHandler {

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.getEntityLiving().world.isRemote) {
			return;
		}

		if (!(event.getEntityLiving() instanceof EntityAnimal)) {
			return;
		}

		EntityAnimal ageable = (EntityAnimal) event.getEntityLiving();
		if (!ageable.getEntityData().getBoolean(GoldenDandelionConstants.NBT_AGE_LOCKED)) {
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

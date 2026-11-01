package net.derexxd.goldendandelion;

import net.derexxd.goldendandelion.block.BlockGoldenDandelion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GoldenDandelionMod implements ModInitializer {
	public static final String MOD_ID = "golden_dandelion";

	public static final BlockGoldenDandelion GOLDEN_DANDELION = new BlockGoldenDandelion();
	public static final BlockItem GOLDEN_DANDELION_ITEM = new BlockItem(GOLDEN_DANDELION, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS).stacksTo(64));

	public static final SoundEvent ITEM_USE = new SoundEvent(new ResourceLocation(MOD_ID, "golden_dandelion_use"));
	public static final SoundEvent ITEM_UNUSE = new SoundEvent(new ResourceLocation(MOD_ID, "golden_dandelion_unuse"));

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, "golden_dandelion"), GOLDEN_DANDELION);
		Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, "golden_dandelion"), GOLDEN_DANDELION_ITEM);
		Registry.register(Registry.SOUND_EVENT, new ResourceLocation(MOD_ID, "golden_dandelion_use"), ITEM_USE);
		Registry.register(Registry.SOUND_EVENT, new ResourceLocation(MOD_ID, "golden_dandelion_unuse"), ITEM_UNUSE);

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.getItem() != GOLDEN_DANDELION_ITEM) {
				return InteractionResult.PASS;
			}
			if (!(entity instanceof AgableMob) || (entity instanceof Villager)) {
				return InteractionResult.PASS;
			}
			AgableMob ageable = (AgableMob) entity;
			if (!ageable.isBaby()) {
				return InteractionResult.PASS;
			}

			GoldenDandelionEntityAccess access = (GoldenDandelionEntityAccess) ageable;
			long currentTime = world.getGameTime();
			if (currentTime < access.goldenDandelion$getCooldown()) {
				return InteractionResult.PASS;
			}

			if (world.isClientSide) {
				player.swing(hand, true);
				return InteractionResult.SUCCESS;
			}

			access.goldenDandelion$setCooldown(currentTime + 40L);
			boolean isLocked = access.goldenDandelion$isAgeLocked();

			if (isLocked) {
				access.goldenDandelion$setAgeLocked(false);
				int savedAge = access.goldenDandelion$getSavedAge();
				ageable.setAge(savedAge);

				if (world instanceof ServerLevel) {
					spawnFeedbackParticles((ServerLevel) world, ageable, false);
				}
				world.playSound(null, ageable.getX(), ageable.getY(), ageable.getZ(), ITEM_UNUSE, SoundSource.PLAYERS, 1.0F, 1.0F);
			} else {
				int currentAge = ageable.getAge();
				access.goldenDandelion$setSavedAge(currentAge);
				access.goldenDandelion$setAgeLocked(true);
				ageable.setAge(-24000);
				ageable.setPersistenceRequired();

				if (world instanceof ServerLevel) {
					spawnFeedbackParticles((ServerLevel) world, ageable, true);
				}
				world.playSound(null, ageable.getX(), ageable.getY(), ageable.getZ(), ITEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
			}

			if (!player.abilities.instabuild) {
				stack.shrink(1);
			}
			player.swing(hand, true);
			return InteractionResult.SUCCESS;
		});
	}

	private static void spawnFeedbackParticles(ServerLevel world, AgableMob target, boolean locking) {
		double width = target.getBbWidth();
		double height = target.getBbHeight();
		for (int i = 0; i < 16; i++) {
			double px = target.getX() + (world.random.nextDouble() - 0.5D) * width * 1.2D;
			double pz = target.getZ() + (world.random.nextDouble() - 0.5D) * width * 1.2D;
			double motionX = (world.random.nextDouble() - 0.5D) * 0.04D;
			double motionZ = (world.random.nextDouble() - 0.5D) * 0.04D;
			if (locking) {
				double py = target.getY() + height * 0.6D + world.random.nextDouble() * height * 0.4D;
				world.sendParticles(ParticleTypes.HAPPY_VILLAGER, px, py, pz, 1, motionX, -0.12D, motionZ, 1.0D);
			} else {
				double py = target.getY() + world.random.nextDouble() * height * 0.5D;
				world.sendParticles(ParticleTypes.SMOKE, px, py, pz, 1, motionX, 0.08D, motionZ, 1.0D);
			}
		}
	}
}


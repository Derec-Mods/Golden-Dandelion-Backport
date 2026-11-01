package net.derexxd.goldendandelion;

import net.derexxd.goldendandelion.block.BlockGoldenDandelion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GoldenDandelionMod implements ModInitializer {
	public static final String MOD_ID = "golden_dandelion";

	public static final BlockGoldenDandelion GOLDEN_DANDELION = new BlockGoldenDandelion();
	public static final BlockItem GOLDEN_DANDELION_ITEM = new BlockItem(GOLDEN_DANDELION, new Item.Properties().stacksTo(64));

	public static final SoundEvent ITEM_USE = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "golden_dandelion_use"));
	public static final SoundEvent ITEM_UNUSE = SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "golden_dandelion_unuse"));

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, "golden_dandelion"), GOLDEN_DANDELION);
		Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "golden_dandelion"), GOLDEN_DANDELION_ITEM);
		Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, "golden_dandelion_use"), ITEM_USE);
		Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, "golden_dandelion_unuse"), ITEM_UNUSE);

		ItemGroupEvents.modifyEntriesEvent(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("minecraft", "natural_blocks"))).register(entries -> entries.accept(GOLDEN_DANDELION_ITEM));

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.getItem() != GOLDEN_DANDELION_ITEM) {
				return InteractionResult.PASS;
			}
			if (!(entity instanceof AgeableMob) || (entity instanceof Villager)) {
				return InteractionResult.PASS;
			}
			AgeableMob ageable = (AgeableMob) entity;
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

			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
			player.swing(hand, true);
			return InteractionResult.SUCCESS;
		});
	}

	private static void spawnFeedbackParticles(ServerLevel world, AgeableMob target, boolean locking) {
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


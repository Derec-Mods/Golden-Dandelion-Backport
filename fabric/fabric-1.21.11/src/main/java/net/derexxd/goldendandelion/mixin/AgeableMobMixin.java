package net.derexxd.goldendandelion.mixin;

import net.derexxd.goldendandelion.GoldenDandelionEntityAccess;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AgeableMob.class)
public abstract class AgeableMobMixin extends PathfinderMob implements GoldenDandelionEntityAccess {
	@Shadow
	public abstract int getAge();

	@Shadow
	public abstract void setAge(int age);

	@Shadow
	public abstract boolean isBaby();

	@Unique
	private boolean goldenDandelion$ageLocked;

	@Unique
	private int goldenDandelion$savedAge = -24000;

	@Unique
	private long goldenDandelion$cooldownUntil;

	protected AgeableMobMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public boolean goldenDandelion$isAgeLocked() {
		return goldenDandelion$ageLocked || getTags().contains("AgeLocked") || getTags().contains("isDandelion");
	}

	@Override
	public void goldenDandelion$setAgeLocked(boolean locked) {
		this.goldenDandelion$ageLocked = locked;
		if (locked) {
			addTag("AgeLocked");
		} else {
			removeTag("AgeLocked");
			removeTag("isDandelion");
		}
	}

	@Override
	public int goldenDandelion$getSavedAge() {
		return goldenDandelion$savedAge;
	}

	@Override
	public void goldenDandelion$setSavedAge(int age) {
		this.goldenDandelion$savedAge = age;
	}

	@Override
	public long goldenDandelion$getCooldown() {
		return goldenDandelion$cooldownUntil;
	}

	@Override
	public void goldenDandelion$setCooldown(long cooldown) {
		this.goldenDandelion$cooldownUntil = cooldown;
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	private void onAiStep(CallbackInfo ci) {
		if (!level().isClientSide() && isBaby() && goldenDandelion$isAgeLocked()) {
			if (getAge() != -24000) {
				setAge(-24000);
			}
		}
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void onAddAdditionalSaveData(ValueOutput valueOutput, CallbackInfo ci) {
		if (goldenDandelion$ageLocked) {
			valueOutput.putBoolean("AgeLocked", true);
			valueOutput.putInt("GoldenDandelionSavedAge", goldenDandelion$savedAge);
		}
		if (goldenDandelion$cooldownUntil > 0) {
			valueOutput.putLong("GoldenDandelionCooldown", goldenDandelion$cooldownUntil);
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void onReadAdditionalSaveData(ValueInput valueInput, CallbackInfo ci) {
		boolean locked = valueInput.getBooleanOr("AgeLocked", false);
		if (!locked) {
			locked = valueInput.getBooleanOr("isDandelion", false);
		}
		if (locked) {
			goldenDandelion$ageLocked = true;
		}
		goldenDandelion$savedAge = valueInput.getIntOr("GoldenDandelionSavedAge", goldenDandelion$savedAge);
		goldenDandelion$cooldownUntil = valueInput.getLongOr("GoldenDandelionCooldown", goldenDandelion$cooldownUntil);
	}
}

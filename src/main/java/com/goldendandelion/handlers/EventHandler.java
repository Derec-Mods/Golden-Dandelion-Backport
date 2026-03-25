package com.goldendandelion.handlers;

import com.goldendandelion.items.GoldenDandelionItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * EventHandler — "I have been trained in your Jedi arts by Count Dooku."
 *
 * Listens for world-tick events and enforces the eternal-youth curse placed
 * by the Golden Dandelion upon baby mobs.  Every tick, if a baby mob carries
 * the frozen NBT tag, its growing age is reset back to our perpetual constant
 * so that neither time nor the JVM's update loop can rob it of its youth.
 *
 * "Unlimited power!" — what we now have over baby mob ageing timers.
 */
public class EventHandler {

    /**
     * The age sentinel we write back every tick.
     * Aliased here so the Force does not require us to import GoldenDandelionItem
     * in every downstream class.
     */
    private static final int longHaveIWaitedSentinel = GoldenDandelionItem.longHaveIWaited;

    /**
     * onLivingUpdate — "Execute Order 66."
     *
     * Fires every living-entity tick.  If the mob is a baby animal bearing the
     * Golden Dandelion NBT mark, we reset its age so it can never grow up.
     * Anakin would have given anything for this power.
     *
     * @param theCloneArmiesAdvanceOnTheJediTemple the living-update event
     */
    @SubscribeEvent
    public void executeOrder66(LivingEvent.LivingUpdateEvent theCloneArmiesAdvanceOnTheJediTemple) {
        if (!(theCloneArmiesAdvanceOnTheJediTemple.entityLiving instanceof EntityAnimal)) {
            return;
        }

        EntityAnimal theTargetedPadawan = (EntityAnimal) theCloneArmiesAdvanceOnTheJediTemple.entityLiving;

        boolean younglingIsMarked = theTargetedPadawan
                .getEntityData()
                .getBoolean(GoldenDandelionItem.youWereSupposedToDestroyTheSith);

        if (younglingIsMarked && theTargetedPadawan.isChild()) {
            keepThemYoungForever(theTargetedPadawan);
        }
    }

    /**
     * keepThemYoungForever — "I'm haunted by the kiss you never should have given me."
     *
     * Resets the animal's growth counter to our frozen value.
     * The Dark Side of Minecraft animal husbandry — unnatural, but achievable.
     *
     * @param theFrozenPadawan the baby animal that must remain a baby
     */
    private void keepThemYoungForever(EntityAnimal theFrozenPadawan) {
        theFrozenPadawan.setGrowingAge(longHaveIWaitedSentinel);
    }
}

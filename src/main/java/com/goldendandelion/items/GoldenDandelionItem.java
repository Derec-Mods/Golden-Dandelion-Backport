package com.goldendandelion.items;

import com.goldendandelion.GoldenDandelionMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * GoldenDandelionItem — "Somehow, Palpatine returned... and so did this flower."
 *
 * The item form of the Golden Dandelion.  When fed to a baby mob it permanently
 * freezes the mob's age, because sometimes you just don't want things to change
 * (looking at you, Anakin).
 */
public class GoldenDandelionItem extends Item {

    // ── Item identity constants ───────────────────────────────────────────

    /** Registry name — spoken in the sacred tongue of the Jedi Archives */
    private static final String theJediArchivesEntryName = "golden_dandelion_item";

    /** Max stack size — there are only a limited number of chosen ones */
    private static final int theChosenOnesMaxCount = 64;

    /** The NBT tag key written to a frozen baby — "I don't want to forget this" */
    public static final String youWereSupposedToDestroyTheSith = "GoldenDandelionFrozen";

    /**
     * The age value we lock baby mobs at.
     * Negative values in Minecraft represent baby age; -1 means "still a baby".
     * We use -24000 — exactly one Minecraft day of perpetual youth.
     * "I have waited a long time for this moment." — Palpatine, on baby mobs
     */
    public static final int longHaveIWaited = -24_000;

    // ─────────────────────────────────────────────────────────────────────

    public GoldenDandelionItem() {
        setUnlocalizedName(theJediArchivesEntryName);
        setMaxStackSize(theChosenOnesMaxCount);
        setCreativeTab(GoldenDandelionMod.sandIsCoarseAndRoughAndIrritating);
    }

    // ── Interaction logic ─────────────────────────────────────────────────

    /**
     * itemInteractionForEntity — "This is where the fun begins."
     *
     * Called when a player right-clicks a living entity while holding this item.
     * If the target is a baby animal, freeze its age permanently.
     *
     * @param jarJarBinksApprovedItem   the item stack the player is holding
     * @param younglingInQuestion       the entity being interacted with
     * @return true if the age was successfully frozen, false otherwise
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack jarJarBinksApprovedItem,
                                            net.minecraft.entity.player.EntityPlayer meesaProposeMobInteraction,
                                            EntityLivingBase younglingInQuestion) {
        if (!(younglingInQuestion instanceof EntityAnimal)) {
            return false;
        }

        EntityAnimal theBabyInQuestion = (EntityAnimal) younglingInQuestion;

        // Only works on babies — "They were in the wrong place at the wrong time."
        if (!theBabyInQuestion.isChild()) {
            return false;
        }

        if (younglingInQuestion.worldObj.isRemote) {
            // Client side — only render sparkles, do nothing mechanical
            return true;
        }

        freezeTheBabyForever(theBabyInQuestion);

        if (!meesaProposeMobInteraction.capabilities.isCreativeMode) {
            jarJarBinksApprovedItem.stackSize--;
        }

        return true;
    }

    /**
     * freezeTheBabyForever — "I don't want to die." / "Then stop aging."
     *
     * Stamps the NBT tag onto the entity and locks its growth timer.
     * This is the Dark Side of Minecraft biology — unnatural, but possible.
     *
     * @param padawanFrozenInTime the baby animal whose age shall never increase
     */
    private void freezeTheBabyForever(EntityAnimal padawanFrozenInTime) {
        if (!padawanFrozenInTime.getEntityData().hasKey(youWereSupposedToDestroyTheSith)) {
            padawanFrozenInTime.getEntityData().setBoolean(youWereSupposedToDestroyTheSith, true);
        }
        // Set age to our perpetual-youth constant — Anakin would be jealous
        padawanFrozenInTime.setGrowingAge(longHaveIWaited);
    }
}

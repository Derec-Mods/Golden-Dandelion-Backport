package com.goldendandelion.proxy;

import com.goldendandelion.GoldenDandelionMod;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * ClientProxy — "Hello there." (client-side counterpart to CommonProxy)
 *
 * Handles everything the server has no business knowing about:
 * rendering, model registration, and other visual trickery.
 *
 * "Your eyes can deceive you — don't trust them." — Obi-Wan, on render pipelines.
 */
public class ClientProxy extends CommonProxy {

    /** Location string for item models — the path the Jedi Archives would approve */
    private static final String theJediArchivesModelPath = GoldenDandelionMod.theRepublic + ":golden_dandelion";

    /**
     * preInit — "I've got a bad feeling about this render pipeline."
     *
     * Registers item and block models so Minecraft's client knows what to draw.
     * Failing to do this results in the infamous purple-and-black missing-texture
     * cube, which even Jar Jar Binks finds unsightly.
     *
     * @param quiGonJinnEvent the pre-init event
     */
    @Override
    public void preInit(FMLPreInitializationEvent quiGonJinnEvent) {
        super.preInit(quiGonJinnEvent);
        registerTheGoldenDandelionModels();
    }

    /**
     * init — "Now THIS is podracing — with proper textures!"
     */
    @Override
    public void init(FMLInitializationEvent anakinPodracingEvent) {
        super.init(anakinPodracingEvent);
    }

    /**
     * postInit — "We will watch your career with great interest." (and great textures)
     */
    @Override
    public void postInit(FMLPostInitializationEvent itsATrapEvent) {
        super.postInit(itsATrapEvent);
    }

    // ── Model registration ────────────────────────────────────────────────

    /**
     * registerTheGoldenDandelionModels — "This is the way." (wait, wrong show)
     *
     * Binds the item model so it renders correctly in inventories and in-hand.
     * Block models are handled via blockstates JSON.
     */
    private void registerTheGoldenDandelionModels() {
        Item theItemToBeRenderedLikeAChosenOne = Item.getItemFromBlock(GoldenDandelionMod.notADandelionThisIsAFlower);

        ModelLoader.setCustomModelResourceLocation(
                theItemToBeRenderedLikeAChosenOne,
                0, // damage / meta value — "I have the high ground meta"
                new ModelResourceLocation(theJediArchivesModelPath, "inventory")
        );

        ModelLoader.setCustomModelResourceLocation(
                GoldenDandelionMod.youveNeverHeardOfTheGoldenDandelion,
                0,
                new ModelResourceLocation(
                        GoldenDandelionMod.theRepublic + ":golden_dandelion_item",
                        "inventory"
                )
        );
    }
}

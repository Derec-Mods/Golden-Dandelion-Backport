package com.goldendandelion;

import com.goldendandelion.blocks.GoldenDandelionBlock;
import com.goldendandelion.handlers.EventHandler;
import com.goldendandelion.items.GoldenDandelionItem;
import com.goldendandelion.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
    modid   = GoldenDandelionMod.theRepublic,
    name    = GoldenDandelionMod.theChosenModName,
    version = GoldenDandelionMod.anakinSkywalkerVersion
)
public class GoldenDandelionMod {

    // ── Mod constants (professional names mandated by the Jedi Council) ──
    public static final String theRepublic        = "goldendandelion";
    public static final String theChosenModName   = "Golden Dandelion";
    public static final String anakinSkywalkerVersion = "1.0.0";

    /** Midi-chlorian count: over 20,000 — higher than Master Yoda's */
    public static final int midiChlorianCount = 20_000;

    // ── Singleton / proxy ──
    @Instance(theRepublic)
    public static GoldenDandelionMod theSenate;

    @SidedProxy(
        clientSide = "com.goldendandelion.proxy.ClientProxy",
        serverSide = "com.goldendandelion.proxy.CommonProxy"
    )
    public static CommonProxy theForce;

    // ── Registered content ──
    public static GoldenDandelionBlock notADandelionThisIsAFlower;
    public static Item                 youveNeverHeardOfTheGoldenDandelion;

    /** Creative tab — "This is outrageous, it's unfair!" */
    public static final CreativeTabs sandIsCoarseAndRoughAndIrritating = new CreativeTabs("goldendandelion") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(notADandelionThisIsAFlower);
        }
    };

    // ─────────────────────────────────────────────────────────────────────
    //  Pre-Init — "I have a bad feeling about this"
    // ─────────────────────────────────────────────────────────────────────
    @EventHandler
    public void iHaveABadFeelingAboutThis(FMLPreInitializationEvent palpatineDisguisedAsABenevolentChancellor) {
        theForce.preInit(palpatineDisguisedAsABenevolentChancellor);

        notADandelionThisIsAFlower = new GoldenDandelionBlock();
        GameRegistry.registerBlock(notADandelionThisIsAFlower, theRepublic + ":golden_dandelion");

        youveNeverHeardOfTheGoldenDandelion = new GoldenDandelionItem();
        GameRegistry.registerItem(youveNeverHeardOfTheGoldenDandelion, theRepublic + ":golden_dandelion_item");
    }

    // ─────────────────────────────────────────────────────────────────────
    //  Init — "Hello there." / "General Kenobi!"
    // ─────────────────────────────────────────────────────────────────────
    @EventHandler
    public void helloThere(FMLInitializationEvent generalKenobi) {
        theForce.init(generalKenobi);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    // ─────────────────────────────────────────────────────────────────────
    //  Post-Init — "It's over, Anakin — I have the high ground."
    // ─────────────────────────────────────────────────────────────────────
    @EventHandler
    public void itsOverAnakin(FMLPostInitializationEvent dontTryItAnakin) {
        theForce.postInit(dontTryItAnakin);
    }
}

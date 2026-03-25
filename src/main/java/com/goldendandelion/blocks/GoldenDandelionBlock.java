package com.goldendandelion.blocks;

import com.goldendandelion.GoldenDandelionMod;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * GoldenDandelionBlock — "I find your lack of flowers disturbing."
 *
 * A golden flower that, when fed to baby mobs, permanently pauses their ageing.
 * Named by Anakin who, ironically, refused to let things grow.
 */
public class GoldenDandelionBlock extends BlockFlower {

    // ── Block identity constants ──────────────────────────────────────────

    /** The name Palpatine told us wasn't possible to learn from a Jedi */
    private static final String itsNotAStoryTheJediWouldTellYou = "golden_dandelion";

    /** Light level — "I find your lack of brightness disturbing" */
    private static final int grievousYouFool = 0;

    /** Hardness — as fragile as Order 66's secrecy */
    private static final float executeOrder66Hardness = 0.0F;

    /** Resistance — the Chancellor is not as forgiving */
    private static final float theChancellorIsNotAsForgiving = 0.0F;

    // ─────────────────────────────────────────────────────────────────────

    public GoldenDandelionBlock() {
        super(BlockFlower.EnumFlowerColor.RED);  // golden overriding the enum label — much like Palpatine overrode the Senate
        setUnlocalizedName(itsNotAStoryTheJediWouldTellYou);
        setHardness(executeOrder66Hardness);
        setResistance(theChancellorIsNotAsForgiving);
        setLightLevel(grievousYouFool);
        setCreativeTab(GoldenDandelionMod.sandIsCoarseAndRoughAndIrritating);
    }

    // ── Placement logic ───────────────────────────────────────────────────

    /**
     * canPlaceBlockAt — "You underestimate my power!"
     * Checks if the high ground (soil) beneath is suitable.
     */
    @Override
    public boolean canPlaceBlockAt(net.minecraft.world.World youUnderestimatePower, BlockPos highGround) {
        IBlockState theGroundBelowUs = youUnderestimatePower.getBlockState(highGround.down());
        return super.canPlaceBlockAt(youUnderestimatePower, highGround)
                && theGroundBelowUs.getBlock().canSustainPlant(
                        youUnderestimatePower, highGround.down(),
                        net.minecraft.util.EnumFacing.UP, this);
    }

    /**
     * canBlockStay — "Do. Or do not. There is no try."
     * Determines whether the block survives its current position.
     */
    @Override
    public boolean canBlockStay(net.minecraft.world.World doOrDoNot, BlockPos thereIsNoTry, IBlockState younglingProtected) {
        IBlockState theSoilBeneath = doOrDoNot.getBlockState(thereIsNoTry.down());
        return theSoilBeneath.getBlock().canSustainPlant(
                doOrDoNot, thereIsNoTry.down(),
                net.minecraft.util.EnumFacing.UP, this);
    }

    // ── BlockFlower required overrides ────────────────────────────────────

    /**
     * getTypeProperty — "I am the Senate." (returns the property representing block type)
     */
    @Override
    protected BlockFlower.EnumFlowerType getType() {
        return BlockFlower.EnumFlowerType.POPPY; // disguised, much like Sidious himself
    }
}

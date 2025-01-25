package keqing.gtqtspace.api.unifications.ore;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import keqing.gtqtspace.api.unifications.GTQTSpaceMaterials;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

import static keqing.gtqtspace.api.unifications.ore.GTQTSOrePrefix.*;


public class GTQTSStoneTypes {
    public static StoneType MOON;
    public static StoneType MARS;
    public static StoneType VENUS;

    public GTQTSStoneTypes() {}
    public static void init(int addID) {
        MOON = new StoneType(21+addID, "moon", SoundType.STONE, oreMoon, GTQTSpaceMaterials.MoonStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.MOON),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.MOON), false);

        MARS= new StoneType(22+addID, "mars", SoundType.STONE, oreMars, GTQTSpaceMaterials.MarsStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.MARS),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.MARS), false);

        VENUS= new StoneType(23+addID, "venus", SoundType.STONE, oreVenus, GTQTSpaceMaterials.VenusStone,
                () -> gtStoneState(GTQTSStoneVariantBlock.StoneType.VENUS),
                state -> gtStonePredicate(state, GTQTSStoneVariantBlock.StoneType.VENUS), false);
    }

    private static IBlockState gtStoneState(GTQTSStoneVariantBlock.StoneType stoneType) {
        return GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(stoneType);
    }

    private static boolean gtStonePredicate(IBlockState state, GTQTSStoneVariantBlock.StoneType stoneType) {
        GTQTSStoneVariantBlock block = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH);
        return state.getBlock() == block && block.getState(state) == stoneType;
    }
}

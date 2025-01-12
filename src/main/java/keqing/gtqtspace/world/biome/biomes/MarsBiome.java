package keqing.gtqtspace.world.biome.biomes;

import keqing.gtqtspace.api.world.BiomeData;
import keqing.gtqtspace.api.world.BiomeGenBaseGC;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import keqing.gtqtspace.world.worldgen.mapGen.BiomeGenFlatMars;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

import static keqing.gtqtspace.common.block.blocks.GTQTSMarsBlock.BlockType.*;


public class MarsBiome extends BiomeGenBaseGC {
    public static final Biome marsFlat = new BiomeGenFlatMars(BiomeData.builder().biomeName("Mars Flat").baseHeight(2.5F).heightVariation(0.4F).build());


    public static final IBlockState BLOCK_TOP = GTQTSMetaBlocks.marsBlock.getState(MARS_TURF);
    public static final IBlockState BLOCK_FILL = GTQTSMetaBlocks.marsBlock.getState(MARS_DIRT);
    public static final IBlockState BLOCK_LOWER = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MARS);


    public MarsBiome(BiomeData properties)
    {
        super(new Biome.BiomeProperties("gtqts_biome.mars").setWaterColor(0xADD8E6).setTemperature(0.5F));
    }

    @Override
    public float getSpawningChance()
    {
        return 0.01F;
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.fillerBlock = BLOCK_LOWER;
        this.topBlock = BLOCK_TOP;
        super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

}
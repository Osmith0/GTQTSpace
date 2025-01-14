package keqing.gtqtspace.world.biome.biomes;

import keqing.gtqtspace.api.world.BiomeData;
import keqing.gtqtspace.api.world.BiomeGenBaseGC;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import keqing.gtqtspace.common.block.blocks.GTQTSVenusBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeVenus extends BiomeGenBaseGC
{
    public static final Biome venusValley = new BiomeGenVenusValley(BiomeData.builder().biomeName("Venus Valley").baseHeight(-0.4F).heightVariation(0.2F).temperature(4.0F).build());

    public BiomeVenus(BiomeData properties)
    {
        super(new Biome.BiomeProperties("gtqts_biome.venus").setWaterColor(0xADD8E6).setTemperature(0.5F));
    }

    @Override
    public float getSpawningChance()
    {
        return 0.01F;
    }
    public final void generateBiomeTerrainVenus(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int p_180628_4_, int p_180628_5_, double p_180628_6_)
    {
        int i = worldIn.getSeaLevel();
        IBlockState topBlock = GTQTSMetaBlocks.venusBlock.getState(GTQTSVenusBlock.BlockType.VENUS_TURF);
        IBlockState stoneBlock = GTQTSMetaBlocks.venusBlock.getState(GTQTSVenusBlock.BlockType.VENUS_DIRT);
        IBlockState fillerBlock = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MARS);

        int j = -1;
        int k = (int) (p_180628_6_ / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int l = p_180628_4_ & 15;
        int i1 = p_180628_5_ & 15;

        for (int j1 = 255; j1 >= 0; --j1)
        {
            if (j1 <= rand.nextInt(5))
            {
                chunkPrimerIn.setBlockState(i1, j1, l, Blocks.BEDROCK.getDefaultState());
            } else
            {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                if (iblockstate2.getMaterial() == Material.AIR)
                {
                    j = -1;
                } else if (iblockstate2 == stoneBlock)
                {
                    if (j == -1)
                    {
                        if (k <= 0)
                        {
                            topBlock = null;
                            fillerBlock = stoneBlock;
                        } else if (j1 >= i - 4 && j1 <= i + 1)
                        {
                            topBlock = this.topBlock;
                            fillerBlock = this.fillerBlock;
                        }

                        j = k;

                        if (j1 >= i - 1)
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, topBlock);
                        } else if (j1 < i - 7 - k)
                        {
                            topBlock = null;
                            fillerBlock = stoneBlock;
                        } else
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, fillerBlock);
                        }
                    } else if (j > 0)
                    {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, fillerBlock);
                    }
                }
            }
        }
    }
}
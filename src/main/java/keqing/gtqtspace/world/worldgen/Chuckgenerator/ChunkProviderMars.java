package keqing.gtqtspace.world.worldgen.Chuckgenerator;

import com.google.common.collect.Lists;
import keqing.gtqtspace.api.world.BiomeDecoratorSpace;
import keqing.gtqtspace.api.world.ChunkProviderSpace;
import keqing.gtqtspace.api.world.gen.MapGenBaseMeta;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import keqing.gtqtspace.world.biome.biomes.BiomeDecoratorMars;
import keqing.gtqtspace.world.biome.biomes.MarsBiome;
import keqing.gtqtspace.world.worldgen.mapGen.MapGenCaveMars;
import keqing.gtqtspace.world.worldgen.mapGen.MapGenCavernMars;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.List;

import static keqing.gtqtspace.common.block.blocks.GTQTSMarsBlock.BlockType.MARS_DIRT;
import static keqing.gtqtspace.common.block.blocks.GTQTSMarsBlock.BlockType.MARS_TURF;
import static keqing.gtqtspace.world.biome.GTQTSBiomeHandler.MARS_BIOME;

public class ChunkProviderMars extends ChunkProviderSpace
{

    private final BiomeDecoratorMars marsBiomeDecorator = new BiomeDecoratorMars();
    private final MapGenCavernMars caveGenerator = new MapGenCavernMars();
    private final MapGenCaveMars cavernGenerator = new MapGenCaveMars();

    public ChunkProviderMars(World par1World, long seed, boolean mapFeaturesEnabled)
    {
        super(par1World, seed, mapFeaturesEnabled);
    }

    @Override
    protected BiomeDecoratorSpace getBiomeGenerator()
    {
        return this.marsBiomeDecorator;
    }

    @Override
    protected Biome[] getBiomesForGeneration()
    {
        return new Biome[]
                {MARS_BIOME};
    }

    @Override
    protected int getSeaLevel()
    {
        return 93;
    }

    @Override
    protected List<MapGenBaseMeta> getWorldGenerators()
    {
        List<MapGenBaseMeta> generators = Lists.newArrayList();
        generators.add(this.caveGenerator);
        generators.add(this.cavernGenerator);
        return generators;
    }

    public static final IBlockState BLOCK_TOP = GTQTSMetaBlocks.marsBlock.getState(MARS_TURF);
    public static final IBlockState BLOCK_FILL = GTQTSMetaBlocks.marsBlock.getState(MARS_DIRT);
    public static final IBlockState BLOCK_LOWER = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.MARS);

    @Override
    protected IBlockState getGrassBlock()
    {
        return BLOCK_TOP;
    }

    @Override
    protected IBlockState getDirtBlock()
    {
        return BLOCK_FILL;
    }

    @Override
    protected IBlockState getStoneBlock()
    {
        return BLOCK_LOWER;
    }

    @Override
    public double getHeightModifier()
    {
        return 12;
    }

    @Override
    public double getSmallFeatureHeightModifier()
    {
        return 26;
    }

    @Override
    public double getMountainHeightModifier()
    {
        return 95;
    }

    @Override
    public double getValleyHeightModifier()
    {
        return 50;
    }

    @Override
    public int getCraterProbability()
    {
        return 2000;
    }

    @Override
    public void onChunkProvide(int cX, int cZ, ChunkPrimer primer)
    {
        //this.dungeonGenerator.generate(this.world, cX, cZ, primer);
    }

    @Override
    public void onPopulate(int cX, int cZ)
    {
        //this.dungeonGenerator.generateStructure(this.world, this.rand, new ChunkPos(cX, cZ));
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z)
    {
        //this.dungeonGenerator.generate(this.world, x, z, null);
    }
}
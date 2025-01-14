package keqing.gtqtspace.world.biome.biomes;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;

public class BiomeDecoratorVenus extends BiomeDecorator
{

    private World world;

    public BiomeDecoratorVenus()
    {
    }

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos blockPos)
    {
        if (this.world != null)
        {
            throw new RuntimeException("Already decorating!!");
        } else
        {
            this.world = worldIn;
            this.chunkPos = blockPos;
            this.generateVenus(random);
            this.world = null;
        }
    }

    private void genStandardOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY, Random random)
    {
        for (int var5 = 0; var5 < amountPerChunk; ++var5)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxY - minY) + minY, random.nextInt(16));
            worldGenerator.generate(this.world, random, blockpos);
        }
    }

    private void generateVenus(Random random)
    {
    }
}
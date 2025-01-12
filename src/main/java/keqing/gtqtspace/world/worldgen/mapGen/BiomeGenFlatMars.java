package keqing.gtqtspace.world.worldgen.mapGen;

import keqing.gtqtspace.api.world.BiomeData;
import keqing.gtqtspace.world.biome.biomes.MarsBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenFlatMars extends MarsBiome
{

    public BiomeGenFlatMars(BiomeData properties)
    {
        super(properties);
    }

    @Override
    public void registerTypes(Biome b)
    {
        BiomeDictionary.addTypes(b, BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SANDY);
    }
}
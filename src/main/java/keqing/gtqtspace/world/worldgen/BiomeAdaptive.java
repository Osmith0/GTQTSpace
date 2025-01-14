package keqing.gtqtspace.world.worldgen;

import keqing.gtqtspace.api.world.BiomeData;
import keqing.gtqtspace.api.world.BiomeGenBaseGC;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.LinkedList;
import java.util.List;

public class BiomeAdaptive extends BiomeGenBaseGC
{

    public static BiomeAdaptive biomeDefault;
    public static List<BiomeAdaptive> biomeList = new LinkedList<>();
    private Biome biomeTrue;
    private final int index;
    private boolean loggedConflict;

    public BiomeAdaptive(int i, Biome biomeInitial)
    {
        super(BiomeData.builder().biomeName("Outer Space" + (i == 0 ? "" : " " + i)).build());
        this.index = i;
        this.biomeTrue = biomeInitial;
        this.decorator = this.createBiomeDecorator();
        if (index == 0)
        {
            biomeDefault = this;
        }
    }

    @Override
    public void registerTypes(Biome b)
    {
        if (this.biomeTrue instanceof BiomeGenBaseGC)
        {
            ((BiomeGenBaseGC) this.biomeTrue).registerTypes(this);
        } else
        {
            BiomeDictionary.addTypes(this, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.SPOOKY);
        }
    }

    public static BiomeGenBaseGC register(int index, BiomeGenBaseGC biome)
    {
        if (index >= biomeList.size())
        {
            BiomeAdaptive newAdaptive = new BiomeAdaptive(index, biome);
            biomeList.add(newAdaptive);
            return newAdaptive;
        }
        return biomeList.get(index);
    }



    public boolean isInstance(Class<?> clazz)
    {
        return clazz.isInstance(this.biomeTrue);
    }



}
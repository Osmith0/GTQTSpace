package keqing.gtqtspace.world.biome.biomes;


import keqing.gtqtspace.api.world.BiomeDecoratorSpace;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorMars extends BiomeDecoratorSpace
{


    private World currentWorld;

    public BiomeDecoratorMars()
    {

    }

    @Override
    protected void decorate()
    {

    }

    @Override
    protected void setCurrentWorld(World world)
    {
        this.currentWorld = world;
    }

    @Override
    protected World getCurrentWorld()
    {
        return this.currentWorld;
    }
}
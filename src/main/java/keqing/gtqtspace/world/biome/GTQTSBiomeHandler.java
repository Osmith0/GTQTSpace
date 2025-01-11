package keqing.gtqtspace.world.biome;

import keqing.gtqtspace.world.biome.biomes.MoonBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class GTQTSBiomeHandler {
    // 创建一个静态实例
    public static final Biome MOON_BIOME = new MoonBiome();
    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        IForgeRegistry<Biome> registry = event.getRegistry();

        MOON_BIOME.setRegistryName(new ResourceLocation("GTQTSpace", "gtqts_biome.moon"));
        registry.register(MOON_BIOME);
        BiomeManager.addSpawnBiome(MOON_BIOME);

    }
}

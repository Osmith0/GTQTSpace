package keqing.gtqtspace.world.biome.biomes;

import net.minecraft.world.biome.Biome;

public class MoonBiome extends Biome {

    public MoonBiome() {
        super(new Biome.BiomeProperties("Moon")
                .setWaterColor(0xADD8E6)
                .setTemperature(0.5F)
                .setRainfall(0.0F)
                .setRainDisabled());
        // 清除所有自然生成的生物列表
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

}
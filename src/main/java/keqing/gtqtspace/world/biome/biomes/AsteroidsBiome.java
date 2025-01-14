package keqing.gtqtspace.world.biome.biomes;

import net.minecraft.world.biome.Biome;

public class AsteroidsBiome extends Biome {

    public AsteroidsBiome() {
        super(new BiomeProperties("Asteroids").setWaterColor(0xADD8E6).setTemperature(0.5F));
        // 清除所有自然生成的生物列表
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

}
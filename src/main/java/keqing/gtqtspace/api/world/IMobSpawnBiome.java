package keqing.gtqtspace.api.world;

import net.minecraft.world.biome.Biome;

import java.util.LinkedList;

public interface IMobSpawnBiome
{

    public void initialiseMobLists(LinkedList<Biome.SpawnListEntry> mobInfo);
}
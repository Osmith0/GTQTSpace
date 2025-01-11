package keqing.gtqtspace.world.dims;

import keqing.gtqtspace.client.skyRender.SkyProviderMoon;
import keqing.gtqtspace.world.biome.GTQTSBiomeHandler;
import keqing.gtqtspace.world.worldgen.Chuckgenerator.ChunkProviderMoon;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static keqing.gtqtspace.world.worldgen.GTQTSDimensionType.MOON_TYPE;

public class MoonProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return MOON_TYPE;
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public long getWorldTime() {

        return super.getWorldTime();
    }

    @Override
    public boolean isDaytime() {

        return super.isDaytime();
    }

    @Override
    protected void init() {
        this.hasSkyLight = true; // 如果你的维度应该有天空光照，设为true
        this.biomeProvider = new BiomeProviderSingle(GTQTSBiomeHandler.MOON_BIOME);// 初始化你维度的生物群系提供器
        // 设置你维度的ID，通常通过DimensionManager.getNextFreeDimId()获取
    }
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderMoon(this.world,  this.world.getSeed(),this.world.getWorldInfo().isMapFeaturesEnabled());
    }
    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
        super.setSkyRenderer(new SkyProviderMoon(true, true));
        return super.getSkyRenderer();
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }
}

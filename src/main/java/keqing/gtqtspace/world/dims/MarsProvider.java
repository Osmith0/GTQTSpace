package keqing.gtqtspace.world.dims;

import keqing.gtqtspace.api.utils.GTQTSConstants;
import keqing.gtqtspace.client.skyRender.SkyProviderMoon;
import keqing.gtqtspace.world.worldgen.Chuckgenerator.ChunkProviderMars;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static keqing.gtqtspace.world.biome.biomes.MarsBiome.marsFlat;
import static keqing.gtqtspace.world.worldgen.GTQTSDimensionType.MARS_TYPE;

public class MarsProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return MARS_TYPE;
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vec3d(210F / 255F * f, 120F / 255F * f, 59F / 255F * f);
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vec3d(154 / 255.0F * f, 114 / 255.0F * f, 66 / 255.0F * f);
    }
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 0.0F;
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
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        float f1 = this.world.getCelestialAngle(par1);
        float f2 = 1.0F - (MathHelper.cos(f1 * GTQTSConstants.twoPI) * 2.0F + 0.25F);

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        return f2 * f2 * 0.75F;
    }
    @Override
    protected void init() {
        this.hasSkyLight = true; // 如果你的维度应该有天空光照，设为true
        this.biomeProvider = new BiomeProviderSingle(marsFlat);// 初始化你维度的生物群系提供器
        // 设置你维度的ID，通常通过DimensionManager.getNextFreeDimId()获取

    }
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderMars(this.world,  this.world.getSeed(),this.world.getWorldInfo().isMapFeaturesEnabled());
    }
    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
        super.setSkyRenderer(new SkyProviderMoon(false, true));
        return super.getSkyRenderer();
    }
}

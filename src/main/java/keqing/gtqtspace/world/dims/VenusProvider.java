package keqing.gtqtspace.world.dims;

import keqing.gtqtspace.api.utils.GTQTSConstants;
import keqing.gtqtspace.client.skyRender.SkyProviderMoon;
import keqing.gtqtspace.world.worldgen.Chuckgenerator.ChunkProviderVenus;
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

import static keqing.gtqtspace.world.biome.biomes.BiomeVenus.*;
import static keqing.gtqtspace.world.worldgen.GTQTSDimensionType.VENUS_TYPE;

public class VenusProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return VENUS_TYPE;
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
        float night = this.getStarBrightness(1.0F);
        float day = 1.0F - this.getStarBrightness(1.0F);
        float dayColR = 1.0f;
        float dayColG = 207.0F / 255.0F;
        float dayColB = 81.0F / 255.0F;
        float nightColR = 118.0F / 255.0F;
        float nightColG = 89.0F / 255.0F;
        float nightColB = 21.0F / 255.0F;
        return new Vec3d(dayColR * day + nightColR * night, dayColG * day + nightColG * night, dayColB * day + nightColB * night);
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        float night = this.getStarBrightness(1.0F);
        float day = 1.0F - this.getStarBrightness(1.0F);
        float dayColR = 203.0F / 255.0F;
        float dayColG = 147.0F / 255.0F;
        float dayColB = 0.0F / 255.0F;
        float nightColR = 131.0F / 255.0F;
        float nightColG = 108.0F / 255.0F;
        float nightColB = 46.0F / 255.0F;
        return new Vec3d(dayColR * day + nightColR * night, dayColG * day + nightColG * night, dayColB * day + nightColB * night);
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
    public float getStarBrightness(float par1) {
        float f1 = this.world.getCelestialAngle(par1);
        float f2 = 1.0F - (MathHelper.cos(f1 * GTQTSConstants.twoPI) * 2.0F + 0.25F);

        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        return f2 * f2 * 0.75F;
    }

    @Override
    protected void init() {
        this.hasSkyLight = true; // 如果你的维度应该有天空光照，设为true
        this.biomeProvider = new BiomeProviderSingle(venusValley);// 初始化你维度的生物群系提供器
    }

    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderVenus(this.world, this.world.getSeed(), this.world.getWorldInfo().isMapFeaturesEnabled());
    }

    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
        super.setSkyRenderer(new SkyProviderMoon(false, true));
        return super.getSkyRenderer();
    }
}

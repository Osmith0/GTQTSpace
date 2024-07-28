package keqing.gtqtspace.world;

import keqing.gtqtspace.GTQTSpace;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;

public class VoidWorldProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return GTQTSpace.type;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new VoidChunkGenerator(world);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {

        return super.getFogColor(p_76562_1_, p_76562_2_);
    }

    @Override
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {

        return super.getSkyColor(cameraEntity, partialTicks);
    }

    @Override
    public long getWorldTime() {

        return super.getWorldTime();
    }

    @Override
    public boolean isDaytime() {

        return super.isDaytime();
    }

    @Nullable
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {

        return super.calcSunriseSunsetColors(celestialAngle, partialTicks);
    }


    public int getRespawnDimension(net.minecraft.entity.player.EntityPlayerMP player)
    {

        return 0;
    }

}
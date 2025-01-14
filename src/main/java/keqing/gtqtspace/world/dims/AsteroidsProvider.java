package keqing.gtqtspace.world.dims;

import keqing.gtqtspace.api.world.AsteroidSaveData;
import keqing.gtqtspace.api.worldgen.vector.BlockVec3;
import keqing.gtqtspace.client.skyRender.SkyProviderMoon;
import keqing.gtqtspace.world.biome.GTQTSBiomeHandler;
import keqing.gtqtspace.world.worldgen.Chuckgenerator.ChunkProviderAsteroids;
import keqing.gtqtspace.world.worldgen.Chuckgenerator.ChunkProviderMoon;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.HashSet;

import static keqing.gtqtspace.world.worldgen.GTQTSDimensionType.ASTEROIDS_TYPE;
import static keqing.gtqtspace.world.worldgen.GTQTSDimensionType.MOON_TYPE;

public class AsteroidsProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return ASTEROIDS_TYPE;
    }
    private AsteroidSaveData datafile;
    @Override
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0, 0, 0);
    }

    // Used to list asteroid centres to external code that needs to know them
    private HashSet<AsteroidData> asteroids = new HashSet<>();
    private boolean dataNotLoaded = true;
    public void addAsteroid(int x, int y, int z, int size, int core)
    {
        AsteroidData coords = new AsteroidData(x, y, z, size, core);
        if (!this.asteroids.contains(coords))
        {
            if (this.dataNotLoaded)
            {
                this.loadAsteroidSavedData();
            }
            if (!this.asteroids.contains(coords))
            {
                this.addToNBT(this.datafile.datacompound, coords);
                this.asteroids.add(coords);
            }
        }
    }


    private void loadAsteroidSavedData()
    {
        this.datafile = (AsteroidSaveData) this.world.loadData(AsteroidSaveData.class, AsteroidSaveData.saveDataID);

        if (this.datafile == null)
        {
            this.datafile = new AsteroidSaveData("");
            this.world.setData(AsteroidSaveData.saveDataID, this.datafile);
            this.writeToNBT(this.datafile.datacompound);
        } else
        {
            this.readFromNBT(this.datafile.datacompound);
        }

        this.dataNotLoaded = false;
    }

    private void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList coordList = nbt.getTagList("coords", 10);
        if (coordList.tagCount() > 0)
        {
            for (int j = 0; j < coordList.tagCount(); j++)
            {
                NBTTagCompound tag1 = coordList.getCompoundTagAt(j);

                if (tag1 != null)
                {
                    this.asteroids.add(AsteroidData.readFromNBT(tag1));
                }
            }
        }
    }

    private void writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList coordList = new NBTTagList();
        for (AsteroidData coords : this.asteroids)
        {
            NBTTagCompound tag = new NBTTagCompound();
            coords.writeToNBT(tag);
            coordList.appendTag(tag);
        }
        nbt.setTag("coords", coordList);
        this.datafile.markDirty();
    }

    private void addToNBT(NBTTagCompound nbt, AsteroidData coords)
    {
        NBTTagList coordList = nbt.getTagList("coords", 10);
        NBTTagCompound tag = new NBTTagCompound();
        coords.writeToNBT(tag);
        coordList.appendTag(tag);
        nbt.setTag("coords", coordList);
        this.datafile.markDirty();
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
        this.biomeProvider = new BiomeProviderSingle(GTQTSBiomeHandler.ASTEROIDS_BIOME);// 初始化你维度的生物群系提供器
        // 设置你维度的ID，通常通过DimensionManager.getNextFreeDimId()获取
    }
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderAsteroids(this.world,  this.world.getSeed(),this.world.getWorldInfo().isMapFeaturesEnabled());
    }
    @Nullable
    @SideOnly(Side.CLIENT)
    public net.minecraftforge.client.IRenderHandler getSkyRenderer() {
        super.setSkyRenderer(new SkyProviderMoon(false, true));
        return super.getSkyRenderer();
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    private static class AsteroidData
    {

        protected BlockVec3 centre;
        protected int sizeAndLandedFlag = 15;
        protected int coreAndSpawnedFlag = -2;

        public AsteroidData(int x, int y, int z)
        {
            this.centre = new BlockVec3(x, y, z);
        }

        public AsteroidData(int x, int y, int z, int size, int core)
        {
            this.centre = new BlockVec3(x, y, z);
            this.sizeAndLandedFlag = size;
            this.coreAndSpawnedFlag = core;
        }

        public AsteroidData(BlockVec3 bv)
        {
            this.centre = bv;
        }

        @Override
        public int hashCode()
        {
            if (this.centre != null)
            {
                return this.centre.hashCode();
            } else
            {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof AsteroidData)
            {
                BlockVec3 vector = ((AsteroidData) o).centre;
                return this.centre.x == vector.x && this.centre.y == vector.y && this.centre.z == vector.z;
            }

            if (o instanceof BlockVec3)
            {
                BlockVec3 vector = (BlockVec3) o;
                return this.centre.x == vector.x && this.centre.y == vector.y && this.centre.z == vector.z;
            }

            return false;
        }

        public NBTTagCompound writeToNBT(NBTTagCompound tag)
        {
            tag.setInteger("x", this.centre.x);
            tag.setInteger("y", this.centre.y);
            tag.setInteger("z", this.centre.z);
            tag.setInteger("coreAndFlag", this.coreAndSpawnedFlag);
            tag.setInteger("sizeAndFlag", this.sizeAndLandedFlag);
            return tag;
        }

        public static AsteroidData readFromNBT(NBTTagCompound tag)
        {
            BlockVec3 tempVector = new BlockVec3();
            tempVector.x = tag.getInteger("x");
            tempVector.y = tag.getInteger("y");
            tempVector.z = tag.getInteger("z");

            AsteroidData roid = new AsteroidData(tempVector);
            if (tag.hasKey("coreAndFlag"))
            {
                roid.coreAndSpawnedFlag = tag.getInteger("coreAndFlag");
            }
            if (tag.hasKey("sizeAndFlag"))
            {
                roid.sizeAndLandedFlag = tag.getInteger("sizeAndFlag");
            }

            return roid;
        }
    }

}

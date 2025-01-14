package keqing.gtqtspace.world.worldgen.mapGen;
import java.util.Random;

import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenVaporPool extends WorldGenerator
{

    public WorldGenVaporPool()
    {
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        if (position.getY() <= 4)
        {
            return false;
        }
        IBlockState stoneBlock = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getState(GTQTSStoneVariantBlock.StoneType.VENUS);

        int radius = 5 + rand.nextInt(4);
        int radiusSq = radius * radius;
        for (int poolX = -radius; poolX <= radius; poolX++)
        {
            for (int poolY = -radius; poolY <= radius; poolY++)
            {
                for (int poolZ = -radius; poolZ <= radius; poolZ++)
                {
                    int distance = poolX * poolX + poolY * poolY + poolZ * poolZ;

                    if (distance <= radiusSq)
                    {
                        BlockPos pos = new BlockPos(poolX + position.getX(), poolY + position.getY(), poolZ + position.getZ());
                        worldIn.setBlockState(pos, distance >= radiusSq - 16 ? stoneBlock : (poolY <= 0 ? stoneBlock : Blocks.AIR.getDefaultState()),
                                distance == radiusSq ? 3 : 2);
                    }
                }
            }
        }

        boolean firstSet = false;
        for (int i = 255; i >= position.getY() + 1; --i)
        {
            BlockPos pos = new BlockPos(position.getX(), i, position.getZ());
            if (worldIn.getBlockState(pos).getBlock() != Blocks.AIR)
            {
                if (!firstSet)
                {
                    worldIn.setBlockState(pos, stoneBlock);
                    firstSet = true;
                } else
                {
                    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                }
            }
        }

        return true;
    }
}
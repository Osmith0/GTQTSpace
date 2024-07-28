package keqing.gtqtspace.world;

import keqing.gtqtspace.GTQTSpace;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class WorldTeleporter extends Teleporter {

    BlockPos pos;
    WorldServer world;

    public WorldTeleporter(WorldServer worldIn) {
        super(worldIn);
        world = worldIn;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {

            pos = new BlockPos(0, 120, 0);
            if (world.getBlockState(pos).getBlock() != GTQTSpace.portal) {
                int color = world.rand.nextInt(15);
                for (int x = -3; x < 4; x++) {
                    for (int z = -3; z < 4; z++) {
                        if (world.isAirBlock(pos.add(x, 0, z))) {
                            world.setBlockState(pos.add(x, 0, z), Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(color));
                        }

                    }
                }
                for(EnumFacing facing : EnumFacing.HORIZONTALS){
                    world.setBlockState(pos.up().offset(facing), Blocks.TORCH.getDefaultState());
                }

            }
        entityIn.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY() + 1, (double) pos.getZ() + 0.5, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = 0.0D;
        entityIn.motionY = 0.0D;
        entityIn.motionZ = 0.0D;

    }
}
package keqing.gtqtspace.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

public class DimensionTeleporter extends Teleporter {
    public DimensionTeleporter(WorldServer worldIn) {
        super(worldIn);
    }

    public static void  teleportToDimension(EntityPlayerMP player, int dimension, @Nullable BlockPos pos) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        WorldServer targetWorld = server.getWorld(dimension);

        if (pos == null) {
            pos = targetWorld.getSpawnPoint();
        }

        // 预加载区块
        targetWorld.getChunkProvider().loadChunk(pos.getX() >> 4, pos.getZ() >> 4);

        // 执行传送
        player.changeDimension(dimension, new CustomTeleporter(targetWorld, pos));
        SyncInit.ROCKET_NETWORK.sendTo(new PacketSyncDimension(dimension, pos), player);


    }
}
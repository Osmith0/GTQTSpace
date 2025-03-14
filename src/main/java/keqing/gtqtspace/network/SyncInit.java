package keqing.gtqtspace.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class SyncInit {
    public static final SimpleNetworkWrapper ROCKET_NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("dr_c_ro");
    public static final SimpleNetworkWrapper PRESSED_NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("dr_c_pre");
    public static void init() {
        // 第一个参数是数据包ID
        ROCKET_NETWORK.registerMessage(PacketSyncDimension.Handler.class,PacketSyncDimension.class,2,Side.CLIENT);
        PRESSED_NETWORK.registerMessage(PacketJumpKey.Handler.class, PacketJumpKey.class, 3, Side.SERVER);
    }
}

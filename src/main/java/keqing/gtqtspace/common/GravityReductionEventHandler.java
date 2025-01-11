package keqing.gtqtspace.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class GravityReductionEventHandler {
    private static final float GRAVITY_REDUCTION_FACTOR = 5f;

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;

        if (!world.isRemote) {
            if (world.provider.getDimension()==51) {
                player.motionY *= GRAVITY_REDUCTION_FACTOR;
            }
        }
    }
}

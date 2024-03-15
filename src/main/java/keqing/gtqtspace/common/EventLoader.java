package keqing.gtqtspace.common;

import gregtech.api.unification.material.event.MaterialEvent;
import keqing.gtqtspace.api.unifications.GTQTSpaceMaterials;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(
        modid = "gtqtspace"
)
public class EventLoader {

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public static void registerMaterials(MaterialEvent event)
    {
        GTQTSpaceMaterials.register();

        //在此处注册材料
    }
}
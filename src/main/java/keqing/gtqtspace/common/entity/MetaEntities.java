package keqing.gtqtspace.common.entity;

import keqing.gtqtspace.GTQTSpace;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetaEntities {
    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(GTQTSpace.MODID, "space_ship"), EntitySpaceShip.class,"Space Ship",5,GTQTSpace.instance,64,3,true);
        EntityRegistry.registerEgg(new ResourceLocation(GTQTSpace.MODID, "space_ship"),0x58e12e, 0x1412038);
    }

    @SideOnly(Side.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySpaceShip.class, new EntitySpaceShipRender(Minecraft.getMinecraft().getRenderManager()));
    }
}

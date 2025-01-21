package keqing.gtqtspace.client.objmodels;

import keqing.gtqtcore.api.obj.AdvancedModelLoader;
import keqing.gtqtcore.api.obj.IModelCustom;
import net.minecraft.util.ResourceLocation;

public class ObjModels {
    public static final IModelCustom Ship = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtspace", "models/obj/ship.obj"));
    public static final ResourceLocation Ship_pic = new ResourceLocation("gtqtspace", "models/obj/ship_pic.png");

    public static final IModelCustom climber = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtspace", "models/obj/climber.obj"));
    public static final ResourceLocation climber_pic = new ResourceLocation("gtqtspace", "models/obj/climber.png");

    public static final IModelCustom dock = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtspace", "models/obj/dock.obj"));
    public static final ResourceLocation dock_pic = new ResourceLocation("gtqtspace", "models/obj/dock.png");

}

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

    public static final IModelCustom launcher_tower_base = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtspace", "models/obj/launcher_tower_base.obj"));
    public static final ResourceLocation launcher_tower_base_pic = new ResourceLocation("gtqtspace", "models/obj/launcher_tower_base.png");

    public static final IModelCustom launcher_tower = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtspace", "models/obj/launcher_tower.obj"));
    public static final ResourceLocation launcher_tower_pic = new ResourceLocation("gtqtspace", "models/obj/launcher_tower.png");

    public static final IModelCustom darius_wind_generator = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtspace", "models/obj/darius_wind_generator.obj"));
    public static final ResourceLocation wind_generator_pic = new ResourceLocation("gtqtspace", "models/obj/wind_generator.png");

}

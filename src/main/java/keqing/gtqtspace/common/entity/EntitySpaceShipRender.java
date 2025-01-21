package keqing.gtqtspace.common.entity;

import keqing.gtqtcore.api.obj.AdvancedModelLoader;
import keqing.gtqtcore.api.obj.IModelCustom;
import keqing.gtqtspace.GTQTSpace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EntitySpaceShipRender extends RenderLiving {
    private static final ResourceLocation MODEL = new ResourceLocation(GTQTSpace.MODID, "models/obj/ship.obj");
    private static final ResourceLocation TEXTURE = new ResourceLocation(GTQTSpace.MODID, "models/obj/ship_pic.png");
    private final IModelCustom model;

    public EntitySpaceShipRender(RenderManager rendermanagerIn) {
        super(rendermanagerIn, null, 0.5f);
        this.model = AdvancedModelLoader.loadModel(MODEL);
    }


    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        this.bindEntityTexture(entity);
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(180, 0F, 1F, 0F);
        GlStateManager.scale(0.05, 0.05, 0.05);
        model.renderAll();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.popAttrib();
        GlStateManager.enableCull();
    }

    protected boolean bindEntityTexture(Entity entity) {
        ResourceLocation resourcelocation = this.getEntityTexture(entity);

        if (resourcelocation == null) {
            return false;
        } else {
            this.bindTexture(resourcelocation);
            return true;
        }
    }

    public void bindTexture(ResourceLocation location) {
        this.renderManager.renderEngine.bindTexture(location);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}

package keqing.gtqtspace.client.Entity;

import keqing.gtqtcore.api.obj.AdvancedModelLoader;
import keqing.gtqtcore.api.obj.IModelCustom;
import keqing.gtqtspace.GTQTSpace;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityFirstRocketRender extends Render {
    private IModelCustom model;
    private static final ResourceLocation MODEL = new ResourceLocation(GTQTSpace.MODID, "models/shipi.obj");
    private static final ResourceLocation TEXTURE =new ResourceLocation(GTQTSpace.MODID,"models/rocket1.png");
    public EntityFirstRocketRender(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
        this.model =  AdvancedModelLoader.loadModel(MODEL);
    }


    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        this.bindEntityTexture(entity);
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.35, 0.35, 0.35);
        model.renderAll();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.popAttrib();
        GlStateManager.enableCull();
    }

    protected boolean bindEntityTexture(Entity entity)
    {
        ResourceLocation resourcelocation = this.getEntityTexture(entity);

        if (resourcelocation == null)
        {
            return false;
        }
        else
        {
            this.bindTexture(resourcelocation);
            return true;
        }
    }

    public void bindTexture(ResourceLocation location)
    {
        this.renderManager.renderEngine.bindTexture(location);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}

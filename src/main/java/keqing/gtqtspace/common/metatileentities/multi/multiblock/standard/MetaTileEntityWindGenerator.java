package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtspace.client.objmodels.ObjModels;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gregtech.api.GTValues.V;
import static net.minecraft.tileentity.TileEntity.INFINITE_EXTENT_AABB;

public class MetaTileEntityWindGenerator extends MultiblockWithDisplayBase implements IFastRenderMetaTileEntity {
    int speed;
    private IEnergyContainer energyContainer;

    public MetaTileEntityWindGenerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("speed", speed);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        speed = data.getInteger("speed");
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityWindGenerator(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (isStructureFormed()) {
            this.energyContainer.addEnergy((long) (V[3] * Math.pow(2, speed)) * getWeatherFactor());
        }
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (speed==0) {
            textList.add(new TextComponentTranslation("当前维度不能使用风力发电机！"));
        }

    }


    public void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation(">>发电量：" + (V[4] * Math.pow(2, speed)) * getWeatherFactor()));
        textList.add(new TextComponentTranslation(">>天气增幅：" + getWeatherFactor()));
        textList.add(new TextComponentTranslation(">>维度增幅：" + speed));
    }
    private int getWeatherFactor() {
        // 考虑更多天气情况
        if (getWorld().isRaining()) {
            return 2;
        } else if (getWorld().isThundering()) {
            return 3; // 雷暴天气
        } else {
            return 1; // 正常天气
        }
    }

    private IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));

        switch (this.getWorld().provider.getDimension()) {
            case 52: {
                speed = 2;
                break;
            }
            case 53: {
                speed = 4;
                break;
            }
            case -1, 1: {
                speed = 0;
                break;
            }
            case 0:
            default: {
                speed = 1;
                break;
            }
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AA   AA", "B     B", "B     B", "AAAAAAA", "       ")
                .aisle("AAAAAAA", " AAAAA ", " AAAAA ", "AAAAAAA", "       ")
                .aisle(" AAAAA ", " AB BA ", " AB BA ", "AAB BAA", "  B B  ")
                .aisle(" AAAAA ", " A   A ", " A   A ", "AA   AA", "       ")
                .aisle(" AAAAA ", " AB BA ", " AB BA ", "AAB BAA", "  B B  ")
                .aisle("AAAAAAA", " ACCCA ", " AASAA ", "AAAAAAA", "       ")
                .aisle("AA   AA", "B     B", "B     B", "AAAAAAA", "       ")
                .where('S', selfPredicate())
                .where(' ', any())
                .where('B', states(getFrameState()))
                .where('C',
                        states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                                .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                                .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                )
                .where('A', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        IFastRenderMetaTileEntity.super.renderMetaTileEntity(x, y, z, partialTicks);

        if (isStructureFormed()) {
            final int xDir = this.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.getFrontFacing().getOpposite().getZOffset();
            //机器开启才会进行渲染
            //这是一些opengl的操作,GlStateManager是mc自身封装的一部分方法  前四条详情请看 https://turou.fun/minecraft/legacy-render-tutor/
            //opengl方法一般需要成对出现，实际上他是一个状态机，改装状态后要还原  一般情况按照我这些去写就OK
            GlStateManager.pushAttrib(); //保存变换前的位置和角度
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.wind_generator_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动
            GlStateManager.translate(xDir * 2 + 0.5, 3.2, zDir * 2 + 0.5);//translate是移动方法 这个移动到xyz是默认的 不要动


            float angle = (System.currentTimeMillis() % 36000) / 10.0f; //我写的随时间变化旋转的角度
            //GlStateManager.rotate(90, 0F, 1F, 0F);//rotate是旋转模型的方法  DNA的初始位置不太对 我旋转了一下   四个参数为：旋转角度，xyz轴，可以控制模型围绕哪个轴旋转
            GlStateManager.rotate(angle, 0F, 1F, 0F);//我让dna围绕z轴旋转，角度是实时变化的


            GlStateManager.scale(1.5, 1.5, 1.5);
            // ObjModels.Tree_Model.renderAllWithMtl(); //这个是模型加载器的渲染方法  这是带MTL的加载方式
            ObjModels.darius_wind_generator.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
            GlStateManager.popMatrix();//读取变换前的位置和角度(恢复原状) 下面都是还原状态机的语句
            GlStateManager.enableLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableCull();

        }

    }
    @Override
    public boolean isGlobalRenderer() {
        return true;
    }
    //渲染模型的位置
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }
}

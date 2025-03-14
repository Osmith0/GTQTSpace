package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
import keqing.gtqtspace.client.objmodels.ObjModels;
import keqing.gtqtspace.common.entity.EntityAdvancedRocket;
import keqing.gtqtspace.network.DimensionTeleporter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.RocketFuel;
import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtspace.api.utils.Datas.*;

public class MetaTileEntityRocketLaunchPad extends MetaTileEntityBaseWithControl implements IFastRenderMetaTileEntity {

    private final FluidStack rocketFuelFluid = RocketFuel.getFluid(100);
    private final FluidStack rp1RocketFuelFluid = RP1RocketFuel.getFluid(100);
    private final FluidStack denseHydrazineMixtureFuelFluid = DenseHydrazineMixtureFuel.getFluid(100);
    private final FluidStack methylhydrazineNitrateRocketFuelFluid = MethylhydrazineNitrateRocketFuel.getFluid(100);
    int fuelAmount;
    int targetDimension;
    int page = 0;

    public MetaTileEntityRocketLaunchPad(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("fuelAmount", fuelAmount);
        data.setInteger("targetDimension", targetDimension);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        fuelAmount = data.getInteger("fuelAmount");
        targetDimension = data.getInteger("targetDimension");
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("BAAAAAAAAAAAAAA")
                .aisle("BAAAAAAAAAAAAAA")
                .aisle("SAAAAAAAAAAAAAA")
                .aisle("BAAAAAAAAAAAAAA")
                .aisle("BAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .aisle("AAAAAAAAAAAAAAA")
                .where('S', selfPredicate())
                .where(' ', any())
                .where('B',
                        states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID))
                                .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                                .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                                .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                )

                .where('A',states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityRocketLaunchPad(this.metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 300, 240);
        builder.dynamicLabel(8, 4, () -> I18n.format(getMetaFullName()), 0xFFFFFF);

        builder.image(4, 14, 172, 142, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 18, this::addDisplayText, 16777215)).setMaxWidthLimit(180));

        builder.image(180, 4, 116, 179, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(184, 8, this::addDimension1, 16777215)).setMaxWidthLimit(120));


        builder.widget(new ClickButtonWidget(179, 182, 120, 18, "扫描装填火箭", data ->
        {
            if (!isStructureFormed()) return;

            // 获取玩家或实体的当前位置
            Entity entity = getWorld().getClosestPlayer(100, 64, 200, -1, false); // 替换为你的实体获取逻辑
            if (entity != null) {
                BlockPos centerPos = getPos();
                double leftRange = 5.0; // 左侧 5 格
                double rightRange = 5.0; // 右侧 5 格
                double backRange = 15.0; // 反方向 15 格
                double heightRange = 16.0; // 高度范围 8 格（上下各 8 格，总共 16 格）
                AxisAlignedBB aabb = new AxisAlignedBB(
                        centerPos.getX() - leftRange, centerPos.getY(), centerPos.getZ() + backRange,
                        centerPos.getX() + rightRange, centerPos.getY() + heightRange, centerPos.getZ()
                );
                for (Entity targetEntity : getWorld().getEntitiesWithinAABB(EntityAdvancedRocket.class, aabb)) {
                    if (targetEntity instanceof EntityAdvancedRocket rocket) { // 判断是否为 EntityAdvancedRocket
                        rocket.setDimId(targetDimension);
                        rocket.setFuelAmount(Math.min(1000, fuelAmount));
                        fuelAmount -= Math.min(1000, fuelAmount);
                    }
                }
            }

        }
        ));

        builder.widget(new ClickButtonWidget(179, 200, 120, 18, "选择这个维度", data ->
                targetDimension = getDimensionIdByIndex(page)));

        builder.widget(new ClickButtonWidget(179, 218, 60, 18, "Page -1", this::decrementThreshold));
        builder.widget(new ClickButtonWidget(237, 218, 60, 18, "Page +1", this::incrementThreshold));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder.build(this.getHolder(), entityPlayer);
    }

    private void incrementThreshold(Widget.ClickData clickData) {
        this.page = MathHelper.clamp(page + 1, 0, DIMENSION_NAMES.size() + 1);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.page = MathHelper.clamp(page - 1, 0, DIMENSION_NAMES.size() + 1);
    }

    private void addDimension1(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation(">>维度：" + getDimensionNameByIndex(page)));
        textList.add(new TextComponentTranslation(">>目标维度：" + getDimensionIdByIndex(page)));
    }

    public void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation(">>燃料量：" + fuelAmount));
        textList.add(new TextComponentTranslation(">>目标维度：" + targetDimension));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote) {
            if (!isStructureFormed()) return;
            IMultipleTankHandler inputTank = this.getInputFluidInventory();
            if (rocketFuelFluid.isFluidStackIdentical(inputTank.drain(rocketFuelFluid, false))) {
                inputTank.drain(rocketFuelFluid, true);
                fuelAmount++;
            }
            if (rp1RocketFuelFluid.isFluidStackIdentical(inputTank.drain(rp1RocketFuelFluid, false))) {
                inputTank.drain(rp1RocketFuelFluid, true);
                fuelAmount += 5;
            }
            if (denseHydrazineMixtureFuelFluid.isFluidStackIdentical(inputTank.drain(denseHydrazineMixtureFuelFluid, false))) {
                inputTank.drain(denseHydrazineMixtureFuelFluid, true);
                fuelAmount += 10;
            }
            if (methylhydrazineNitrateRocketFuelFluid.isFluidStackIdentical(inputTank.drain(methylhydrazineNitrateRocketFuelFluid, false))) {
                inputTank.drain(methylhydrazineNitrateRocketFuelFluid, true);
                fuelAmount += 20;
            }
        }
    }

    // 维度切换方法
    private void teleportPlayerToDimension(EntityPlayer player, int targetDimId) {
        // 判断玩家是否已经在目标维度
        if (player.world.provider.getDimension() == targetDimId) {
            return;
        }
        // 执行传送
        DimensionTeleporter.teleportToDimension((EntityPlayerMP) player, targetDimId, null);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        IFastRenderMetaTileEntity.super.renderMetaTileEntity(x, y, z, partialTicks);

        if (isStructureFormed()) {
            final int xDir = this.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.getFrontFacing().getOpposite().getZOffset();




            GlStateManager.pushAttrib(); //保存变换前的位置和角度
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.launcher_tower_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动
            GlStateManager.translate(xDir * 9 + 0.5, 0, zDir * 9 + 0.5);//translate是移动方法 这个移动到xyz是默认的 不要动
            if(this.getFrontFacing()== EnumFacing.NORTH) GlStateManager.rotate(0, 0F, 1F, 0F);
            if(this.getFrontFacing()== EnumFacing.SOUTH) GlStateManager.rotate(180, 0F, 1F, 0F);
            if(this.getFrontFacing()== EnumFacing.WEST) GlStateManager.rotate(90, 0F, 1F, 0F);
            if(this.getFrontFacing()== EnumFacing.EAST) GlStateManager.rotate(-90, 0F, 1F, 0F);
            GlStateManager.scale(0.3, 0.3, 0.3);
            ObjModels.launcher_tower.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
            GlStateManager.popMatrix();//读取变换前的位置和角度(恢复原状) 下面都是还原状态机的语句
            GlStateManager.enableLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableCull();


            GlStateManager.pushAttrib(); //保存变换前的位置和角度
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.launcher_tower_base_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动
            GlStateManager.translate(xDir * 9 + 0.5, 0, zDir * 9 + 0.5);//translate是移动方法 这个移动到xyz是默认的 不要动
            if(this.getFrontFacing()== EnumFacing.NORTH) GlStateManager.rotate(0, 0F, 1F, 0F);
            if(this.getFrontFacing()== EnumFacing.SOUTH) GlStateManager.rotate(180, 0F, 1F, 0F);
            if(this.getFrontFacing()== EnumFacing.WEST) GlStateManager.rotate(90, 0F, 1F, 0F);
            if(this.getFrontFacing()== EnumFacing.EAST) GlStateManager.rotate(-90, 0F, 1F, 0F);
            GlStateManager.scale(0.3, 0.3, 0.3);
            ObjModels.launcher_tower_base.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
            GlStateManager.popMatrix();//读取变换前的位置和角度(恢复原状) 下面都是还原状态机的语句
            GlStateManager.enableLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableCull();
        }

    }

    //渲染模型的位置

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //这个影响模型的可视范围，正常方块都是 1 1 1，长宽高各为1，当这个方块离线玩家视线后，obj模型渲染会停止，所以可以适当放大这个大小能让模型有更多角度的可视
        return new AxisAlignedBB(getPos().add(-10, -10, -10), getPos().add(10, 10, 10));
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }
}

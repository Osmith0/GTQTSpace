package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SpaceDockSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSSolarPlate;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidController;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class Dock extends MetaTileEntityBaseWithControl {
    BlockPos ControlPos = new BlockPos(0, 0, 0);
    public void setControlPos(BlockPos pos)
    {
        ControlPos=pos;
    }
    int[] part = {0, 0, 0, 0, 0};
    private final ItemStackHandler containerInventory;
    boolean work;
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 保存容器库存
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        // 写入单个整数
        data.setIntArray("part", part);
        data.setBoolean("work", work);
        // 写入 BlockPos
        if (ControlPos != null) {
            data.setInteger("ControlPosX", ControlPos.getX());
            data.setInteger("ControlPosY", ControlPos.getY());
            data.setInteger("ControlPosZ", ControlPos.getZ());
        }

        return super.writeToNBT(data);
    }
    protected void addShipDetail(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "部件列表："));
                        tl.add(new TextComponentTranslation("舰船类型:" + DockUtils.getTypeByID(part[0])));
                        tl.add(new TextComponentTranslation("反应堆模块:" + DockUtils.getEnginModelByID(part[1]) + "-等级:" + part[1]));
                        tl.add(new TextComponentTranslation("推进器模块:" + DockUtils.getGeneratorModelByID(part[2]) + "-等级:" + part[2]));
                        tl.add(new TextComponentTranslation("导航计算机模块:" + DockUtils.getCountModelByID(part[3]) + "-等级:" + part[3]));
                        tl.add(new TextComponentTranslation("探测器模块:" + DockUtils.getSenerModelByID(part[4]) + "-等级:" + part[4]));
                    }
                });
    }
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        // 读取容器库存
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));

        // 读取单个整数
        part = data.getIntArray("part");
        work = data.getBoolean("work");
        // 读取 BlockPos
        if (data.hasKey("ControlPosX") && data.hasKey("ControlPosY") && data.hasKey("ControlPosZ")) {
            int posX = data.getInteger("ControlPosX");
            int posY = data.getInteger("ControlPosY");
            int posZ = data.getInteger("ControlPosZ");
            ControlPos = new BlockPos(posX, posY, posZ);
        } else {
            ControlPos = null;
        }
    }
    public void setWork(boolean work)
    {
       this.work=work;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new Dock(metaTileEntityId);
    }
    public Dock(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.containerInventory = new GTItemStackHandler(this, 1);
    }
    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240);
        builder.dynamicLabel(24, 8, () -> "太空船坞", 0xFFFFFF);
        builder.widget(new SlotWidget(containerInventory, 0, 8, 8, !work, !work)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("输入槽位"));

        builder.image(4, 28, 172, 128, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 32 , this::addShipDetail, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder;
    }
    @Override
    protected void updateFormedValid() {
        ItemStack item = this.containerInventory.getStackInSlot(0);
        NBTTagCompound compound = item.getTagCompound();
        int meta = this.containerInventory.getStackInSlot(0).getMetadata();
        part[0]=meta - 199;
        if (compound != null) {
            if (compound.hasKey("enginModel")) part[1] = compound.getInteger("enginModel");
            if (compound.hasKey("generatorModel")) part[2] = compound.getInteger("generatorModel");
            if (compound.hasKey("countModel")) part[3] = compound.getInteger("countModel");
            if (compound.hasKey("senerModel")) part[4] = compound.getInteger("senerModel");
        }
        if(!ControlCheck(ControlPos))
        {
            work=false;
        }
    }
    //控制器检查
    public boolean ControlCheck(BlockPos pos) {
        MetaTileEntity mt = GTUtility.getMetaTileEntity(this.getWorld(), pos);
        if (mt instanceof MetaTileEntity) {
            TileEntity te = this.getWorld().getTileEntity(pos);
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof DockManager) {
                    return ((DockManager) mte).isStructureFormed();
                }
            }
        }
        ControlPos = new BlockPos(0, 0, 0);
        return false;
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC","CCC","CCC")
                .aisle("CCC","CCC","CCC")
                .aisle("CMC","CSC","CCC")
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('S', selfPredicate())
                .where('C', states(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_CASING)))
                .build();
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.isActive(),
                this.isStructureFormed());
    }
    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.SOLAR_PLATE_CASING;
    }
    @Override
    public boolean hasMaintenanceMechanics() {
        return true;
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}

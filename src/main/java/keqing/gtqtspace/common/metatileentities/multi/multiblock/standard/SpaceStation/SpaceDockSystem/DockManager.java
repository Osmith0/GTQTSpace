package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SpaceDockSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtspace.api.utils.GTQTSLog;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSSolarPlate;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.Asteroid;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidDrill;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DockManager extends MetaTileEntityBaseWithControl {
    int IDtoDeal;
    int total;
    int[] perTotal={0,0,0,0,0,0};
    boolean deal;
    List<Material> materials;

    int[][] DockList;
    @Override
    protected void addDisplayText (List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("待处理：%s", IDtoDeal));
        textList.add(new TextComponentTranslation("绑定状态：%s %s %s", ControlPos.getX(),ControlPos.getY(),ControlPos.getZ()));
        textList.add(new TextComponentTranslation("矿脉总数：%s", total));
        if(materials==null)return;
        for (int i=0;i<6;i++) {
            textList.add(new TextComponentTranslation("：%s %s", materials.get(i).getLocalizedName(),perTotal[i]));
        }
    }
    public boolean canSetIDtoDeal()
    {
        return IDtoDeal==0;
    }
    public boolean setIDtoDeal(int ID)
    {
        if(IDtoDeal==0)
        {
            IDtoDeal=ID;
            return true;
        }
        return false;
    }
    BlockPos ControlPos=new BlockPos(0,0,0);
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 写入单个整数
        data.setInteger("IDtoDeal", IDtoDeal);
        data.setIntArray("perTotal", perTotal);
        data.setBoolean("deal", deal);
        data.setInteger("total",total);
        // 写入 BlockPos
        if (ControlPos != null) {
            data.setInteger("ControlPosX", ControlPos.getX());
            data.setInteger("ControlPosY", ControlPos.getY());
            data.setInteger("ControlPosZ", ControlPos.getZ());
        }

        return super.writeToNBT(data);
    }
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        // 读取单个整数
        IDtoDeal = data.getInteger("IDtoDeal");
        materials= Asteroid.getMaterialListById(IDtoDeal);
        perTotal=data.getIntArray("perTotal");
        deal=data.getBoolean("deal");
        total=data.getInteger("total");
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
    public void setControlPos(BlockPos pos)
    {
        ControlPos=pos;
    }
    @Override
    protected void updateFormedValid() {
        if(!isStructureFormed())return;
        if(MachineCheck(ControlPos))
        {
            if(IDtoDeal!=0)
            {
                if(!deal) {
                    total = Asteroid.getRateById(IDtoDeal);
                    perTotal=Asteroid.getPerRateById(IDtoDeal);
                    materials=Asteroid.getMaterialListById(IDtoDeal);
                    if(total==0) GTQTSLog.logger.info("非法的ID!!!");
                    deal=true;
                }
                else
                {
                    for (int i=0;i<6;i++) {
                        if(perTotal[i]!=0&&materials!=null) {

                            GTTransferUtils.insertItem(this.outputInventory, OreDictUnifier.get(new UnificationEntry(OrePrefix.ore, materials.get(i))), false);
                            perTotal[i]--;
                            total--;
                        }
                    }
                    if(total==0)
                    {
                        IDtoDeal=0;
                        deal=false;
                        Arrays.fill(perTotal, 0);
                        materials=null;
                    }
                }
            }
        }
    }
    public boolean MachineCheck(BlockPos pos) {
        MetaTileEntity mt = GTUtility.getMetaTileEntity(this.getWorld(), pos);
        if (mt instanceof MetaTileEntity) {
            TileEntity te = this.getWorld().getTileEntity(pos);
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof AsteroidDrill) {
                    return ((AsteroidDrill) mte).isStructureFormed();
                }
            }
        }
        ControlPos=new BlockPos(0,0,0);
        return false;
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new DockManager(metaTileEntityId);
    }
    public DockManager(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
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
                .where('C', states(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_CASING))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(2).setMinGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
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

package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import keqing.gtqtspace.api.multiblock.SatelliteGenerators;
import keqing.gtqtspace.api.multiblock.SatelliteSeniorUpdates;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public abstract class MetaTileEntitySatelliteRecipeMapMultiblockController extends RecipeMapMultiblockController {
    //这个基类是用来进行配方操作的
    int x;
    int y;
    int z;
    boolean network;
    boolean work;
    int solarTierTMP;
    SatelliteSeniorUpdates seniorUpdateTMP;
    SatelliteGenerators generatorTMP;

    public MetaTileEntitySatelliteRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
    }

    //目标 在这里实现卫星寻址
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("========刻晴的妙妙工具======="));
        textList.add(new TextComponentTranslation("连接状态：%s|卫星获取状态：%s", network,work));
        textList.add(new TextComponentTranslation("=========================="));
    }
    @Override
    protected void updateFormedValid() {
        //首先自检
        if (checkLoacl(true)) {
            //xyz已经赋值，开始找控制器
            BlockPos poss = new BlockPos(x, y, z);
            if (GTUtility.getMetaTileEntity(this.getWorld(), poss) instanceof MetaTileEntity) {
                MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(this.getWorld(), poss);
                if (mte instanceof MetaTileEntitySentryArray) {
                    network=true;
                    work= ((MetaTileEntitySentryArray) mte).findSatellite(solarTierTMP, seniorUpdateTMP, generatorTMP);
                }
            }
        }
    }
    public boolean checkLoacl(boolean sim) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");
                }
                this.getInputInventory().extractItem(i, 1, sim);
                return true;
            }
        }
        return false;
    }
    public boolean getWork()
    {
        return work;
    }
    public void setSatellite(int solarTierTMP,SatelliteSeniorUpdates seniorUpdateTMP,SatelliteGenerators generatorTMP)
    {
        this.solarTierTMP=solarTierTMP;
        this.seniorUpdateTMP=seniorUpdateTMP;
        this.generatorTMP=generatorTMP;
    }
}

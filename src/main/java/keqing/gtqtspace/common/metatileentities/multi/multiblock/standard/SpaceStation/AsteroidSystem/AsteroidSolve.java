package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSSolarPlate;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;

import static keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.IntArrayOperations.containsZero;
import static keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.IntArrayOperations.replaceFirstZero;

public class AsteroidSolve extends MetaTileEntityBaseWithControl implements IOpticalComputationReceiver {
    int IDtoDeal;
    int IDResolve;
    private IOpticalComputationProvider computationProvider;
    int requestCWUt;
    private List<String> whitelist = new ArrayList<>();
    private boolean isWhitelist = true;
    int totalTime;
    public boolean setIDtoDeal(int ID)
    {
        if(IDtoDeal==0)
        {
            IDtoDeal=processID(ID);
            return true;
        }
        return false;
    }
    BlockPos ControlPos=new BlockPos(0,0,0);
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        // 写入单个整数
        data.setInteger("IDtoDeal", IDtoDeal);
        data.setInteger("IDResolve", IDResolve);
        data.setInteger("totalTime", totalTime);
        // 写入 BlockPos
        if (ControlPos != null) {
            data.setInteger("ControlPosX", ControlPos.getX());
            data.setInteger("ControlPosY", ControlPos.getY());
            data.setInteger("ControlPosZ", ControlPos.getZ());
        }
        data.setBoolean("isWhitelist", this.isWhitelist);


        NBTTagList nbtTagList = new NBTTagList();

        for(String s : this.whitelist) {
            NBTTagCompound tag = new NBTTagCompound();

            tag.setString("material", s);
            nbtTagList.appendTag(tag);
        }

        data.setInteger("whitelistSize", this.whitelist.size());
        data.setTag("whitelist", nbtTagList);

        return super.writeToNBT(data);
    }
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        // 读取单个整数
        IDtoDeal = data.getInteger("IDtoDeal");
        IDResolve = data.getInteger("IDResolve");
        totalTime = data.getInteger("totalTime");
        // 读取 BlockPos
        if (data.hasKey("ControlPosX") && data.hasKey("ControlPosY") && data.hasKey("ControlPosZ")) {
            int posX = data.getInteger("ControlPosX");
            int posY = data.getInteger("ControlPosY");
            int posZ = data.getInteger("ControlPosZ");
            ControlPos = new BlockPos(posX, posY, posZ);
        } else {
            ControlPos = null;
        }
        this.isWhitelist = data.getBoolean("isWhitelist");

        if (data.getInteger("whitelistSize") > 0) {
            NBTTagList nbtTagList = data.getTagList("whitelist", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < data.getInteger("whitelistSize"); i++) {
                NBTTagCompound tag = nbtTagList.getCompoundTagAt(i);
                this.whitelist.add(tag.getString("material"));
            }
        }
    }
    public void setControlPos(BlockPos pos)
    {
        ControlPos=pos;
    }
    int time=0;
    @Override
    protected void addDisplayText (List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("绑定状态：%s %s %s", ControlPos.getX(),ControlPos.getY(),ControlPos.getZ()));
        textList.add(new TextComponentTranslation("处理进度：%s / %s", time,totalTime));
        if(isWhitelist)textList.add(new TextComponentTranslation("白名单：%s", whitelist));
        else textList.add(new TextComponentTranslation("黑名单：%s", whitelist));
    }

    protected void addDisplayText1 (List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("正在处理：%s", IDtoDeal));
        if(IDtoDeal!=0) {
            textList.add(new TextComponentTranslation("矿脉总数：%s", Asteroid.getRateById(IDtoDeal)));
            textList.add(new TextComponentTranslation("矿脉距离：%s", Asteroid.TimeToConsume(IDtoDeal)));
            for (int i = 0; i < 6; i++) {
                textList.add(new TextComponentTranslation(">>%s %s", Asteroid.getMaterialByID(IDtoDeal, i).getLocalizedName(), Asteroid.getOreNumByID(IDtoDeal, i)));
            }
        }
    }

    protected void addDisplayText2 (List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("处理完毕：%s",IDResolve));
        if(IDResolve!=0) {
            textList.add(new TextComponentTranslation("矿脉总数：%s", Asteroid.getRateById(IDResolve)));
            textList.add(new TextComponentTranslation("矿脉距离：%s", Asteroid.TimeToConsume(IDResolve)));
            for (int i = 0; i < 6; i++) {
                textList.add(new TextComponentTranslation(">>%s %s", Asteroid.getMaterialByID(IDResolve, i).getLocalizedName(), Asteroid.getOreNumByID(IDResolve, i)));
            }
        }
    }
    protected void addTotal1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能源管理："));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量存储上限： %s", this.energyContainer.getEnergyCapacity()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量缓存上限： %s", this.energyContainer.getEnergyStored()));
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "能量输入速率： %s", this.energyContainer.getInputPerSec()));
                    }
                });
    }
    protected void addTotal2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "解析管理："));
                        tl.add(new TextComponentTranslation("算力提供：%s", requestCWUt));
                        tl.add(new TextComponentTranslation("最大通量：%s", computationProvider.getMaxCWUt()));
                        if(requestCWUt>=1024) tl.add(new TextComponentTranslation("解析速率：%s", 32));
                        else if(requestCWUt>=896) tl.add(new TextComponentTranslation("解析速率：%s", 16));
                        else if(requestCWUt>=640) tl.add(new TextComponentTranslation("解析速率：%s", 8));
                        else if(requestCWUt>=512) tl.add(new TextComponentTranslation("解析速率：%s", 6));
                        else if(requestCWUt>=384) tl.add(new TextComponentTranslation("解析速率：%s", 4));
                        else if(requestCWUt>=256) tl.add(new TextComponentTranslation("解析速率：%s", 2));
                        else if(requestCWUt>=128) tl.add(new TextComponentTranslation("解析速率：%s", 1));
                    }
                });
    }
    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 380, 240);
        //1号 X=0
        builder.image(4, 4, 96, 160, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 8, this::addDisplayText1, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        //能源监控
        builder.image(4, 164, 96, 76, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 168, this::addTotal1, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        //2号 X=100
        builder.image(104, 4, 96, 160, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(108, 8, this::addDisplayText2, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));

        //算力监控
        builder.image(104, 164, 96, 76, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(108, 168, this::addTotal2, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));

        //3号 X=200
        builder.image(200, 4, 176, 131, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(208, 8, this::addDisplayText, 16777215)).setMaxWidthLimit(100).setClickHandler(this::handleDisplayClick));
        //按钮
        builder.widget(new ClickButtonWidget(200, 135, 60, 20, "删除待处理", this::DeletToDeal));
        builder.widget(new ClickButtonWidget(260, 135, 60, 20, "删除已处理", this::DeletToResolve));

        builder.widget(new TextFieldWidget2(220, 80, 40, 20, this::getBlankName, this::addToWhiteList).setMaxLength(25).setAllowedChars(TextFieldWidget2.LETTERS));
        builder.widget(new TextFieldWidget2(220, 100, 40, 20, this::getBlankRemoveName, this::removeFromWhitelist).setMaxLength(25).setAllowedChars(TextFieldWidget2.LETTERS));

        //clear or retrieve list //TODO change the texture of this
        builder.widget(new ClickButtonWidget(325, 135, 20, 20, "", data -> printWhitelistOrClear(data, entityPlayer)).setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID).setTooltipText("gtqtspace.gui.mining_module.print_whitelist_or_clear"));

        //whitelist or blacklist
        builder.widget(new ImageCycleButtonWidget(350, 135, 20, 20, GTQTSTextures.BUTTON_WHITE_BLACK_LIST, this::getWhitelistMode, this::setWhitelistMode).setTooltipHoverString("gtqtspace.gui.mining_module.change_whitelist_mode"));


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 200, 160);
        return builder;
    }
    private void printWhitelistOrClear(Widget.ClickData data, EntityPlayer player) {

        if(this.whitelist.isEmpty()) {
            player.sendStatusMessage(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gtqtspace.gui.mining_module." + (this.isWhitelist ? "whitelist_empty" : "blacklist_empty")), false);
            return;
        }

        if(data.isShiftClick) {
            this.whitelist.clear();
            player.sendStatusMessage(TextComponentUtil.translationWithColor(TextFormatting.WHITE, "gtqtspace.gui.mining_module." + (this.isWhitelist ? "whitelist_cleared" : "blacklist_cleared")), false);
        }
        else {

            StringBuilder sb = new StringBuilder(I18n.format("gtqtspace.gui.mining_module." + (this.isWhitelist ? "whitelist" : "blacklist")) + "\n");

            for(int i = 0; i < this.whitelist.size(); i++) {
                sb.append(this.whitelist.get(i));
                if(i != this.whitelist.size() - 1) {
                    sb.append("\n");
                }
            }

            player.sendStatusMessage(TextComponentUtil.stringWithColor(TextFormatting.WHITE, sb.toString()), false);
        }
    }

    private void addToWhiteList(String name) {
        if (!this.whitelist.contains(name) && !Objects.equals(name, I18n.format("gtqtspace.gui.mining_module.blank_name"))) {
            this.whitelist.add(name);
        }
    }

    private void removeFromWhitelist(String name) {
        this.whitelist.remove(name);
    }

    private String getBlankName() {
        return I18n.format("gtqtspace.gui.mining_module.blank_name");
    }

    private String getBlankRemoveName() {
        return I18n.format("gtqtspace.gui.mining_module.blank_name_remove");
    }

    private void setWhitelistMode(boolean bool) {
        this.isWhitelist = bool;
    }

    private boolean getWhitelistMode() {
        return this.isWhitelist;
    }

    //按钮
    private void DeletToDeal(Widget.ClickData clickData) {
        time=0;
        totalTime=0;
        IDtoDeal=0;
    }
    private void DeletToResolve(Widget.ClickData clickData) {
        IDResolve=0;
    }

    @Override
    protected void updateFormedValid() {
        if(!isStructureFormed())return;
        if(MachineCheck(ControlPos))
        {
            if(IDResolve!=0) {
                AsteroidController control = getMteFromPos(ControlPos);
                if(containsZero(control.WaitToDrill))
                {
                    replaceFirstZero(control.WaitToDrill,IDResolve);
                    IDResolve=0;
                }
            }
            if(IDResolve==0&&IDtoDeal!=0)
            {
                totalTime=Asteroid.TimeToSolve(IDtoDeal);
                requestCWUt=computationProvider.requestCWUt(1024, false);

                if(requestCWUt>=1024) time+=32;
                else if(requestCWUt>=896) time+=16;
                else if(requestCWUt>=640) time+=8;
                else if(requestCWUt>=512) time+=6;
                else if(requestCWUt>=384) time+=4;
                else if(requestCWUt>=256) time+=2;
                else if(requestCWUt>=128) time++;

                if(time==totalTime&&totalTime!=0)
                {
                    totalTime=0;
                    IDResolve=IDtoDeal;
                    time=0;
                    IDtoDeal=0;
                }
            }
        }
    }
    public int processID(int ID) {

        boolean foundInWhitelist = false;
        boolean foundInBlacklist = false;

        for (int n = 0; n <= 5; n++) {
            String oreName = Asteroid.getOreNameByID(ID, n);

            if (isWhitelist) {
                if (whitelist.contains(oreName)) {
                    foundInWhitelist = true;
                    break;
                }
            } else {
                if (!whitelist.contains(oreName)) {
                    foundInBlacklist = true;
                    break;
                }
            }
        }

        if ((isWhitelist && !foundInWhitelist) || (!isWhitelist && foundInBlacklist)) {
            IDtoDeal = 0;
        }

        return IDtoDeal;
    }
    public AsteroidController getMteFromPos(BlockPos pos)
    {
        TileEntity te = this.getWorld().getTileEntity(pos);
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            if (mte instanceof AsteroidController) {
                return ((AsteroidController) mte);
            }
        }
        return null;
    }
    public boolean MachineCheck(BlockPos pos) {
        MetaTileEntity mt = GTUtility.getMetaTileEntity(this.getWorld(), pos);
        if (mt instanceof MetaTileEntity) {
            TileEntity te = this.getWorld().getTileEntity(pos);
            if (te instanceof IGregTechTileEntity igtte) {
                MetaTileEntity mte = igtte.getMetaTileEntity();
                if (mte instanceof AsteroidController) {
                    return ((AsteroidController) mte).isStructureFormed();
                }
            }
        }
        ControlPos=new BlockPos(0,0,0);
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }
    }
    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new AsteroidSolve(metaTileEntityId);
    }
    public AsteroidSolve(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC","CCC","CCC")
                .aisle("CCC","CCC","CCC")
                .aisle("CMC","CSC","CXC")
                .where('M', abilities(MultiblockAbility.MAINTENANCE_HATCH))
                .where('S', selfPredicate())
                .where('X', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('C', states(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_CASING))
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
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}

package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtspace.api.multiblock.SatelliteGenerators;
import keqing.gtqtspace.api.multiblock.SatelliteSeniorUpdates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.util.RelativeDirection;
import keqing.gtqtspace.common.items.GTQTSMetaItems;

public class MetaTileEntitySatelliteAssembler extends MetaTileEntityBaseWithControl implements IProgressBarMultiblock {
	int process, maxProcess;
	int time;
	int solarTierTMP;
	SatelliteSeniorUpdates seniorUpdateTMP;
	SatelliteGenerators generatorTMP;

	public MetaTileEntitySatelliteAssembler(ResourceLocation metaTileEntityId) {
		super(metaTileEntityId);
		this.seniorUpdateTMP = SatelliteSeniorUpdates.EMPTY;
		this.generatorTMP = SatelliteGenerators.EMPTY;
	}

	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new MetaTileEntitySatelliteAssembler(this.metaTileEntityId);
	}

	//怎么写
	//塞入卫星
	//塞入部件
	//检查
	//执行安装 如果有就强制替换
	@Override
	protected void updateFormedValid() {

		//首先自检

		if (checkSatellite(true)) {
			if (time < 1000) time++;
			else {
				GTTransferUtils.insertItem(this.outputInventory, setSatellite(checkSolar(false), checkSenior(false), checkGenerator(false)), false);
				checkSatellite(false);
				solarTierTMP = 0;
				seniorUpdateTMP = SatelliteSeniorUpdates.EMPTY;
				generatorTMP = SatelliteGenerators.EMPTY;
				time = 0;
			}
		}
	}

	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		super.addDisplayText(textList);
		textList.add(new TextComponentTranslation("========刻晴的妙妙工具======="));
		textList.add(new TextComponentTranslation("%s: %s%s", I18n.format("tile.gui.contained_part"), Integer.toString(solarTierTMP), I18n.format("tile.gui.solar_tier")));
		textList.add(new TextComponentTranslation("%s: %s", I18n.format("tile.gui.contained_part"), seniorUpdateTMP.getName()));
		textList.add(new TextComponentTranslation("%s: %s", I18n.format("tile.gui.contained_part"), generatorTMP.getName()));
		textList.add(new TextComponentTranslation("=========================="));
		textList.add(new TextComponentTranslation("%s: %s%s", I18n.format("tile.gui.will_update"), Integer.toString(checkSolar(true)), I18n.format("tile.gui.solar_tier")));
		textList.add(new TextComponentTranslation("%s: %s", I18n.format("tile.gui.will_update"), SatelliteSeniorUpdates.getSeniorUpdateFromID(checkSenior(true)).getName()));
		textList.add(new TextComponentTranslation("%s: %s", I18n.format("tile.gui.will_update"), SatelliteGenerators.getGeneratorFromID(checkGenerator(true)).getName()));
		textList.add(new TextComponentTranslation("=========================="));
	}

	public boolean checkSatellite(boolean sim) {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.BASIC_SATELLITE.getMetaValue()) {
				NBTTagCompound compound = item.getTagCompound();
				if (compound != null && compound.hasKey("solarTier") && compound.hasKey("seniorTier") && compound.hasKey("generatorTier")) {
					solarTierTMP = compound.getInteger("solarTier");
					seniorUpdateTMP = SatelliteSeniorUpdates.getSeniorUpdateFromID(compound.getInteger("seniorTier"));
					generatorTMP = SatelliteGenerators.getGeneratorFromID(compound.getInteger("generatorTier"));
				}
				this.getInputInventory().extractItem(i, 1, sim);
				return true;
			}
		}
		return false;
	}

	public int checkSolar(boolean sim) {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SOLAR_PLATE_MKI.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 1;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SOLAR_PLATE_MKII.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 2;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SOLAR_PLATE_MKIII.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 3;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SOLAR_PLATE_MKIV.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 4;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SOLAR_PLATE_MKV.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 5;
			}
		}
		return 0;
	}

	public int checkSenior(boolean sim) {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SATELLITEPRIMARYFUNCTION1.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 1;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SATELLITEPRIMARYFUNCTION2.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 2;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SATELLITEPRIMARYFUNCTION3.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 3;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SATELLITEPRIMARYFUNCTION4.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 4;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.SATELLITEPRIMARYFUNCTION5.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 5;
			}
		}
		return 0;
	}

	public int checkGenerator(boolean sim) {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.COMBUSTIONENGINE.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 1;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.ADVCOMBUSTIONENGINE.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return 2;
			}
		}
		return 0;
	}

	public ItemStack setSatellite(int solar, int senior, int generator) {
		ItemStack Satellite = new ItemStack(GTQTSMetaItems.BASIC_SATELLITE.getMetaItem(), 1, 100);
		NBTTagCompound nodeTagCompound = new NBTTagCompound();
		nodeTagCompound.setInteger("solarTier", solar == 0 ? solarTierTMP : solar);
		nodeTagCompound.setInteger("seniorTier", senior);
		nodeTagCompound.setInteger("generatorTier", generator);
		Satellite.setTagCompound(nodeTagCompound);
		return Satellite;
	}

	@Override
	protected BlockPattern createStructurePattern() {
		return FactoryBlockPattern.start(RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
				.aisle("XXX", "XXX", "XXX")
				.aisle("XXX", "CAX", "CCX").setRepeatable(3)
				.aisle("XXX", "XXX", "XXX")
				.aisle("XXX", "XAX", "#XX")
				.aisle("XXX", "STX", "#XX")
				.aisle("XXX", "XAX", "#XX")
				.aisle("XXX", "XXX", "XXX")
				.where('S', selfPredicate())
				.where('X', states(getCasingState()).setMinGlobalLimited(40)
						.or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
						.or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
						.or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
						.or(abilities(MultiblockAbility.MUFFLER_HATCH).setExactLimit(1))
						.or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
				.where('C', states(getCasingState2()))
				.where('T', GCYMRecipeMapMultiblockController.tieredCasing().or(air()))
				.where('A', air())
				.where('#', any())
				.build();
	}

	private static IBlockState getCasingState() {
		return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ASSEMBLING_CASING);
	}

	private static IBlockState getCasingState2() {
		return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
	}

	@Override
	public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
		return GCYMTextures.ASSEMBLING_CASING;
	}

	@Override
	protected OrientedOverlayRenderer getFrontOverlay() {
		return GCYMTextures.LARGE_ASSEMBLER_OVERLAY;
	}


	@Override
	public List<ITextComponent> getDataInfo() {
		return Collections.emptyList();
	}

	@Override
	public int getNumProgressBars() {
		return 1;
	}


	@Override
	public double getFillPercentage(int index) {
		return (double) time / 1000;
	}

	@Override
	public TextureArea getProgressBarTexture(int index) {
		return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
	}

	@Override
	public void addBarHoverText(List<ITextComponent> hoverList, int index) {
		ITextComponent cwutInfo = TextComponentUtil.stringWithColor(
				TextFormatting.AQUA,
				time + " / " + 1000);
		hoverList.add(TextComponentUtil.translationWithColor(
				TextFormatting.GRAY,
				"安装进度：%s",
				cwutInfo));
	}

	@Override
	public int getProgress() {
		return process;
	}

	@Override
	public int getMaxProgress() {
		return maxProcess;
	}
}

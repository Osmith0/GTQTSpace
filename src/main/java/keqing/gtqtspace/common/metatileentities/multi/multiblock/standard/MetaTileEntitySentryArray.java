package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtspace.api.multiblock.Satellite;
import keqing.gtqtspace.api.multiblock.SatelliteGenerators;
import keqing.gtqtspace.api.multiblock.SatelliteSeniorUpdates;
import keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gregtech.api.GTValues;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtspace.common.items.GTQTSMetaItems;

public class MetaTileEntitySentryArray extends MetaTileEntityBaseWithControl {
	private final Satellite[] satellite = new Satellite[26];
	private int maxNumber;
	int process, maxProcess;
	int circuit;
	int Upgrade;
	int solarTierTMP;
	SatelliteSeniorUpdates seniorUpdateTMP;
	SatelliteGenerators generatorTMP;

	@Override
	protected void updateFormedValid() {
		if (checkSatellite(false) && satellite[circuit] == null) {
			satellite[circuit] = new Satellite(circuit, solarTierTMP, seniorUpdateTMP, generatorTMP);
			solarTierTMP = 0;
			seniorUpdateTMP = SatelliteSeniorUpdates.EMPTY;
			generatorTMP = SatelliteGenerators.EMPTY;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		data = super.writeToNBT(data);
		NBTTagList list = new NBTTagList();
		for (Satellite satellite1 : satellite) {
			list.appendTag(satellite1.serialize());
		}
		data.setTag("satellites", list);
		return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		if (data.hasKey("satellites")) {
			NBTTagList satellites = data.getTagList("satellites", 0);
			final int[] it = {0};
			satellites.iterator().forEachRemaining(s -> {
				this.satellite[it[0]] = Satellite.deserialize((NBTTagCompound) s);
				++it[0];
			});
		}
	}

	public boolean checkSatellite(boolean sim) {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getDisplayName().equals("基础卫星")) {
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

	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		MultiblockDisplayText.builder(textList, isStructureFormed())
				.setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
				.addCustom(tl -> {
					if (isStructureFormed()) {
						tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "=========中央控制塔========="));
						for (int i = -2; i <= 2; i++) {

							if (i == 0) {
								if (this.satellite[circuit] == null)
									tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s|状态：%s ", circuit + 1, "未使用"));
								else {
									tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s|状态：%s 电量：%s", circuit + 1, this.satellite[circuit].isUsing(), this.satellite[circuit].getDuration()));
									tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "设备参数：%s %s %s", this.satellite[circuit].getSolarTier(), this.satellite[circuit].getSeniorTier(), this.satellite[circuit].getGeneratorTier()));
								}
							} else if (circuit + i >= 0 && circuit + i < 25) {
								if (this.satellite[circuit + i] == null)
									tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s|状态：%s ", circuit + 1 + i, "未使用"));
								else {
									tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s|状态：%s 电量：%s", circuit + 1 + i, this.satellite[circuit + i].isUsing(), this.satellite[circuit + i].getDuration()));
									tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "设备参数：%s %s %s", this.satellite[circuit + i].getSolarTier(), this.satellite[circuit + i].getSeniorTier(), this.satellite[circuit + i].getGeneratorTier()));
								}
							}
						}
						tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "======================="));
					}
				});
	}

	public MetaTileEntitySentryArray(ResourceLocation metaTileEntityId) {
		super(metaTileEntityId);
	}

	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new MetaTileEntitySentryArray(this.metaTileEntityId);
	}

	@Override
	protected BlockPattern createStructurePattern() {
		return FactoryBlockPattern.start()
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            NNN            ", "            NMN            ", "            NNN            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            NNN            ", "            NMN            ", "            NNN            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            NNN            ", "            NMN            ", "            NNN            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            CCC            ", "       DDDDDCACDDDDD       ", "            CCC            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "       D    DAD    D       ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "            DDD            ", "          DDD DDD          ", "       D  DDDADDD  D       ", "          DDD DDD          ", "            DDD            ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "             B             ", "             D             ", "           DD DD           ", "           DD DD           ", "         DD     DD         ", "       D DD  A  DD D       ", "         DD     DD         ", "           DD DD           ", "           DDDDD           ", "             B             ", "             B             ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "                           ", "                           ", "           DDDDD           ", "          D     D          ", "          D     D          ", "         D       D         ", "       D D   A   D D       ", "         D       D         ", "          D     D          ", "          DDDDDDD          ", "                           ", "                           ", "             B             ", "                           ", "                           ", "                           ")
				.aisle("                           ", "             B             ", "             B             ", "             B             ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "                           ", "            BBB            ", "           DDDDD           ", "         DD     DD         ", "         DD     DD         ", " P P P CDD       DDC L L L ", " P P P CDD   A   DDC L L L ", " P P P CDD       DDC L L L ", "         DD     DD         ", "         DDDDDDDDD         ", "            BBB            ", "                           ", "             B             ", "                           ", "             B             ", "                           ")
				.aisle("             A             ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "           BBABB           ", "          B  A  B          ", "          B BAB B          ", "          DDDADDD          ", "         D   A   D         ", "         D   A   D         ", " P P P CD    A    DC L L L ", "OOOOOOOAAAAAAAAAAAAAKKKKKKK", " P P P CD    A    DC L L L ", "         D   A   D         ", "         DDDDADDDD         ", "          B BAB B          ", "          B  A  B          ", "           BBABB           ", "             A             ", "            BAB            ", "             A             ")
				.aisle("                           ", "             B             ", "             B             ", "             B             ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "                           ", "            BBB            ", "           DDDDD           ", "         DD     DD         ", "         DD     DD         ", " P P P CDD       DDC L L L ", " P P P CDD   A   DDC L L L ", " P P P CDD       DDC L L L ", "         DD     DD         ", "         DDDDDDDDD         ", "            BBB            ", "                           ", "             B             ", "                           ", "             B             ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "                           ", "                           ", "           DDDDD           ", "          D     D          ", "          D     D          ", "         D       D         ", "       D D   A   D D       ", "         D       D         ", "          D     D          ", "          DDDDDDD          ", "                           ", "                           ", "             B             ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "             B             ", "             D             ", "           DD DD           ", "           DD DD           ", "         DD     DD         ", "       D DD  A  DD D       ", "         DD     DD         ", "           DD DD           ", "           DDDDD           ", "             B             ", "             B             ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "            DSD            ", "          DDD DDD          ", "       D  DDDADDD  D       ", "          DDD DDD          ", "            DDD            ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "       D    DAD    D       ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            CCC            ", "       DDDDDCACDDDDD       ", "            CCC            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            JJJ            ", "            JIJ            ", "            JJJ            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            JJJ            ", "            JIJ            ", "            JJJ            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            JJJ            ", "            JIJ            ", "            JJJ            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
				.where('S', selfPredicate())
				.where('A', states(getCasingState()).setMinGlobalLimited(40))
				.where('B', states(getCasingState2()))
				.where('C', states(getCasingState3()))
				.where('D', states(getCasingState4())
						.or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
						.or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
						.or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
						.or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
				//  天线1
				.where('I', GTQTTraceabilityPredicate.optionalStates("UpgradeT1", getCasingState()))
				.where('J', GTQTTraceabilityPredicate.optionalStates("UpgradeT1", getCasingState2()))
				//  天线2
				.where('K', GTQTTraceabilityPredicate.optionalStates("UpgradeT2", getCasingState()))
				.where('L', GTQTTraceabilityPredicate.optionalStates("UpgradeT2", getCasingState2()))
				//  天线3
				.where('M', GTQTTraceabilityPredicate.optionalStates("UpgradeT3", getCasingState()))
				.where('N', GTQTTraceabilityPredicate.optionalStates("UpgradeT3", getCasingState2()))
				//  天线4
				.where('O', GTQTTraceabilityPredicate.optionalStates("UpgradeT4", getCasingState()))
				.where('P', GTQTTraceabilityPredicate.optionalStates("UpgradeT4", getCasingState2()))
				.where(' ', any())
				.build();
	}

	@Override
	protected void formStructure(PatternMatchContext context) {
		super.formStructure(context);
		if (context.get("UpgradeT1") != null) {
			this.Upgrade += 1;
		}
		if (context.get("UpgradeT2") != null) {
			this.Upgrade += 1;
		}
		if (context.get("UpgradeT3") != null) {
			this.Upgrade += 1;
		}
		if (context.get("UpgradeT4") != null) {
			this.Upgrade += 1;
		}
		maxNumber = 5 + Upgrade * 5;
	}

	public boolean hasMufflerMechanics() {
		return false;
	}

	@Override
	public List<MultiblockShapeInfo> getMatchingShapes() {
		ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
		MultiblockShapeInfo.Builder builder;
		if (Blocks.AIR != null) {
			builder = MultiblockShapeInfo.builder()
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            NNN            ", "            NMN            ", "            NNN            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            NNN            ", "            NMN            ", "            NNN            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            NNN            ", "            NMN            ", "            NNN            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             M             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            CCC            ", "       DDDDDCACDDDDD       ", "            CCC            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "       D    DAD    D       ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "            DDD            ", "          DDD DDD          ", "       D  DDDADDD  D       ", "          DDD DDD          ", "            DDD            ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "             B             ", "             D             ", "           DD DD           ", "           DD DD           ", "         DD     DD         ", "       D DD  A  DD D       ", "         DD     DD         ", "           DD DD           ", "           DDDDD           ", "             B             ", "             B             ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "                           ", "                           ", "           DDDDD           ", "          D     D          ", "          D     D          ", "         D       D         ", "       D D   A   D D       ", "         D       D         ", "          D     D          ", "          DDDDDDD          ", "                           ", "                           ", "             B             ", "                           ", "                           ", "                           ")
					.aisle("                           ", "             B             ", "             B             ", "             B             ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "                           ", "            BBB            ", "           DDDDD           ", "         DD     DD         ", "         DD     DD         ", " P P P CDD       DDC L L L ", " P P P CDD   A   DDC L L L ", " P P P CDD       DDC L L L ", "         DD     DD         ", "         DDDDDDDDD         ", "            BBB            ", "                           ", "             B             ", "                           ", "             B             ", "                           ")
					.aisle("             A             ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "            BAB            ", "           BBABB           ", "          B  A  B          ", "          B BAB B          ", "          DDDADDD          ", "         D   A   D         ", "         D   A   D         ", " P P P CD    A    DC L L L ", "OOOOOOOAAAAAAAAAAAAAKKKKKKK", " P P P CD    A    DC L L L ", "         D   A   D         ", "         DDDDADDDD         ", "          B BAB B          ", "          B  A  B          ", "           BBABB           ", "             A             ", "            BAB            ", "             A             ")
					.aisle("                           ", "             B             ", "             B             ", "             B             ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "             B             ", "            BBB            ", "                           ", "            BBB            ", "           DDDDD           ", "         DD     DD         ", "         DD     DD         ", " P P P CDD       DDC L L L ", " P P P CDD   A   DDC L L L ", " P P P CDD       DDC L L L ", "         DD     DD         ", "         DDDDDDDDD         ", "            BBB            ", "                           ", "             B             ", "                           ", "             B             ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "                           ", "                           ", "           DDDDD           ", "          D     D          ", "          D     D          ", "         D       D         ", "       D D   A   D D       ", "         D       D         ", "          D     D          ", "          DDDDDDD          ", "                           ", "                           ", "             B             ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             B             ", "             B             ", "             D             ", "           DD DD           ", "           DD DD           ", "         DD     DD         ", "       D DD  A  DD D       ", "         DD     DD         ", "           DD DD           ", "           DDDDD           ", "             B             ", "             B             ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            ijm            ", "            eSD            ", "          DDD DDD          ", "       D  DDDADDD  D       ", "          DDD DDD          ", "            DDD            ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            DDD            ", "       D    DAD    D       ", "            DDD            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            CCC            ", "       DDDDDCACDDDDD       ", "            CCC            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            JJJ            ", "            JIJ            ", "            JJJ            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            JJJ            ", "            JIJ            ", "            JJJ            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "            JJJ            ", "            JIJ            ", "            JJJ            ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.aisle("                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "             I             ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ", "                           ")
					.where('S', GTQTSMetaTileEntities.SENTRY_ARRAY, EnumFacing.SOUTH)
					.where('A', getCasingState())
					.where('B', getCasingState2())
					.where('C', getCasingState3())
					.where('D', getCasingState4())
					.where(' ', Blocks.AIR.getDefaultState())
					.where('i', MetaTileEntities.ITEM_IMPORT_BUS_ME, EnumFacing.SOUTH)
					.where('j', MetaTileEntities.ITEM_EXPORT_BUS_ME, EnumFacing.SOUTH)
					.where('m', MetaTileEntities.AUTO_MAINTENANCE_HATCH, EnumFacing.SOUTH)
					.where('e', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.EV], EnumFacing.SOUTH);
			shapeInfo.add(builder.build());
			shapeInfo.add(builder
					.where('I', getCasingState())
					.where('J', getCasingState2())
					.build());
			shapeInfo.add(builder
					.where('K', getCasingState())
					.where('L', getCasingState2())
					.build());
			shapeInfo.add(builder
					.where('M', getCasingState())
					.where('N', getCasingState2())
					.build());
			shapeInfo.add(builder
					.where('O', getCasingState())
					.where('P', getCasingState2())
					.build());
		}
		return shapeInfo;
	}

	private static IBlockState getCasingState() {
		return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ASSEMBLING_CASING);
	}

	private static IBlockState getCasingState2() {
		return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
	}

	private static IBlockState getCasingState3() {
		return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.ATOMIC_CASING);
	}

	private static IBlockState getCasingState4() {
		return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(BlockLargeMultiblockCasing.CasingType.CUTTER_CASING);
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
	@Nonnull
	protected Widget getFlexButton(int x, int y, int width, int height) {
		WidgetGroup group = new WidgetGroup(x, y, width, height);
		group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
				.setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
				.setTooltipText("序列向后"));
		group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
				.setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
				.setTooltipText("序列向前"));
		group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::back)
				.setButtonTexture(GuiTextures.BUTTON_CLEAR_GRID)
				.setTooltipText("退档（返还卫星到输出总线）"));
		group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::stop)
				.setButtonTexture(GuiTextures.BUTTON_LOCK)
				.setTooltipText("卫星停机"));
		return group;
	}

	private void incrementThreshold(Widget.ClickData clickData) {
		this.circuit = MathHelper.clamp(circuit + 1, 0, maxNumber);
	}

	private void decrementThreshold(Widget.ClickData clickData) {
		this.circuit = MathHelper.clamp(circuit - 1, 0, maxNumber);
	}

	private void back(Widget.ClickData clickData) {
		GTTransferUtils.insertItem(this.outputInventory, setSatellite(), false);
		satellite[circuit] = null;
	}

	private void stop(Widget.ClickData clickData) {
		satellite[circuit].setUsing(false);
	}

	public ItemStack setSatellite() {
		ItemStack Satellite = new ItemStack(GTQTSMetaItems.BASIC_SATELLITE.getMetaItem(), 1, 100);
		NBTTagCompound nodeTagCompound = new NBTTagCompound();
		nodeTagCompound.setInteger("SolarTier", this.satellite[circuit].getSolarTier());
		nodeTagCompound.setInteger("SeniorTier", this.satellite[circuit].getSeniorTier().getID());
		nodeTagCompound.setInteger("GeneratorTier", this.satellite[circuit].getGeneratorTier().getID());
		Satellite.setTagCompound(nodeTagCompound);
		return Satellite;
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

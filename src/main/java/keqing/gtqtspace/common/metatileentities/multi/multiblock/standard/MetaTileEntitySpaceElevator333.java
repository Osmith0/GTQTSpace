package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtspace.api.GTQTSValue;
import keqing.gtqtspace.api.blocks.impl.WrappedIntTired;
import keqing.gtqtspace.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps;
import keqing.gtqtspace.api.recipes.properties.SEProperty;
import keqing.gtqtspace.api.utils.GTQTSUtil;
import keqing.gtqtspace.client.textures.GTQTSTextures;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSpaceElevator;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntitySpaceElevator333 extends MultiMapMultiblockController implements IOpticalComputationReceiver {
	private IOpticalComputationProvider computationProvider;

	public IOpticalComputationProvider getComputationProvider() {
		return this.computationProvider;
	}

	private int motortier;
	int coe = 0;
	private int Atier;// 矿机
	private int Btier;// 超频
	private int Ctier;// 耗能
	private int Dtier;// 并行

	int dim;

	public MetaTileEntitySpaceElevator333(ResourceLocation metaTileEntityId) {
		super(metaTileEntityId, new RecipeMap[]{
				GTQTScoreRecipeMaps.SPACE_ELEVATOR_DRILLING_MODULE,
				GTQTScoreRecipeMaps.SPACE_ELEVATOR_MINING_MODULE
				/*
				GTLiteRecipeMaps.SPACE_ELEVATOR_MINING_MODULE,
				GTLiteRecipeMaps.SPACE_ELEVATOR_ASSEMBLING_MODULE
*/
		});
		this.recipeMapWorkable = new SpaceElevatorRecipeLogic(this);
	}

	@Override
	public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
		if (recipe.getProperty(SEProperty.getInstance(), 0) <= motortier) {
			return super.checkRecipe(recipe, consumeIfSuccess);
		}
		return false;
	}

	private int getCoe() {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_LV.getMetaValue()) {
				return 1;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_MV.getMetaValue()) {
				return 2;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_HV.getMetaValue()) {
				return 3;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_EV.getMetaValue()) {
				return 4;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_IV.getMetaValue()) {
				return 5;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_LuV.getMetaValue()) {
				return 6;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_ZPM.getMetaValue()) {
				return 7;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_UV.getMetaValue()) {
				return 8;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_UHV.getMetaValue()) {
				return 9;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_UEV.getMetaValue()) {
				return 10;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_UIV.getMetaValue()) {
				return 11;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_UXV.getMetaValue()) {
				return 12;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_OpV.getMetaValue()) {
				return 13;
			}
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.MINING_DRONE_MAX.getMetaValue()) {
				return 14;
			}
		}
		return 0;
	}

	@Override
	@Nonnull
	protected Widget getFlexButton(int x, int y, int width, int height) {
		WidgetGroup group = new WidgetGroup(x, y, width, height);
		group.addWidget(new ClickButtonWidget(0, 0, 9, 18, "", this::decrementThreshold)
				.setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
				.setTooltipText("gtqtcore.multiblock.se.threshold_decrement"));
		group.addWidget(new ClickButtonWidget(9, 0, 9, 18, "", this::incrementThreshold)
				.setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
				.setTooltipText("gtqtcore.multiblock.se.threshold_increment"));
		return group;
	}

	private void incrementThreshold(Widget.ClickData clickData) {
		this.dim = MathHelper.clamp(dim + 1, 0, motortier * 2);
	}

	private void decrementThreshold(Widget.ClickData clickData) {
		this.dim = MathHelper.clamp(dim - 1, 0, motortier * 2);
	}

	@Override
	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
		return new MetaTileEntitySpaceElevator333(metaTileEntityId);
	}

	@Override
	public void receiveCustomData(int dataId, PacketBuffer buf) {
		super.receiveCustomData(dataId, buf);
		if (dataId == GTQTSValue.UPDATE_TIER) {
			this.dim = buf.readInt();
		}
		if (dataId == GTQTSValue.REQUIRE_DATA_UPDATE) {
			this.writeCustomData(GTQTSValue.UPDATE_TIER, buf1 -> buf1.writeInt(this.dim));
		}
	}

	@Override
	protected void formStructure(PatternMatchContext context) {
		super.formStructure(context);
		List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
		if (providers != null && !providers.isEmpty()) {
			this.computationProvider = providers.get(0);
		}
		Object motortier = context.get("SETiredStats");
		Object Atier = context.get("SEATiredStats");
		Object Btier = context.get("SEBTiredStats");
		Object Ctier = context.get("SECTiredStats");
		Object Dtier = context.get("SEDTiredStats");
		this.motortier = GTQTSUtil.getOrDefault(() -> motortier instanceof WrappedIntTired,
				() -> ((WrappedIntTired) motortier).getIntTier(),
				0);

		this.Atier = GTQTSUtil.getOrDefault(() -> Atier instanceof WrappedIntTired,
				() -> ((WrappedIntTired) Atier).getIntTier(),
				0);
		this.Btier = GTQTSUtil.getOrDefault(() -> Btier instanceof WrappedIntTired,
				() -> ((WrappedIntTired) Btier).getIntTier(),
				0);
		this.Ctier = GTQTSUtil.getOrDefault(() -> Ctier instanceof WrappedIntTired,
				() -> ((WrappedIntTired) Ctier).getIntTier(),
				0);
		this.Dtier = GTQTSUtil.getOrDefault(() -> Dtier instanceof WrappedIntTired,
				() -> ((WrappedIntTired) Dtier).getIntTier(),
				0);

		this.writeCustomData(GTQTSValue.UPDATE_TIER, buf -> buf.writeInt(this.motortier));
	}

	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		super.addDisplayText(textList);
		textList.add(new TextComponentTranslation("gtqtspace.multiblock.se.motor", motortier, dim, getCoe()));
		textList.add(new TextComponentTranslation("gtqtspace.multiblock.se.abcdn"));
		textList.add(new TextComponentTranslation("gtqtspace.multiblock.se.abcd", Atier, Btier, Ctier, Dtier));

	}


	@Override
	public void invalidateStructure() {
		super.invalidateStructure();
	}


	@Nonnull
	@Override
	protected BlockPattern createStructurePattern() {
		return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.DOWN, RelativeDirection.FRONT)
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "               X2X2X               ", "               X2X2X               ", "               X2X2X               ", "               X2X2X               ", "   AAAAAAAAAAAAX2X2XAAAAAAAAAAAA   ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E     X2X2X     E         ", "         E     I2I2I     E         ", "         E     X2X2X     E         ", "   FF    E     X2X2X     E    FF   ", "   AAAAAAAAAAAAX2X2XAAAAAAAAAAAA   ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                2 2                ", "                2 2                ", "   D            2 2            D   ", "  FFF          F2F2F          FFF  ", "  AAAAAAAAAAAAA 2 2 AAAAAAAAAAAAA  ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                2 2                ", "                2 2                ", "   D            2 2            D   ", "  FFF          F2F2F          FFF  ", "  AAAAAAAAAAAAA 2 2 AAAAAAAAAAAAA  ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
				.aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                X~X                ", "  D             XXX             D  ", " FFF        FFFFFFFFFFF        FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
				.aisle("               F D F               ", "                 D                 ", "                 D                 ", "                 D                 ", "                 D                 ", "                 D                 ", "              F  D  F              ", "                 D                 ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "           DD    D    DD           ", "           D     D     D           ", "           D     D     D           ", "           D   F D F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD XX     XDCDX     XX DD     ", "    DD  XI     XDCDX     IX  DD    ", " DDDD   XX     XDCDX     XX   DDDD ", "FFFD    XXFFFFFDDDDDFFFFFXX    DFFF", "AAAAAAAAXX  AAAXXXXXAAA  XXAAAAAAAA")
				.aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE 3333  XD   DX  1111 EEE    ", "   EEE  3333  XD   DX  1111  EEE   ", "  EE    3333  XD   DX  1111    EE  ", "FFF     3333FFFDDDDDFFF1111     FFF", "AAAAAAAA3333AAAXXXXXAAA1111AAAAAAAA")
				.aisle("              FD   DF              ", "              ED   DE              ", "              ED   DE              ", "              ED   DE              ", "              ED   DE              ", "              ED   DE              ", "             F D   D F             ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D   D               ", "               D - D               ", "              FD - DF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "        XX    XC - CX    XX        ", "        XI    XC - CX    IX        ", "        XX    XC - CX    XX        ", "        XXFFFFFDDDDDFFFFFXX        ", "AAAAAAAAXX  AAAXXXXXAAA  XXAAAAAAAA")
				.aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE 3333  XD   DX  1111 EEE    ", "   EEE  3333  XD   DX  1111  EEE   ", "  EE    3333  XD   DX  1111    EE  ", "FFF     3333FFFDDDDDFFF1111     FFF", "AAAAAAAA3333AAAXXXXXAAA1111AAAAAAAA")
				.aisle("               F D F               ", "                 D                 ", "                 D                 ", "                 D                 ", "                 D                 ", "                 D                 ", "              F  D  F              ", "                 D                 ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "            D    D    D            ", "           DD    D    DD           ", "           D     D     D           ", "           D     D     D           ", "           D   F D F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD XX     XDCDX     XX DD     ", "    DD  XI     XDCDX     IX  DD    ", " DDDD   XX     XDCDX     XX   DDDD ", "FFFD    XXFFFFFDDDDDFFFFFXX    DFFF", "AAAAAAAAXX  AAAXXXXXAAA  XXAAAAAAAA")
				.aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                XGX                ", "  D             XXX             D  ", " FFF        FFFFFFFFFFF        FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                4 4                ", "                4 4                ", "   D            4 4            D   ", "  FFF          F4F4F          FFF  ", "  AAAAAAAAAAAAA 4 4 AAAAAAAAAAAAA  ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                4 4                ", "                4 4                ", "   D            4 4            D   ", "  FFF          F4F4F          FFF  ", "  AAAAAAAAAAAAA 4 4 AAAAAAAAAAAAA  ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E     X4X4X     E         ", "         E     I4I4I     E         ", "         E     X4X4X     E         ", "   FF    E     X4X4X     E    FF   ", "   AAAAAAAAAAAAX4X4XAAAAAAAAAAAA   ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "               X4X4X               ", "               X4X4X               ", "               X4X4X               ", "               X4X4X               ", "   AAAAAAAAAAAAX4X4XAAAAAAAAAAAA   ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
				.aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
				.where('~', this.selfPredicate())
				.where('G', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
				.where('E', states(getCasingState()))
				.where('B', states(getSecondCasingState()))
				.where('X', states(getThirdCasingState()))
				.where('H', states(getFrameState()))
				.where('F', states(getFourthCasingState()))
				.where('C', TiredTraceabilityPredicate.CP_SE_CASING)
				.where('1', TiredTraceabilityPredicate.CP_SEA_CASING)
				.where('2', TiredTraceabilityPredicate.CP_SEB_CASING)
				.where('3', TiredTraceabilityPredicate.CP_SEC_CASING)
				.where('4', TiredTraceabilityPredicate.CP_SED_CASING)
				.where('A', states(getFifthCasingState()))
				.where('D', states(getSixthCasingState()))
				.where('I', states(getSixthCasingState())
						.or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
						.or(abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(1))
						.or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setPreviewCount(2))
						.or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(2))
						.or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setPreviewCount(2))
						.or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2).setPreviewCount(2)))
				.where('-', air())
				.where(' ', any())
				.build();
	}

	private static IBlockState getCasingState() {
		return GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.SUPPORT_STRUCTURE);
	}

	private static IBlockState getSecondCasingState() {
		return GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.CABLE_CASING);
	}

	private static IBlockState getThirdCasingState() {
		return GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.SUPPORT_STRUCTURE);
	}

	private static IBlockState getFourthCasingState() {
		return GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.INTERNAL_STRUCTURE);
	}

	private static IBlockState getFifthCasingState() {
		return GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.FLOOR);
	}

	private static IBlockState getSixthCasingState() {
		return GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.BASIC_CASING);
	}

	private static IBlockState getFrameState() {
		return MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		data.setInteger("Dim", dim);
		return super.writeToNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		dim = data.getInteger("Dim");
		super.readFromNBT(data);
	}

	@Override
	public void writeInitialSyncData(PacketBuffer buf) {
		super.writeInitialSyncData(buf);
		buf.writeVarInt(dim);
	}

	@Override
	public void receiveInitialSyncData(PacketBuffer buf) {
		super.receiveInitialSyncData(buf);
		dim = buf.readVarInt();
	}

	public int getThrottle() {
		return dim;
	}


	@SideOnly(Side.CLIENT)
	@Override
	public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
		return GTQTSTextures.SPACE_ELEVATOR_CASING;
	}

	@SideOnly(Side.CLIENT)
	@Nonnull
	@Override
	protected ICubeRenderer getFrontOverlay() {
		return Textures.FUSION_REACTOR_OVERLAY;
	}

	@Override
	public void addInformation(ItemStack stack,
	                           @Nullable World player,
	                           @Nonnull List<String> tooltip,
	                           boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add(I18n.format("gtqtspace.machine.space_elevator.tooltip.13"));
	}

	public boolean hasMufflerMechanics() {
		return false;
	}

	@Override
	public boolean canBeDistinct() {
		return true;
	}

	private class SpaceElevatorRecipeLogic extends ComputationRecipeLogic {

		public SpaceElevatorRecipeLogic(MetaTileEntitySpaceElevator333 tileEntity) {
			super(tileEntity, ComputationType.SPORADIC);
		}

		@Override
		public int getParallelLimit() {
			return Atier * Dtier * getCoe() == Atier * Dtier ? 1 : Atier * Dtier * (getCoe() + 1);
		}

	}

}

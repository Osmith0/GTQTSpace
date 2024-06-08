package keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.suvery;

import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps;
import keqing.gtqtspace.api.recipes.properties.StarProperty;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing1.TurbineCasingType.AL_TURBINE_CASING;

public class MetaTileEntitySatelliteSuvery extends RecipeMapMultiblockController implements IOpticalComputationReceiver {
	int tier = 2;
	private IOpticalComputationProvider computationProvider;

	public MetaTileEntitySatelliteSuvery(ResourceLocation metaTileEntityId) {
		super(metaTileEntityId, GTQTScoreRecipeMaps.STAR_SURVEY);
		this.recipeMapWorkable = new CosmicRayDetectorLogic(this);
	}

	@Override
	protected void formStructure(PatternMatchContext context) {
		super.formStructure(context);
		List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
		if (providers != null && !providers.isEmpty()) {
			this.computationProvider = providers.get(0);
		}
	}
	//有卫星就行
	@Override
	public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
		if (recipe.getProperty(StarProperty.getInstance(), 0) <= tier) {
			return super.checkRecipe(recipe, consumeIfSuccess)&&checkSatellite(false);
		}
		return false;
	}
	public boolean checkSatellite(boolean sim) {
		var slots = this.getInputInventory().getSlots();
		for (int i = 0; i < slots; i++) {
			ItemStack item = this.getInputInventory().getStackInSlot(i);
			if (item.getItem() == GTQTSMetaItems.GTQTS_META_ITEM && item.getMetadata() == GTQTSMetaItems.BASIC_SATELLITE.getMetaValue()) {
				this.getInputInventory().extractItem(i, 1, sim);
				return true;
			}
		}
		return false;
	}
	@Override
	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
		return new MetaTileEntitySatelliteSuvery(metaTileEntityId);
	}

	protected BlockPattern createStructurePattern() {
		return FactoryBlockPattern.start(RIGHT, FRONT, UP)
				.aisle(" CCSHCF", "PCP PCP", "PCP PCP", "PCP PCP"," CFCFCF")
				.aisle(" CFCFCF", "PCP PCP", "PCP PCP", "PCP PCP"," CFCFCF")
				.aisle(" CFCFCF", "PCP PCP", "PCP PCP", "PCP PCP"," CFCFCF")
				.aisle(" CFCFCF", "PPP PPP", "PPP PPP", "PPP PPP"," CFCFCF")
				.aisle(" CFCFCF", "  P   P", "  P   P", "  P   P"," CFCFCF")
				.aisle(" CCCCCF", "  P   P", "  P   P", "  P   P"," CFCFCF")
				.aisle("  FFFFF", "  P   P", "  P   P", "  P   P","  FFFFF")
				.aisle("  FF FF", "  P   P", "  P   P", "  P   P","  FF FF")
				.aisle("  FF FF", "  P   P", "  P   P", "  P   P","  FF FF")
				.aisle("  FF FF", "  P   P", "  P   P", "  P   P","  FF FF")
				.aisle("  FF FF", "       ", "       ", "       ","  FF FF")
				.aisle("  FF FF", "       ", "       ", "       ","  FF FF")
				.where('S', this.selfPredicate())
				.where('P', states(this.getPipeCasingState()))
				.where('H', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
				.where('F', states(this.getFrameState()))
				.where('C', states(this.getCasingState()).setMinGlobalLimited(50).or(this.autoAbilities()))
				.build();
	}
	public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
		return Textures.STABLE_TITANIUM_CASING;
	}
	protected IBlockState getFrameState() {
		return MetaBlocks.FRAMES.get(Materials.Titanium).getBlock(Materials.Titanium);
	}
	protected IBlockState getCasingState() {
		return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE);
	}
	protected IBlockState getPipeCasingState() {
		return MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.TITANIUM_TURBINE_CASING);
	}

	public IOpticalComputationProvider getComputationProvider() {
		return this.computationProvider;
	}

	@Override
	public SoundEvent getBreakdownSound() {
		return GTSoundEvents.BREAKDOWN_ELECTRICAL;
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected ICubeRenderer getFrontOverlay() {
		return Textures.FUSION_REACTOR_OVERLAY;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public SoundEvent getSound() {
		return GTSoundEvents.ELECTROLYZER;
	}

	@Override
	public void invalidateStructure() {
		super.invalidateStructure();
	}

	@Override
	public boolean hasMufflerMechanics() {
		return false;
	}

	@Override
	public boolean canBeDistinct() {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, World world, @Nonnull List<String> tooltip, boolean advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		tooltip.add(I18n.format("gtqtspace.machine.cosmic_ray_detector.tooltip.1"));
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			tooltip.add(I18n.format("gtqtspace.machine.cosmic_ray_detector.tooltip_shift.1"));
			tooltip.add(I18n.format("gtqtspace.machine.cosmic_ray_detector.tooltip_shift.2"));
		} else {
			tooltip.add(I18n.format("gregtech.tooltip.hold_shift"));
		}
	}

	protected class CosmicRayDetectorLogic extends ComputationRecipeLogic {
		public CosmicRayDetectorLogic(RecipeMapMultiblockController tileEntity) {
			super(tileEntity, ComputationType.SPORADIC);
		}
	}
}
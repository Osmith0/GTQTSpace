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
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps;
import keqing.gtqtspace.api.recipes.properties.StarProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.*;

public class MetaTileEntityCosmicRayDetector extends RecipeMapMultiblockController implements IOpticalComputationReceiver {
	int tier = 1;
	private IOpticalComputationProvider computationProvider;

	public MetaTileEntityCosmicRayDetector(ResourceLocation metaTileEntityId) {
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

	@Override
	public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
		if (recipe.getProperty(StarProperty.getInstance(), 0) <= tier) {
			return super.checkRecipe(recipe, consumeIfSuccess);
		}
		return false;
	}

	@Override
	public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
		return new MetaTileEntityCosmicRayDetector(metaTileEntityId);
	}

	@Override
	protected BlockPattern createStructurePattern() {
		return FactoryBlockPattern.start()
				.aisle(
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"      XXX      ",
						"               ")
				.aisle(
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"      XXX      ",
						"    XX   XX    ",
						"               ")
				.aisle(
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"       X       ",
						"    XXX XXX    ",
						"   X       X   ",
						"               ")
				.aisle(
						"      CCC      ",
						"      CCC      ",
						"      CCC      ",
						"               ",
						"               ",
						"       C       ",
						"     XXXXX     ",
						"   XX     XX   ",
						"  X         X  ",
						"               ")
				.aisle(
						"     CCCCC     ",
						"     C   C     ",
						"     C   C     ",
						"      CCC      ",
						"      CCC      ",
						"     CCCCC     ",
						"    XXXXXXX    ",
						"  XX       XX  ",
						" X           X ",
						"               ")
				.aisle(
						"    CCCCCCC    ",
						"    C     C    ",
						"    C     C    ",
						"     C   C     ",
						"     C   C     ",
						"    CCXXXCC    ",
						"   XXX   XXX   ",
						"  X         X  ",
						" X           X ",
						"               ")
				.aisle(
						"   CCCCCCCCC   ",
						"   C   E   C   ",
						"   C       C   ",
						"    C     C    ",
						"    C  F  C    ",
						"    CXXXXXC    ",
						"   XX     XX   ",
						" XX         XX ",
						"X             X",
						"               ")
				.aisle(
						"   CCCCCCCCC   ",
						"   C  ELE  C   ",
						"   C   L   C   ",
						"    C  L  C    ",
						"    C FLF C    ",
						"   CCXXEXXCC   ",
						"  XXX  T  XXX  ",
						" X     T     X ",
						"X      T      X",
						"       T       ")
				.aisle(
						"   CCCCCCCCC   ",
						"   C   E   C   ",
						"   C       C   ",
						"    C     C    ",
						"    C  F  C    ",
						"    CXXXXXC    ",
						"   XX     XX   ",
						" XX         XX ",
						"X             X",
						"               ")
				.aisle(
						"    CCCCCCC    ",
						"    C     C    ",
						"    C     C    ",
						"     C   C     ",
						"     C   C     ",
						"    CCXXXCC    ",
						"   XXX   XXX   ",
						"  X         X  ",
						" X           X ",
						"               ")
				.aisle(
						"     CCCCC     ",
						"     C   C     ",
						"     C   C     ",
						"      CCC      ",
						"      CCC      ",
						"     CCCCC     ",
						"    XXXXXXX    ",
						"  XX       XX  ",
						" X           X ",
						"               ")
				.aisle(
						"      CPC      ",
						"      CSC      ",
						"      CCC      ",
						"               ",
						"               ",
						"       C       ",
						"     XXXXX     ",
						"   XX     XX   ",
						"  X         X  ",
						"               ")
				.aisle(
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"       X       ",
						"    XXX XXX    ",
						"   X       X   ",
						"               ")
				.aisle(
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"      XXX      ",
						"    XX   XX    ",
						"               ")
				.aisle(
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"               ",
						"      XXX      ",
						"               ")
				.where('S', selfPredicate())
				.where('C', states(getCasingAState()).or(autoAbilities()))
				.where('P', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
				.where('X', states(getFrameState()))
				.where('E', states(getFrameState()))
				.where('F', states(getFrameState()))
				.where('L', states(getFrameState()))
				.where('T', states(getFrameState()))
				.where(' ', any())
				.build();
	}

	private static IBlockState getCasingAState() {
		return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
	}

	private static IBlockState getFrameState() {
		return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
		return Textures.SOLID_STEEL_CASING;
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

	public boolean checkNaturalLighting() {

		if (!this.getWorld().isDaytime())
			return false;
		for (BlockPos pos : BlockPos.getAllInBox(this.getPos().up(8).offset(this.frontFacing.rotateY(), 3),
				this.getPos().up(8).offset(this.getFrontFacing().rotateYCCW(), 3).offset(this.getFrontFacing().getOpposite(), 6))) {
			if (!this.getWorld().canSeeSky(pos.up())) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected void addDisplayText(List<ITextComponent> textList) {
		super.addDisplayText(textList);
		if (this.isStructureFormed()) {
			textList.add(new TextComponentTranslation("gtqtspace.machine.cosmic_ray_detector.altitude", this.getPos().getY()));
			textList.add(new TextComponentTranslation("gtqtspace.machine.cosmic_ray_detector.can_see_sky." + checkNaturalLighting()));
		}
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
		tooltip.add(I18n.format("探测等级——1"));
	}

	protected class CosmicRayDetectorLogic extends ComputationRecipeLogic {
		public CosmicRayDetectorLogic(RecipeMapMultiblockController tileEntity) {
			super(tileEntity, ComputationType.SPORADIC);
		}

		@Override
		protected boolean canProgressRecipe() {
			return super.canProgressRecipe() && checkNaturalLighting();
		}


	}
}
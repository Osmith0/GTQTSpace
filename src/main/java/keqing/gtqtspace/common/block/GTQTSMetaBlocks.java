package keqing.gtqtspace.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import keqing.gtqtspace.common.block.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public class GTQTSMetaBlocks {
	public static GTQTSpaceElevatorCasing spaceElevatorCasing;
	public static GTQTUpdateCasing updateCasing;
	public static GTQTSMultiblockCasing multiblockCasing;
	public static GTQTSMultiblockCasing1 multiblockCasing1;
	public static GTQTSMoonBlock moonBlock;
	public static GTQTSMarsBlock marsBlock;
	public static GTQTSVenusBlock venusBlock;

	public static final EnumMap<GTQTSStoneVariantBlock.StoneVariant, GTQTSStoneVariantBlock> GTQTS_STONE_BLOCKS = new EnumMap<>(GTQTSStoneVariantBlock.StoneVariant.class);
	private GTQTSMetaBlocks() {
	}

	public static void init() {
		spaceElevatorCasing = new GTQTSpaceElevatorCasing();
		spaceElevatorCasing.setRegistryName("space_elevator");

		updateCasing = new GTQTUpdateCasing();
		updateCasing.setRegistryName("update_core");

		multiblockCasing = new GTQTSMultiblockCasing();
		multiblockCasing.setRegistryName("multiblock_casing");

		multiblockCasing1 = new GTQTSMultiblockCasing1();
		multiblockCasing1.setRegistryName("multiblock_casing1");

		moonBlock = new GTQTSMoonBlock();
		moonBlock.setRegistryName("moon_block");

		marsBlock = new GTQTSMarsBlock();
		marsBlock.setRegistryName("mars_block");

		venusBlock = new GTQTSVenusBlock();
		venusBlock.setRegistryName("venus_block");

		for (GTQTSStoneVariantBlock.StoneVariant shape : GTQTSStoneVariantBlock.StoneVariant.values()) {
			GTQTS_STONE_BLOCKS.put(shape, new GTQTSStoneVariantBlock(shape));
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		registerItemModel(spaceElevatorCasing);
		registerItemModel(updateCasing);
		registerItemModel(multiblockCasing);
		registerItemModel(multiblockCasing1);
		registerItemModel(moonBlock);
		registerItemModel(marsBlock);
		registerItemModel(venusBlock);

		for (GTQTSStoneVariantBlock block : GTQTS_STONE_BLOCKS.values())
			registerItemModel(block);
	}

	@SideOnly(Side.CLIENT)
	private static void registerItemModel(Block block) {
		for (IBlockState state : block.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
					block.getMetaFromState(state),
					new ModelResourceLocation(block.getRegistryName(),
							MetaBlocks.statePropertiesToString(state.getProperties())));
		}
	}
}

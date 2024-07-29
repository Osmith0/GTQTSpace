package keqing.gtqtspace.common.block;

import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtspace.common.block.blocks.BlockPortal;
import keqing.gtqtspace.common.block.blocks.GTQTSpaceElevator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GTQTSMetaBlocks {
	public static GTQTSpaceElevator SPACE_ELEVATOR;
	public static final BlockPortal portal= new BlockPortal();
	private GTQTSMetaBlocks() {
	}

	public static void init() {
		SPACE_ELEVATOR = new GTQTSpaceElevator();
		SPACE_ELEVATOR.setRegistryName("space_elevator");

	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		registerItemModel(SPACE_ELEVATOR);
		registerItemModel(portal);
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

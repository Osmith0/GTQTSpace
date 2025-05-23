package keqing.gtqtspace.common;

import gregtech.api.block.VariantItemBlock;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTStoneVariantBlock;
import keqing.gtqtspace.api.recipes.properties.SEProperty;
import keqing.gtqtspace.api.recipes.properties.StarProperty;
import keqing.gtqtspace.api.unifications.ore.GTQTSStoneTypes;
import keqing.gtqtspace.api.utils.GTQTSLog;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import keqing.gtqtspace.loaders.OreDictionaryLoader;
import keqing.gtqtspace.loaders.recipes.GTQTSRecipesManager;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

@Mod.EventBusSubscriber(
		modid = "gtqtspace"
)
public class CommonProxy {

	public static final CreativeTabs GTQTSpace_TAB = new CreativeTabs("gtqtspace_core") {
		@Override
		public ItemStack createIcon() {
			return GTQTSMetaItems.MINING_DRONE_MAX.getStackForm();
		}
	};
	public static final CreativeTabs Ship_TAB = new CreativeTabs("gtqtspace_core_ship") {
		@Override
		public ItemStack createIcon() {
			return GTQTSMetaItems.ENGIN_MODEL_I.getStackForm();
		}
	};
	public void preLoad() {
		if (Loader.isModLoaded("pollution")) {
			GTQTSStoneTypes.init(3);
		}
		else GTQTSStoneTypes.init(0);
	}

	public void init() throws IOException {
		OreDictionaryLoader.init();
		GTQTSRecipesManager.init();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		GTQTSLog.logger.info("Registering blocks...");
		IForgeRegistry<Block> registry = event.getRegistry();
        /*
        在此处注册方块
        例子：
        registry.register(方块实例);
        在注册MetaBlock时用到
        */
		registry.register(GTQTSMetaBlocks.spaceElevatorCasing);
		registry.register(GTQTSMetaBlocks.updateCasing);
		registry.register(GTQTSMetaBlocks.multiblockCasing);
		registry.register(GTQTSMetaBlocks.multiblockCasing1);
		registry.register(GTQTSMetaBlocks.moonBlock);
		registry.register(GTQTSMetaBlocks.marsBlock);
		registry.register(GTQTSMetaBlocks.venusBlock);

		for (GTQTSStoneVariantBlock block : GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.values()) registry.register(block);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		GTQTSLog.logger.info("Registering Items...");
		IForgeRegistry<Item> registry = event.getRegistry();
        /*
        在此处注册方块的物品
        例子：
        registry.register(createItemBlock(方块实例, VariantItemBlock::new));
        在注册MetaBlock时用到
        */
		registry.register(createItemBlock(GTQTSMetaBlocks.spaceElevatorCasing, VariantItemBlock::new));
		registry.register(createItemBlock(GTQTSMetaBlocks.updateCasing, VariantItemBlock::new));
		registry.register(createItemBlock(GTQTSMetaBlocks.multiblockCasing, VariantItemBlock::new));
		registry.register(createItemBlock(GTQTSMetaBlocks.multiblockCasing1, VariantItemBlock::new));
		registry.register(createItemBlock(GTQTSMetaBlocks.moonBlock, VariantItemBlock::new));
		registry.register(createItemBlock(GTQTSMetaBlocks.marsBlock, VariantItemBlock::new));
		registry.register(createItemBlock(GTQTSMetaBlocks.venusBlock, VariantItemBlock::new));

		for (GTQTSStoneVariantBlock block : GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.values())
			registry.register(createItemBlock(block, VariantItemBlock::new));
	}

	private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
		ItemBlock itemBlock = producer.apply(block);
		itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
		return itemBlock;
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		GTQTSLog.logger.info("Registering recipes...");

		for (int i = 1; i <= 7; i++) SEProperty.registeredMotor(i, String.valueOf(i));

		StarProperty.registeredNB(1, "射电望远镜-1级");
		StarProperty.registeredNB(2, "远轨道卫星探测-2级");
		StarProperty.registeredNB(3, "大型轨道望远镜-3级");
		StarProperty.registeredNB(4, "太空探测阵列-4级");
		StarProperty.registeredNB(5, "引力波探测-5级");
		StarProperty.registeredNB(6, "不知道-6级");
	}
}
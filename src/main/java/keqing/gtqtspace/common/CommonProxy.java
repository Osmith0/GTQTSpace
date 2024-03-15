package keqing.gtqtspace.common;

import keqing.gtqtspace.api.utils.GTQTSLog;
import keqing.gtqtspace.common.items.GTQTSMetaItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

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
    }
    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GTQTSLog.logger.info("Registering recipes...");
    }
}
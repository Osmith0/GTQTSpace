package keqing.gtqtspace.loaders;

import gregtech.api.unification.OreDictUnifier;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSStoneVariantBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {
    public static void init() {
        loadStoneOredict();
    }

    public static void loadStoneOredict() {
        for (GTQTSStoneVariantBlock.StoneType type : GTQTSStoneVariantBlock.StoneType.values()) {
            ItemStack smooth = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.SMOOTH).getItemVariant(type);
            ItemStack cobble = GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.COBBLE).getItemVariant(type);
            OreDictUnifier.registerOre(smooth, type.getOrePrefix(), type.getMaterial());
            OreDictionary.registerOre("stone", smooth);
            OreDictionary.registerOre("cobblestone", cobble);
            OreDictionary.registerOre("stoneCobble", cobble);
        }

    }
}

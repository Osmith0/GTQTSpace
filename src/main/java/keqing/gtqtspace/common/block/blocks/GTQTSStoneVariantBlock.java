package keqing.gtqtspace.common.block.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantBlock;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtspace.api.unifications.GTQTSpaceMaterials;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Random;

public class GTQTSStoneVariantBlock extends VariantBlock<GTQTSStoneVariantBlock.StoneType> {
    private static final PropertyEnum<GTQTSStoneVariantBlock.StoneType> PROPERTY = PropertyEnum.create("variant", GTQTSStoneVariantBlock.StoneType.class);
    private final GTQTSStoneVariantBlock.StoneVariant stoneVariant;

    public GTQTSStoneVariantBlock(@Nonnull GTQTSStoneVariantBlock.StoneVariant stoneVariant) {
        super(Material.ROCK);
        this.stoneVariant = stoneVariant;
        this.setRegistryName(stoneVariant.id);
        this.setTranslationKey(stoneVariant.translationKey);
        this.setHardness(stoneVariant.hardness);
        this.setResistance(stoneVariant.resistance);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.getState(GTQTSStoneVariantBlock.StoneType.MOON));
        this.setCreativeTab(GregTechAPI.TAB_GREGTECH_DECORATIONS);
    }

    @Nonnull
    protected BlockStateContainer createBlockState() {
        this.VARIANT = PROPERTY;
        this.VALUES = GTQTSStoneVariantBlock.StoneType.values();
        return new BlockStateContainer(this, this.VARIANT);
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }



    protected boolean canSilkHarvest() {
        return this.stoneVariant == GTQTSStoneVariantBlock.StoneVariant.SMOOTH;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock((Block)(this.stoneVariant == GTQTSStoneVariantBlock.StoneVariant.SMOOTH ? (Block) GTQTSMetaBlocks.GTQTS_STONE_BLOCKS.get(GTQTSStoneVariantBlock.StoneVariant.COBBLE) : this));
    }

    public static enum StoneVariant {
        SMOOTH("gtqts_stone_smooth"),
        COBBLE("gtqts_stone_cobble", 2.0F, 10.0F),
        BRICKS("gtqts_stone_bricks");

        public final String id;
        public final String translationKey;
        public final float hardness;
        public final float resistance;

        private StoneVariant(@Nonnull String id) {
            this(id, id);
        }

        private StoneVariant(@Nonnull String id, @Nonnull String translationKey) {
            this(id, translationKey, 1.5F, 10.0F);
        }

        private StoneVariant(@Nonnull String id, float hardness, float resistance) {
            this(id, id, hardness, resistance);
        }

        private StoneVariant(@Nonnull String id, @Nonnull String translationKey, float hardness, float resistance) {
            this.id = id;
            this.translationKey = translationKey;
            this.hardness = hardness;
            this.resistance = resistance;
        }
    }

    public static enum StoneType implements IStringSerializable {
        MOON("moon", MapColor.GRAY);

        private final String name;
        public final MapColor mapColor;

        private StoneType(@Nonnull String name, @Nonnull MapColor mapColor) {
            this.name = name;
            this.mapColor = mapColor;
        }

        @Nonnull
        public String getName() {
            return this.name;
        }

        public OrePrefix getOrePrefix() {
            switch (this) {
                case MOON:
                    return OrePrefix.stone;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }

        public gregtech.api.unification.material.Material getMaterial() {
            switch (this) {
                case MOON:
                    return GTQTSpaceMaterials.MoonStone;
                default:
                    throw new IllegalStateException("Unreachable");
            }
        }
    }
}
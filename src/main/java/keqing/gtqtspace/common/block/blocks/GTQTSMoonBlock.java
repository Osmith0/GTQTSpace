package keqing.gtqtspace.common.block.blocks;

import gregtech.api.block.VariantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class GTQTSMoonBlock extends VariantBlock<GTQTSMoonBlock.BlockType> {
    public GTQTSMoonBlock() {
        super(Material.IRON);
        this.setTranslationKey("moon_block");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("wrench", 4);
        this.setDefaultState(this.getState(GTQTSMoonBlock.BlockType.MOON_TURF));
    }

    public boolean canCreatureSpawn(@Nonnull IBlockState state,
                                    @Nonnull IBlockAccess world,
                                    @Nonnull BlockPos pos,
                                    @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum BlockType implements IStringSerializable {
        MOON_TURF("moon_turf"),
        MOON_DIRT("moon_dirt");

        private final String name;

        BlockType(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }
}


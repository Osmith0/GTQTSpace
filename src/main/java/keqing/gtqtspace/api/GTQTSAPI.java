package keqing.gtqtspace.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.gtqtcore.api.blocks.IBlockTier;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtspace.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;
import keqing.gtqtspace.common.block.blocks.GTQTSSolarPlate;
import keqing.gtqtspace.common.block.blocks.GTQTSpaceElevator;
import net.minecraft.block.state.IBlockState;

public class GTQTSAPI {
    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_SE_CASING= new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectOpenHashMap<IBlockState, IBlockTier> MAP_SP_CASING= new Object2ObjectOpenHashMap<>();
    public static void init() {
        MAP_SP_CASING.put(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKI),
                new WrappedIntTired(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKI, 1));
        MAP_SP_CASING.put(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKII),
                new WrappedIntTired(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKII, 2));
        MAP_SP_CASING.put(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKIII),
                new WrappedIntTired(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKIII, 3));
        MAP_SP_CASING.put(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKIV),
                new WrappedIntTired(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKIV, 4));
        MAP_SP_CASING.put(GTQTSMetaBlocks.SOLAT_PLATE.getState(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKV),
                new WrappedIntTired(GTQTSSolarPlate.CasingType.SOLAR_PLATE_MKV, 5));

        MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK1),
                new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK1, 1));
        MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK2),
                new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK2, 2));
        MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK3),
                new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK3, 3));
        MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK4),
                new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK4, 4));
        MAP_SE_CASING.put(GTQTSMetaBlocks.SPACE_ELEVATOR.getState(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK5),
                new WrappedIntTired(GTQTSpaceElevator.ElevatorCasingType.MOTOR_CASING_MK5, 5));
    }
}

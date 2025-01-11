package keqing.gtqtspace.world.worldgen;

import keqing.gtqtspace.world.dims.MoonProvider;
import keqing.gtqtspace.world.dims.SpaceStationdProvider;
import net.minecraft.world.DimensionType;

public class GTQTSDimensionType {
    public static DimensionType SPACE_STATION_TYPE;
    public static DimensionType MOON_TYPE;
    public static void init() {
        SPACE_STATION_TYPE = DimensionType.register("SpaceStationWorld", "_void", 50, SpaceStationdProvider.class, false);
        MOON_TYPE = DimensionType.register("Moon", "_plants", 51, MoonProvider.class, false);
    }
}

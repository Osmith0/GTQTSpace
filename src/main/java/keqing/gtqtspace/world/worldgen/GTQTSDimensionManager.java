package keqing.gtqtspace.world.worldgen;

import keqing.gtqtspace.world.dims.MoonProvider;
import keqing.gtqtspace.world.dims.SpaceStationdProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class GTQTSDimensionManager {

    public static int SPACE_STATION_ID;
    public static int MOON_ID;
    public static void init() {
        SPACE_STATION_ID=50;
        DimensionManager.registerDimension(SPACE_STATION_ID, DimensionType.register("SpaceStationWorld", "_void", SPACE_STATION_ID, SpaceStationdProvider.class, false));

        MOON_ID=51;
        DimensionManager.registerDimension(MOON_ID, DimensionType.register("Moon", "_plants", MOON_ID, MoonProvider.class, false));

    }
}

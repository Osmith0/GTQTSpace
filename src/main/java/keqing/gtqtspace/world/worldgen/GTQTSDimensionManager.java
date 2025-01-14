package keqing.gtqtspace.world.worldgen;

import keqing.gtqtspace.world.dims.*;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class GTQTSDimensionManager {

    public static int SPACE_STATION_ID;
    public static int MOON_ID;
    public static int MARS_ID;
    public static int VENUS_ID;
    public static int ASTEROIDS_ID;
    public static void init() {
        SPACE_STATION_ID=50;
        DimensionManager.registerDimension(SPACE_STATION_ID, DimensionType.register("SpaceStationWorld", "_void", SPACE_STATION_ID, SpaceStationdProvider.class, false));

        MOON_ID=51;
        DimensionManager.registerDimension(MOON_ID, DimensionType.register("Moon", "_plants", MOON_ID, MoonProvider.class, false));

        MARS_ID=52;
        DimensionManager.registerDimension(MARS_ID, DimensionType.register("Mars", "_plants", MARS_ID, MarsProvider.class, false));

        VENUS_ID=53;
        DimensionManager.registerDimension(VENUS_ID, DimensionType.register("Venus", "_plants", VENUS_ID, VenusProvider.class, false));

        ASTEROIDS_ID=54;
        DimensionManager.registerDimension(ASTEROIDS_ID, DimensionType.register("Asteroids", "_plants", ASTEROIDS_ID, AsteroidsProvider.class, false));
    }
}

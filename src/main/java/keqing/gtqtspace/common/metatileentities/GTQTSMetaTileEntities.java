package keqing.gtqtspace.common.metatileentities;

import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntityRocketLaunchPad;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntityWindGenerator;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidController;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidDrill;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidSearch;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.AsteroidSystem.AsteroidSolve;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.MetaTileEntityCoreTower;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SolarPlate.MetaTileEntitySolarPlate;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SolarPlate.MetaTileEntitySolarPlateController;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SpaceDockSystem.Dock;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SpaceDockSystem.DockBuilder;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.SpaceStation.SpaceDockSystem.DockManager;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.MetaTileEntitySpaceElevator;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.elevatormodules.MetaTileEntityAssemblerModule;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.elevatormodules.MetaTileEntityMiningModule;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.elevatormodules.MetaTileEntityPumpingModule;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtspace.api.GTQTSValue.gtqtspaceId;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.ASSEMBLER_MODULE_RECIPES;

public class GTQTSMetaTileEntities {

    public static MetaTileEntityCoreTower TRANSPORT;

    public static MetaTileEntitySolarPlate SOLAR_PLATE;
    public static MetaTileEntitySolarPlateController SOLAR_PLATE_CONTROLLER;

    public static AsteroidController ASTEROID_CONTROLLER;
    public static AsteroidSolve ASTEROID_SOLVE;
    public static AsteroidSearch ASTEROID_SEARCH;
    public static AsteroidDrill ASTEROID_DRILL;

    public static DockManager DOCK_MANGER;
    public static DockBuilder DOCK_BUILDER;
    public static Dock DOCK;
    ///////////////////////////////////////////////////
    public static MetaTileEntitySpaceElevator SPACE_ELEVATOR;
    public static MetaTileEntityPumpingModule[] PUMP_MODULE = new MetaTileEntityPumpingModule[3];
    public static MetaTileEntityMiningModule[] MINING_MODULE = new MetaTileEntityMiningModule[3];
    public static MetaTileEntityAssemblerModule[] ASSEMBLER_MODULE = new MetaTileEntityAssemblerModule[3];
    public static MetaTileEntityRocketLaunchPad ROCKET_LAUNCH_PAD;
    public static MetaTileEntityWindGenerator WIND_GENERATOR;

    public static int id = 5100;

    public static void initialization() {
        TRANSPORT = registerMetaTileEntity(5006, new MetaTileEntityCoreTower(gtqtspaceId("transport")));
        SOLAR_PLATE = registerMetaTileEntity(5007, new MetaTileEntitySolarPlate(gtqtspaceId("solar_plate")));
        SOLAR_PLATE_CONTROLLER = registerMetaTileEntity(5008, new MetaTileEntitySolarPlateController(gtqtspaceId("solar_plate_controller")));

        ASTEROID_CONTROLLER = registerMetaTileEntity(5020, new AsteroidController(gtqtspaceId("asteroid_controller")));
        ASTEROID_SOLVE = registerMetaTileEntity(5021, new AsteroidSolve(gtqtspaceId("asteroid_solve")));
        ASTEROID_SEARCH = registerMetaTileEntity(5022, new AsteroidSearch(gtqtspaceId("asteroid_search")));
        ASTEROID_DRILL = registerMetaTileEntity(5023, new AsteroidDrill(gtqtspaceId("asteroid_drill")));

        DOCK_MANGER = registerMetaTileEntity(5025, new DockManager(gtqtspaceId("dock_manager")));
        DOCK_BUILDER = registerMetaTileEntity(5026, new DockBuilder(gtqtspaceId("dock_builder")));
        DOCK = registerMetaTileEntity(5027, new Dock(gtqtspaceId("dock")));

        ROCKET_LAUNCH_PAD = registerMetaTileEntity(5030, new MetaTileEntityRocketLaunchPad(gtqtspaceId("rocket_launch_pad")));
        WIND_GENERATOR = registerMetaTileEntity(5031, new MetaTileEntityWindGenerator(gtqtspaceId("wind_generator")));

        SPACE_ELEVATOR = registerMetaTileEntity(++id, new MetaTileEntitySpaceElevator(gtqtspaceId("space_elevator")));
        PUMP_MODULE[0] = registerMetaTileEntity(++id, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_1"), 6, 1, 2));
        PUMP_MODULE[1] = registerMetaTileEntity(++id, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_2"), 8, 2, 3));
        PUMP_MODULE[2] = registerMetaTileEntity(++id, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_3"), 10, 3, 4));
        MINING_MODULE[0] = registerMetaTileEntity(++id, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_1"), 6, 1, 1, 2));
        MINING_MODULE[1] = registerMetaTileEntity(++id, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_2"), 8, 2, 2, 4));
        MINING_MODULE[2] = registerMetaTileEntity(++id, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_3"), 10, 3, 3, 8));
        ASSEMBLER_MODULE[0] = registerMetaTileEntity(++id, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_1"), ASSEMBLER_MODULE_RECIPES, 9, 1, 1));
        ASSEMBLER_MODULE[1] = registerMetaTileEntity(++id, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_2"), ASSEMBLER_MODULE_RECIPES, 11, 2, 3));
        ASSEMBLER_MODULE[2] = registerMetaTileEntity(++id, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_3"), ASSEMBLER_MODULE_RECIPES, 13, 3, 5));
    }
}

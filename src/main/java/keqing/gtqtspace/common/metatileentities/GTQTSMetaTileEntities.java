package keqing.gtqtspace.common.metatileentities;

import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntitySatelliteAssembler;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.MetaTileEntitySentryArray;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.MetaTileEntitySpaceElevator;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.MetaTileEntitySpaceElevatorExtendView;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.elevatormodules.MetaTileEntityAssemblerModule;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.elevatormodules.MetaTileEntityMiningModule;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.elevator.elevatormodules.MetaTileEntityPumpingModule;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.suvery.MetaTileEntityCosmicRayDetector;
import keqing.gtqtspace.common.metatileentities.multi.multiblock.standard.suvery.MetaTileEntitySatelliteSuvery;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static keqing.gtqtspace.api.GTQTSValue.gtqtspaceId;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.ASSEMBLER_MODULE_RECIPES;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.MINING_MODULE_RECIPES;

public class GTQTSMetaTileEntities {
	public static MetaTileEntityCosmicRayDetector COSMIC_RAY_DETECTOR;
	public static MetaTileEntitySatelliteAssembler SATELLITE_ASSEMBLER;
	public static MetaTileEntitySentryArray SENTRY_ARRAY;
	public static MetaTileEntitySatelliteSuvery SATELLITE_SUVERY;


	///////////////////////////////////////////////////
	public static MetaTileEntitySpaceElevator SPACE_ELEVATOR;
	public static MetaTileEntitySpaceElevatorExtendView VIEW_SPACE_ELEVATOR;
	public static MetaTileEntityPumpingModule[] PUMP_MODULE = new MetaTileEntityPumpingModule[3];
	public static MetaTileEntityMiningModule[] MINING_MODULE = new MetaTileEntityMiningModule[3];
	public static MetaTileEntityAssemblerModule[] ASSEMBLER_MODULE = new MetaTileEntityAssemblerModule[3];

	public static int id = 5100;
	public static void initialization() {
		COSMIC_RAY_DETECTOR = registerMetaTileEntity(5001, new MetaTileEntityCosmicRayDetector(gtqtspaceId("cosmic_ray_detector")));
		SATELLITE_SUVERY = registerMetaTileEntity(5002, new MetaTileEntitySatelliteSuvery(gtqtspaceId("satellite_suvery")));
		SATELLITE_ASSEMBLER = registerMetaTileEntity(5003, new MetaTileEntitySatelliteAssembler(gtqtspaceId("satellite_assembler")));
		SENTRY_ARRAY = registerMetaTileEntity(5004, new MetaTileEntitySentryArray(gtqtspaceId("sentry_array")));

		SPACE_ELEVATOR = registerMetaTileEntity(++id, new MetaTileEntitySpaceElevator(gtqtspaceId("space_elevator")));
		VIEW_SPACE_ELEVATOR = registerMetaTileEntity(++id, new MetaTileEntitySpaceElevatorExtendView(gtqtspaceId("view_space_elevator")));
		PUMP_MODULE[0] = registerMetaTileEntity(++id, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_1"), 9,1,2));
		PUMP_MODULE[1] = registerMetaTileEntity(++id, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_2"), 11,2,3));
		PUMP_MODULE[2] = registerMetaTileEntity(++id, new MetaTileEntityPumpingModule(gtqtspaceId("pump_module_3"), 13,3,4));
		MINING_MODULE[0] = registerMetaTileEntity(++id, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_1"),9,1,1, 2));
		MINING_MODULE[1] = registerMetaTileEntity(++id, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_2"),11,2,2, 4));
		MINING_MODULE[2] = registerMetaTileEntity(++id, new MetaTileEntityMiningModule(gtqtspaceId("mining_module_3"),13,3,3, 8));
		ASSEMBLER_MODULE[0] = registerMetaTileEntity(++id, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_1"), ASSEMBLER_MODULE_RECIPES,9,1,1));
		ASSEMBLER_MODULE[1] = registerMetaTileEntity(++id, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_2"), ASSEMBLER_MODULE_RECIPES,11,2,3));
		ASSEMBLER_MODULE[2] = registerMetaTileEntity(++id, new MetaTileEntityAssemblerModule(gtqtspaceId("assembler_module_3"), ASSEMBLER_MODULE_RECIPES,13,3,5));
	}
}

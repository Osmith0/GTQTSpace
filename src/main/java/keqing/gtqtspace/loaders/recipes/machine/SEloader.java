package keqing.gtqtspace.loaders.recipes.machine;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.unification.material.MarkerMaterials;
import keqing.gtqtcore.api.unification.MaterialHelper;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.CWT;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtspace.common.block.blocks.GTQTSpaceElevatorCasing.ElevatorCasingType.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.*;
import static keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.*;

public class SEloader {
    public static void init() {
        casing();
        mining();
        drilling();
        motor();
        DroneRecipes();
    }
    private static void DroneRecipes() {

        //  LV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,8)
                .input(toolHeadDrill, Steel,16)
                .input(frameGt, Steel,16)
                .input(circuit, MarkerMaterials.Tier.LV,16)
                .input(ROBOT_ARM_LV, 8)
                .input(SENSOR_LV, 8)
                .input(wireGtSingle, MaterialHelper.Superconductor[0], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[0], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_LV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  MV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,8)
                .input(toolHeadDrill, Aluminium,16)
                .input(frameGt, Aluminium,16)
                .input(circuit, MarkerMaterials.Tier.LV,16)
                .input(circuit, MarkerMaterials.Tier.MV,16)
                .input(ROBOT_ARM_MV, 2)
                .input(SENSOR_MV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[1], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[1], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_MV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  HV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,8)
                .input(toolHeadDrill, StainlessSteel,16)
                .input(frameGt, StainlessSteel,16)
                .input(circuit, MarkerMaterials.Tier.LV,16)
                .input(circuit, MarkerMaterials.Tier.MV,16)
                .input(circuit, MarkerMaterials.Tier.HV,16)
                .input(ROBOT_ARM_HV, 2)
                .input(SENSOR_HV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[2], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[2], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_HV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_9.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  EV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .input(toolHeadDrill, Titanium,16)
                .input(frameGt, Titanium,16)
                .input(circuit, MarkerMaterials.Tier.MV,16)
                .input(circuit, MarkerMaterials.Tier.HV,16)
                .input(circuit, MarkerMaterials.Tier.EV,16)
                .input(ROBOT_ARM_EV, 2)
                .input(SENSOR_EV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[3], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[3], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_EV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  IV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .input(toolHeadDrill, TungstenSteel,16)
                .input(frameGt, TungstenSteel,16)
                .input(circuit, MarkerMaterials.Tier.HV,16)
                .input(circuit, MarkerMaterials.Tier.EV,16)
                .input(circuit, MarkerMaterials.Tier.IV,16)
                .input(ROBOT_ARM_IV, 2)
                .input(SENSOR_IV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[4], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[4], 64)
                .fluidInputs(SolderingAlloy.getFluid(L))
                .output(MINING_DRONE_IV)
                .EUt(VA[IV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  LuV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .input(toolHeadDrill, BlackTitanium,16)
                .input(frameGt, BlackTitanium,16)
                .input(circuit, MarkerMaterials.Tier.EV,16)
                .input(circuit, MarkerMaterials.Tier.IV,16)
                .input(circuit, MarkerMaterials.Tier.LuV,16)
                .input(ROBOT_ARM_LuV, 2)
                .input(SENSOR_LuV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[5], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[5], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Cupronickel.getFluid(L))
                .output(MINING_DRONE_LuV)
                .EUt(VA[LuV])
                .duration(2000)
                .scannerResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[EV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  ZPM
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III,8)
                .input(toolHeadDrill, NaquadahAlloy,16)
                .input(frameGt, NaquadahAlloy,16)
                .input(circuit, MarkerMaterials.Tier.IV,16)
                .input(circuit, MarkerMaterials.Tier.LuV,16)
                .input(circuit, MarkerMaterials.Tier.ZPM,16)
                .input(ROBOT_ARM_ZPM, 2)
                .input(SENSOR_ZPM, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[6], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[6], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Kanthal.getFluid(L))
                .output(MINING_DRONE_ZPM)
                .EUt(VA[ZPM])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[LuV])
                        .CWUt(CWT[LuV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III,8)
                .input(toolHeadDrill, Neutronium,16)
                .input(frameGt, Neutronium,16)
                .input(circuit, MarkerMaterials.Tier.LuV,16)
                .input(circuit, MarkerMaterials.Tier.ZPM,16)
                .input(circuit, MarkerMaterials.Tier.UV,16)
                .input(ROBOT_ARM_UV, 2)
                .input(SENSOR_UV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[7], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[7], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Nichrome.getFluid(L))
                .output(MINING_DRONE_UV)
                .EUt(VA[UV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[ZPM])
                        .CWUt(CWT[ZPM]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UHV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III,8)
                .input(toolHeadDrill, Orichalcum,16)
                .input(frameGt, Orichalcum,16)
                .input(circuit, MarkerMaterials.Tier.ZPM,16)
                .input(circuit, MarkerMaterials.Tier.UV,16)
                .input(circuit, MarkerMaterials.Tier.UHV,16)
                .input(ROBOT_ARM_UHV, 2)
                .input(SENSOR_UHV, 2)
                .input(wireGtSingle, MaterialHelper.Superconductor[8], 64)
                .input(wireGtSingle, MaterialHelper.Superconductor[8], 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(RTMAlloy.getFluid(L))
                .output(MINING_DRONE_UHV)
                .EUt(VA[UHV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(DISK_10.getStackForm())
                        .EUt(VA[UV])
                        .CWUt(CWT[UV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        /*
        //  UEV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,8)
                .input(toolHeadDrill, Adamantium,16)
                .input(frameGt, Adamantium,16)
                .input(circuit, MarkerMaterials.Tier.UV,16)
                .input(circuit, MarkerMaterials.Tier.UHV,16)
                .input(circuit, MarkerMaterials.Tier.UEV,16)
                .input(ROBOT_ARM_UEV, 2)
                .input(SENSOR_UEV, 2)
                .input(cableGtSingle, PedotTMA, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(HSSG.getFluid(L))
                .output(MINING_DRONE_UEV)
                .EUt(VA[UEV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(MINING_DRONE_UHV.getStackForm())
                        .EUt(VA[UHV])
                        .CWUt(CWT[UHV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UIV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,8)
                .input(toolHeadDrill, Infinity,16)
                .input(frameGt, Infinity,16)
                .input(circuit, MarkerMaterials.Tier.UHV,16)
                .input(circuit, MarkerMaterials.Tier.UEV,16)
                .input(circuit, MarkerMaterials.Tier.UIV,16)
                .input(ROBOT_ARM_UIV, 2)
                .input(SENSOR_UIV, 2)
                .input(cableGtSingle, Solarium, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Naquadah.getFluid(L))
                .output(MINING_DRONE_UIV)
                .EUt(VA[UIV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(MINING_DRONE_UEV.getStackForm())
                        .EUt(VA[UEV])
                        .CWUt(CWT[UEV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

        //  UXV
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV,8)
                .input(toolHeadDrill, CosmicNeutronium,16)
                .input(frameGt, CosmicNeutronium,16)
                .input(circuit, MarkerMaterials.Tier.UEV,16)
                .input(circuit, MarkerMaterials.Tier.UIV,16)
                .input(circuit, MarkerMaterials.Tier.UXV,16)
                .input(ROBOT_ARM_UXV, 2)
                .input(SENSOR_UXV, 2)
                .input(cableGtSingle, Hypogen, 2)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Trinium.getFluid(L))
                .output(MINING_DRONE_UXV)
                .EUt(VA[UXV])
                .duration(2000)
                .stationResearch(b -> b
                        .researchStack(MINING_DRONE_UIV.getStackForm())
                        .EUt(VA[UIV])
                        .CWUt(CWT[UIV]))
                .cleanroom(CleanroomType.CLEANROOM)
                .buildAndRegister();

         */
    }
    private static void motor() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_IV, 2)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK1))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_LuV, 2)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK2))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_ZPM, 2)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK3))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_UV, 2)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK4))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_V)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_UHV, 2)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(MOTOR_CASING_MK5))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UHV]).buildAndRegister();
    }
    private static void casing() {
        //控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,64)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,32))
                .input(ELECTRIC_PISTON_IV, 64)
                .input(ELECTRIC_MOTOR_IV, 64)
                .input(frameGt, Naquadah, 64)
                .input(frameGt, Naquadah, 64)
                .input(plate, HSSS, 64)
                .input(ring, HSSS, 64)
                .input(stickLong,HSSE,64)
                .input(stickLong,Samarium,64)
                .input(foil,PPB,64)
                .input(foil,PPB,64)
                .input(wireFine,NiobiumTitanium,64)
                .input(wireFine,NiobiumTitanium,64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .output(SPACE_ELEVATOR)
                .fluidInputs(Polybenzimidazole.getFluid(L * 32))
                .fluidInputs(Zylon.getFluid(L * 64))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 64))
                .fluidInputs(UltraGlue.getFluid(L * 64))
                .scannerResearch(b -> b
                        .researchStack(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING))
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I)
                .input(HULL[IV],8)
                .input(frameGt, Naquadah, 2)
                .input(plate, RhodiumPlatedPalladium, 8)
                .input(gearSmall,HSSS,8)
                .input(screw,NanometerBariumTitanate,8)
                .input(foil,Ruridit,8)
                .input(wireFine, Platinum, 16)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[IV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(CIRCUIT_GOOD_I)
                .input(frameGt,PPB,2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,HSSS,8)
                .input(foil,Ruridit,8)
                .input(screw,NanometerBariumTitanate,8)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(INTERNAL_STRUCTURE))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(CIRCUIT_GOOD_I)
                .input(frameGt,HSSS,2)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,PPB,8)
                .input(foil,Ruridit,8)
                .input(screw,NanometerBariumTitanate,8)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(SUPPORT_STRUCTURE))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,4))
                .input(CIRCUIT_GOOD_I)
                .input(FIELD_GENERATOR_IV, 1)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(foil,PPB,2)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(FLOOR))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .input(stickLong, CarbonNanotube, 64)
                .outputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(CABLE_CASING))
                .fluidInputs(Polybenzimidazole.getFluid(L*32))
                .fluidInputs(Zylon.getFluid(L*32))
                .fluidInputs(UltraGlue.getFluid(L*32))
                .duration(1000).EUt(VA[EV]).buildAndRegister();
    }

    private static void mining() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,8)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,8))
                .input(LARGE_MINER)
                .input(ELECTRIC_MOTOR_IV,8)
                .input(plate, NaquadahAlloy, 16)
                .input(foil,PPB,64)
                .input(wireGtSingle, IVSuperconductor, 32)
                .output(MINING_MODULE[0])
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(LARGE_MINER.getStackForm())
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,8))
                .input(ADVANCED_LARGE_MINER)
                .input(ELECTRIC_MOTOR_LuV,8)
                .input(plate, NaquadahAlloy, 16)
                .input(foil,PPB,64)
                .input(wireGtSingle, LuVSuperconductor, 32)
                .output(MINING_MODULE[1])
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(MINING_MODULE[0].getStackForm())
                        .EUt(VA[LuV]))
                .duration(2000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III,8)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,8))
                .input(MINING_MODULE[1])
                .input(ELECTRIC_MOTOR_ZPM,8)
                .input(plate, NaquadahAlloy, 16)
                .input(foil,PPB,64)
                .input(wireGtSingle, ZPMSuperconductor, 32)
                .output(MINING_MODULE[2])
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(MINING_MODULE[1].getStackForm())
                        .EUt(VA[ZPM]))
                .duration(2000).EUt(VA[ZPM]).buildAndRegister();
    }

    private static void drilling() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,8)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,8))
                .input(FLUID_DRILLING_RIG)
                .input(ELECTRIC_PUMP_IV,8)
                .input(plate, NaquadahAlloy, 16)
                .input(foil,PPB,64)
                .input(wireGtSingle, IVSuperconductor, 32)
                .output(PUMP_MODULE[0])
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(FLUID_DRILLING_RIG.getStackForm())
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,8))
                .input(ADVANCED_FLUID_DRILLING_RIG)
                .input(ELECTRIC_PUMP_LuV,8)
                .input(plate, NaquadahAlloy, 16)
                .input(foil,PPB,64)
                .input(wireGtSingle, LuVSuperconductor, 32)
                .output(PUMP_MODULE[1])
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(PUMP_MODULE[0].getStackForm())
                        .EUt(VA[LuV]))
                .duration(2000).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III,8)
                .inputs(GTQTSMetaBlocks.spaceElevatorCasing.getItemVariant(BASIC_CASING,8))
                .input(PUMP_MODULE[1])
                .input(ELECTRIC_PUMP_ZPM,8)
                .input(plate, NaquadahAlloy, 16)
                .input(foil,PPB,64)
                .input(wireGtSingle, ZPMSuperconductor, 32)
                .output(PUMP_MODULE[2])
                .fluidInputs(Polybenzimidazole.getFluid(L * 16))
                .fluidInputs(Zylon.getFluid(L * 8))
                .fluidInputs(TitaniumTungstenCarbide.getFluid(L * 8))
                .fluidInputs(UltraGlue.getFluid(L * 8))
                .scannerResearch(b -> b
                        .researchStack(PUMP_MODULE[1].getStackForm())
                        .EUt(VA[ZPM]))
                .duration(2000).EUt(VA[ZPM]).buildAndRegister();
    }
}

package keqing.gtqtspace.loaders.recipes.machine;

import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtspace.GTQTSpace;
import keqing.gtqtspace.common.block.GTQTSMetaBlocks;

import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.*;
import static keqing.gtqtcore.api.unification.GCYSMaterials.CarbonNanotube;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator.MachineType.*;
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;
import static keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities.PARTICLE_ACCELERATOR;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;
import static keqing.gtqtspace.common.block.blocks.GTQTSpaceElevator.ElevatorCasingType.*;
import static keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.*;

public class SEloader {
    public static void init() {
        casing();
        mining();
        drilling();
        motor();
    }

    private static void motor() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_IV, 2)
                .input(wireGtSingle, IVSuperconductor, 64)
                .input(wireGtSingle, IVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(MOTOR_CASING_MK1))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_LuV, 2)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .input(wireGtSingle, LuVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(MOTOR_CASING_MK2))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[LuV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_III)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_ZPM, 2)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .input(wireGtSingle, ZPMSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(MOTOR_CASING_MK3))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[ZPM]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_IV)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_UV, 2)
                .input(wireGtSingle, UVSuperconductor, 64)
                .input(wireGtSingle, UVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(MOTOR_CASING_MK4))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_V)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(VOLTAGE_COIL_UHV, 2)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .input(wireGtSingle, UHVSuperconductor, 64)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(MOTOR_CASING_MK5))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[UHV]).buildAndRegister();
    }
    private static void casing() {
        //控制器
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,64)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,32))
                .input(ELECTRIC_PISTON_IV, 64)
                .input(ELECTRIC_MOTOR_IV, 64)
                .input(frameGt, NaquadahEnriched, 64)
                .input(frameGt, NaquadahEnriched, 64)
                .input(plate, NaquadahAlloy, 64)
                .input(ring, HSSS, 64)
                .input(gearSmall,HSSG,64)
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
                        .researchStack(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING))
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I)
                .input(HULL[IV],8)
                .input(frameGt, NaquadahEnriched, 2)
                .input(plate, RhodiumPlatedPalladium, 8)
                .input(gearSmall,HSSS,8)
                .input(screw,NanometerBariumTitanate,8)
                .input(foil,Ruridit,8)
                .input(wireFine, Platinum, 16)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING))
                .fluidInputs(Polybenzimidazole.getFluid(L*2))
                .fluidInputs(Zylon.getFluid(L * 4))
                .fluidInputs(NiobiumTitanium.getFluid(L * 4))
                .duration(800).EUt(VA[IV]).buildAndRegister();

        //
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(CIRCUIT_GOOD_I)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gearSmall,NanometerBariumTitanate,2)
                .input(foil,PPB,12)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(INTERNAL_STRUCTURE))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(CIRCUIT_GOOD_I)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(gear,NaquadahEnriched,2)
                .input(stickLong,TungstenSteel,16)
                .input(foil,PPB,12)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(SUPPORT_STRUCTURE))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,4))
                .input(CIRCUIT_GOOD_I)
                .input(FIELD_GENERATOR_IV, 1)
                .input(plate, RhodiumPlatedPalladium, 4)
                .input(foil,PPB,2)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(FLOOR))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .fluidInputs(Zylon.getFluid(L * 2))
                .fluidInputs(NiobiumTitanium.getFluid(L * 2))
                .duration(1000).EUt(VA[EV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(wireFine, CarbonNanotube, 64)
                .input(wireFine, CarbonNanotube, 64)
                .input(wireFine, CarbonNanotube, 64)
                .input(wireFine, CarbonNanotube, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(stickLong, Naquadah, 64)
                .input(wireFine, CarbonNanotube, 64)
                .input(wireFine, CarbonNanotube, 64)
                .input(wireFine, CarbonNanotube, 64)
                .input(wireFine, CarbonNanotube, 64)
                .outputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(CABLE_CASING))
                .fluidInputs(Polybenzimidazole.getFluid(L*32))
                .fluidInputs(Zylon.getFluid(L*32))
                .fluidInputs(UltraGlue.getFluid(L*32))
                .duration(1000).EUt(VA[EV]).buildAndRegister();
    }

    private static void mining() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_I,8)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,8))
                .input(ADVANCED_LARGE_MINER)
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
                        .researchStack(ADVANCED_LARGE_MINER.getStackForm())
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,8))
                .input(MINING_MODULE[0])
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
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,8))
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
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,8))
                .input(ADVANCED_FLUID_DRILLING_RIG)
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
                        .researchStack(ADVANCED_FLUID_DRILLING_RIG.getStackForm())
                        .EUt(VA[IV]))
                .duration(2000).EUt(VA[IV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CIRCUIT_GOOD_II,8)
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,8))
                .input(PUMP_MODULE[0])
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
                .inputs(GTQTSMetaBlocks.SPACE_ELEVATOR.getItemVariant(BASIC_CASING,8))
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

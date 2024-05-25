package keqing.gtqtspace.loaders.recipes.machine;

import gregtech.api.GTValues;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static keqing.gtqtcore.common.items.GTQTMetaItems.DISK_8;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;
import static keqing.gtqtspace.common.items.GTQTSMetaItems.*;
import static keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.COSMIC_RAY_DETECTOR;
import static keqing.gtqtspace.common.metatileentities.GTQTSMetaTileEntities.SATELLITE_SUVERY;

public class StarSuvery {
    public static void init() {

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[3],16)
                .inputs(MetaItems.FIELD_GENERATOR_HV.getStackForm(16))
                .inputs(MetaItems.SENSOR_HV.getStackForm(16))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV, 4)
                .input(OrePrefix.pipeSmallFluid, Aluminium, 4)
                .input(OrePrefix.plate, StainlessSteel, 4)
                .fluidInputs(Polyethylene.getFluid(GTValues.L * 4))
                .output(COSMIC_RAY_DETECTOR)
                .duration(800).EUt(VA[HV]).buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[4],16)
                .inputs(MetaItems.FIELD_GENERATOR_EV.getStackForm(16))
                .inputs(MetaItems.SENSOR_EV.getStackForm(16))
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV, 4)
                .input(OrePrefix.pipeSmallFluid, StainlessSteel, 4)
                .input(OrePrefix.plate, Platinum, 4)
                .fluidInputs(Polyethylene.getFluid(GTValues.L * 4))
                .output(SATELLITE_SUVERY)
                .duration(800).EUt(VA[EV]).buildAndRegister();


        //test

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.HV, 1)
                .input(FIELD_GENERATOR_HV, 1)
                .input(EMITTER_HV, 1)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER1)
                .NB(1)
                .CWUt(24)
                .EUt(480)
                .duration(2000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .input(FIELD_GENERATOR_EV, 2)
                .input(EMITTER_EV, 2)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER2)
                .NB(2)
                .CWUt(96)
                .EUt(480*4)
                .duration(2000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.IV, 4)
                .input(FIELD_GENERATOR_IV, 4)
                .input(EMITTER_IV, 4)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER3)
                .NB(3)
                .CWUt(384)
                .EUt(480*16)
                .duration(2000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.LuV, 8)
                .input(FIELD_GENERATOR_LuV, 8)
                .input(EMITTER_LuV, 8)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER4)
                .NB(4)
                .CWUt(1536)
                .EUt(480*64)
                .duration(2000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(FIELD_GENERATOR_ZPM, 16)
                .input(EMITTER_ZPM, 16)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER5)
                .NB(5)
                .CWUt(6144)
                .EUt(480*256)
                .duration(2000)
                .buildAndRegister();

        STAR_SURVEY.recipeBuilder()
                .input(circuit, MarkerMaterials.Tier.UHV, 32)
                .input(FIELD_GENERATOR_UHV, 32)
                .input(EMITTER_UHV, 32)
                .input(plateDouble, Platinum, 16)
                .output(COMPUTERTIER6)
                .NB(6)
                .CWUt(24576)
                .EUt(480*1024)
                .duration(2000)
                .buildAndRegister();

    }
}

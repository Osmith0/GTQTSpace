package keqing.gtqtspace.loaders.recipes.machine;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.ore;
import static keqing.gtqtspace.api.recipes.GTQTScoreRecipeMaps.*;

public class SEloader {
    public static void init() {
        casing();
        mining();
        drilling();
    }

    private static void casing() {
    }

    private static void mining() {
        SPACE_ELEVATOR_MINING_MODULE.recipeBuilder()
                .input(dust,Iron)
                .fluidInputs(Water.getFluid(14))
                .output(ore,Naquadah)
                .Motor(3)
                .CWUt(1000)
                .EUt(7680)
                .duration(2000*64)
                .buildAndRegister();
    }

    private static void drilling() {
        SPACE_ELEVATOR_DRILLING_MODULE.recipeBuilder()
                .input(dust,Iron)
                .fluidInputs(Water.getFluid(14))
                .fluidOutputs(OilHeavy.getFluid(114))
                .Motor(3)
                .CWUt(1000)
                .EUt(7680)
                .duration(2000*64)
                .buildAndRegister();
    }
}

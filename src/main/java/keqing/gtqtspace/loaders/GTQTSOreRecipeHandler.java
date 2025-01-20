package keqing.gtqtspace.loaders;

import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import keqing.gtqtspace.api.unifications.ore.GTQTSOrePrefix;

public class GTQTSOreRecipeHandler {
    public static void init(){
        GTQTSOrePrefix.oreMoon.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        GTQTSOrePrefix.oreMars.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        GTQTSOrePrefix.oreVenus.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

    }
}
